package com.husky.concurrent.simple;

/**
 * 线程生命特征
 * @author dengweichang
 * <p>
 * 一个线程的状态包含5部分：
 * <li>
 * @see Thread#name		线程名称
 * @see Thread#isAlive() 	存活标识
 * @see Thread#getState() 	执行状态 {@link java.lang.Thread.State}
 * @see Thread#priority		优先级
 * @see Thread#daemon 	是否为守护线程
 * </li>
 */
@SuppressWarnings("all")
public class Life {

	/**
	 * 线程默认名称，
	 */
	static void defaultName() {
		Thread thread = new Thread(() -> System.out.println("线程执行1"));
		System.out.println(thread.getName());
	}

	/**
	 * 自定义线程名称
	 * <p>若传入的线程名为空则 throw null pointer exception
	 * @param name	线程名称
	 */
	static void definedName(String name) {
		Thread thread = new Thread(() -> System.out.println("自定义名称"), name);
		System.out.println(thread.getName());
	}

	static void alive() {
		Thread thread = new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "暂停10秒的线程");
		info(thread);
		thread.start();
		info(thread);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		info(thread);
	}

	/**
	 * 当前环境可提供的处理器数量，即线程最大并行数
	 */
	static void availableNum() {
		System.out.println(Runtime.getRuntime().availableProcessors());
	}

	static void daemon() throws InterruptedException {
		Thread thread = new Thread(() -> {
			System.out.println("是否为守护线程:" + Thread.currentThread().isDaemon());
			new Thread(() -> {
				try {
					System.out.println(Thread.currentThread().getName());
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		});
		thread.setDaemon(true);
		thread.start();
		Thread.sleep(1000);
		int count = 1;
		while (count < 10) {
			System.out.println(thread.getName() + "----" + thread.getState());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count ++;
		}
	}

	public static void main(String[] args) throws Exception {
//		defaultName();
//		definedName("这里可以是空字符串，但不可为null");
//		alive();
		daemon();
	}

	/**
	 * 线程状态 {@link java.lang.Thread.State}
	 * @param thread
	 */
	private static void info(Thread thread) {
		System.out.println(thread.isAlive() + "---" + thread.getState());
	}
}
