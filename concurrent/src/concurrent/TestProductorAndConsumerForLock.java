package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者案例
 * @author yxd
 *
 */
public class TestProductorAndConsumerForLock {

	public static void main(String[] args) {
		Clerk clerk = new Clerk();
		new Thread(new Productor(clerk), "生产者 A").start();
		new Thread(new Consumer(clerk), "消费者 B").start();

		new Thread(new Productor(clerk), "生产者 C").start();
		new Thread(new Consumer(clerk), "消费者 D").start();
	}

}

class Clerk {

	private int product = 0;
	private int productMax = 1;
	private int productMin = 0;
	
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public void get() {
		lock.lock();
		try {
			while (product >= productMax) { // 为了避免虚假唤醒问题，用该总是使用在循环中
				System.out.println("仓库已满");
				try {
					condition.await(); // 当前线程等待，并释放资源  多并发场景会引发虚假唤醒问题
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + " : " + ++product);
			condition.signalAll(); // 唤醒所有线程
		}finally {
			lock.unlock();
		}
	}

	public void sale() {
		lock.lock();
		try {
			while (product <= productMin) {
				System.out.println("需要进货");
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + " : " + --product);
			condition.signalAll();
		}finally {
			lock.unlock();
		}
		
	}
}

// 生产者
class Productor implements Runnable {

	private Clerk clerk;

	public Productor(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(200); // 模拟网络延迟
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			clerk.get();
		}
	}

}

class Consumer implements Runnable {

	private Clerk clerk;

	public Consumer(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
	}

}
