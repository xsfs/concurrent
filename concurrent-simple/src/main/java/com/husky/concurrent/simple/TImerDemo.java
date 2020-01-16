package com.husky.concurrent.simple;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器框架示例
 * @author dengweichang
 */
public class TImerDemo {

	public static void main(String[] args) throws InterruptedException {
		TimerTask timerTask = new TimerTask() {
			private int i;
			@Override
			public void run() {
				System.out.println("alarm going off");
				if (i++ > 3) {
					System.out.println("终止任务");
					cancel();
				}
			}
		};
		Timer timer = new Timer();
		timer.schedule(timerTask, 0, 100);
		Thread.sleep(1000);
		System.out.println("终止程序");
		System.out.println(timer.purge());
		timer.cancel();
	}

}
