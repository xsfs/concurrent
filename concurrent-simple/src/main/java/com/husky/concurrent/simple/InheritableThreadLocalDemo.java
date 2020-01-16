package com.husky.concurrent.simple;

/**
 * 可继承的线程局部变量
 * @author dengweichang
 * <p>
 *	InheritableThreadLocal可以获取到父线程的线程局部变量，
 * 但子线程可以重新设置，与父线程独立，父线程与此独立
 * </p>
 */
public class InheritableThreadLocalDemo {

	private static final InheritableThreadLocal<Integer> intVal = new InheritableThreadLocal<>();

	public static void main(String[] args) {
		Runnable rp = () -> {
			intVal.set(10);
			Runnable runnable = () -> {
				Thread currentThread = Thread.currentThread();
				String name = currentThread.getName();
				System.out.println(name + " " + intVal.get());
				intVal.set(100);
				System.out.println(name + " " + intVal.get());
			};
			Thread child = new Thread(runnable, "child");
			child.start();
			System.out.println(Thread.currentThread().getName() + " " + intVal.get());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " " + intVal.get());
		};
		new Thread(rp, "parent").start();
	}
}
