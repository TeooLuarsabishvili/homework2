import java.util.Vector;

public class VectorClass {

    static volatile boolean running = true;

    public static void main(String[] args) {
        Vector<String> animals = new Vector<>();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("first Thread started: ");
                try {
                    for (int i = 1; i <= 7; i++) {
                        String animal = "animals" + i;
                        animals.add(animal);
                        System.out.println("Element " + animal + " added");
                    }
                    Thread.sleep(200);
                    running = false;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        };

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread 2 started ");
                while (running || animals.size() > 0) {
                    for (int i = animals.size() - 1; i >= 0; i--) {
                        System.out.println("Thread2 removed: " + animals.elementAt(i));
                        animals.remove(i);
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        };


        Thread thread1 = new Thread(runnable, "Thread 1");
        thread1.start();
        Thread thread2 = new Thread(runnable1, "Thread 2");
        thread2.start();
    }

}