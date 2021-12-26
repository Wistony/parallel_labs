package Lab1_4;

import java.util.concurrent.atomic.AtomicReference;

public class Node<T>
{
    public T data;
    public AtomicReference<Node<T>> next;

    public Node(T data, AtomicReference<Node<T>> next)
    {
        this.data = data;
        this.next = next;
    }
}