package Lab2;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Library {
    static List<String> books = new ArrayList<>();
    static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    static final Lock writeLock = lock.writeLock();
    static final Lock readLock = lock.readLock();
}

class Writer extends Thread {
    private final String threadName;

    Writer(String threadName) {
        this.threadName = threadName;
    }

    public void run() {
        int written = 0;
        while (written < 14) {
            try {
                Library.writeLock.lock();
                String book = "Book" + (Library.books.size() + 1);
                //Thread.sleep(500);
                Library.books.add(book);
                written += 1;
                System.out.println(threadName + " has written " + book);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
            } finally {
                Library.writeLock.unlock();
            }
        }
    }
}
