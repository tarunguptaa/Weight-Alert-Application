package com.egen.egen_be_challenge.utilities;

import java.io.Serializable;

public class Sensor implements Serializable {

	private static final long serialVersionUID = -32715445682403486L;
	private long timeStamp;
	private int value;

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
