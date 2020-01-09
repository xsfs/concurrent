package com.husky.concurrent.simple;

/**
 * 线程操作
 * @author dengweichang
 * @see #join()
 * @see #sleep()
 */
@SuppressWarnings("all")
public class Operator {

	private static int result;

	public static void main(String[] args) throws Exception {
//		join();
//		sleep();
		test();
	}

	/**
	 * @see Thread#join()
	 * wait如何释放，详情见 jvm JavaThread::exit
	 * wait方法会释放该对象在当前线程的锁，TODO:示例方法待补充
	 */
	private static void join() {
		Thread currentThread = Thread.currentThread();
		Thread thread = new Thread(() -> {
			result = compute(0);
			while (true) {
				if (currentThread.interrupted()) {
					return;
				} else {
					System.out.println(currentThread.getState());
				}
			}
		});
		thread.start();
		System.out.println(result);
		try {
			thread.join(1000);
			System.out.println(thread.getState());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

	@SuppressWarnings("all")
	private static void sleep() throws InterruptedException {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String name = Thread.currentThread().getName();
				int count = 0;
				for (;;) {
					if (Thread.interrupted()) {
						System.out.println("-----------线程被中断");
						return;
					} else {
						System.out.println(name + count);
					}
				}
			}
		};

		Thread thread1 = new Thread(runnable, "第一个");
		Thread thread2 = new Thread(runnable, "第二个");
//		thread1.setDaemon(true);
//		thread2.setDaemon(true);
		thread1.start();
		thread2.start();
		Thread.sleep(1000);
		thread1.interrupt();
		thread2.interrupt();
	}

	@SuppressWarnings("all")
	private static int compute(int value) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		for (int i = 0; i < 1000000000L; i++) {
			value++;
		}
		return value;
	}


	/**
	 * 测试线程中断
	 * @see Thread#interrupt()
	 * 若线程在正常interrupt并不能停止线程运行
	 * @throws InterruptedException
	 */
	private static void test() throws InterruptedException {
		Thread thread = new Thread(() -> {
			while (true) {
				System.out.println(Thread.currentThread().getState());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("interrupted");
					break;
				}
			}
		});
		thread.start();
		Thread.sleep(2000);
		thread.interrupt();
	}
}
