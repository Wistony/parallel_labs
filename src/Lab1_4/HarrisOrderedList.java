package Lab1_4;

import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class HarrisOrderedList<T extends Comparable<T>>
{
    private Node<T> head = new Node<>(null, new AtomicReference<>(null));

    public boolean remove(T data)
    {
        if (isNull(data))
        {
            throw new IllegalArgumentException("Argument is null");
        }

        Node<T> prevEl = head;
        while (nonNull(prevEl.next.get()))
        {
            Node<T> currEl = prevEl.next.get();
            Node<T> nextEl = currEl.next.get();

            if (currEl.data.compareTo(data) == 0)
            {
                if (currEl.next.compareAndSet(nextEl, null) && prevEl.next.compareAndSet(currEl, nextEl))
                {
                    return true;
                }
            }
            else
            {
                prevEl = currEl;
            }
        }

        return false;
    }

    public void add(T data)
    {
        if (isNull(data))
        {
            throw new IllegalArgumentException("Argument is null");
        }

        Node<T> newEl = new Node<>(data, new AtomicReference<>(null));
        Node<T> current = head;

        while (true)
        {
            Node<T> next = current.next.get();

            if (nonNull(next))
            {
                if (next.data.compareTo(data) >= 0)
                {
                    newEl.next = new AtomicReference<>(next);
                    if (current.next.compareAndSet(next, newEl))
                    {
                        return;
                    }
                }
                else
                {
                    current = next;
                }
            }
            else if (current.next.compareAndSet(null, newEl))
            {
                return;
            }
        }
    }

    public boolean contains(T data) {
        Node<T> currentEl = head.next.get();

        while (nonNull(currentEl)) {
            if (currentEl.data.compareTo(data) == 0) {
                return true;
            }

            currentEl = currentEl.next.get();
        }

        return false;
    }

    public void Print()
    {
        Node<T> current = head.next.get();
        while (nonNull(current))
        {
            System.out.println(current.data);
            current = current.next.get();
        }
    }
}
