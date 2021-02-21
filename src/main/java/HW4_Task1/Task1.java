package HW4_Task1;

public class Task1 {
    static volatile char c = 'A';
    private static Object monitor = new Object();


    public static void main(String[] args) {
        System.out.println("Task1");
        new Thread(new WaitNotifyClass('A', 'B')).start();
        new Thread(new WaitNotifyClass('B', 'C')).start();
        new Thread(new WaitNotifyClass('C', 'A')).start();
    }
    static class WaitNotifyClass implements Runnable {
        private final char current;
        private final char next;

        public WaitNotifyClass(char currentLetter, char nextLetter) {
            this.current = currentLetter;
            this.next = nextLetter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                synchronized (monitor) {
                    try {
                        while (c != current)
                            monitor.wait();
                        System.out.print(current);
                        c = next;
                        monitor.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
