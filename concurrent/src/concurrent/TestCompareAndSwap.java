package concurrent;

/**
 *      模拟CAS算法
 * @author yxd
 *
 */
public class TestCompareAndSwap {

	public static void main(String[] args) {
		final CompareAndSwap cas = new CompareAndSwap();
		for(int i =0;i<10;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					int expecteValue = cas.get();
					boolean b = cas.compareAndSet(expecteValue, (int)(Math.random())*101);
					System.out.println(b);
				}
			}).start();
		}
	}

}

class CompareAndSwap{
private int value;
	
	// 获取内存值
	public synchronized int get() {
		return value;
	}
	
	// 比较
	public synchronized int compareAndSwap(int expecteValue,int newValue) {
		int oldValue = value;
		if(oldValue==expecteValue) {
			this.value=newValue;
		}
		return oldValue;
	}
	
	// 设置
	public synchronized boolean compareAndSet(int expecteValue,int newValue) {
		return expecteValue== compareAndSwap(expecteValue,newValue);
	}
}
