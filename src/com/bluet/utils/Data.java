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
	public static final byte TAG_FILL  = 0x14;
	public static final byte TAG_WASH  = 0x15;
	public static final byte TAG_EMPTY = 0x16;
	public static final byte TAG_WORK_MODE = 0x11;
	public static final byte TAG_WORK_STATE = 0x12;//工作流程
	public static final byte TAG_RUN_STATE = 0x13;
	public static final byte TAG_BWOL=0X17;
	
	
	
	public abstract interface DataChangeListener {
		public abstract void dataChanged();
	}
	
	private ArrayList<DataChangeListener> listeners = new ArrayList<DataChangeListener>();
	
	private static int mCurrentSpeed;
	private static int mFillTotalVolume;
	private static int mWashTotalVolume;
	private static int mEmptyTotalVolume;

	private static int mWorkState;
	private static int mBowl;
	private static int mWorkMOde;
	
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
	
	public void setFillTotalVolume(int FillTotalVolume) {
		mFillTotalVolume = FillTotalVolume;
		notifyChanged();
	}
	public void setWashTotalVolume(int WashTotalVolume) {
		mWashTotalVolume = WashTotalVolume;
		notifyChanged();
	}
	public void setEmptyTotalVolume(int EmptyTotalVolume) {
		mEmptyTotalVolume = EmptyTotalVolume;
		notifyChanged();
	}
	public void setWork_state(int Work_state) {
		mWorkState = Work_state;
		notifyChanged();
	}
	public void GetWorkmodeFromBlue(int Workmode) {
		mWorkMOde = Workmode;
		notifyChanged();
	}
	public void GetBowlFromBlue(int bowl) {
		mBowl = bowl;
		notifyChanged();
	}
	public int getCurrentSpeed() {
		return mCurrentSpeed;
	}
	
	public String GetCurrentInfo(byte tag) {
		String info = new String();
		switch (tag) {
		case TAG_FILL:
			info = "进血:" + String.valueOf(mFillTotalVolume);
			break;
		case TAG_WASH:
			info = "清洗:" + String.valueOf(mWashTotalVolume);
			break;
		case TAG_EMPTY:
			info = "清空:" + String.valueOf(mEmptyTotalVolume);
			break;
		case TAG_WORK_STATE:
			info = "状态:" + String.valueOf(mWorkState);
			break;	
		case TAG_WORK_MODE:
			info = "模式:" + String.valueOf(mWorkState);
			break;	
		case TAG_RUN_STATE:
			info = "暂停:" + String.valueOf(mWorkState);
			break;	
		default:
			break;
		}
		
		return info;
	}

	
	public String GetInfo_Fill() {
		return  String.valueOf(mFillTotalVolume);
	}

	public String allInfo_wash(){
		return  String.valueOf(mWashTotalVolume);
		
	}
	
	public String allInfo_empty(){
	//	mEmptyTotalVolume = 12345;
		return  String.valueOf(mEmptyTotalVolume);

		
	}
	public int getWork_State() {
		return mWorkState;
	}
	public int getBowl() {
		return mBowl;
	}
	public int getwork_mode() {
		return mWorkMOde;
	}
	public String hex2str(int datatochange){
		return  String.valueOf(datatochange);
		
	}


}
