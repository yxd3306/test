package concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread中用于解决多线程安全问题的方式：
 * stnchronized:隐士锁
 * 1.同步代码块
 * 2.同步方法
 * 
 * 3.同步锁lock
 * lock是一个同步锁，需要通过lock()方法上锁，必须通过unlock()方法释放锁
 * @author yxd
 *
 */
public class TestLock {

	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		
		new Thread(ticket,"1号窗口").start();
		new Thread(ticket,"2号窗口").start();
		new Thread(ticket,"3号窗口").start();
	}
	
}

class Ticket implements Runnable{

	private int tick = 100;
	
	private Lock lock = new ReentrantLock();
	
	@Override
	public void run() {
		while(true) {
			lock.lock();
			try {
				if(tick>0) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"完成售票，余票为："+--tick);
				}
			}finally {
				lock.unlock();
			}
			
		}
	}
	
}
