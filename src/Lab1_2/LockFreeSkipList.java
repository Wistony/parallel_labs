package Lab1_2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class LockFreeSkipList<T extends Comparable<T>>
{
    private int h;
    private double p;
    private Node<T> head;

    public LockFreeSkipList(int height, double p)
    {
        this.h = height;
        this.p = p;

        Node<T> emptyElement = new Node<>(null, new AtomicReference<>(null), null);
        head = emptyElement;

        for (int i = 0; i < height - 1; i++)
        {
            Node<T> newListHead = new Node<>(null, new AtomicReference<>(null), null);
            emptyElement.down = newListHead;
            emptyElement = newListHead;
        }
    }

    public boolean add(T data)
    {
        if (isNull(data))
        {
            throw new IllegalArgumentException("Argument is null");
        }

        List<Node<T>> prev = new ArrayList<>();
        List<Node<T>> prevRight = new ArrayList<>();
        Node<T> currentNode = head;

        int goalHeight = randomizeHeight();
        int currentLevel = h;

        while (currentLevel > 0)
        {
            Node<T> rightEl = currentNode.next.get();

            if (currentLevel <= goalHeight)
            {
                if (isNull(rightEl) || rightEl.data.compareTo(data) >= 0)
                {
                    prev.add(currentNode);
                    prevRight.add(rightEl);
                }
            }

            if (nonNull(rightEl) && rightEl.data.compareTo(data) < 0)
            {
                currentNode  = rightEl;
            }
            else
            {
                currentNode = currentNode.down;
                currentLevel--;
            }
        }

        Node<T> downEl = null;
        for (int i = prev.size() - 1; i >= 0; i--)
        {
            Node<T> newEl = new Node<>(data, new AtomicReference<>(prevRight.get(i)), null);

            if (nonNull(downEl))
            {
                newEl.down = downEl;
            }

            if (!prev.get(i).next.compareAndSet(prevRight.get(i), newEl) && i == prev.size() - 1)
            {
                return false;
            }

            downEl = newEl;
        }

        return true;
    }

    public boolean remove(T data)
    {
        if (isNull(data))
        {
            throw new IllegalArgumentException("Argument is null");
        }

        Node<T> currentNode = head;
        int currentLevel = h;
        boolean towerUnmarked = true;

        while (currentLevel > 0)
        {
            Node<T> rightEl = currentNode.next.get();
            if (nonNull(rightEl) && rightEl.data.compareTo(data) == 0)
            {
                Node<T> afterRightEl = rightEl.next.get();
                if (towerUnmarked)
                {
                    Node<T> towerEl = rightEl;
                    while (nonNull(towerEl))
                    {
                        towerEl.next.compareAndSet(towerEl.next.get(), null);
                        towerEl = towerEl.down;
                    }
                    towerUnmarked = false;
                }

                currentNode.next.compareAndSet(rightEl, afterRightEl);
            }

            if (nonNull(rightEl) && rightEl.data.compareTo(data) < 0)
            {
                currentNode = rightEl;
            }
            else
            {
                currentNode = currentNode.down;
                currentLevel--;
            }
        }

        return !towerUnmarked;
    }

    public boolean contains(T data)
    {
        Node<T> currentNode = head;

        while (nonNull(currentNode))
        {
            Node<T> rightEl = currentNode.next.get();
            if (nonNull(currentNode.data) && currentNode.data.compareTo(data) == 0)
            {
                return true;
            }
            else if (nonNull(rightEl) && rightEl.data.compareTo(data) <= 0)
            {
                currentNode = rightEl;
            }
            else
            {
                currentNode = currentNode.down;
            }
        }

        return false;
    }

    public void PrintElement()
    {
        Node<T> curr = head;

        while (nonNull(curr.down))
        {
            curr = curr.down;
        }

        curr = curr.next.get();

        while (nonNull(curr))
        {
            System.out.println(curr.data);
            curr = curr.next.get();
        }
    }

    private int randomizeHeight()
    {
        int level = 1;

        while (level < h && Math.random() < p)
        {
            level++;
        }

        return level;
    }
}
