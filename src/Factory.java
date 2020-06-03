/* Quarter-Long Virtual World Project - Factory Class (for Factory Design Pattern)
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;

// external library imports
import processing.core.PImage;

public class Factory 
{
    // static variables
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;

    // all of the create() methods will be here to follow the Factory Design Pattern

    public static ActionActivity createActionActivity(
        IEntityActive entity, 
        WorldModel world, 
        ImageStore imageStore) 
    { 
        return new ActionActivity(entity, world, imageStore); 
    }

    public static ActionAnimation createActionAnimation(
        IEntityAnimated entity, 
        int repeatCount) 
    { 
        return new ActionAnimation(entity, repeatCount); 
    }

    public static EntityBlacksmith createEntityBlacksmith(
        String id, 
        Point position, 
        List<PImage> images) 
    { 
        return new EntityBlacksmith(id, position, images); 
    }

    public static EntityMinerFull createEntityMinerFull(
        String id,
        int resourceLimit,
        Point position,
        int actionPeriod,
        int animationPeriod,
        List<PImage> images)
    {
        return new EntityMinerFull(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }

    public static EntityMinerNotFull createEntityMinerNotFull(
        String id,
        int resourceLimit,
        Point position,
        int actionPeriod,
        int animationPeriod,
        List<PImage> images)
    {
        return new EntityMinerNotFull(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public static EntityObstacle createEntityObstacle(
        String id, 
        Point position, 
        List<PImage> images)
    {
        return new EntityObstacle(id, position, images);
    }

    public static EntityOre createEntityOre(
        String id, 
        Point position, 
        int actionPeriod, 
        List<PImage> images)
    {
        return new EntityOre(id, position, images, actionPeriod);
    }

    public static EntityOreBlob createEntityOreBlob(
        String id,
        Point position,
        int actionPeriod,
        int animationPeriod,
        List<PImage> images)
    {
        return new EntityOreBlob(id, position, images, actionPeriod, animationPeriod);
    }

    public static EntityQuake createEntityQuake(
        Point position, 
        List<PImage> images)
    {
        return new EntityQuake(QUAKE_ID, position, images, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public static EntityVein createVein(
        String id, 
        Point position, 
        int actionPeriod, 
        List<PImage> images)
    {
        return new EntityVein(id, position, images, actionPeriod);
    }
}