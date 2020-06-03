/* Quarter-Long Virtual World Project - EntityVein Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;
import java.util.Optional;
import java.util.Random;

// external library imports
import processing.core.PImage;

public class EntityVein extends EntityActiveBase
{
    // static variables
    private static final String ORE_ID_PREFIX = "ore -- ";
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;

    // instance variables
    private Random rand = new Random();

    // constructor
    public EntityVein(
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
        Optional<Point> openPt = world.findOpenAround(super.getPosition());

        if (openPt.isPresent()) 
        {
            EntityOre ore = Factory.createEntityOre(
                ORE_ID_PREFIX + super.getId(), 
                openPt.get(),
                ORE_CORRUPT_MIN + rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                imageStore.getImageList(Functions.ORE_KEY));
            
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(
            this,
            Factory.createActionActivity(this, world, imageStore),
            super.getActionPeriod());
    }
}