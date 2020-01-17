package com.husky.concurrent.simple;

/**
 * 线程局部变量示例
 * @author dengweichang
 * <o>
 *     ThreadLocal的值并不能传递至子线程
 * </o>
 */
public class ThreadLocalDemo {
	private static volatile ThreadLocal<String> userID = new ThreadLocal<String>();

	public static void main(String[] args) {
		Runnable runnable = () -> {
			userID.set("parent");
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "  " + userID.get());
			}, "child").start();
			System.out.println(Thread.currentThread().getName() +" "+userID.get());
		};
		new Thread(runnable, "parent").start();
	}
}
