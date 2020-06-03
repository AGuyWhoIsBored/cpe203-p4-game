/* Quarter-Long Virtual World Project - Entity Interface
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.Optional;
import java.util.List;

// external library imports
import processing.core.PImage;

public interface IEntity
{
    // methods shared by all entities
    public Point getPosition();
    public PImage getCurrentImage();
    public void setPosition(Point p);

    // static methods
    public static Optional<IEntity> nearestEntity(List<IEntity> entities, Point pos)
    {
        if (entities.isEmpty()) 
        {
            return Optional.empty();
        }
        else 
        {
            IEntity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), pos);

            for (IEntity other : entities) 
            {
                int otherDistance = distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance) 
                {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    // private static methods used for implementation of public static methods
    private static int distanceSquared(Point p1, Point p2) 
    {
        int deltaX = p1.getX() - p2.getX();
        int deltaY = p1.getY() - p2.getY();

        return deltaX * deltaX + deltaY * deltaY;
    }
}