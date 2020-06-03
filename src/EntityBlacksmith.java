/* Quarter-Long Virtual World Project - EntityBlacksmith Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;

// external library imports
import processing.core.PImage;

public class EntityBlacksmith extends EntityBase
{
    // constructor
    public EntityBlacksmith(
        String id,
        Point position,
        List<PImage> images)
    {
        super(id, position, images);
    }
}