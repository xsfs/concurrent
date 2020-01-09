package com.husky.concurrent.simple;

/**
 * 线程创建示例
 * @author dengweichang
 */
@SuppressWarnings("all")
public class Builder {

	/**
	 * 使用匿名类创建一个runnable
	 * @return	{@link Runnable}
	 */
	static Runnable runnableBuild() {
		return new Runnable() {
			@Override
			public void run() {
				System.out.println("使用匿名类创建");
			}
		};
	}

	/**
	 * 使用lambda方式创建runnable
	 * @return	{@link Runnable}
	 */
	static Runnable runnableBuildWithLambda() {
		return () -> System.out.println("使用lambda创建");
	}

	/**
	 * 使用匿名类创建thread
	 * @return
	 */
	static Thread threadBuild() {
		return new Thread() {
			@Override
			public void run() {
				System.out.println("不使用runnable");
			}
		};
	}

	/**
	 * 通过runnable创建thread
	 * @param runnable	{@link Runnable}
	 * @return	{@link Thread}
	 */
	static Thread threadBuild(Runnable runnable) {
		return new Thread(runnable);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			threadBuild().start();
			threadBuild(runnableBuild()).start();
			threadBuild(runnableBuildWithLambda()).start();
			System.out.println("=========");
		}
	}

}
