/* Quarter-Long Virtual World Project - EntityBase Abstract Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.List;

// external library imports
import processing.core.PImage;

// all entites will extend this base entity in some fashion
public abstract class EntityBase implements IEntity 
{
    // instance variables
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    // constructor
    public EntityBase(
        String id,
        Point position,
        List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    // accessor methods
    public PImage getCurrentImage() { return images.get(imageIndex); }
    public Point getPosition() { return position; }
    protected int getImageIndex() { return imageIndex; }
    protected List<PImage> getImages() { return images; }
    protected String getId() { return id; }

    // mutator methods
    public void setPosition(Point pos) { position = pos; }
    protected void setImageIndex(int imageIndex) { this.imageIndex = imageIndex; }
}