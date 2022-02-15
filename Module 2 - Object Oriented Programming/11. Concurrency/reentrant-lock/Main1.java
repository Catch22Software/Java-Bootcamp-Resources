import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Main1 {
    
    static int counter = 0;
    
    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(4);
        ReentrantLock lock = new ReentrantLock();
        int nThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        for(int i = 0; i< 4; i++)
            executor.submit(() -> task(lock,latch));
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }
        System.out.println(counter);
    }
    
    public static void task(ReentrantLock lock, CountDownLatch latch) {
        for (int i = 0; i < 10000; i++) {
            lock.lock();
            counter++;
            lock.unlock();
        }
        latch.countDown();
    }
  
}
