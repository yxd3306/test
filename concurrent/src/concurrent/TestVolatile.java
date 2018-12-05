package concurrent;

/**
 * volatile 关键字：当多个线程操作共享数据时，可以保证内存中的数据是可见的。
 * 				  相较于synchronize是一种较为轻量级的同步策略
 * 
 * --注意：
 * 1. volatile 不具备互斥性
 * 2. volatile 不能保证变量的原子性
 * @author yxd
 *
 */
public class TestVolatile {

	public static void main(String[] args) {
		ThreadDemo td = new ThreadDemo();
		new Thread(td).start();

		while (true) {
			if (td.isFlag()) {
				System.out.println("------------------");
				break;
			}
		}

	}

}

class ThreadDemo implements Runnable {

	private volatile boolean flag = false;
//	private boolean flag = false;

	@Override
	public void run() {

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}

		flag = true;

		System.out.println("flag=" + isFlag());

	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
