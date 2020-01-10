package com.husky.concurrent.simple.model;

public class IDClass {
	private static int counter;
	public static synchronized int getID() {
		return counter = counter + 1;
	}
}
