/* Quarter-Long Virtual World Project - ActionAnimation Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

public class ActionAnimation implements IAction
{
    // instance variables
    private IEntityAnimated entity;
    private int repeatCount;

    // constructor
    public ActionAnimation(
        IEntityAnimated entity,
        int repeatCount)
    {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    public void execute(EventScheduler scheduler) 
    {
        entity.nextImage();

        if (repeatCount != 1) 
        {
            scheduler.scheduleEvent(
                entity,
                Factory.createActionAnimation(
                    entity,
                    Math.max(repeatCount - 1, 0)),
                entity.getAnimationPeriod());
        }
    }
}