package concurrent;

/**
 * 8线程题目:判断打印的是"one" or "two"
 * 1.两个普通同步方法，两个线程，标准打印，打印? //one   two
 * 2.新增Thread.sleep()给getOne() 打印? //one   two
 * 3.新增普通方法getThread(), 打印? //thread   one   two
 * 4.两个普通同步方法，两个Number对象，两个线程，标准打印，打印? //two   one
 * 5.修改getOne() 为静态同步方法，打印? //two   one
 * 6.两个静态同步方法，两个线程，标准打印，打印? //one   two
 * 7.一个静态同步方法，一个非静态同步方法，两个Number对象? 打印 //two   one
 * 8.两个静态同步方法，两个Number对象，打印? //one  two
 * 
 * 线程八锁的关键：
 * ①非静态方法的锁默认为 this，静态方法的锁为对应的Class实例。
 * ②某一个时刻内，只能有一个线程持有锁，无论几个方法。
 * @author yxd
 *
 */
public class TestThread8Monitor {

	public static void main(String[] args) {
		Number number = new Number();
		Number number2 = new Number();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				number.getOne();
			}}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				number2.geTwo();
			}}).start();
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				number.getThread();
			}}).start();*/
	}

}

class Number{
	public static synchronized void getOne() { // Number.class
		try {
			Thread.sleep(3000);
			System.out.println("one");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static synchronized void geTwo() {
		System.out.println("two");
	}
	
	public void getThread() {
		System.out.println(Thread.currentThread().getName());
	}
}
