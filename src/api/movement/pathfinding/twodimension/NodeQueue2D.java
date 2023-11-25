package api.movement.pathfinding.twodimension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * Thanks, ChatGPT
 */
public class NodeQueue2D
{

    private static final int INITIAL_CAPACITY = 32;
    private int size;
    private Node2D[] elements;

    public NodeQueue2D()
    {
        this.elements = new Node2D[INITIAL_CAPACITY];
        this.size = 0;
    }

    public int size()
    {
        return this.size;
    }

    public void addAll(List<Node2D> node2DS)
    {
        ensureCapacity(size + node2DS.size());

        for (Node2D node2D : node2DS)
        {
            elements[size++] = node2D;
            heapifyUp();
        }
    }

    public void add(Node2D element)
    {
        ensureCapacity();
        elements[size++] = element;
        heapifyUp();
    }

    public Node2D poll()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("Queue is empty");
        }

        Node2D minElement = elements[0];
        elements[0] = elements[size - 1];
        size--;
        heapifyDown();
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

    private void heapifyUp()
    {
        int index = size - 1;

        while (index > 0)
        {
            int parentIndex = (index - 1) / 2;

            if (elements[index].compareTo(elements[parentIndex]) >= 0)
            {
                break;
            }

            swap(index, parentIndex);
            index = parentIndex;
        }

    }

    private void heapifyDown()
    {
        int index = 0;

        while (true)
        {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;

            if (leftChildIndex >= size && rightChildIndex >= size)
            {
                break;
            }

            int smallestChildIndex = (rightChildIndex >= size || elements[leftChildIndex].compareTo(elements[rightChildIndex]) < 0)
                    ? leftChildIndex : rightChildIndex;

            if (elements[smallestChildIndex].compareTo(elements[index]) >= 0)
            {
                break;
            }

            swap(index, smallestChildIndex);
            index = smallestChildIndex;
        }
    }

    private void swap(int i, int j)
    {
        Node2D temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

}
