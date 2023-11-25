package api.movement.pathfinding.collision.map.local;

import api.movement.pathfinding.collision.map.Direction;
import org.osbot.rs07.script.MethodProvider;

import static api.movement.CollisionDataFlag.BLOCK_LINE_OF_SIGHT_FULL;
import static api.movement.CollisionDataFlag.BLOCK_MOVEMENT_EAST;
import static api.movement.CollisionDataFlag.BLOCK_MOVEMENT_FLOOR;
import static api.movement.CollisionDataFlag.BLOCK_MOVEMENT_NORTH;
import static api.movement.CollisionDataFlag.BLOCK_MOVEMENT_OBJECT;
import static api.movement.CollisionDataFlag.BLOCK_MOVEMENT_SOUTH;
import static api.movement.CollisionDataFlag.BLOCK_MOVEMENT_WEST;

public class LocalCollision
{

    public final int[][] flags;
    public final int baseX;
    public final int baseY;
    public final int capX;
    public final int capY;

    public LocalCollision(MethodProvider mp)
    {
        this.flags = mp.getMap().getClippingFlags();
        this.baseX = mp.getMap().getBaseX();
        this.baseY = mp.getMap().getBaseY();
        this.capX = baseX + 104;
        this.capY = baseY + 104;
    }

    public boolean check(int flag, int checkFlag)
    {
        return (flag & checkFlag) != 0;
    }

    public boolean isObstacle(int endFlag)
    {
        return check(endFlag, BLOCK_MOVEMENT_OBJECT | BLOCK_LINE_OF_SIGHT_FULL | BLOCK_MOVEMENT_FLOOR | 0x1000000);
    }

    public boolean isObstacle(int x, int y)
    {
        return isObstacle(getCollisionFlag(x, y));
    }

    public int getCollisionFlag(int x, int y)
    {
        final int sceneX = x - baseX;
        final int sceneY = y - baseY;

        if (sceneX <= 0 || sceneY <= 0 || sceneX >= 104 || sceneY >= 104)
        {
            return 0xFFFFFF;
        }

        return flags[sceneX][sceneY];
    }

    public boolean isWalled(Direction direction, int startFlag)
    {
        switch (direction)
        {
            case NORTH:
                return check(startFlag, BLOCK_MOVEMENT_NORTH);
            case SOUTH:
                return check(startFlag, BLOCK_MOVEMENT_SOUTH);
            case WEST:
                return check(startFlag, BLOCK_MOVEMENT_WEST);
            case EAST:
                return check(startFlag, BLOCK_MOVEMENT_EAST);
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean canWalk(Direction direction, int startFlag, int endFlag)
    {
        if (isObstacle(endFlag))
        {
            return false;
        }

        return !isWalled(direction, startFlag);
    }

}
