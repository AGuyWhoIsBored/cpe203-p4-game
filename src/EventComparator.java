/* Quarter-Long Virtual World Project - EventComparator Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.Comparator;

public final class EventComparator implements Comparator<Event>
{
    public int compare(Event lft, Event rht) { return (int)(lft.getTime() - rht.getTime()); }
}
