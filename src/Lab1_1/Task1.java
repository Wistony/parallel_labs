package Lab1_1;

public class Task1
{
    public static void main(String[] args) throws InterruptedException
    {
        CustomMutex mutex = new CustomMutex();

        for (int i = 0; i < 3; i++)
        {
            new Thread(new MutexTest(mutex)).start();
            Thread.sleep(100);
        }

        //Test notifyAll
        /*for (int i = 0; i < 5; i++) {
            Thread.sleep(500);
            mutex1._notifyAll();
        }*/
    }

    static class MutexTest implements Runnable
    {

        private static CustomMutex mutex;

        public MutexTest(CustomMutex mutex)
        {
            this.mutex = mutex;
        }

        @Override
        public void run() {
            try
            {
                mutex.Lock();
                if(Thread.currentThread().getName().equals("Thread-2"))
                {
                    mutex.Notify();
                    mutex.Unlock();
                }
                else
                {
                    mutex.Wait();
                    mutex.Notify();
                    mutex.Unlock();
                }
            }
            catch (InterruptedException ex)
            {
                System.err.println(ex.getMessage());
            }
        }
    }
}
