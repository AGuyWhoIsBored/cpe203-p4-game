import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class EntityCPStudent extends EntityMovableBase
{
    private static final String CP_KEY = "cpstudent";
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
        // debugging
        //System.out.println(System.currentTimeMillis() + " CPstudent activity executed");

        Optional<IEntity> testTarget = world.findNearest(super.getPosition(), EntityOreBlob.class);
        long nextPeriod = super.getActionPeriod();
        if (testTarget.isPresent())
        {
            Point tgtPos = testTarget.get().getPosition();

            if (move(world, testTarget.get(), scheduler))
            {
                EntityCPStudent cpStudent = Factory.createCPStudent("cpstudent", tgtPos, imageStore.getImageList(CP_KEY), 500, 100);

                world.addEntity(cpStudent);
                nextPeriod += super.getActionPeriod();
                cpStudent.scheduleActions(scheduler, world, imageStore);
            }
        }
        return new long[] { 1, nextPeriod };
    }

    protected boolean _moveHelper(WorldModel world, IEntity target, EventScheduler scheduler)
    {
        // debugging
        //System.out.println(System.currentTimeMillis() + " CPstudent move helper executed");
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);

        return true;
    }

    protected boolean _nextPositionHelper(WorldModel world, Point newPos)
    {
        // debugging
        //System.out.println(System.currentTimeMillis() + " CPstudent next position helper executed");

        return world.isOccupied(newPos);
    }
}