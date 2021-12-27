package Lab1_3;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

//wiki implementation
public class MichaelAndScottAlgorithm<T>
{
    private Node<T> dummy = new Node<>(null, new AtomicReference<>(null));
    private AtomicReference<Node<T>> head = new AtomicReference<>(dummy);
    private AtomicReference<Node<T>> tail = new AtomicReference<>(dummy);

    public T pop()
    {
        while (true)
        {
            Node<T> currentHead = head.get();
            Node<T> currentTail = tail.get();
            Node<T> nextHead = currentHead.next.get();

            if (currentHead == currentTail)
            {
                if (nextHead == null)
                {
                    throw new NoSuchElementException();
                }
                else
                {
                    tail.compareAndSet(currentTail, currentTail.next.get());
                }
            }
            else
            {
                if (head.compareAndSet(currentHead, nextHead))
                {
                    return nextHead.data;
                }
            }
        }
    }

    public void push(T data)
    {
        Node<T> newTail = new Node<>(data, new AtomicReference<>(null));

        while(true)
        {
            Node<T> currentTail = tail.get();

            if (currentTail.next.compareAndSet(null, newTail))
            {
                tail.compareAndSet(currentTail, newTail);
                return;
            }
            else
            {
                tail.compareAndSet(currentTail, currentTail.next.get());
            }
        }
    }

    public void Print()
    {
        Node<T> current = head.get();

        while (current != null)
        {
            System.out.println(current.data);
            current = current.next.get();
        }
    }
}
