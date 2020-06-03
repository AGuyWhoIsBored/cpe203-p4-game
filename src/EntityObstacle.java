/* Quarter-Long Virtual World Project - EntityObstacle Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;

// external library imports
import processing.core.PImage;

public class EntityObstacle extends EntityBase
{
    // constructor
    public EntityObstacle(
        String id,
        Point position,
        List<PImage> images)
    {
        super(id, position, images);
    }
}