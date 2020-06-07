
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
        Optional<IEntity> testTarget = world.findNearest(super.getPosition(), EntityBlacksmith.class);
        long nextPeriod = super.getActionPeriod();
        if (testTarget.isPresent())
        {
            // counterintuitive - returns TRUE if we are adjacent to target!
            if (!move(world, testTarget.get(), scheduler))
            {
                EntityVistaGrande vg = Factory.createEntityVistaGrande("vistagrande", new Point(super.getPosition().getX() + (rand.nextInt(5) - 3), super.getPosition().getY() + (rand.nextInt(5) - 3)), imageStore.getImageList(VG_KEY));
                try
                {
                    if (vgCount < 3) 
                    { 
                        world.tryAddEntity(vg); 
                        vgCount++; // only increment if we are able to successfully add in vg
                    } 
                }
                catch (Exception e) { /*System.out.println(e.getMessage());*/ }
                nextPeriod += super.getActionPeriod();
            }
            else
            {
                // despawn jeff once he reaches blacksmith
                world.removeEntity(this);
                scheduler.unscheduleAllEvents(this);
            }
            return new long[] { 1, nextPeriod };
        }
        else { return new long[] { 0, 0 }; }
    }

    protected boolean _moveHelper(WorldModel world, IEntity target, EventScheduler scheduler) { return true; }
    protected boolean _nextPositionHelper(WorldModel world, Point newPos) { return world.isOccupied(newPos); }
}