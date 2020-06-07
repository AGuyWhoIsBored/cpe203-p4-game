import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class EntityCPStudent extends EntityMovableBase
{
    private static final String CP_KEY = "cpstudent";
    private boolean gotAPlus = false;

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
            // debugging
            //boolean moved = false;
            //if (testTarget.isPresent() && move(world, testTarget.get(), scheduler)) { moved = true; }
            //System.out.println(testTarget.isPresent() + " " + moved + " " + gotAPlus);
            //if (testTarget.isPresent() && moved && !gotAPlus)
            if (testTarget.isPresent() && move(world, testTarget.get(), scheduler) && !gotAPlus)
            {
                Point tgtPos = testTarget.get().getPosition(); // conserve position before entity is deleted
    
                world.removeEntity(testTarget.get());
                scheduler.unscheduleAllEvents(testTarget.get());
    
                EntityAPlus aPlus = Factory.createAPlus("aplus", tgtPos, imageStore.getImageList("aplus"));
                world.addEntity(aPlus);
                gotAPlus = true;
    
                System.out.println("remove 1 " + gotAPlus);
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

    protected boolean _moveHelper(WorldModel world, IEntity target, EventScheduler scheduler)
    {
        return true;
    }

    protected boolean _nextPositionHelper(WorldModel world, Point newPos)
    {
        return world.isOccupied(newPos);
    }
}