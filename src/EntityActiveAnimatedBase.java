/* Quarter-Long Virtual World Project - EntityActiveAnimated Abstract Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;

// external library imports
import processing.core.PImage;

public abstract class EntityActiveAnimatedBase extends EntityActiveBase implements IEntityAnimated
{
    // instance variables
    private int animationPeriod;

    // constructor
    public EntityActiveAnimatedBase(
        String id,
        Point position, 
        List<PImage> images,
        int actionPeriod,
        int animationPeriod
    )
    {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    // accessor methods
    public int getAnimationPeriod() { return animationPeriod; }

    // pass down implementation requirement of EntityActive interface to child classes
    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler eventScheduler);

    // common implementation
    public void scheduleActions(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore)
    {
        super.scheduleActions(eventScheduler, world, imageStore);
        eventScheduler.scheduleEvent(
            this,
            Factory.createActionAnimation(this, 0),
            getAnimationPeriod());
    }
    public void nextImage() { super.setImageIndex((super.getImageIndex() + 1) % super.getImages().size()); }
}