/* Quarter-Long Virtual World Project - EntityActiveBase Abstract Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;

// external library imports
import processing.core.PImage;

public abstract class EntityActiveBase extends EntityBase implements IEntityActive
{
    // instance variables
    private int actionPeriod;

    public EntityActiveBase(
        String id,
        Point position, 
        List<PImage> images,
        int actionPeriod)
    {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
    }

    // accessor methods
    protected int getActionPeriod() { return actionPeriod; }

    // pass down implementation requirement of EntityActive interface to child classes
    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler eventScheduler);

    // common implementation
    public void scheduleActions(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore)
    {
        eventScheduler.scheduleEvent(
            this,
            Factory.createActionActivity(this, world, imageStore),
            actionPeriod);
    }
}