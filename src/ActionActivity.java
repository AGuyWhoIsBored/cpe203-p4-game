/* Quarter-Long Virtual World Project - ActionActivity Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

public class ActionActivity implements IAction
{
    //
    // instance variables
    private IEntityActive entity;
    private WorldModel world;
    private ImageStore imageStore;

    // constructor
    public ActionActivity(
        IEntityActive entity,
        WorldModel world,
        ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void execute(EventScheduler scheduler) 
    {
        entity.executeActivity(world, imageStore, scheduler);
    }
}