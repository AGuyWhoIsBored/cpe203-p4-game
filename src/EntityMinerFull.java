/* Quarter-Long Virtual World Project - EntityMinerFull Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;
import java.util.Optional;

// external library imports
import processing.core.PImage;

public class EntityMinerFull extends EntityMovableBase
{
    // instance variables
    private int resourceLimit;

    // constructor
    public EntityMinerFull(
        String id,
        Point position,
        List<PImage> images,
        int resourceLimit,
        int actionPeriod,
        int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }

    // implemented abstract methods to complete parent implementation of these methods
    protected long[] _executeActivityHelper(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<IEntity> fullTarget = world.findNearest(super.getPosition(), EntityBlacksmith.class);

        if (fullTarget.isPresent() && move(world, fullTarget.get(), scheduler))
        {
            transformFull(world, scheduler, imageStore);
            return new long[] { 0, 0 };
        }
        else 
        {
            return new long[] { 1, super.getActionPeriod() };
        } 
    }

    protected boolean _moveHelper(WorldModel world, IEntity target, EventScheduler scheduler)
    {
        return true;
    }
    
    protected boolean _nextPositionHelper(WorldModel world, Point newPos)
    {
        return world.isOccupied(newPos);
    }

    // private helper methods for functional implementation
    private void transformFull(
        WorldModel world,
        EventScheduler scheduler,
        ImageStore imageStore)
    {
        EntityMinerNotFull miner = Factory.createEntityMinerNotFull(
            super.getId(), 
            resourceLimit,
            super.getPosition(), 
            super.getActionPeriod(),
            super.getAnimationPeriod(),
            super.getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }
}