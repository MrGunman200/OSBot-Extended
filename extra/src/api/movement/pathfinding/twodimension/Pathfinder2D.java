package api.movement.pathfinding.twodimension;

import api.movement.pathfinding.collision.map.CollisionMap;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * Pathing without object handling, not the best paths, but should be fast.
 * Modified from another open source project, along with most of the collision map package
 */
public class Pathfinder2D implements Callable<List<Position>>
{
    private final CollisionMap map;
    private final List<Node2D> start;
    private final Area target;
    private final NodeQueue2D boundary = new NodeQueue2D();
    private final Set<Integer> visited = new HashSet<>();
    private Node2D nearest;
    private float bestDistance = Float.MAX_VALUE;

    public Pathfinder2D(CollisionMap collisionMap, List<Position> start, Area target)
    {
        this.map = collisionMap;
        this.target = target;
        this.start = start.stream().map(point -> new Node2D(null, target, point.getX(), point.getY(), point.getZ(), 0)).collect(Collectors.toList());
        this.nearest = null;
    }

    private void addNeighbors(Node2D node2D)
    {
        final int x = node2D.x;
        final int y = node2D.y;
        final int plane = node2D.z;

        final boolean canWest = map.w(x, y, plane);
        final boolean canEast = map.e(x, y, plane);
        final boolean canSouth = map.s(x, y, plane);
        final boolean canNorth = map.n(x, y, plane);

        final boolean canSouthWest = (canSouth || canWest) && map.sw(x, y, plane);
        final boolean canSouthEast = (canSouth || canEast) && map.se(x, y, plane);
        final boolean canNorthWest = (canNorth || canWest) && map.nw(x, y, plane);
        final boolean canNorthEast = (canNorth || canEast) && map.ne(x, y, plane);

        if (canWest)
        {
            addNeighbor(node2D, x - 1, y, plane);
        }

        if (canEast)
        {
            addNeighbor(node2D, x + 1, y, plane);
        }

        if (canSouth)
        {
            addNeighbor(node2D, x, y - 1, plane);
        }

        if (canNorth)
        {
            addNeighbor(node2D, x, y + 1, plane);
        }

        if (canSouthWest)
        {
            addNeighbor(node2D, x - 1, y - 1, plane);
        }

        if (canSouthEast)
        {
            addNeighbor(node2D, x + 1, y - 1, plane);
        }

        if (canNorthWest)
        {
            addNeighbor(node2D, x - 1, y + 1, plane);
        }

        if (canNorthEast)
        {
            addNeighbor(node2D, x + 1, y + 1, plane);
        }

    }

    private void addNeighbor(Node2D node2D, int x, int y, int z)
    {

        // 15001 is just a prime number above the theoretical max x and y we're using for hashing
        final int hash = x + 15001 * y + 15001 * 15001 * z;

        if (!visited.add(hash))
        {
            return;
        }

        final float distance = node2D.distance;
        final int hops = node2D.hops + 1;

        if (distance < bestDistance)
        {
            nearest = node2D;
            bestDistance = distance;
        }

        boundary.add(new Node2D(node2D, target, x, y, z, hops));
    }

    public List<Position> find()
    {
        //long startTime = System.currentTimeMillis();
        List<Position> path = find(5_000_000);
        //System.out.println("\nPath Generated In " + (System.currentTimeMillis() - startTime) + "ms");
        return path;
    }

    public List<Position> find(int maxSearch)
    {
        boundary.addAll(start);

        while (!boundary.isEmpty())
        {
            /* Don't think these are needed?
            if (Thread.interrupted())
            {
                return new ArrayList<>();
            }

            if (visited.size() >= maxSearch)
            {
                return nearest.path();
            }
             */

            final Node2D node2D = boundary.poll();

            if (node2D.distance == 0.00)
            {
                return node2D.path();
            }

            addNeighbors(node2D);
        }

        if (nearest != null)
        {
            return nearest.path();
        }

        return new ArrayList<>();
    }

    @Override
    public List<Position> call()
    {
        return find();
    }

}
