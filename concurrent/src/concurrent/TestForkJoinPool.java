package concurrent;

import java.awt.Insets;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

import org.junit.Test;

/**
 * 
 * @author yxd
 *
 */
public class TestForkJoinPool {

	public static void main(String[] args) {
		Instant start = Instant.now();
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Long> task = new ForJoinSumCalculate(0L, 10000000000L);

		Long sum = pool.invoke(task);
		System.out.println(sum);
		
		Instant end = Instant.now();

		System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());
	}

	@Test
	public void test1() {
		Instant start = Instant.now();
		long sum = 0L;
		for (long i = 0L; i <= 10000000000L; i++) {
			sum += i;
		}
		System.out.println(sum);

		Instant end = Instant.now();

		System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());

	}
	@Test
	public void test2() {
		Instant start = Instant.now();
		
		Long sum = LongStream.rangeClosed(0L, 50000000000L)
				.parallel()
				.reduce(0L, Long::sum);
		System.out.println(sum);
		
		Instant end = Instant.now();
		System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());
	}

}

class ForJoinSumCalculate extends RecursiveTask<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long start;
	private long end;

	private static final long THURSHOLD = 10000L;// 临界值

	public ForJoinSumCalculate(long start, long end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		long length = end - start;
		if (length <= THURSHOLD) {
			long sum = 0L;
			for (long i = start; i <= end; i++) {
				sum += i;
			}
			return sum;
		} else {
			long middle = (start + end) / 2;
			ForJoinSumCalculate left = new ForJoinSumCalculate(start, middle);
			left.fork();// 进行拆分，同时押入线程队列
			ForJoinSumCalculate right = new ForJoinSumCalculate(middle + 1, end);
			right.fork();
			return left.join() + right.join();
		}
	}
}