/* Quarter-Long Virtual World Project - VirtualWorld Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

 // java standard library imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.Optional;

// external library imports
import processing.core.PApplet;
import processing.core.PImage;

public final class VirtualWorld extends PApplet
{
    // static variables
    private static final int TIMER_ACTION_PERIOD = 100;

    private static final int VIEW_WIDTH = 640;
    private static final int VIEW_HEIGHT = 480;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private static final int WORLD_WIDTH_SCALE = 2;
    private static final int WORLD_HEIGHT_SCALE = 2;

    private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

    private static final String IMAGE_LIST_FILE_NAME = "imagelist";
    private static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 0x808080;

    private static final String LOAD_FILE_NAME = "world.sav";

    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.10;

    private static double timeScale = 1.0;

    // instance variables
    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;
    private long nextTime;
    private Random rand = new Random();

    // PApplet setup methods
    public void settings() { size(VIEW_WIDTH, VIEW_HEIGHT); }

    // Processing entry point for "sketch" setup.
    public void setup() 
    {
        this.imageStore = new ImageStore(createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(
            WORLD_ROWS,
            WORLD_COLS,
            createDefaultBackground(imageStore));
        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH, TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);

        loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
        loadWorld(world, LOAD_FILE_NAME, imageStore);

        scheduleActions(world, scheduler, imageStore);

        nextTime = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public void draw() 
    {
        long time = System.currentTimeMillis();
        if (time >= nextTime) 
        {
            this.scheduler.updateOnTime(time);
            nextTime = time + TIMER_ACTION_PERIOD;
        }

        view.drawViewport();
    }

    public void keyPressed() 
    {
        if (key == CODED) 
        {
            int dx = 0;
            int dy = 0;

            switch (keyCode) 
            {
                case UP:    dy = -1; break;
                case DOWN:  dy = 1;  break;
                case LEFT:  dx = -1; break;
                case RIGHT: dx = 1;  break;
            }
            view.shiftView(dx, dy);
        }
    }

    // Processing fires this event whenever mouse is pressed on screen
    public void mousePressed()
    {
        Point clickedTile = new Point((mouseX / TILE_WIDTH) + view.getViewport().getCol(), (mouseY / TILE_HEIGHT) + view.getViewport().getRow());

        // update background at clicked tile (we would want to do this in a radius)
        for (int i = -1; i<2; i++){
            for (int j = -1; j<2; j++){
                if(rand.nextInt(2)>0) {
                    world.setBackground(new Point(clickedTile.getX() + i, clickedTile.getY() + j), new Background("background_CP", imageStore.getImageList("background_CP")));
                }
            }
        }

        // standard animationPeriod seems to be 100ms
        EntityJeff jeffTest = Factory.createJeff("jeff", new Point(clickedTile.getX() - 1, clickedTile.getY()), imageStore.getImageList("jeff"), 850, 0);
        EntityCPStudent cpStudentTest = Factory.createCPStudent("cpstudent", new Point(clickedTile.getX(), clickedTile.getY() + 1), imageStore.getImageList("cpstudent"), 500, 100);

        // try to spawn entities in
        try 
        {
            world.tryAddEntity(jeffTest);

            // have to register actions with eventScheduler in order to enable actions & animations
            // only do if we're able to successfully add entities in - or else we'll see weird behavior!
            jeffTest.scheduleActions(scheduler, world, imageStore);
            cpStudentTest.scheduleActions(scheduler, world, imageStore);

            // transform all miners in 7x7 radius to CPstudents
            for (int i = -3; i < 4; i++)
            {
                for (int j = -3; j < 4; j++)
                {
                    Point p = new Point(clickedTile.getX() + i, clickedTile.getY() + j);
                    Optional<IEntity> e = world.getOccupant(p);
                    if (e.isPresent() && (e.get().getClass() == EntityMinerNotFull.class || e.get().getClass() == EntityMinerFull.class))
                    {
                        // remove miner
                        world.removeEntity(e.get());
                        scheduler.unscheduleAllEvents(e.get());

                        // add CPstudent
                        EntityCPStudent cpStudent = Factory.createCPStudent("cpstudent", p, imageStore.getImageList("cpstudent"), 500, 100);
                        world.addEntity(cpStudent);
                        cpStudent.scheduleActions(scheduler, world, imageStore);
                    }
                }
            }

        } 
    catch (Exception e) { /* System.out.println(e.getMessage()); */ } // simply catch and don't do anything if pos is occupiped
    }

    // main game entry point
    public static void main(String[] args) 
    {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class);
    }

    // private functional methods
    private static Background createDefaultBackground(ImageStore imageStore) 
    {
        return new Background(DEFAULT_IMAGE_NAME, imageStore.getImageList(DEFAULT_IMAGE_NAME));
    }

    private static PImage createImageColored(int width, int height, int color) 
    {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) 
        { 
            img.pixels[i] = color; 
        }
        img.updatePixels();
        return img;
    }

    private static void loadImages(String filename, ImageStore imageStore, PApplet screen)
    {
        try 
        {
            Scanner in = new Scanner(new File(filename));
            Functions.loadImages(in, imageStore, screen);
        }
        catch (FileNotFoundException e) 
        { 
            System.err.println(e.getMessage()); 
        }
    }

    private static void loadWorld(WorldModel world, String filename, ImageStore imageStore)
    {
        try 
        {
            Scanner in = new Scanner(new File(filename));
            Functions.load(in, world, imageStore);
        }
        catch (FileNotFoundException e) 
        { 
            System.err.println(e.getMessage()); 
        }
    }

    private static void scheduleActions(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        for (IEntity entity : world.getEntities()) 
        {
            if (entity instanceof IEntityActive)
            {
                ((IEntityActive)entity).scheduleActions(scheduler, world, imageStore);
            }
        }
    }

    private static void parseCommandLine(String[] args) 
    {
        for (String arg : args) 
        {
            switch (arg) 
            {
                case FAST_FLAG:    timeScale = Math.min(FAST_SCALE, timeScale);    break;
                case FASTER_FLAG:  timeScale = Math.min(FASTER_SCALE, timeScale);  break;
                case FASTEST_FLAG: timeScale = Math.min(FASTEST_SCALE, timeScale); break;
            }
        }
    }
}
