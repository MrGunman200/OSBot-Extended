package api.movement;

import api.movement.pathfinding.collision.map.Direction;
import api.movement.pathfinding.collision.map.local.LocalCollisionMap;
import api.movement.pathfinding.twodimension.Pathfinder2D;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.MethodProvider;

import java.util.ArrayList;
import java.util.List;

public class Pathing {

    public static boolean canTravelInDirection(MethodProvider mp, Position start, int dx, int dy)
    {
        final LocalCollisionMap map = new LocalCollisionMap(mp);
        final int startFlag = map.localCollision.getCollisionFlag(start.getX(), start.getY());
        final boolean walledNorth = map.localCollision.isWalled(Direction.NORTH, startFlag);
        final boolean walledSouth = map.localCollision.isWalled(Direction.SOUTH, startFlag);
        final boolean walledEast = map.localCollision.isWalled(Direction.EAST, startFlag);
        final boolean walledWest = map.localCollision.isWalled(Direction.WEST, startFlag);
        boolean canTravel = true;

        if (dy == 1) {
            canTravel = !walledNorth;
        }

        if (dy == -1) {
            canTravel = !walledSouth;
        }

        if (dx == 1) {
            canTravel = canTravel && !walledEast;
        }

        if (dx == -1) {
            canTravel = canTravel && !walledWest;
        }

        return canTravel;
    }

    public static List<Position> pathTo(MethodProvider mp, Position endTile)
    {
        final Position start = mp.myPosition();
        return pathTo(mp, start, endTile);
    }

    /*
     * Custom pathfinder algorithm because I don't trust OSBot's to be lightweight
     * and accurate or be assed to figure out how to use the local one
     */
    public static List<Position> pathTo(MethodProvider mp, Position startTile, Position endTile)
    {
        return pathTo(mp, startTile, new Area(endTile, endTile));
    }

    /*
     * Custom pathfinder algorithm because I don't trust OSBot's to be lightweight
     * and accurate or be assed to figure out how to use the local one
     */
    public static List<Position> pathTo(MethodProvider mp, Position startTile, Area endArea)
    {
        if (mp == null || startTile == null || endArea == null || startTile.getZ() != endArea.getPlane())
        {
            return new ArrayList<>();
        }

        final List<Position> starts = new ArrayList<>();
        starts.add(startTile);

        final LocalCollisionMap localCollisionMap = new LocalCollisionMap(mp);
        final Pathfinder2D pathfinder2D = new Pathfinder2D(localCollisionMap, starts, endArea);
        return pathfinder2D.find();
    }

    public static boolean canPath(MethodProvider mp, Position end)
    {
        return canPath(mp, mp.myPosition(), new Area(end, end));
    }

    public static boolean canPath(MethodProvider mp, Position start, Position end)
    {
        return canPath(mp, start, new Area(end, end));
    }

    public static boolean canPath(MethodProvider mp, Area end)
    {
        return canPath(mp, mp.myPosition(), end);
    }

    public static boolean canPath(MethodProvider mp, Position start, Area end)
    {
        final List<Position> path = pathTo(mp, start, end);

        if (path.isEmpty()) {
            return false;
        }

        final Position last = path.get(path.size() - 1);
        return end.contains(last);
    }

}
