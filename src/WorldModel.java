/* Quarter-Long Virtual World Project - WorldModel Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

// java standard library imports
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;
import java.util.List;
import java.util.LinkedList;

// external library imports
import processing.core.PImage;

public final class WorldModel
{
    // static variables
    private static final int ORE_REACH = 1;

    // instance variables
    private int numRows;
    private int numCols;
    private Background background[][];
    private IEntity occupancy[][];
    private Set<IEntity> entities;

    // constructor
    public WorldModel(int numRows, int numCols, Background defaultBackground) 
    {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new IEntity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) 
        {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }

    // public accessor methods
    public Set<IEntity> getEntities() { return entities; }
    public int getNumRows() { return numRows; }
    public int getNumCols() { return numCols; }

    // public functional methods
    public void tryAddEntity(IEntity entity) 
    {
        if (isOccupied(entity.getPosition())) 
        {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }

    public boolean isOccupied(Point pos)
    { 
        return withinBounds(pos) 
            && getOccupancyCell(pos) != null; 
    }

    public Optional<PImage> getBackgroundImage(Point pos)
    {
        if (withinBounds(pos)) 
        { 
            return Optional.of(getBackgroundCell(pos).getCurrentImage()); 
        }
        else 
        { 
            return Optional.empty(); 
        }
    }

    public void setBackground(Point pos, Background background)
    {
        if (withinBounds(pos)) 
        { 
            setBackgroundCell(pos, background); 
        }
    }

    public Optional<IEntity> getOccupant(Point pos)
    {
        if (isOccupied(pos)) 
        { 
            return Optional.of(getOccupancyCell(pos)); 
        }
        else 
        { 
            return Optional.empty();
        }
    }

    public Optional<Point> findOpenAround(Point pos)
    {
        for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++) 
        {
            for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++) 
            {
                Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
                if (withinBounds(newPt) && !isOccupied(newPt)) 
                { 
                    return Optional.of(newPt); 
                }
            }
        }

        return Optional.empty();
    }

    public Optional<IEntity> findNearest(Point pos, Class<?> classKind)
    {
        List<IEntity> ofType = new LinkedList<>();
        for (IEntity entity : entities) 
        {
            if (entity.getClass() == classKind) 
            { 
                ofType.add(entity); 
            }
        }

        return IEntity.nearestEntity(ofType, pos);
    }

    /*
        Assumes that there is no entity currently occupying the
        intended destination cell.
    */
    public void addEntity(IEntity entity) 
    {
        if (withinBounds(entity.getPosition())) 
        {
            setOccupancyCell(entity.getPosition(), entity);
            entities.add(entity);
        }
    }

    public void moveEntity(IEntity entity, Point pos) 
    {
        Point oldPos = entity.getPosition();
        if (withinBounds(pos) && !pos.equals(oldPos)) 
        {
            setOccupancyCell(oldPos, null);
            removeEntityAt(pos);
            setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public void removeEntity(IEntity entity) { removeEntityAt(entity.getPosition()); }

    // private accessor methods
    private IEntity getOccupancyCell(Point pos) { return occupancy[pos.getY()][pos.getX()]; }
    private Background getBackgroundCell(Point pos) { return background[pos.getY()][pos.getX()]; }

    // private mutator methods
    private void setBackgroundCell(Point pos, Background background) { this.background[pos.getY()][pos.getX()] = background; }
    private void setOccupancyCell(Point pos, IEntity entity) { occupancy[pos.getY()][pos.getX()] = entity; }

    // private functional methods
    private void removeEntityAt(Point pos) 
    {
        if (withinBounds(pos) && getOccupancyCell(pos) != null)
        {
            IEntity entity = getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
                * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }

    private boolean withinBounds(Point pos) 
    {
        return pos.getY() >= 0 
            && pos.getY() < numRows 
            && pos.getX() >= 0
            && pos.getX() < numCols;
    }
}
