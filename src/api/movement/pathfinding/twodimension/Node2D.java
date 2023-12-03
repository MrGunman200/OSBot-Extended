package api.movement.pathfinding.twodimension;

import api.util.AreaUtils;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Node2D
{
    private final Node2D previous;
    public final Area target;
    public final int x;
    public final int y;
    public final int z;
    public final int hops;
    public final float euclidDist;
    public final float cost;

    public Node2D (Node2D previous, Area target, int x, int y, int z, int hops)
    {
        this.target = target;
        this.x = x;
        this.y = y;
        this.z = z;
        this.previous = previous;
        this.hops = hops;

        final Point p = AreaUtils.getComparisonPoint(target, new Area(x, y, x, y).setPlane(z));
        final int xdif = Math.abs(x - p.x);
        final int ydif = Math.abs(y - p.y);

        euclidDist = xdif + ydif;
        cost = euclidDist + hops;
    }

    public List<Position> path()
    {
        List<Position> path = new ArrayList<>();
        Node2D node2D = this;

        while (node2D != null)
        {
            path.add(0, new Position(node2D.x, node2D.y, node2D.z));
            node2D = node2D.previous;
        }

        return path;
    }

    public int compareTo(Node2D other)
    {
        return Float.compare(this.cost, other.cost);
    }

}
