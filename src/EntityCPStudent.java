import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class EntityCPStudent extends EntityMovableBase
{
    public EntityCPStudent(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected long[] _executeActivityHelper(WorldModel world, ImageStore imaegStore, EventScheduler scheduler)
    {
        // debugging
        //System.out.println(System.currentTimeMillis() + " CPstudent activity executed");

        Optional<IEntity> testTarget = world.findNearest(super.getPosition(), EntityMinerNotFull.class);

        if (testTarget.isPresent())
        {
            move(world, testTarget.get(), scheduler);
            return new long[] { 1, super.getActionPeriod() };
        }
        else { return new long[] { 0, 0 } ; }
    }

    protected boolean _moveHelper(WorldModel world, IEntity target, EventScheduler scheduler)
    {
        // debugging
        //System.out.println(System.currentTimeMillis() + " CPstudent move helper executed");

        return true;
    }

    protected boolean _nextPositionHelper(WorldModel world, Point newPos)
    {
        // debugging
        //System.out.println(System.currentTimeMillis() + " CPstudent next position helper executed");

        return world.isOccupied(newPos);
    }
}