package api.movement.pathfinding.global;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private final Node previous;
    public final Area[] target;
    public final int x;
    public final int y;
    public final int z;
    public final int hops;

    public Node(Node previous, Area[] target, int x, int y, int z, int hops)
    {
        this.target = target;
        this.x = x;
        this.y = y;
        this.z = z;
        this.previous = previous;
        this.hops = hops;
    }

    public List<Position> path()
    {
        List<Position> path = new ArrayList<>();
        Node node = this;

        while (node != null)
        {
            path.add(0, new Position(node.x, node.y, node.z));
            node = node.previous;
        }

        return path;
    }

}
