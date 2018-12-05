package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 一、线程池:提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁的额外开销，提高了响应速度 二、线程池的体系结构：
 * java.util.concurrent.Executor：负责线程的使用与调度的跟借口 |--***ExecutorService
 * 子接口：线程池的主要接口 |--ThreadPoolExecutor 线程池的实现类 |--ScheduledExecutorService
 * 子接口：负责线程的调度
 * |--ScheduledThreadPoolExecutor：继承ThreadPoolExecutor，实现ScheduledExecutorService
 * 三、工具类：Executors ExecutorService newFixedThreadPool()：创建固定大小的线程池
 * ExecutorService newCachedThreadPool()：缓存线程池，线程池的数量不固定，可以根据需求更改线程池大小
 * ExecutorService newSingleThreadExecutor()：创建单个线程池。线程池中只有一个线程。
 * 
 * ScheduledExecutorService newScheduledThreadPool()：创建固定大小的线程，可以延迟或定时的任务
 * 
 * @author yxd
 *
 */
public class TestThreadPool {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// 1.创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(5);

		List<Future<Integer>> list = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			Future<Integer> f = pool.submit(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					int sum = 0;
					for (int i = 0; i <= 100; i++) {
						sum += i;
					}
					return sum;
				}
			});

			list.add(f);
		}
		pool.shutdown();
		for(Future<Integer> future:list) {
			System.out.println(future.get());
		}
		
		/*
		 * ThreadPoolDemo tpd = new ThreadPoolDemo();
		 * 
		 * //2.为线程池中的线程分配任务 for(int i=0;i<10;i++) { pool.submit(tpd); }
		 * 
		 * //3.关闭线程池 pool.shutdown(); // 等待线程池中的线程所有任务完成后执行
		 */
	}
//	new Thread(tpd).start();
//	new Thread(tpd).start();

}

class ThreadPoolDemo implements Runnable {

	private int i = 0;

	@Override
	public void run() {
		while (i <= 100) {
			System.out.println(Thread.currentThread().getName() + ":" + i++);
		}
	}

}
