/* Quarter-Long Virtual World Project - EntityActive Interface
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

public interface IEntityActive extends IEntity
{
    // methods shared by all active entities (have actions)
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler eventScheduler);
    public void scheduleActions(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore);
}