package api.util;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;

import java.awt.Point;
import java.util.List;

public class AreaUtils
{

    public static Area getArea(Entity e)
    {
        final Position p1 = e.getPosition();
        Position p2 = p1;

        if (e instanceof RS2Object)
        {
            RS2Object object = (RS2Object) e;
            p2 = new Position(p1.getX() + Math.max(0, object.getSizeX() - 1), p1.getY() + Math.max(0, object.getSizeY() - 1), p1.getZ());
        }
        else if (e instanceof NPC)
        {
            NPC npc = (NPC) e;
            p2 = new Position(p1.getX() + Math.max(0, npc.getSizeX() - 1), p1.getY() + Math.max(0, npc.getSizeY() - 1), p1.getZ());
        }

        return new Area(p1, p2).setPlane(p1.getZ());
    }

    public static Area getMeleeArea(Area a)
    {
        final List<Position> areaPoints = a.getPositions();
        final Position p1 = areaPoints.get(0).translate(-1, -1);
        final Position p2 = areaPoints.get(areaPoints.size() - 1).translate(1, 1);
        return new Area(p1, p2).setPlane(p1.getZ());
    }

    /**
     * Taken from OpenOSRS, modified for OSBot
     */
    public static Point getComparisonPoint(Area first, Area other)
    {
        int firstHeight = getAreaHeight(first);
        int firstWidth = getAreaWidth(first);
        int firstX = first.getPositions().get(0).getX();
        int firstY = first.getPositions().get(0).getY();
        int otherX = other.getPositions().get(0).getX();
        int otherY = other.getPositions().get(0).getY();
        int x, y;
        if (otherX <= firstX)
        {
            x = firstX;
        }
        else if (otherX >= firstX + firstWidth - 1)
        {
            x = firstX + firstWidth - 1;
        }
        else
        {
            x = otherX;
        }
        if (otherY <= firstY)
        {
            y = firstY;
        }
        else if (otherY >= firstY + firstHeight - 1)
        {
            y = firstY + firstHeight - 1;
        }
        else
        {
            y = otherY;
        }
        return new Point(x, y);
    }

    public static int getAreaWidth(Area area)
    {
        final List<Position> areaPoints = area.getPositions();
        final Position p1 = areaPoints.get(0);
        final Position p2 = areaPoints.get(areaPoints.size() - 1);
        return Math.max(1, Math.abs(p1.getX() - p2.getX()) + 1);
    }

    public static int getAreaHeight(Area area)
    {
        final List<Position> areaPoints = area.getPositions();
        final Position p1 = areaPoints.get(0);
        final Position p2 = areaPoints.get(areaPoints.size() - 1);
        return Math.max(1, Math.abs(p1.getY() - p2.getY()) + 1);
    }

}
