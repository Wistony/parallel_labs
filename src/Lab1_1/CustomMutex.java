package Lab1_1;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class CustomMutex
{
    private AtomicReference<Thread> _lockedThread = new AtomicReference<>();
    private LinkedBlockingQueue<Thread> _waitingThreads = new LinkedBlockingQueue<>();

    public CustomMutex()
    {
    }

    public void Lock() throws InterruptedException
    {
        if (Thread.currentThread().equals(_lockedThread.get()))
        {
            throw new RuntimeException("This thread is already locked");
        }

        while (!_lockedThread.compareAndSet(null, Thread.currentThread()))
        {
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + " get lock");
    }

    public void Unlock() {
        if (!Thread.currentThread().equals(_lockedThread.get()))
        {
            throw new RuntimeException("You can`t call unlock() if thread is locked");
        }

        System.out.println(Thread.currentThread().getName() + " call unlock()");
        _lockedThread.set(null);
    }

    public void Wait() throws InterruptedException
    {
        Thread currentThread = Thread.currentThread();
        if (!currentThread.equals(_lockedThread.get()))
        {
            throw new RuntimeException("You can call wait() if thread is locked");
        }

        _waitingThreads.put(currentThread);
        System.out.println(Thread.currentThread().getName() + " call wait()");
        Unlock();

        while (_waitingThreads.contains(currentThread))
        {
            Thread.yield();
        }

        Lock();
        System.out.println(Thread.currentThread().getName() + " stop waiting");
    }

    public void Notify() throws InterruptedException
    {
        Thread currentThread = Thread.currentThread();
        if (!currentThread.equals(_lockedThread.get()))
        {
            throw new RuntimeException("You can call notify() if thread is locked");
        }

        if(!_waitingThreads.isEmpty())
        {
            _waitingThreads.take();
        }
        System.out.println(Thread.currentThread().getName() + " call notify()");
    }

    public void NotifyAll()
    {
        Thread currentThread = Thread.currentThread();
        if (!currentThread.equals(_lockedThread.get()))
        {
            throw new RuntimeException("You can call notify() if thread is locked");
        }

        _waitingThreads.clear();
        System.out.println(Thread.currentThread().getName() + " call notifyAll()");
    }
}
