
// java standard library imports
import java.util.List;
import java.util.Optional;
import java.util.Random;

// external library imports
import processing.core.PImage;

public class EntityJeff extends EntityMovableBase
{
    // constructor
    private static final String VG_KEY = "vistagrande";
    private static final int MAX_VG = 4;
    private int vgCount;
    private Random rand = new Random();
    public EntityJeff(
        String id,
        Point position,
        List<PImage> images,
        int actionPeriod,
        int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
        vgCount = 0;
    }

    protected long[] _executeActivityHelper(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        // debugging
        //System.out.println(System.currentTimeMillis() + " CPstudent activity executed");

        Optional<IEntity> testTarget = world.findNearest(super.getPosition(), EntityBlacksmith.class);
        long nextPeriod = super.getActionPeriod();
        if (testTarget.isPresent())
        {
            move(world, testTarget.get(), scheduler);
            EntityVistaGrande vg = Factory.createEntityVistaGrande("vistagrande", new Point(super.getPosition().getX()-rand.nextInt(5), super.getPosition().getY()-rand.nextInt(5)), imageStore.getImageList(VG_KEY));
            vgCount++;
            try
            {
                world.tryAddEntity(vg);
            }
            catch (Exception e) { System.out.println(e.getMessage()); }
            nextPeriod += super.getActionPeriod();
            if(vgCount>MAX_VG){
                world.removeEntity(this);
                scheduler.unscheduleAllEvents(this);
            }
            return new long[] { 1, nextPeriod };
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