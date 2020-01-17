package com.husky.concurrent.simple;

import lombok.extern.slf4j.Slf4j;

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
		volatileTest();
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

/**
 * 同步方法（执行完成后释放线程锁）/线程切换/CPU空闲会强制读取一次主内存
 */
@Slf4j
class StoppableThread extends Thread {
	private boolean stopped;

	@Override
	public void run() {
		while (true) {
			if (stopped) {
				log.info("running stop");
				break;
			} else {
				test();
			}
		}
	}

	void stopThread() {
		stopped = true;
		System.out.println("------stop------");
	}

	private void test () {

	}
}