/* Quarter-Long Virtual World Project - EventScheduler Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.LinkedList;

public final class EventScheduler
{
    // instance variables
    private PriorityQueue<Event> eventQueue;
    private Map<IEntity, List<Event>> pendingEvents;
    private double timeScale;

    // constructor
    public EventScheduler(double timeScale) 
    {
        this.eventQueue = new PriorityQueue<>(new EventComparator());
        this.pendingEvents = new HashMap<>();
        this.timeScale = timeScale;
    }

    // functional methods
    public void scheduleEvent(IEntity entity, IAction action, long afterPeriod)
    {
        long time = System.currentTimeMillis() + (long)(afterPeriod * timeScale);
        Event event = new Event(action, time, entity);
        eventQueue.add(event);

        // update list of pending events for the given entity
        List<Event> pending = pendingEvents.getOrDefault(entity, new LinkedList<>());
        pending.add(event);
        pendingEvents.put(entity, pending);
    }

    public void unscheduleAllEvents(IEntity entity)
    {
        List<Event> pending = pendingEvents.remove(entity);

        if (pending != null) 
        {
            for (Event event : pending) { eventQueue.remove(event); }
        }
    }

    public void updateOnTime(long time) 
    {
        while (!eventQueue.isEmpty() && eventQueue.peek().getTime() < time) 
        {
            Event next = eventQueue.poll();
            removePendingEvent(next);
            next.getAction().execute(this);
        }
    }

    private void removePendingEvent(Event event)
    {
        List<Event> pending = pendingEvents.get(event.getEntity());
        if (pending != null) { pending.remove(event); }
    }
}
