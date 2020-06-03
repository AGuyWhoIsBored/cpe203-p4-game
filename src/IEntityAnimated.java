/* Quarter-Long Virtual World Project - EntityAnimated Interface
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

public interface IEntityAnimated extends IEntity
{
    // all methods shared by animated entities
    public void nextImage();
    public int getAnimationPeriod();
}