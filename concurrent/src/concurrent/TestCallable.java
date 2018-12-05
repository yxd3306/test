package concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable接口使用-->>有返回值的线程
 * @author yxd
 *
 */
public class TestCallable {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CallableDemo cd = new CallableDemo();
		FutureTask<Integer> cdTask = new FutureTask<>(cd);
		new Thread(cdTask).start();
		
		// FutureTask<T>.get() 阻塞等待所有线程执行完
		System.out.println(cdTask.get());
		System.out.println("-------------------------");
	}
	
	
}

class CallableDemo implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		int sum = 0;
		for(int i =0;i<=10;i++)
			sum+=i;
		return sum;
	}
	
}
