/* Quarter-Long Virtual World Project - EntityOreBlob Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;
import java.util.Optional;

// external library imports
import processing.core.PImage;

public class EntityOreBlob extends EntityMovableBase
{
    // static variables
    private static final String QUAKE_KEY = "quake";

    // constructor
    public EntityOreBlob(
        String id,
        Point position,
        List<PImage> images,
        int actionPeriod,
        int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    // implemented abstract methods to complete parent implementation of these methods
    protected long[] _executeActivityHelper(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<IEntity> blobTarget = world.findNearest(super.getPosition(), EntityVein.class);
        long nextPeriod = super.getActionPeriod();

        if (blobTarget.isPresent()) 
        {
            Point tgtPos = blobTarget.get().getPosition();

            if (move(world, blobTarget.get(), scheduler)) 
            {
                EntityQuake quake = Factory.createEntityQuake(tgtPos, imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += super.getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        return new long[] { 1, nextPeriod };
    }

    protected boolean _moveHelper(WorldModel world, IEntity target, EventScheduler scheduler)
    {
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
        return true;
    }

    protected boolean _nextPositionHelper(WorldModel world, Point newPos)
    {
        Optional<IEntity> occupant = world.getOccupant(newPos);
        return (occupant.isPresent() 
        && !(occupant.get().getClass() == EntityOre.class));
    }
}