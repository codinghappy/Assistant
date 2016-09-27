package com.bluet.utils;


import java.io.File;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.bluet.massistant.BluetoothChatService;
import com.bluet.massistant.DeviceListActivity;
import com.bluet.massistant.HomeFragmentActivity;
import com.bluet.massistant.R;
import com.bluet.massistant.Utils;

public class BluetoothClient {
	private HomeFragmentActivity homeActivity = null;
	// Intent request codes
	public static final int REQUEST_CONNECT_DEVICE = 1;
	public static final int REQUEST_ENABLE_BT = 2;
	// String buffer for outgoing messages
	public StringBuffer mOutStringBuffer;
	// Local Bluetooth adapter
	public BluetoothAdapter mBluetoothAdapter = null;

	public BluetoothChatService mChatService = null;
	public Context mContext = null;
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;

	private BluetoothStateChange mBluetoothStateChange;

	public interface BluetoothStateChange {
		void onStateChanged(int state);
	}

	public BluetoothClient(HomeFragmentActivity homeActivity,
			Context context,
			BluetoothStateChange bluetoothStateChange) {
		homeActivity = homeActivity;
		mContext = context;
		mBluetoothStateChange = bluetoothStateChange;
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				
				 decode(readBuf, msg.arg1);
				//Decode_Packet(readBuf, msg.arg1);
				break;
			case MESSAGE_STATE_CHANGE:
				switch (msg.arg1) {
				case BluetoothChatService.STATE_CONNECTED:
					findhead = 0;
					buf_index = 0;
					frame_len = 0;
					head_en = false;
				case BluetoothChatService.STATE_CONNECTING:
				case BluetoothChatService.STATE_LISTEN:
				case BluetoothChatService.STATE_NONE:
					mBluetoothStateChange.onStateChanged(msg.arg1);
					break;
				}
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
		byte[] buf = new byte[6];
		buf[0] = (byte) 0xAA;
		buf[1] = (byte) 0x55;
		buf[2] = (byte) 0x01;
		buf[3] = (byte) my_add;
		buf[4] = (byte) target_add;
		buf[5] = (byte) 0x02;
		//buf[6] = (byte) 0x04;
		sendMessage(buf);
	}

