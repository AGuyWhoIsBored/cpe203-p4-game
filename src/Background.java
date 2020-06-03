/* Quarter-Long Virtual World Project - Background Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;

// external library imports
import processing.core.PImage;

public final class Background
{
    // instance variables
    private String id;
    private List<PImage> images;
    private int imageIndex;

    // constructor
    public Background(String id, List<PImage> images) 
    {
        this.id = id;
        this.images = images;
        this.imageIndex = 0;
    }

    // functional methods
    public PImage getCurrentImage() { return images.get(imageIndex);  }
}
