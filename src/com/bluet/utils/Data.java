package com.bluet.utils;
import java.util.ArrayList;

import android.R.string;

public class Data {
	private static Data instance = null;    
	
	public static Data getInstance() {
		if (instance == null) {   
			 instance = new Data();
		}
		return instance;
	}
	
//	private Data() {
		
//	}
	
	public static final byte TAG_SPEED = 0x03;
	public static final byte TAG_FILL  = 0x03;
	public static final byte TAG_WASH  = 0x04;
	public static final byte TAG_EMPTY = 0x05;
	public static final byte TAG_WORK_MODE = 0x11;
	
	
	
	public abstract interface DataChangeListener {
		public abstract void dataChanged();
	}
	
	private ArrayList<DataChangeListener> listeners = new ArrayList<DataChangeListener>();
	
	private static int mCurrentSpeed;
	private static int mFillTatalVolume;
	private static int mWashTatalVolume;
	private static int mEmptyTatalVolume;

	private static int mWorkMOde;
	private static string mWorkMOde1;
	
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
	
	public void setFillTotalVolume(int FillTatalVolume) {
		mFillTatalVolume = FillTatalVolume;
		notifyChanged();
	}	
	public int getCurrentSpeed() {
		return mCurrentSpeed;
	}
	
	public String GetCurrentInfo(byte tag) {
		String info = new String();
		switch (tag) {
		case TAG_FILL:
			info = "速度:" + String.valueOf(mFillTatalVolume);
			break;
		case TAG_WASH:
			info = "清洗:" + String.valueOf(mWashTatalVolume);
			break;
		case TAG_EMPTY:
			info = "清空:" + String.valueOf(mEmptyTatalVolume);
			break;
		default:
			break;
		}
		
		return info;
	}

	
	public String GetInfo_Fill() {
		return  String.valueOf(mCurrentSpeed);
	}

	public String allInfo_wash(){
		return  String.valueOf(mFillTatalVolume)+"mL";
		
	}
	public String hex2str(int datatochange){
		return  String.valueOf(datatochange);
		
	}
}
