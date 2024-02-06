package api.movement.pathfinding.global;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class NodeQueue
{

    private static final short INITIAL_CAPACITY = 32;
    private int size;
    private Node[] elements;

    public NodeQueue()
    {
        this.elements = new Node[INITIAL_CAPACITY];
        this.size = 0;
    }

    public void addAll(List<Node> nodes)
    {
        ensureCapacity(size + nodes.size());

        for (Node node : nodes)
        {
            elements[size++] = node;
        }
    }

    public void add(Node element)
    {
        ensureCapacity();
        elements[size++] = element;
    }

    public Node poll()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("Queue is empty");
        }

        Node minElement = elements[0];
        elements[0] = elements[size - 1];
        size--;
        return minElement;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    private void ensureCapacity(int minCapacity)
    {
        if (minCapacity > elements.length)
        {
            int newCapacity = Math.max(elements.length * 2, minCapacity);
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    private void ensureCapacity()
    {
        if (size == elements.length)
        {
            elements = Arrays.copyOf(elements, 2 * elements.length);
        }
    }

}
