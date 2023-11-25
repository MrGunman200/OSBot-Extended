package api.movement.pathfinding.collision.map.local;

import api.movement.pathfinding.collision.map.CollisionMap;
import api.movement.pathfinding.collision.map.Direction;
import org.osbot.rs07.script.MethodProvider;

public class LocalCollisionMap implements CollisionMap
{
	public final LocalCollision localCollision;

	public LocalCollisionMap(MethodProvider mp)
	{
		this.localCollision = new LocalCollision(mp);
	}

	@Override
	public boolean n(int x, int y, int z)
	{
		if (localCollision.isObstacle(x, y))
		{
			return false;
		}

		return localCollision.canWalk(Direction.NORTH, localCollision.getCollisionFlag(x, y), localCollision.getCollisionFlag(x, y + 1));
	}

	@Override
	public boolean e(int x, int y, int z)
	{
		if (localCollision.isObstacle(x, y))
		{
			return false;
		}

		return localCollision.canWalk(Direction.EAST, localCollision.getCollisionFlag(x, y), localCollision.getCollisionFlag(x + 1, y));
	}

}
