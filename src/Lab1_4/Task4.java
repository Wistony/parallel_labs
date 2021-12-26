package Lab1_4;

public class Task4 {

    public static void main(String[] args) throws InterruptedException
    {
        HarrisOrderedList<String> list = new HarrisOrderedList<>();
        Thread[] threads = new Thread[15];

        for (int i = 0; i < 15; i++)
        {
            threads[i] = new Thread(new ListTest(list));
            threads[i].start();
        }

        for (int i = 0; i < 15; i++)
        {
            threads[i].join();
        }

        System.out.println("Contains: " + list.contains("Thread-9"));
        list.nonSafePrint();
    }

    static class ListTest implements Runnable
    {
        private HarrisOrderedList<String> harrisOrderedList;

        public ListTest(HarrisOrderedList<String> harrisOrderedList)
        {
            this.harrisOrderedList = harrisOrderedList;
        }

        @Override
        public void run()
        {
            String currThreadName = Thread.currentThread().getName();
            harrisOrderedList.add(currThreadName);

            if (currThreadName.equals("Thread-8"))
            {
                System.out.println("Remove: " + harrisOrderedList.remove(currThreadName));
            }
        }
    }
}