	public void sendContent(int contentTag, int contentLen, byte[] content) {
		byte[] buf = new byte[contentLen + 6];
		//byte contentvsein = 1;
		int temp;
		int crc_sum = 0;
		buf[1] = (byte) (0X01);
		buf[0] = (byte) (contentLen + 4);
		buf[2] = (byte) contentTag;
		buf[3] = (byte) contentLen;
		for (temp = 0; temp < contentLen; temp++) {
			buf[4 + temp] = content[temp];
			crc_sum += content[temp];
		}
		buf[contentLen + 6 - 2] = (byte) (crc_sum / 256);
		buf[contentLen + 6 - 1] = (byte) (crc_sum % 256);

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
	
	byte[] PacketHead = { (byte) 0xAA, 0x55 };
	int PacketHeadLen = 1;
	boolean FindHead_EN = false ;
	int Frame_lenth=0;
	byte[] RX_databuf = new byte[1024];
	byte  Frame_ver;
	
	private void Decode_Packet(byte[] buf, int len) {
		for (int i = 0; i < len; i++) {
			if (PacketHead[0] == buf[i] && PacketHead[1] == buf[i + 1]) {
				Log.i("Decode", "find head!");
				FindHead_EN = true;
				Frame_ver = (byte) (buf[i + 2] & 0xff);
				Frame_ver = buf[i + 3];
				Frame_ver = buf[i + 4];
				Frame_ver = buf[i + 5];
				
				Frame_lenth = buf[i + 6]; // 待验证 查找数据帧长度
				Log.i("Decode", "frame_len " + Frame_lenth);
				int RX_Crc_sum = buf[i + 5 + Frame_lenth];
				int Crc_SUM = 0;
				for (int j = 0; j < Frame_lenth; j++) {
					RX_databuf[j] = buf[i + 6];
					Crc_SUM = Crc_SUM + RX_databuf[j];
				}
				 if(Crc_SUM == RX_Crc_sum){ // rx right
					 //接收数据帧正确  进行数据解析
					 Log.i("Decode", "check sum OK!");
					 break;
				 }
				 else{// rx error 
					 Log.i("Decode", "check err!");
				 }
			}
			else{
				FindHead_EN = false;
				
			}
			// if(FindHead_EN== true){
			//
			// }
		}
	}

	private void Decode_data(byte[] databuf, int len) {
		int Read_ptr = 0;// 读取数据的位置
		byte Data_tag;
		byte Data_len;
		byte data_temp[] = { 0 };
		for (int i = 0; i < len;) {
			Data_tag = databuf[i];
			Data_len = databuf[i + 1];
			check_data(Data_tag, Data_len, data_temp);
			i = i + Data_len;// 准备接收下一个参数

		}
	}

	private void check_data(byte tag,byte len,byte data[]){
		int temp = 0;
		if(len==(byte)0x01){
			temp = (short) (data[0] & 0xff);
		}
			
		else if(len==(byte)0x02){
			temp = (short) (((data[1] & 0xff) << 8) | ((data[0] & 0xff)));
		}
			
		switch (tag) {
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
			Data.getInstance().SetWorkmode (temp);
			break;// //TAG_WORK_MODE
		case Data.TAG_RUN_STATE:
			Data.getInstance().SetRunState (temp);
			break;// //TAG_WORK_MODE GetBowlFromBlue
		case Data.TAG_BWOL:
			Data.getInstance().SetBowl (temp);
			break;//
		case Data.TAG_PUMP_SPEED:
			Data.getInstance().SetPmpSpeed(temp);
			break;// //TAG_PUMP_SPEED
		case Data.TAG_FILL_SPEED_SET:
			Data.getInstance().SetFillSpeed(temp);
			break;
		case Data.TAG_WASH_SPEED_SET:
			Data.getInstance().SetWashSpeed(temp);
			break;
		case Data.TAG_EMPTY_SPEED_SET:
			Data.getInstance().SetEmptySpeed(temp);
			break;
		case Data.TAG_WASH_VOLUME_SET:
			Data.getInstance().SetMaxWahVolume(temp);
			break;
		case Data.TAG_AUTO_RUN_SET:
			Data.getInstance().SetAutoRunVolume(temp);
			break;
		case Data.TAG_PATIENT_NAME:
		//	Data.getInstance().setPatient_name(Temp_byte);
			break;
		default:
			break;
		}
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

				if (buf_index == (frame_len + 5)) { // 收完一帧了。 2 crc 1 len 2 tail
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
		byte[] Temp_byte=new byte [512];
		
		Log.i("Decode", "本帧需要校验字节" + ((in[0] & 0xff)));
		for (i = 0; i < (in[0] & 0xff); i++) {
			sum += (in[i + 1] & 0xff);
		}
		temp = (short) (((in[len - 3] & 0xff) << 8) | ((in[len - 4] & 0xff)));
//		if (temp != sum) {
//			Log.i("Decode", "check sum err!");
//			head_en = false;
//			
//			return;
//		}
		Log.i("Decode", "check sum OK!");
		int index = 2;
		isget = 0;
		while (isget < len - 6)
		//do { // 提取数据
		{
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
			}else if((in[index]> (byte) 0x02)){
				Log.i("Decode", "大于2字节 ");
				for(int j=0;j<in[index];j++){
					Temp_byte[j]=in[index + j + 1];
				}
				index += in[3]+1;
				isget += in[3]+2;
				
				
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
				Data.getInstance().SetWorkmode (temp);
				break;// //TAG_WORK_MODE
			case Data.TAG_RUN_STATE:
				Data.getInstance().SetRunState (temp);
				break;// //TAG_WORK_MODE GetBowlFromBlue
			case Data.TAG_BWOL:
				Data.getInstance().SetBowl (temp);
				break;//
			case Data.TAG_PUMP_SPEED:
				Data.getInstance().SetPmpSpeed(temp);
				break;// //TAG_PUMP_SPEED
			case Data.TAG_FILL_SPEED_SET:
				Data.getInstance().SetFillSpeed(temp);
				break;
			case Data.TAG_WASH_SPEED_SET:
				Data.getInstance().SetWashSpeed(temp);
				break;
			case Data.TAG_EMPTY_SPEED_SET:
				Data.getInstance().SetEmptySpeed(temp);
				break;
			case Data.TAG_WASH_VOLUME_SET:
				Data.getInstance().SetMaxWahVolume(temp);
				break;
			case Data.TAG_AUTO_RUN_SET:
				Data.getInstance().SetAutoRunVolume(temp);
				break;
			case Data.TAG_PATIENT_NAME:
				Data.getInstance().setPatient_name(Temp_byte);
				break;
			case Data.TAG_PRODUCT_NUM:
				String dirName = null;
				try {
					dirName = new String(Temp_byte, "ISO-8859-1");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				dirName = dirName.trim();
				Data.getInstance().setSerialNumber(dirName.getBytes());
				String dataDir = Utils.GetDataDir().concat(dirName) + File.separator;
				if (Utils.MakeDir(dataDir)) {
					Utils.setFullPath(dataDir);
					sendHead();
					sendContent(Data.TAG_PRODUCT_NUM, dirName.getBytes().length,dirName.getBytes());
					sendTail();
				}
				break;
			case Data.TAG_SW:
				Data.getInstance().setSoftVersen(Temp_byte);
				break;
			case Data.TAG_HW:
				Data.getInstance().setHardVersen(Temp_byte);
				break;
			case Data.TAG_ALARM:
				Data.getInstance().setAlarmState(Temp_byte);
				break;
			case Data.TAG_BOWL_FILL:
				Data.getInstance().setmBowlFillTotalVolume(temp);
				break;
			case Data.TAG_BOWL_WASH:
				Data.getInstance().setmBowlWashTotalVolume(temp);
				break;
			case Data.TAG_BOWL_EMPTY:
				Data.getInstance().setmBowlEmptyTotalVolume(temp);
				break;
			case Data.TAG_BOWL_NUM:
				Data.getInstance().setmBowlNum(temp);
				break;
			case Data.TAG_FILENAME:
				try {
					Utils.Write_File("", new String(Temp_byte, "gb2312").trim());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 break;
			case Data.TAG_FILEDATA:
				try {
					//if(Temp_byte!=null)
					//{
					Utils.Write_File(new String(Temp_byte, "gb2312").trim(), Utils.getCurrentFileName());
					Utils.Write_File("\r\n", Utils.getCurrentFileName());
					//}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			default:
				break;
			}

			Log.i("Decode", Data.getInstance().GetCurrentInfo(in[tag_id]));

			// if (in[index] == (byte) 0x01) {// 速度
			// Log.i("Decode", "速度 ");
			// } else if (in[index] == (byte) 0x02) {// 加速度
			// Log.i("Decode", "加速度 ");
			// } else if (in[index] == (byte) 0x03) {// 高度
			// Log.i("Decode", "高度 ");
			// } else if (in[index] == (byte) 0x04) {// 电量
			// Log.i("Decode", "电量 ");
			// } else if (in[index] == (byte) 0x05) {// 左转向
			// Log.i("Decode", "左转向 ");
			// }
		} //while (isget < len - 6);

	}

}