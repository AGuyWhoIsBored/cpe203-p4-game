/* Quarter-Long Virtual World Project Extended - EntityCPStudent Class
 * Names: Duncan Applegarth, William Terlinden
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;
import java.util.Optional;

// external library imports
import processing.core.PImage;

public class EntityCPStudent extends EntityMovableBase
{
    // instance variables
    private boolean gotAPlus = false;

    // constructor
    public EntityCPStudent(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected long[] _executeActivityHelper(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<IEntity> testTarget = world.findNearest(super.getPosition(), EntityOreBlob.class);
        long nextPeriod = super.getActionPeriod();

        // if we haven't given an ore blob an A+, do so
        if (!gotAPlus)
        {
            if (testTarget.isPresent() && move(world, testTarget.get(), scheduler) && !gotAPlus)
            {
                Point tgtPos = testTarget.get().getPosition(); // conserve position before entity is deleted
    
                world.removeEntity(testTarget.get());
                scheduler.unscheduleAllEvents(testTarget.get());

                EntityObstacle aPlus = Factory.createEntityObstacle("aplus", tgtPos, imageStore.getImageList("aplus"));
                world.addEntity(aPlus);
                gotAPlus = true;
    
                return new long[] { 1, nextPeriod + super.getActionPeriod() };
            }
            else
            {
                return new long[] { 1, nextPeriod + super.getActionPeriod() }; 
            }
        }
        // if we have, go to blacksmith and despawn
        else 
        {
            testTarget = world.findNearest(super.getPosition(), EntityBlacksmith.class);
            
            if (testTarget.isPresent() && move(world, testTarget.get(), scheduler))
            {
                world.removeEntity(this);
                scheduler.unscheduleAllEvents(this);

                return new long[] { 0, 0 };
            }
            else { return new long[] { 1, nextPeriod + super.getActionPeriod() }; }
        }
    }

    protected boolean _moveHelper(WorldModel world, IEntity target, EventScheduler scheduler) { return true; }
    protected boolean _nextPositionHelper(WorldModel world, Point newPos) { return world.isOccupied(newPos); }
}