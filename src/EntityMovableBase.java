/* Quarter-Long Virtual World Project - EntityMovableBase Abstract Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;
import java.util.Optional;

// external library imports
import processing.core.PImage;

public abstract class EntityMovableBase extends EntityActiveAnimatedBase implements IEntityMovable
{
    // constructor
    public EntityMovableBase(
        String id,
        Point position,
        List<PImage> images,
        int actionPeriod,
        int animationPeriod
    )   
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    // partially implemented - child classes complete implementation
    public boolean move(WorldModel world, IEntity target, EventScheduler scheduler)
    {
        if (IEntityMovable.adjacent(super.getPosition(), target.getPosition()))
        {
            return _moveHelper(world, target, scheduler);
        }
        else 
        {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!super.getPosition().equals(nextPos)) 
            {
                Optional<IEntity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) 
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }       
    }
    
    // partially implemented - child classes complete implementation
    public Point nextPosition(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - super.getPosition().getX());
        Point newPos = new Point(super.getPosition().getX() + horiz, super.getPosition().getY());

        if (horiz == 0 || _nextPositionHelper(world, newPos))
        {
            int vert = Integer.signum(destPos.getY() - super.getPosition().getY());
            newPos = new Point(super.getPosition().getX(), super.getPosition().getY() + vert);
            
            if (vert == 0 || _nextPositionHelper(world, newPos))
            {
                newPos = super.getPosition();
            }
        }

        return newPos;
    }

    // partially implemented - child classes complete implementation
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        // do some weird trickery here
        // first element is whether we let the scheduler schedule an event or not
        // second element is afterPeriod passed into scheduler
        long[] afterPeriod = _executeActivityHelper(world, imageStore, scheduler);

        // use int representation of bool as array needs to be all of one type (long)
        if (afterPeriod[0] == 1)
        {
            scheduler.scheduleEvent(
                this,
                Factory.createActionActivity(this, world, imageStore),
                afterPeriod[1]);
        }
    }

    private void transformCPStudent(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        EntityCPStudent cpstudent = Factory.createEntityCPStudent(
                super.getId(),
                super.getPosition(),
                super.getActionPeriod(),
                super.getAnimationPeriod(),
                super.getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(cpstudent);
        super.scheduleActions(scheduler, world, imageStore);
    }
    
    // helper abstract methods
    protected abstract boolean _moveHelper(WorldModel world, IEntity target, EventScheduler scheduler);
    protected abstract boolean _nextPositionHelper(WorldModel world, Point newPos);
    protected abstract long[] _executeActivityHelper(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}