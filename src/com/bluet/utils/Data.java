package com.bluet.utils;

import java.util.ArrayList;

public class Data {
	private static Data instance = null;    
	
	public static Data getInstance() {
		if (instance == null) {   
			 instance = new Data();
		}
		return instance;
	}
	
	private Data() {
		
	}
	
	public static final byte TAG_SPEED= 0x03;
//	public static byte SPEED= 0x01;
//	public static byte SPEED= 0x01;
//	public static byte SPEED= 0x01;
	
	public abstract interface DataChangeListener {
		public abstract void dataChanged();
	}
	
	private ArrayList<DataChangeListener> listeners = new ArrayList<DataChangeListener>();
	
	private static int mCurrentSpeed;
	
	private void notifyChanged(){
		
		for (DataChangeListener l : listeners) {
			l.dataChanged();
		}
	}
	
	public void addListener(DataChangeListener listener) {
		listeners.add(listener);
	}
	
	
	public void setCurrentSpeed(int currentSpeed) {
		mCurrentSpeed = currentSpeed;
		notifyChanged();
	}
	
	public int getCurrentSpeed() {
		return mCurrentSpeed;
	}
	
	public String GetCurrentInfo(byte tag) {
		String info = new String();
		switch (tag) {
		case TAG_SPEED:
			info = "速度:" + String.valueOf(mCurrentSpeed);
			break;
		default:
			break;
		}
		
		return info;
	}
	
	public String allInfo() {
		return "速度:" + String.valueOf(mCurrentSpeed);
	}

}
