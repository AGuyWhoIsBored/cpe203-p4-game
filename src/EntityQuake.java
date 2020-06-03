/* Quarter-Long Virtual World Project - EntityQuake Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;

// external library imports
import processing.core.PImage;

public class EntityQuake extends EntityActiveAnimatedBase
{
    // constructor
    public EntityQuake(
        String id,
        Point position,
        List<PImage> images,
        int actionPeriod,
        int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(
        WorldModel world,
        ImageStore imageStore,
        EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }
}