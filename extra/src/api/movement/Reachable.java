package api.movement;

import api.util.AreaUtils;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.MethodProvider;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reachable
{

    public static boolean canReach(MethodProvider mp, Entity entity)
    {
        return canReach(mp, entity, true);
    }

    public static boolean canReach(MethodProvider mp, Entity entity, boolean useTravel)
    {
        if (entity == null || !entity.exists())
        {
            return false;
        }

        final Area area = AreaUtils.getArea(entity);
        return canReach(mp, area, useTravel);
    }

    public static boolean canReach(MethodProvider mp, Position position)
    {
        return canReach(mp, position, true);
    }

    public static boolean canReach(MethodProvider mp, Position position, boolean useTravel)
    {
        final Area area = new Area(position, position);
        return canReach(mp, area, useTravel);
    }

    public static boolean canReach(MethodProvider mp, Area area)
    {
        return canReach(mp, area, true);
    }

    public static boolean canReach(MethodProvider mp, Area area, boolean useTravel)
    {
        if (area == null || mp == null || mp.myPosition() == null)
        {
            return false;
        }

        final Position p2 = mp.myPosition();
        boolean canPath = Pathing.canPath(mp, p2, area);

        if (canPath)
        {
            return true;
        }

        final Area meleeArea = AreaUtils.getMeleeArea(area);
        final List<Position> meleePoints = meleeArea.getPositions();
        final int width = AreaUtils.getAreaWidth(meleeArea) - 1;
        final int height = AreaUtils.getAreaHeight(meleeArea) - 1;
        final int startX = meleePoints.get(0).getX();
        final int startY = meleePoints.get(0).getY();
        final int plane = meleeArea.getPlane();

        // Remove corners of melee area
        meleePoints.removeAll(Arrays.asList(
                new Position(startX, startY, plane),
                new Position(startX, startY + height, plane),
                new Position(startX + width, startY, plane),
                new Position(startX + width, startY + height, plane)
        ));

        for (Position point : new ArrayList<>(meleePoints))
        {
            final Area pArea = point.getArea(0);
            final Point p5 = AreaUtils.getComparisonPoint(area, pArea);
            final Point p6 = AreaUtils.getComparisonPoint(pArea, area);
            final Position w1p = new Position(p5.x, p5.y, point.getZ());
            final boolean canTravel = Pathing.canTravelInDirection(mp, w1p, p6.x - p5.x, p6.y - p5.y);
            canPath = Pathing.canPath(mp, point, p2);

            if ((!canTravel && useTravel) || !canPath)
            {
                meleePoints.remove(point);
            }
        }

        return !meleePoints.isEmpty();
    }

}
