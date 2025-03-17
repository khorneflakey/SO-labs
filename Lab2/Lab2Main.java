package Lab2;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

class Reader extends Thread {
    private final String threadName;
    private ArrayList<String> booksRead = new ArrayList<>();
    private final Random random = new Random();

    Reader(String threadName) {
        this.threadName = threadName;
    }

    public void run() {
        String bookRandom;

        while (booksRead.size() < 14) {
            try {
                Library.readLock.lock();
                if (Library.books.size() > 0) {
                    int randomValue = random.nextInt(Library.books.size());
                    bookRandom = Library.books.get(randomValue);
                    //Thread.sleep(500);
                    booksRead.add(bookRandom);
                    System.out.println(threadName + " has read " + bookRandom);
                } else {
                    System.out.println(threadName + " is waiting to begin");
                }
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            } finally {
                Library.readLock.unlock();
            }
        }

        System.out.println(threadName + " has stopped with " + booksRead);
    }
}

public class Lab2Main {
    public static void main(String[] args) throws Exception {
        Integer readersNumber = 14;
        Integer writersNumber = 4;
        
        Stack<Thread> threadStack = new Stack<>();
        Random random = new Random();

        for (int i = 1; i <= writersNumber; i++) {
            threadStack.push(new Writer("Writer" + i));
        }

        for (int i = 1; i <= readersNumber; i++) {
            threadStack.push(new Reader("Reader" + i));
        }

        Stack<Thread> shuffledStack = new Stack<>();
        while (!threadStack.isEmpty()) {
            int index = random.nextInt(threadStack.size());
            shuffledStack.push(threadStack.remove(index));
        }

        while (!shuffledStack.isEmpty()) {
            shuffledStack.pop().start();
        }
    }
}
