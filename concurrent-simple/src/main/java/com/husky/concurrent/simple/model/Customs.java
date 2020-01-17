package com.husky.concurrent.simple.model;

import lombok.Setter;

/**
 * producer and consumer test
 * 关卡测试
 * @author dengweichang
 */
public class Customs {

	public static void main(String[] args) {
		CustomsSkip skip = new CustomsSkip(false, 0);
		Runnable runnable = skip::passCustoms;
		new Thread(runnable, "A").start();
		new Thread(runnable, "B").start();
		new Thread(runnable, "C").start();
		new Thread(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			skip.setPass(true);
			skip.notifyAll();
		}).start();
	}


}

class CustomsSkip {
	@Setter
	private volatile boolean pass;
	private int count;

	public CustomsSkip(boolean pass, int count) {
		this.pass = pass;
		this.count = count;
	}

	synchronized void passCustoms() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "--custom waiting");
		count++;
		while (!pass) {
			try {
				wait();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + "--custom waiting");
	}
}
