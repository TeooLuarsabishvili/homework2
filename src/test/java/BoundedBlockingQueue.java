import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public record BoundedBlockingQueue(BlockingQueue<Integer> queue) implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i < 11; i++) {
            System.out.println("[Producer] Put : " + i);
            queue.add(i);
            System.out.println("[Producer] Queue remainingCapacity : " + queue.remainingCapacity());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(queue);
        queue.add(74);


    }

    public static void main(String[] args) {

        BlockingQueue<Integer> queue = new LinkedBlockingDeque<>(10);
        Thread t = new Thread(new BoundedBlockingQueue(queue));
        t.setUncaughtExceptionHandler((new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t + " throws exception: " + e);
                Thread.currentThread().interrupt();
                System.out.println("remove 1 from: "+queue);
                queue.remove(1);
                System.out.println("updated queue is: "+queue);

            }
        }));
        t.start();
    }

}