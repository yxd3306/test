package concurrent;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1. ReadWriteLock：读写锁
 * 写写/读写 需要“互斥”
 * 读读 不需要“互斥”
 * @author yxd
 *
 */
public class TestReadWriteLock {
	
	
	
	public static void main(String[] args) {
		
		
		ReadWriteLockDemo lockDemo = new ReadWriteLockDemo();
		new Thread(new Runnable() {

			@Override
			public void run() {
				lockDemo.set((int)(Math.random()*101));
			}},"Write:")
		.start();
		
		
		
		
		for(int i =0;i<100;i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					lockDemo.get();
				}})
			.start();
			
		}
		
		
	}

}



class ReadWriteLockDemo{
	private int number = 0;
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	// 读数据
	public void get() {
		lock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+":"+number);
		}finally {
			lock.readLock().unlock();
		}
	}
	
	// 写数据
	public void set(int number) {
		
		lock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName());
			this.number=number;
		}finally {
			lock.writeLock().unlock();
		}
	}
}