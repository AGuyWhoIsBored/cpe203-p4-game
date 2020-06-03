/* Quarter-Long Virtual World Project - ImageStore Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

// external library imports
import processing.core.PImage;

public final class ImageStore
{
    // instance variables
    private Map<String, List<PImage>> images;
    private List<PImage> defaultImages;

    // constructor
    public ImageStore(PImage defaultImage) 
    {
        this.images = new HashMap<>();
        defaultImages = new LinkedList<>();
        defaultImages.add(defaultImage);
    }

    // accessor methods
    public Map<String, List<PImage>> getImages() { return images; }

    // public functional methods
    public List<PImage> getImageList(String key) { return images.getOrDefault(key, defaultImages); }
}
