package com.bluet.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bluet.massistant.BluetoothChatService;
import com.bluet.massistant.DeviceListActivity;

public class BluetoothClient {
	// Intent request codes
	public static final int REQUEST_CONNECT_DEVICE = 1;
	public static final int REQUEST_ENABLE_BT = 2;
	// String buffer for outgoing messages
	public StringBuffer mOutStringBuffer;
	// Local Bluetooth adapter
	public BluetoothAdapter mBluetoothAdapter = null;

	public BluetoothChatService mChatService = null;
	public Context mContext = null;
	public static final int MESSAGE_READ = 2;
	
	public BluetoothClient(Context context) {
		mContext = context;
	}
	
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				decode(readBuf, msg.arg1);
				break;
			}
		}

	};

	public boolean init() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		// 如果设备没有蓝牙模块。程序退出
		if (mBluetoothAdapter == null) {
			return false;
		}
		return true;

	}

	public void sendHead() {
		byte[] buf = new byte[7];
		buf[0] = (byte) 0xAA;
		buf[1] = (byte) 0x55;
		buf[2] = (byte) 0x01;
		buf[3] = (byte) my_add;
		buf[4] = (byte) target_add;
		buf[5] = (byte) 0x02;
		buf[6] = (byte) 0x04;
		sendMessage(buf);
	}
	public void sendTail() {
		byte[] buf = new byte[2];
		buf[0] = (byte) 0xcc;
		buf[1] = (byte) 0x33;
		sendMessage(buf);
	}

	public void sendMessage(byte[] send) {
		if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
			return;
		}
		mChatService.write(send);
		// Reset out string buffer to zero and clear the edit text field
		mOutStringBuffer.setLength(0);
	}


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				String address = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				// Get the BLuetoothDevice object
				BluetoothDevice device = mBluetoothAdapter
						.getRemoteDevice(address);
				// Attempt to connect to the device
				mChatService.connect(device);
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				setupChat();
			}
		}
	}

	public void setupChat() {
		// Initialize the BluetoothChatService to perform bluetooth connections
		mChatService = new BluetoothChatService(mContext, mHandler);
		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
	}
	
	int findhead = 0;
	int buf_index = 0;
	int frame_len = 0;
	boolean head_en = false;
	byte[] databuf = new byte[1024];
	byte my_add = 0x01, target_add = 0x02;
	private void decode(byte[] buf, int len) {
		for (int i = 0; i < len; i++) {
			if (head_en == false) {
				switch (findhead) {
				case 0:
					if (buf[i] == (byte) 0xAA)
						findhead++;
					else
						findhead = 0;
					break;
				case 1:
					if (buf[i] == (byte) 0x55)
						findhead++;
					else
						findhead = 0;
					break;
				case 2:
					if (buf[i] == (byte) 0x01)
						findhead++;
					else
						findhead = 0;
					break;
				case 3:
					if (buf[i] == (byte) target_add)
						findhead++;
					else
						findhead = 0;
					break;
				case 4:
					if (buf[i] == (byte) my_add)
						findhead++;
					else
						findhead = 0;
					break;
				case 5:
					if (buf[i] == (byte) 0x01) {
						head_en = true;
						buf_index = 0;
						frame_len = 0;
						Log.i("Decode", "find head!");
					}
					findhead = 0;
					break;
				default:
					findhead = 0;
					break;
				}
			} else {

				if (buf_index == 0) {
					frame_len = buf[i] & 0xff;
					Log.i("Decode", "frame_len " + frame_len);
				}
				databuf[buf_index++] = buf[i];

				if (buf_index == (frame_len + 5)) { // 收完一帧了。
					get_data(databuf, frame_len + 5);
					findhead = 0;
					head_en = false;
					buf_index = 0;
					frame_len = 0;
				}
			}
		}
	}
	
	void get_data(byte[] in, int len) {
		int i, isget = 0;
		short sum = 0, temp;
		Log.i("Decode", "本帧需要校验字节" + ((in[0] & 0xff)));
		for (i = 0; i < (in[0] & 0xff); i++) {
			sum += (in[i + 1] & 0xff);
		}
		temp = (short) (((in[len - 3] & 0xff) << 8) | ((in[len - 4] & 0xff)));
		if (temp != sum) {
			Log.i("Decode", "check sum err!");
			return;
		}
		Log.i("Decode", "check sum OK!");
		int index = 2;
		isget = 0;
		do { // 提取数据
			Log.i("Decode", "当前   " + index);
			int tag_id = index;
			
			index++; // 字节数量
			if (in[index] == (byte) 0x01) {
				temp = (short) (in[index + 1] & 0xff);
				Log.i("Decode", "1字节 ");
				
				index += 2;
				isget += 3;
			} else if (in[index] == (byte) 0x02) {
				Log.i("Decode", "2字节 ");
				temp = (short) (((in[index + 2] & 0xff) << 8) | ((in[index + 1] & 0xff)));
				index += 3;
				isget += 4;
			}
			Log.i("Value = ", String.valueOf(temp));
			
			switch (in[tag_id]) {
			case Data.TAG_FILL:
				Data.getInstance().setFillTotalVolume(temp);
				break;
			case Data.TAG_WASH:
				Data.getInstance().setWashTotalVolume(temp);
				break;
			case Data.TAG_EMPTY:
				Data.getInstance().setEmptyTotalVolume(temp);
				break;
			case Data.TAG_WORK_STATE:
				Data.getInstance().setWork_state(temp);
				break;//
			case Data.TAG_WORK_MODE:
				Data.getInstance().GetWorkmodeFromBlue(temp);
				break;//				//TAG_WORK_MODE
			case Data.TAG_RUN_STATE:
				Data.getInstance().setWork_state(temp);
				break;//				//TAG_WORK_MODE GetBowlFromBlue
			case Data.TAG_BWOL:
				Data.getInstance().setWork_state(temp);
				break;//
			default:
				break;
			}
			
			Log.i("Decode", Data.getInstance().GetCurrentInfo(in[tag_id]));
			
//			if (in[index] == (byte) 0x01) {// 速度
//				Log.i("Decode", "速度 ");
//			} else if (in[index] == (byte) 0x02) {// 加速度
//				Log.i("Decode", "加速度 ");
//			} else if (in[index] == (byte) 0x03) {// 高度
//				Log.i("Decode", "高度 ");
//			} else if (in[index] == (byte) 0x04) {// 电量
//				Log.i("Decode", "电量 ");
//			} else if (in[index] == (byte) 0x05) {// 左转向
//				Log.i("Decode", "左转向 ");
//			}
		} while (isget < len - 6);

	}
	
	
}