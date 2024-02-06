package api.movement.pathfinding.collision.map;

import org.osbot.rs07.api.map.Position;

public interface CollisionMap
{
	boolean n(int x, int y, int z);

	boolean e(int x, int y, int z);

	default boolean s(int x, int y, int z)
	{
		return n(x, y - 1, z);
	}

	default boolean w(int x, int y, int z)
	{
		return e(x - 1, y, z);
	}

	default boolean ne(int x, int y, int z)
	{
		return n(x, y, z) && e(x, y + 1, z) && e(x, y, z) && n(x + 1, y, z);
	}

	default boolean nw(int x, int y, int z)
	{
		return n(x, y, z) && w(x, y + 1, z) && w(x, y, z) && n(x - 1, y, z);
	}

	default boolean se(int x, int y, int z)
	{
		return s(x, y, z) && e(x, y - 1, z) && e(x, y, z) && s(x + 1, y, z);
	}

	default boolean sw(int x, int y, int z)
	{
		return s(x, y, z) && w(x, y - 1, z) && w(x, y, z) && s(x - 1, y, z);
	}

	// Full block (like gameobjects)
	default boolean fullBlock(int x, int y, int z)
	{
		return !n(x, y, z) && !s(x, y, z) && !w(x, y, z) && !e(x, y, z);
	}

	default boolean n(Position position)
	{
		return n(position.getX(), position.getY(), position.getZ());
	}

	default boolean s(Position position)
	{
		return s(position.getX(), position.getY(), position.getZ());
	}

	default boolean w(Position position)
	{
		return w(position.getX(), position.getY(), position.getZ());
	}

	default boolean e(Position position)
	{
		return e(position.getX(), position.getY(), position.getZ());
	}

	default boolean ne(Position position)
	{
		return ne(position.getX(), position.getY(), position.getZ());
	}

	default boolean nw(Position position)
	{
		return nw(position.getX(), position.getY(), position.getZ());
	}

	default boolean se(Position position)
	{
		return se(position.getX(), position.getY(), position.getZ());
	}

	default boolean sw(Position position)
	{
		return sw(position.getX(), position.getY(), position.getZ());
	}

	default boolean fullBlock(Position position)
	{
		return !n(position) && !s(position) && !w(position) && !e(position);
	}

}
