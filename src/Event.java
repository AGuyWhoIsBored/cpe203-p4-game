/* Quarter-Long Virtual World Project - Event Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

public final class Event
{
    // instance variables
    private IAction action;
    private long time;
    private IEntity entity;

    // constructor
    public Event(IAction action, long time, IEntity entity) 
    {
        this.action = action;
        this.time = time;
        this.entity = entity;
    }

    // accessor methods
    public IAction getAction() { return action; }
    public long getTime() { return time; }
    public IEntity getEntity() { return entity; }
}