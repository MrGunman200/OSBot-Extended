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
    private final Set<BasicPosition> visited = new HashSet<>();
    private Node2D nearest;
    private double bestDistance = Double.MAX_VALUE;

    public Pathfinder2D(CollisionMap collisionMap, List<Position> start, Area target)
    {
        this.map = collisionMap;
        this.target = target;
        this.start = new ArrayList<>();
        this.start.addAll(start.stream().map(point -> new Node2D(null, target, point.getX(), point.getY(), point.getZ(), 0)).collect(Collectors.toList()));
        this.nearest = null;
    }

    private void addNeighbors(Node2D node2D)
    {
        int x = node2D.x;
        int y = node2D.y;
        int plane = node2D.z;

        if (map.w(x, y, plane))
        {
            addNeighbor(node2D, x - 1, y, plane);
        }

        if (map.e(x, y, plane))
        {
            addNeighbor(node2D, x + 1, y, plane);
        }

        if (map.s(x, y, plane))
        {
            addNeighbor(node2D, x, y - 1, plane);
        }

        if (map.n(x, y, plane))
        {
            addNeighbor(node2D, x, y + 1, plane);
        }

        if (map.sw(x, y, plane))
        {
            addNeighbor(node2D, x - 1, y - 1, plane);
        }

        if (map.se(x, y, plane))
        {
            addNeighbor(node2D, x + 1, y - 1, plane);
        }

        if (map.nw(x, y, plane))
        {
            addNeighbor(node2D, x - 1, y + 1, plane);
        }

        if (map.ne(x, y, plane))
        {
            addNeighbor(node2D, x + 1, y + 1, plane);
        }

    }

    private void addNeighbor(Node2D node2D, int x, int y, int z)
    {
        final BasicPosition p = new BasicPosition(x, y, z);

        if (!visited.add(p))
        {
            return;
        }

        final double distance = node2D.euclidDist;
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
            if (Thread.interrupted())
            {
                return new ArrayList<>();
            }

            if (visited.size() >= maxSearch)
            {
                return nearest.path();
            }

            final Node2D node2D = boundary.poll();

            if (node2D.euclidDist == 0.00)
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
