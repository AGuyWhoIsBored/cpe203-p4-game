/* Quarter-Long Virtual World Project - Action Interface
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

public interface IAction
{
    // methods shared by all instances of Action
    public void execute(EventScheduler scheduler);
}