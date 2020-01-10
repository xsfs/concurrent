package com.husky.concurrent.simple;

/**
 * 程序活跃性
 * @author dengweichang
 * @see DeadLock	死锁
 * TODO:活锁
 * TODO:饿死
 */
@SuppressWarnings("all")
public class Lock {

	public static void main(String[] args) throws InterruptedException {
//		new DeadLock().deadTest();
		StoppableThread stoppableThread = new StoppableThread();
		stoppableThread.start();
		Thread.sleep(100);
		stoppableThread.stopThread();
	}

	private static void volatileTest() throws InterruptedException {
		StoppableThread stoppableThread = new StoppableThread();
		stoppableThread.start();
		Thread.sleep(100);
		stoppableThread.stopThread();
	}


}

/**
 * 多个线程互斥持有的资源，互相等待
 */
@SuppressWarnings("all")
class DeadLock{
	private final Object lock1 = new Object();
	private final Object lock2 = new Object();

	public void instanceMethod1() {
		synchronized (lock1) {
			synchronized (lock2) {
				System.out.println("method 1");
			}
		}
	}

	public void instanceMethod2() {
		synchronized (lock2) {
			synchronized (lock1) {
				System.out.println("method 2");
			}
		}
	}

	public void deadTest() {
		new Thread(() -> {
			while (true) {
				instanceMethod1();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(() -> {
			while (true) {
				instanceMethod2();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}

class StoppableThread extends Thread {
	private boolean stopped;

	@Override
	public void run() {
		int i = 0;
		while (!stopped) {
			System.out.println("running" + i++);
		}
	}

	void stopThread() {
		stopped = true;
		System.out.println("------stop------");
	}
}