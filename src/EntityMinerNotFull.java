/* Quarter-Long Virtual World Project - EntityMinerNotFull Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;
import java.util.Optional;

// external library imports
import processing.core.PImage;

public class EntityMinerNotFull extends EntityMovableBase
{
    // instance variables
    private int resourceLimit;
    private int resourceCount;
    
    // constructor
    public EntityMinerNotFull(
        String id,
        Point position,
        List<PImage> images,
        int resourceLimit,
        int resourceCount,
        int actionPeriod,
        int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    // functional implementation of EntityActive interface (inherited from EntityMovable)
    protected long[] _executeActivityHelper(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<IEntity> notFullTarget = world.findNearest(super.getPosition(), EntityOre.class);

        if (!notFullTarget.isPresent() 
            || !move(world, notFullTarget.get(), scheduler)
            || !transformNotFull(world, scheduler, imageStore))
        {
            return new long[] { 1, super.getActionPeriod() };
        }
        return new long[] { 0, 0 };
    }

    // implemented abstract methods to complete parent implementation of these methods
    protected boolean _moveHelper(WorldModel world, IEntity target, EventScheduler scheduler)
    {
        resourceCount += 1;
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);

        return true;
    }

    protected boolean _nextPositionHelper(WorldModel world, Point newPos)
    {
        return world.isOccupied(newPos);
    }

    // private helper methods for functional implementation
    private boolean transformNotFull(
        WorldModel world,
        EventScheduler scheduler,
        ImageStore imageStore)
    {
        if (resourceCount >= resourceLimit) 
        {
            EntityMinerFull miner = Factory.createEntityMinerFull(
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

            return true;
        }

        return false;
    }
}