package Lab1_1;

import java.util.concurrent.LinkedBlockingQueue;

public class Task1 {

    public static void main(String[] args) throws InterruptedException {
        CustomMutex mutex = new CustomMutex();

        for (int i = 0; i < 3; i++)
        {
            new Thread(new Test1(mutex)).start();
            Thread.sleep(100);
        }

        //Test notifyAll
        /*for (int i = 0; i < 5; i++) {
            Thread.sleep(500);
            mutex1._notifyAll();
        }*/
    }

    static class Test1 implements Runnable {

        private static CustomMutex mutex;

        public Test1(CustomMutex mutex)
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
                    System.out.println("HERE");
                    mutex.Unlock();
                }
                else
                {
                    mutex.Wait();
                    System.out.println(Thread.currentThread().getName());
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
