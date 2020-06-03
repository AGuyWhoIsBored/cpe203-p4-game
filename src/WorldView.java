/* Quarter-Long Virtual World Project - WorldView Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

 // java standard library imports
import java.util.Optional;

// external library imports
import processing.core.PApplet;
import processing.core.PImage;

public final class WorldView
{
    // instance variables
    private PApplet screen;
    private WorldModel world;
    private int tileWidth;
    private int tileHeight;
    private Viewport viewport;

    // constructor
    public WorldView(
        int numRows,
        int numCols,
        PApplet screen,
        WorldModel world,
        int tileWidth,
        int tileHeight)
    {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    // public functional methods
    public void drawViewport() 
    {
        drawBackground();
        drawEntities();
    }

    public void shiftView(int colDelta, int rowDelta) 
    {
        int newCol = clamp(viewport.getCol() + colDelta, 0, world.getNumCols() - viewport.getNumCols());
        int newRow = clamp(viewport.getRow() + rowDelta, 0, world.getNumRows() - viewport.getNumRows());
        viewport.shift(newCol, newRow);
    }

    // private functional methods
    private void drawBackground() 
    {
        for (int row = 0; row < viewport.getNumRows(); row++) 
        {
            for (int col = 0; col < viewport.getNumCols(); col++) 
            {
                Point worldPoint = viewport.viewportToWorld(col, row);
                Optional<PImage> image = world.getBackgroundImage(worldPoint);
                if (image.isPresent()) 
                {
                    screen.image(image.get(), col * tileWidth, row * tileHeight);
                }
            }
        }
    }
    
    private void drawEntities() 
    {
        for (IEntity entity : world.getEntities()) 
        {
            Point pos = entity.getPosition();

            if (viewport.contains(pos)) 
            {
                Point viewPoint = viewport.worldToViewport(pos.getX(), pos.getY());
                screen.image(
                    entity.getCurrentImage(),
                    viewPoint.getX() * tileWidth,
                    viewPoint.getY() * tileHeight);
            }
        }
    }

    private static int clamp(int value, int low, int high) 
    {
        return Math.min(high, Math.max(value, low));
    }
}
