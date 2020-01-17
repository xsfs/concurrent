package com.husky.concurrent.simple;

/**
 * producer and consumer
 * @author dengweichang
 */
public class PC {

	public static void main(String[] args) {
		Shared shared = new Shared();
		new Producer(shared).start();
		new Consumer(shared).start();
	}
}

class Producer extends Thread {
	private final Shared shared;

	Producer(Shared shared) {
		this.shared = shared;
	}

	@Override
	public void run() {
		for (char ch = 'A'; ch <= 'Z'; ch++) {
			synchronized (shared) {
				shared.setSharedChar(ch);
				System.out.println(ch + "produced by producer.");
			}
		}
	}
}

class Consumer extends Thread {
	private final Shared shared;

	Consumer(Shared shared) {
		this.shared = shared;
	}

	@Override
	public void run() {
		char ch;
		do {
			synchronized (shared) {
				ch = shared.getSharedChar();
				System.out.println(ch + "consumed by consumer.");
			}
		} while (ch != 'Z');
	}
}

class Shared {
	private char c;
	private volatile boolean writeable = true;

	synchronized char getSharedChar() {
		while (writeable) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		writeable = true;
		notify();
		return c;
	}

	synchronized void setSharedChar (char c) {
		while (!writeable) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.c = c;
		writeable = false;
		notify();
	}

}