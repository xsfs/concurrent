package com.husky.concurrent.simple.model;

/**
 * 账户线程问题类
 * @author dengweichang
 * @see #withdraw(int) 需要使用synchronized保持同步
 */
public class CheckingAccount {
	private int balance;

	public CheckingAccount(int balance) {
		this.balance = balance;
	}

	/**
	 * 撤销
	 * @param amount	撤销金额
	 */
	public boolean withdraw(int amount) {
		if (amount <= balance) {
			try {
				Thread.sleep((int) (Math.random() * 200));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			balance -= amount;
			System.out.println(balance);
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		final CheckingAccount checkingAccount = new CheckingAccount(100);
		Runnable runnable = () -> {
			String name = Thread.currentThread().getName();
			for (int i = 0; i < 10; i++) {
				System.out.println(name + "withdraws $10:" + checkingAccount.withdraw(10));
			}
		};
		Thread husband = new Thread(runnable, "husband");
		Thread wife = new Thread(runnable, "wife");
		husband.start();
		wife.start();
	}
}
