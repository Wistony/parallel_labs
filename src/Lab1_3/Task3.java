package Lab1_3;

public class Task3
{
    public static void main(String[] args) throws InterruptedException
    {
        MichaelAndScottAlgorithm<String> queue = new MichaelAndScottAlgorithm<>();
        Thread[] arr = new Thread[50];

        for (int i = 0; i < 50; i++)
        {
            arr[i] = new Thread(new QueueTest(queue));
            arr[i].start();
        }

        for (int i = 0; i < 50; i++)
        {
            arr[i].join();
        }

        queue.Print();
    }

    static class QueueTest implements Runnable
    {
        private MichaelAndScottAlgorithm<String> queue;

        public QueueTest(MichaelAndScottAlgorithm<String> michaelAndScottAlgorithm)
        {
            this.queue = michaelAndScottAlgorithm;
        }

        @Override
        public void run()
        {
            queue.push(Thread.currentThread().getName());
            if (Math.random() > 0.4)
            {
                queue.pop();
            }
        }
    }
}
