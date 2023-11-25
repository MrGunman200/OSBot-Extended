package api.movement;

import api.util.AreaUtils;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.MethodProvider;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Reachable
{

    /**
     * Should work with ground items on top of objects
     * Probably more accurate than OSBot's default
     */
    public static boolean canReach(MethodProvider mp, Entity entity)
    {
        return canReach(mp, entity, false);
    }

    /**
     * Use this if object is walled in but tiles around it are still reachable to the target
     * Example being the hopper at Motherlode Mine
     */
    public static boolean canReach2(MethodProvider mp, Entity entity)
    {
        return canReach(mp, entity, true);
    }

    private static boolean canReach(MethodProvider mp, Entity entity, boolean travel)
    {
        if (entity == null || !entity.exists() || mp == null || mp.myPosition() == null)
        {
            return false;
        }

        final Position p1 = entity.getPosition();
        final Position p2 = mp.myPosition();
        boolean canPath = Pathing.canPath(mp, p2, p1);

        if (canPath)
        {
            return true;
        }

        final Area area = AreaUtils.getArea(entity);
        final Area extendedArea = AreaUtils.getMeleeArea(area);
        final List<Position> meleePoints = extendedArea.getPositions();
        final List<Position> clone = new ArrayList<>(meleePoints);

        for (Position point : clone)
        {
            final Area pArea = point.getArea(0);
            final Point p5 = AreaUtils.getComparisonPoint(area, pArea);
            final Point p6 = AreaUtils.getComparisonPoint(pArea, area);
            final Position w1p = new Position(p5.x, p5.y, point.getZ());
            final Area w1 = new Area(w1p, w1p);
            final boolean canTravel = Pathing.canTravelInDirection(mp, w1, p6.x - p5.x, p6.y - p5.y);
            canPath = Pathing.canPath(mp, w1p, p2);

            if ((!canTravel && travel) || !canPath)
            {
                meleePoints.remove(point);
            }
        }

        return !meleePoints.isEmpty();
    }

}
