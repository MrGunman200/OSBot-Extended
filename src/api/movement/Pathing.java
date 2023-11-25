package api.movement;

import api.movement.pathfinding.collision.map.local.LocalCollisionMap;
import api.movement.pathfinding.twodimension.Pathfinder2D;
import api.util.AreaUtils;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.MethodProvider;

import java.util.ArrayList;
import java.util.List;

public class Pathing {

    /**
     * Taken from OpenOSRS
     */
    public static boolean canTravelInDirection(MethodProvider mp, Area area, int dx, int dy)
    {
        int width = AreaUtils.getAreaWidth(area);
        int height = AreaUtils.getAreaHeight(area);
        Position startPosition = area.getPositions().get(0);
        dx = Integer.signum(dx);
        dy = Integer.signum(dy);

        if (dx == 0 && dy == 0)
        {
            return true;
        }

        int startX = startPosition.getX() - mp.getMap().getBaseX();
        int startY = startPosition.getY() - mp.getMap().getBaseY();
        int checkX = startX + (dx > 0 ? width : 0);
        int checkY = startY + (dy > 0 ? height : 0);
        int endX = startX + width;
        int endY = startY + height;

        int xFlags = CollisionDataFlag.BLOCK_MOVEMENT_FULL;
        int yFlags = CollisionDataFlag.BLOCK_MOVEMENT_FULL;
        int xyFlags = CollisionDataFlag.BLOCK_MOVEMENT_FULL;
        int xWallFlagsSouth = CollisionDataFlag.BLOCK_MOVEMENT_FULL;
        int xWallFlagsNorth = CollisionDataFlag.BLOCK_MOVEMENT_FULL;
        int yWallFlagsWest = CollisionDataFlag.BLOCK_MOVEMENT_FULL;
        int yWallFlagsEast = CollisionDataFlag.BLOCK_MOVEMENT_FULL;

        if (checkX >= 104 || checkY >= 104)
        {
            return false;
        }
        if (dx < 0)
        {
            xFlags |= CollisionDataFlag.BLOCK_MOVEMENT_EAST;
            xWallFlagsSouth |= CollisionDataFlag.BLOCK_MOVEMENT_SOUTH |
                    CollisionDataFlag.BLOCK_MOVEMENT_SOUTH_EAST;
            xWallFlagsNorth |= CollisionDataFlag.BLOCK_MOVEMENT_NORTH |
                    CollisionDataFlag.BLOCK_MOVEMENT_NORTH_EAST;
        }
        if (dx > 0)
        {
            xFlags |= CollisionDataFlag.BLOCK_MOVEMENT_WEST;
            xWallFlagsSouth |= CollisionDataFlag.BLOCK_MOVEMENT_SOUTH |
                    CollisionDataFlag.BLOCK_MOVEMENT_SOUTH_WEST;
            xWallFlagsNorth |= CollisionDataFlag.BLOCK_MOVEMENT_NORTH |
                    CollisionDataFlag.BLOCK_MOVEMENT_NORTH_WEST;
        }
        if (dy < 0)
        {
            yFlags |= CollisionDataFlag.BLOCK_MOVEMENT_NORTH;
            yWallFlagsWest |= CollisionDataFlag.BLOCK_MOVEMENT_WEST |
                    CollisionDataFlag.BLOCK_MOVEMENT_NORTH_WEST;
            yWallFlagsEast |= CollisionDataFlag.BLOCK_MOVEMENT_EAST |
                    CollisionDataFlag.BLOCK_MOVEMENT_NORTH_EAST;
        }
        if (dy > 0)
        {
            yFlags |= CollisionDataFlag.BLOCK_MOVEMENT_SOUTH;
            yWallFlagsWest |= CollisionDataFlag.BLOCK_MOVEMENT_WEST |
                    CollisionDataFlag.BLOCK_MOVEMENT_SOUTH_WEST;
            yWallFlagsEast |= CollisionDataFlag.BLOCK_MOVEMENT_EAST |
                    CollisionDataFlag.BLOCK_MOVEMENT_SOUTH_EAST;
        }
        if (dx < 0 && dy < 0)
        {
            xyFlags |= CollisionDataFlag.BLOCK_MOVEMENT_NORTH_EAST;
        }
        if (dx < 0 && dy > 0)
        {
            xyFlags |= CollisionDataFlag.BLOCK_MOVEMENT_SOUTH_EAST;
        }
        if (dx > 0 && dy < 0)
        {
            xyFlags |= CollisionDataFlag.BLOCK_MOVEMENT_NORTH_WEST;
        }
        if (dx > 0 && dy > 0)
        {
            xyFlags |= CollisionDataFlag.BLOCK_MOVEMENT_SOUTH_WEST;
        }

        int[][] collisionDataFlags = mp.getMap().getClippingFlags();

        if (dx != 0)
        {
            // Check that the area doesn't bypass a wall
            for (int y = startY; y <= endY; y++)
            {
                if ((collisionDataFlags[checkX][y] & xFlags) != 0)
                {
                    // Collision while attempting to travel along the x axis
                    return false;
                }
            }

            // Check that the new area tiles don't contain a wall
            for (int y = startY + 1; y <= endY; y++)
            {
                if ((collisionDataFlags[checkX][y] & xWallFlagsSouth) != 0)
                {
                    // The new area tiles contains a wall
                    return false;
                }
            }
            for (int y = endY - 1; y >= startY; y--)
            {
                if ((collisionDataFlags[checkX][y] & xWallFlagsNorth) != 0)
                {
                    // The new area tiles contains a wall
                    return false;
                }
            }
        }
        if (dy != 0)
        {
            // Check that the area tiles don't bypass a wall
            for (int x = startX; x <= endX; x++)
            {
                if ((collisionDataFlags[x][checkY] & yFlags) != 0)
                {
                    // Collision while attempting to travel along the y axis
                    return false;
                }
            }

            // Check that the new area tiles don't contain a wall
            for (int x = startX + 1; x <= endX; x++)
            {
                if ((collisionDataFlags[x][checkY] & yWallFlagsWest) != 0)
                {
                    // The new area tiles contains a wall
                    return false;
                }
            }
            for (int x = endX - 1; x >= startX; x--)
            {
                if ((collisionDataFlags[x][checkY] & yWallFlagsEast) != 0)
                {
                    // The new area tiles contains a wall
                    return false;
                }
            }
        }
        if (dx != 0 && dy != 0)
        {
            if ((collisionDataFlags[checkX][checkY] & xyFlags) != 0)
            {
                // Collision while attempting to travel diagonally
                return false;
            }

            // When the areas edge size is 1 and it attempts to travel
            // diagonally, a collision check is done for respective
            // x and y axis as well.
            if (width == 1)
            {
                if ((collisionDataFlags[checkX][checkY - dy] & xFlags) != 0)
                {
                    return false;
                }
            }
            if (height == 1)
            {
                return (collisionDataFlags[checkX - dx][checkY] & yFlags) == 0;
            }
        }

        return true;
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
        if (mp == null || startTile == null || endTile == null)
        {
            return null;
        }

        final Area target = new Area(endTile, endTile);
        final List<Position> starts = new ArrayList<>();
        starts.add(startTile);

        final LocalCollisionMap localCollisionMap = new LocalCollisionMap(mp);
        final Pathfinder2D pathfinder2D = new Pathfinder2D(localCollisionMap, starts, target);
        return pathfinder2D.find();
    }

    public static boolean canPath(MethodProvider mp, Position end)
    {
        return canPath(mp, mp.myPosition(), end);
    }

    public static boolean canPath(MethodProvider mp, Position start, Position end)
    {
        final List<Position> path = pathTo(mp, start, end);
        return path != null && path.contains(end);
    }

}
