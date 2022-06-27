import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueClass {
    private ConcurrentLinkedQueue<Integer> numbers = null;
    private volatile boolean running = true;


    ConcurrentLinkedQueueClass() {
        numbers = new ConcurrentLinkedQueue<>();
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread2.start();
        thread1.start();

    }

    class Thread1 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread one started: ");
            for (int i = 100; i <= 1000; i += 100) {
                numbers.add(i);
                System.out.println("Thread has added: " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            running = false;
        }
    }

    class Thread2 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread 2 started: ");
            while (running || numbers.size() > 0) {
                for (int deletedInt:numbers) {
                    numbers.remove(deletedInt);
                    System.out.println(deletedInt + " has been deleted");

                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    public static void main(String[] args){
        new ConcurrentLinkedQueueClass();
    }
}