package api.movement.pathfinding.global;

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
 * Modified from another open source project, along with most of the collision map package
 */
public class Pathfinder implements Callable<List<Position>>
{
    private final CollisionMap map;
    private final List<Node> start;
    private final Area[] target;
    private final NodeQueue boundary = new NodeQueue();
    private final Set<Integer> visited = new HashSet<>();

    public Pathfinder(CollisionMap collisionMap, List<Position> start, Area[] target)
    {
        this.map = collisionMap;
        this.target = target;
        this.start = start.stream().map(point -> new Node(null, target, point.getX(), point.getY(), point.getZ(), 0)).collect(Collectors.toList());
    }

    private void addNeighbors(Node node)
    {
        final int x = node.x;
        final int y = node.y;
        final int plane = node.z;

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
            addNeighbor(node, x - 1, y, plane);
        }

        if (canEast)
        {
            addNeighbor(node, x + 1, y, plane);
        }

        if (canSouth)
        {
            addNeighbor(node, x, y - 1, plane);
        }

        if (canNorth)
        {
            addNeighbor(node, x, y + 1, plane);
        }

        if (canSouthWest)
        {
            addNeighbor(node, x - 1, y - 1, plane);
        }

        if (canSouthEast)
        {
            addNeighbor(node, x + 1, y - 1, plane);
        }

        if (canNorthWest)
        {
            addNeighbor(node, x - 1, y + 1, plane);
        }

        if (canNorthEast)
        {
            addNeighbor(node, x + 1, y + 1, plane);
        }

        /*
         * Check position for transports
         * If transport present, add neighbor
         */
    }

    private void addNeighbor(Node node, int x, int y, int z)
    {
        // 15001 is just a prime number above the theoretical max x and y we're using for hashing
        final int hash = x + 15001 * y + 15001 * 15001 * z;

        if (!visited.add(hash))
        {
            return;
        }

        final int hops = node.hops + 1;
        boundary.add(new Node(node, target, x, y, z, hops));
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

            final Node node = boundary.poll();

            if (contains(node.x, node.y, node.z, target))
            {
                return node.path();
            }

            addNeighbors(node);
        }

        return new ArrayList<>();
    }

    @Override
    public List<Position> call()
    {
        return find();
    }

    private static boolean contains(int x, int y, int z, Area... areas)
    {
        for (Area area : areas)
        {
            if (area.contains(x, y, z))
            {
                return true;
            }
        }

        return false;
    }

}
