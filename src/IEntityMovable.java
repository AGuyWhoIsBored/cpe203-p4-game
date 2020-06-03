/* Quarter-Long Virtual World Project - EntityMovable Interface
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

public interface IEntityMovable extends IEntityActive, IEntityAnimated
{
    // all methods shared by movable entities
    public boolean move(WorldModel world, IEntity target, EventScheduler scheduler);
    public Point nextPosition(WorldModel world, Point destPos);

    // static methods
    public static boolean adjacent(Point p1, Point p2) 
    {
        return (p1.getX() == p2.getX() 
            && Math.abs(p1.getY() - p2.getY()) == 1) 
            || (p1.getY() == p2.getY()
            && Math.abs(p1.getX() - p2.getX()) == 1);
    }
}