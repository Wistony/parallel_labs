package Lab1_2;

public class Task2
{
    public static void main(String[] args) throws InterruptedException
    {
        LockFreeSkipList<String> lockFreeSkipList = new LockFreeSkipList<>(16, 0.5);
        Thread[] threads = new Thread[5];

        for (int i = 0; i < 5; i++)
        {
            threads[i] = new Thread(new SkipListTest(lockFreeSkipList));
            threads[i].start();
        }

        for (int i = 0; i < 5; i++) {
            threads[i].join();
        }

        System.out.println("Contains: " + lockFreeSkipList.contains("Thread-4"));
        lockFreeSkipList.PrintElement();
    }

    static class SkipListTest implements Runnable
    {
        private static LockFreeSkipList<String> lockFreeSkipList;

        public SkipListTest(LockFreeSkipList<String> lockFreeSkipList)
        {
            this.lockFreeSkipList = lockFreeSkipList;
        }

        @Override
        public void run()
        {
            String threadName = Thread.currentThread().getName();
            while (!lockFreeSkipList.add(threadName))
            {
                System.out.println(threadName + " not added in skiplist");
            }

            System.out.println(threadName + " added in skiplist");

            if (threadName.equals("Thread-2"))
            {
                System.out.println("Remove " + threadName + ": " + lockFreeSkipList.remove(threadName));
            }
        }
    }
}
