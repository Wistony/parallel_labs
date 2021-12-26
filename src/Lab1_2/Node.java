package Lab1_2;

import java.util.concurrent.atomic.AtomicReference;

public class Node<T>
{
    public T data;
    public AtomicReference<Node<T>> next;
    public Node<T> down;

    public Node(T data, AtomicReference<Node<T>> next, Node<T> down)
    {
        this.data = data;
        this.next = next;
        this.down = down;
    }
}