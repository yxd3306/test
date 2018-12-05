package concurrent;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
public class TestScheduledThreadPool {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

		for (int i = 0; i < 10; i++) {

			Future<Integer> f = pool.schedule(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					int num = new Random().nextInt(100); // 生成随机数
					System.out.println(Thread.currentThread().getName() + ":" + num);
					return num;
				}
			}, 3, TimeUnit.SECONDS);
			System.out.println(f.get());
		}
		pool.shutdown();
	}

}
