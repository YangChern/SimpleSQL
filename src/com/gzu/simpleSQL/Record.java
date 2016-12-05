package com.gzu.simpleSQL;

import java.util.Random;

public class Record {
	private int A;
	private String B;
	//private String C;
	
	@Override
	public String toString() {
		return A+"#"+B;
	}
	
	public String getRecordString(){
		int A =Math.abs( new Random().nextInt());
		String B = "AAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAA";
		return A+"#"+B;
	}
	

	public Record() {
		super();
	}

	public Record(String line) {
		super();
		String [] t = line.split("#");
		this.A = Integer.valueOf(t[0]);
		this.B = t[1];
		//this.C = t[2];
	}


	public int getA() {
		return A;
	}


	public void setA(int a) {
		A = a;
	}


	public String getB() {
		return B;
	}


	public void setB(String b) {
		B = b;
	}
	
}