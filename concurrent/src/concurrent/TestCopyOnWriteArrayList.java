package concurrent;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList/CopyOnWriteArraySet:"写入并复制"
 * 注意：添加操作多时，效率低 因为每次添加都会复制，cpu负载大。并发迭代操作多时可以选择
 * @author yxd
 *
 */
public class TestCopyOnWriteArrayList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 0;i<10;i++) {
			new Thread(new HelloThread()).start();
		}
	}

}

class HelloThread implements Runnable {

	// CopyOnWriteArrayList 适合保持数据安全的情况下遍历操作多的场景使用
	private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

	static {
		list.add("AA");
		list.add("BB");
		list.add("CC");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Iterator<String> it = list.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
			list.add("AA");
		}
	}

}