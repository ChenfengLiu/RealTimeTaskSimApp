package model;

/**
 * Created by chenfeng on 26/11/16.
 */
public class Task {

	// from user input
	private int c, p;

	// program assigned & generated
	private int id;

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	////////////////////////////////////////////////////////////////////////////////////////////////
	public Task(int c, int p, int id) {
		this.c = c;
		this.p = p;
		this.id = id;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Getters and Setters
	////////////////////////////////////////////////////////////////////////////////////////////////
	public void setC(int c) {
		this.c = c;
	}

	public int getC() {
		return c;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getP() {
		return p;
	}

	public int getId() {
		return id;
	}

}