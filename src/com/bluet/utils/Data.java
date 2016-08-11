package com.bluet.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import android.R.string;
import android.widget.TextView;

public class Data {
	private static Data instance = null;

	public static Data getInstance() {
		if (instance == null) {
			instance = new Data();
		}
		return instance;
	}

	// private Data() {

	// }
	public static final byte TAG_PATIENT_NAME = 0x01;
	public static final byte TAG_PRODUCT_NUM = 0x02;
	public static final byte TAG_SW = 0x03;
	public static final byte TAG_HW = 0x04;
	
	public static final byte TAG_SPEED = 0x03;
	public static final byte TAG_WORK_MODE = 0x11;
	public static final byte TAG_WORK_STATE = 0x12;// 工作流程
	public static final byte TAG_BWOL = 0X13;
	public static final byte TAG_FILL = 0x14;
	public static final byte TAG_WASH = 0x15;
	public static final byte TAG_EMPTY = 0x16;
	public static final byte TAG_RUN_STATE = 0x17;
	public static final byte TAG_PUMP_SPEED = 0x18;

	public static final byte TAG_FILL_SPEED_SET = 0x31;
	public static final byte TAG_WASH_SPEED_SET = 0x32;
	public static final byte TAG_EMPTY_SPEED_SET = 0x33;
	public static final byte TAG_WASH_VOLUME_SET = 0x34;
	public static final byte TAG_AUTO_RUN_SET = 0x35;
	

	public abstract interface DataChangeListener {
		public abstract void dataChanged();
	}

	private ArrayList<DataChangeListener> listeners = new ArrayList<DataChangeListener>();

	private static int mCurrentSpeed = 0;
	private static int mFillTotalVolume = 0;
	private static int mWashTotalVolume = 0;
	private static int mEmptyTotalVolume = 0;

	private static int mWorkState = 0;
	private static int mRunState = 0;
	private static int mBowl = 0;
	private static int mWorkMOde = 0;

	private static int mFillSpeed = 0;
	private static int mWashSpeed = 0;
	private static int mEmptySpeed = 0;
	private static int mPumpSpeed = 0;

	private static int mMaxWashVolume = 0;
	private static int mAutoRunVolume = 0;

	private static String Save_lag = "";

	private static byte[] patient_name = null;
	private static byte[] patient_num = null;
	private static byte[] SerialNumber = null;
	private static byte[] ProductNum = null;
	private static byte[] SoftVersen = null;
	private static byte[] HardVersen = null;

	private void notifyChanged() {

		for (DataChangeListener l : listeners) {
			l.dataChanged();
		}
	}

	public void addListener(DataChangeListener listener) {
		listeners.add(listener);
	}

	public static String getString(byte[] bytes, String charsetName) {
		return new String(bytes, Charset.forName(charsetName));
	}

	public static String getString(byte[] bytes) {
		return getString(bytes, "GBK");
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

	public void SetWorkmode(int Workmode) {
		mWorkMOde = Workmode;
		notifyChanged();
	}

	public void SetBowl(int bowl) {
		mBowl = bowl;
		notifyChanged();
	}

	public void SetRunState(int runstate) {
		mRunState = runstate;
		notifyChanged();
	}

	public void SetPmpSpeed(int PmpSpeed) {
		mPumpSpeed = PmpSpeed;
		notifyChanged();
	}

	public void SetFillSpeed(int fillspeed) {
		mFillSpeed = fillspeed;
		notifyChanged();
	}

	public void SetWashSpeed(int washspeed) {
		mWashSpeed = washspeed;
		notifyChanged();
	}

	public void SetEmptySpeed(int emptyspeed) {
		mEmptySpeed = emptyspeed;
		notifyChanged();
	}

	public void SetMaxWahVolume(int MaxWahVolume) {
		mMaxWashVolume = MaxWahVolume;
		notifyChanged();
	}

	public void SetAutoRunVolume(int AutoRunVolume) {
		mAutoRunVolume = AutoRunVolume;
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
			info = "暂停:" + String.valueOf(mRunState);
			break;
		case TAG_WASH_VOLUME_SET:
			info = "最大清洗量" + String.valueOf(mMaxWashVolume);
			break;

		default:
			break;
		}

		return info;
	}

	public String GetInfo_Fill() {
		return String.valueOf(mFillTotalVolume);
	}

	public String GetInfo_wash() {
		return String.valueOf(mWashTotalVolume);

	}

	public String GetInfo_empty() {
		// mEmptyTotalVolume = 12345;
		return String.valueOf(mEmptyTotalVolume);
	}

	public int getWork_State() {
		return mWorkState;
	}

	public int getRun_State() {
		return mRunState;
	}

	public int getBowl() {
		return mBowl;
	}

	public int getwork_mode() {
		return mWorkMOde;
	}

	public int getPump_speed() {
		return mPumpSpeed;
	}

	public int GetFillspeed() {
		return mFillSpeed;
	}

	public int GetWashspeed() {
		return mWashSpeed;
	}

	public int GetEmptyspeed() {
		return mEmptySpeed;
	}

	public int GetMaxWashVolume() {
		return mMaxWashVolume;
	}

	public int GetAutoRunVolume() {
		return mAutoRunVolume;
	}

	public static String getSave_lag() {
		Save_lag = "hello,word";
		return Save_lag;
	}

	public static void setSave_lag(String save_lag) {
		Save_lag = save_lag;
	}

	public  String getPatient_name() {
		String t = null;
		if (patient_name != null) {
			try {
				t = new String(patient_name, "gb2312");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;
	}

	public  void setPatient_name(byte[] patient_name) {
		Data.patient_name = patient_name;

	}

	public static byte[] getPatient_num() {
		return patient_num;
	}

	public static void setPatient_num(byte[] patient_num) {
		Data.patient_num = patient_num;
	}

	public static byte[] getSerialNumber() {
		return SerialNumber;
	}

	public void setSerialNumber(byte[] serialNumber) {
		SerialNumber = serialNumber;
	}

	public static byte[] getProductNum() {
		return ProductNum;
	}

	public static void setProductNum(byte[] productNum) {
		ProductNum = productNum;
	}

	public String getSoftVersen() {
		String t = null;
		if (SoftVersen != null) {
			try {
				t = new String(SoftVersen, "gb2312");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;
	}

	public void setSoftVersen(byte[] softVersen) {
		SoftVersen = softVersen;
	}

	public  String getHardVersen() {
		String t = null;
		if (HardVersen != null) {
			try {
				t = new String(HardVersen, "gb2312");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;
	}

	public void setHardVersen(byte[] hardVersen) {
		HardVersen = hardVersen;
	}

}
