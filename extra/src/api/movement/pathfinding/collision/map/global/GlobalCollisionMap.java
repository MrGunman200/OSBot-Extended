package api.movement.pathfinding.collision.map.global;

import api.movement.pathfinding.collision.map.CollisionMap;

import java.nio.ByteBuffer;

public class GlobalCollisionMap implements CollisionMap
{
	private static GlobalCollisionMap globalCollisionMap = null;
	public final BitSet4D[] regions = new BitSet4D[256 * 256];

	public GlobalCollisionMap()
	{
	}

	public GlobalCollisionMap(byte[] data)
	{
		ByteBuffer buffer = ByteBuffer.wrap(data);

		while (buffer.hasRemaining())
		{
			int region = buffer.getShort() & 0xffff;
			regions[region] = new BitSet4D(buffer, 64, 64, 4, 2);
		}
	}

	public static GlobalCollisionMap get()
	{
		if (globalCollisionMap == null) {
			//TODO Load map
		}

		return globalCollisionMap;
	}

	public BitSet4D getRegion(int x, int y)
	{
		int regionId = x / 64 * 256 + y / 64;
		return regions[regionId];
	}

	public boolean get(int x, int y, int z, int w)
	{
		BitSet4D region = getRegion(x, y);

		if (region == null)
		{
			return false;
		}

		int regionX = x % 64;
		int regionY = y % 64;

		return region.get(regionX, regionY, z, w);
	}

	@Override
	public boolean n(int x, int y, int z)
	{
		return get(x, y, z, 0);
	}

	@Override
	public boolean e(int x, int y, int z)
	{
		return get(x, y, z, 1);
	}

}
