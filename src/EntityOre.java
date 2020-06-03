/* Quarter-Long Virtual World Project - EntityOre Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;
import java.util.Random;

// external library imports
import processing.core.PImage;

public class EntityOre extends EntityActiveBase
{
    // static variables
    private static final String BLOB_KEY = "blob";
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final int BLOB_ANIMATION_MIN = 50;
    private static final int BLOB_ANIMATION_MAX = 150;

    // instance variables
    private Random rand = new Random();

    // constructor
    public EntityOre(
        String id,
        Point position,
        List<PImage> images,
        int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }

    // functional implementation of EntityActiveBase abstract classes
    public void executeActivity(
        WorldModel world,
        ImageStore imageStore,
        EventScheduler scheduler)
    {
        Point pos = super.getPosition();

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        EntityOreBlob blob = Factory.createEntityOreBlob(
            super.getId() + BLOB_ID_SUFFIX, 
            pos,
            super.getActionPeriod() / BLOB_PERIOD_SCALE,
            BLOB_ANIMATION_MIN + rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
            imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }
}