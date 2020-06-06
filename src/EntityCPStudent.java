import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class EntityCPStudent extends EntityMovableBase{
    public EntityCPStudent(
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
        Optional<IEntity> studentTarget = world.findNearest(super.getPosition(), EntityOreBlob.class);
        long nextPeriod = super.getActionPeriod();

        if (studentTarget.isPresent())
        {
            Point tgtPos = studentTarget.get().getPosition();

            if (move(world, studentTarget.get(), scheduler))
            {
       //         EntityQuake quake = Factory.createEntityQuake(tgtPos, imageStore.getImageList(QUAKE_KEY));

         //       world.addEntity(quake);
           //     nextPeriod += super.getActionPeriod();
             //   quake.scheduleActions(scheduler, world, imageStore);
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
