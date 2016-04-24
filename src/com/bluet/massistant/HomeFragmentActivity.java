package com.bluet.massistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.bluet.ui.Account;
import com.bluet.ui.CellSaverControl;
import com.bluet.ui.DataManager;
import com.bluet.ui.ParameterHome;
import com.bluet.utils.BluetoothClient;
import com.viewpagerindicator.IconPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private IconTabPageIndicator mIndicator;
	private BluetoothClient mClient = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo);
		
		initViews();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mClient = new BluetoothClient(this);
		mClient.init();
		if (mClient.mBluetoothAdapter == null)
			return;
		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mClient.mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, mClient.REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else {
			if ((mClient.mChatService == null))
				mClient.setupChat();
		}
		
		ConectToDevice();
	}
	
	private void initViews() {
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mIndicator = (IconTabPageIndicator) findViewById(R.id.indicator);
		List<BaseFragment> fragments = initFragments();
		FragmentAdapter adapter = new FragmentAdapter(fragments,
				getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
		mIndicator.setViewPager(mViewPager);
	}

	private List<BaseFragment> initFragments() {
		List<BaseFragment> fragments = new ArrayList<BaseFragment>();

		BaseFragment recordFragment = new CellSaverControl();
		recordFragment.setTitle("设备操作");
		recordFragment.setIconId(R.drawable.tab_record_selector);
		recordFragment.setHomeFragment(this);
		fragments.add(recordFragment);

		BaseFragment userFragment = new StatusMonitor();
		userFragment.setTitle("状态显示");
		userFragment.setIconId(R.drawable.tab_user_selector);
		userFragment.setHomeFragment(this);
		fragments.add(userFragment);

		BaseFragment noteFragment = new ParameterHome();
		noteFragment.setTitle("参数设置");
		noteFragment.setIconId(R.drawable.tab_record_selector);
		noteFragment.setHomeFragment(this);
		fragments.add(noteFragment);

		BaseFragment contactFragment = new DataManager();
		contactFragment.setTitle("数据管理");
		contactFragment.setIconId(R.drawable.tab_user_selector);
		contactFragment.setHomeFragment(this);
		fragments.add(contactFragment);

		return fragments;
	}

	public BluetoothClient getBluetoothClient() {
		return mClient;
	}

	class FragmentAdapter extends FragmentPagerAdapter implements
			IconPagerAdapter {
		private List<BaseFragment> mFragments;

		public FragmentAdapter(List<BaseFragment> fragments, FragmentManager fm) {
			super(fm);
			mFragments = fragments;
		}

		@Override
		public Fragment getItem(int i) {
			return mFragments.get(i);
		}

		@Override
		public int getIconResId(int index) {
			return mFragments.get(index).getIconId();
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragments.get(position).getTitle();
		}
	}

	String lastdevice;

	void ConectToDevice() {
		SharedPreferences config = this.getSharedPreferences("perference",
				MODE_PRIVATE);
		lastdevice = config.getString("lastdevice_addr", "");

		new AlertDialog.Builder(this)
				.setTitle("连接请求")
				.setIcon(android.R.drawable.ic_menu_info_details)
				.setMessage("软件并没有连接蓝牙设备，请连接设备")
				.setPositiveButton("连接上次的设备",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (lastdevice == "") { // 没有设备，那就连接新设备吧

									Intent serverIntent = new Intent(
											getApplicationContext(),
											DeviceListActivity.class);
									startActivityForResult(serverIntent,
											mClient.REQUEST_CONNECT_DEVICE);
								} else {
									// Get the BLuetoothDevice object
									BluetoothDevice device = mClient.mBluetoothAdapter
											.getRemoteDevice(lastdevice);
									// Attempt to connect to the device
									mClient.mChatService.connect(device);
								}
							}
						})
				.setNegativeButton("连接新的设备",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								//
								Intent serverIntent = new Intent(
										getApplicationContext(),
										DeviceListActivity.class);
								startActivityForResult(serverIntent,
										mClient.REQUEST_CONNECT_DEVICE);
							}
						}).show();

	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case BluetoothClient.REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				String address = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				// Get the BLuetoothDevice object
				BluetoothDevice device = mClient.mBluetoothAdapter
						.getRemoteDevice(address);
				// Attempt to connect to the device
				mClient.mChatService.connect(device);
			}
			break;
		case BluetoothClient.REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				mClient.setupChat();
				// ("蓝牙启动成功...");
			} else {
				// User did not enable Bluetooth or an error occured
				Toast.makeText(this, R.string.bt_not_enabled_leaving,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
