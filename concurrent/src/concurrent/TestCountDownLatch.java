package concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch： 闭锁，在完成某些运算时，只有其他线程所有的运算全部完成，当先运算才会继续执行
 * 
 * @author yxd
 *
 */
public class TestCountDownLatch {

	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(5);
		LatchDemo ld = new LatchDemo(latch);
		long l = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			new Thread(ld).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("耗时：" + (System.currentTimeMillis() - l));
	}

}

class LatchDemo implements Runnable {

	private CountDownLatch latch;

	public LatchDemo(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		synchronized (this) {
			try {
				for (int i = 0; i < 50000; i++) {
					if (i % 2 == 0) {
						System.out.println(i);
					}
				}
			} finally {
				latch.countDown();
			}
		}
	}

}
