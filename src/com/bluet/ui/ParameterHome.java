package com.bluet.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluet.massistant.BaseFragment;
import com.bluet.massistant.R;
import com.bluet.utils.Data;
import com.bluet.utils.Data.DataChangeListener;

public class ParameterHome extends BaseFragment implements DataChangeListener {
	byte[] message_button = new byte[8];
	TextView Fill_text_view;
	TextView Bwol_text_view;
	TextView Fill_speed_text_view;
	TextView Wash_speed_text_view;
	TextView Empty_speed_text_view;
	TextView Auto_run_volume_text_view;
	TextView Max_wash_volume_text_view;
	ImageView param_setting;
	ImageView alert_setting;
	ImageView Auto_run_volume_inc;
	ImageView Auto_run_volume_dec;
	RelativeLayout param_layout;
	RelativeLayout alert_layout;
	ImageView MaxWash_volume_dec;
	ImageView Fill_speed_inc;
	ImageView Fill_speed_dec;
	ImageView Wash_speed_inc;
	ImageView Wash_speed_dec;
	ImageView Empty_speed_inc;
	ImageView Empty_speed_dec;

	int temp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.param_setting_home, null, false);
		CheckBox Alarm_louye_switch = (CheckBox)this.findViewById(R.id.Alarm_louye_switch);
		//Alarm_louye_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        //    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
//            	@Override
			//Toast toast = Toast.makeText(getActivity(), arg1?"选中了":"取消了选中",
			//			Toast.LENGTH_SHORT);
//                Toast.makeText(MyActivity.this, 
//                        arg1?"选中了":"取消了选中"    , Toast.LENGTH_LONG).show();
      //     }
      //  });
		Bwol_text_view = (TextView) view.findViewById(R.id.Set_pra_line1_Value);
		Bwol_text_view.setText("125");
		Fill_speed_text_view = (TextView) view
				.findViewById(R.id.Set_pra_line2_Value);
		Fill_speed_text_view.setText("300");
		Wash_speed_text_view = (TextView) view
				.findViewById(R.id.Set_pra_line3_Value);
		Empty_speed_text_view = (TextView) view
				.findViewById(R.id.Set_pra_line4_Value);
		Auto_run_volume_text_view = (TextView) view
				.findViewById(R.id.Set_pra_line6_Value);
		Max_wash_volume_text_view = (TextView) view
				.findViewById(R.id.Set_pra_line5_Value);
		Auto_run_volume_inc = (ImageView) view
				.findViewById(R.id.Set_pra_line6_Inc);
		Auto_run_volume_dec = (ImageView) view
				.findViewById(R.id.Set_pra_line6_dec);
		ImageView MaxWash_volume_inc = (ImageView) view
				.findViewById(R.id.Set_pra_line5_Inc);
		MaxWash_volume_dec = (ImageView) view
				.findViewById(R.id.Set_pra_line5_dec);
		Empty_speed_inc = (ImageView) view.findViewById(R.id.Set_pra_line4_Inc);
		Empty_speed_dec = (ImageView) view.findViewById(R.id.Set_pra_line4_dec);
		Wash_speed_inc = (ImageView) view.findViewById(R.id.Set_pra_line3_Inc);
		Wash_speed_dec = (ImageView) view.findViewById(R.id.Set_pra_line3_dec);
		Fill_speed_inc = (ImageView) view.findViewById(R.id.Set_pra_line2_Inc);
		Fill_speed_dec = (ImageView) view.findViewById(R.id.Set_pra_line2_dec);
		
		param_setting = (ImageView) view.findViewById(R.id.ImageView01);
		param_layout = (RelativeLayout) view
				.findViewById(R.id.params_set_details);
		param_setting.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				alert_layout.setVisibility(RelativeLayout.GONE);
				param_layout.setVisibility(RelativeLayout.VISIBLE);
			}
		});
		alert_setting = (ImageView) view.findViewById(R.id.alarm_set_img);
		alert_layout = (RelativeLayout) view
				.findViewById(R.id.params_set_alert);
		alert_setting.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				param_layout.setVisibility(RelativeLayout.GONE);
				alert_layout.setVisibility(RelativeLayout.VISIBLE);
			}
		});
		Wash_speed_inc.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ((Data.getInstance().GetWashspeed() >= 1000)) {
					Toast toast = Toast.makeText(getActivity(), "已到最大值",
							Toast.LENGTH_SHORT);

					toast.show();
				} else {
					message_button[1] = (byte) ((Data.getInstance()
							.GetWashspeed() + 10) / 256);
					message_button[0] = (byte) ((Data.getInstance()
							.GetWashspeed() + 10) % 256);
					sendMessage_fromat(0x32, 2, message_button);
				}

			}
		});

		Fill_speed_dec.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ((Data.getInstance().GetFillspeed() <= 50)) {
					Toast toast = Toast.makeText(getActivity(), "已到最xiao值",
							Toast.LENGTH_SHORT);

					toast.show();
				} else {
					message_button[1] = (byte) ((Data.getInstance()
							.GetFillspeed() - 10) / 256);
					message_button[0] = (byte) ((Data.getInstance()
							.GetFillspeed() - 10) % 256);
					sendMessage_fromat(0x31, 2, message_button);
				}

			}
		});
		Fill_speed_inc.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ((Data.getInstance().GetFillspeed() >= 1000)) {
					Toast toast = Toast.makeText(getActivity(), "已到最大值",
							Toast.LENGTH_SHORT);

					toast.show();
				} else {
					message_button[1] = (byte) ((Data.getInstance()
							.GetFillspeed() + 10) / 256);
					message_button[0] = (byte) ((Data.getInstance()
							.GetFillspeed() + 10) % 256);
					sendMessage_fromat(0x31, 2, message_button);
				}

			}
		});

		Wash_speed_dec.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ((Data.getInstance().GetWashspeed() <= 50)) {
					Toast toast = Toast.makeText(getActivity(), "已到最xiao值",
							Toast.LENGTH_SHORT);

					toast.show();
				} else {
					message_button[1] = (byte) ((Data.getInstance()
							.GetWashspeed() - 10) / 256);
					message_button[0] = (byte) ((Data.getInstance()
							.GetWashspeed() - 10) % 256);
					sendMessage_fromat(0x32, 2, message_button);
				}

			}
		});
		Empty_speed_inc.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ((Data.getInstance().GetEmptyspeed() >= 1000)) {
					Toast toast = Toast.makeText(getActivity(), "已到最大值",
							Toast.LENGTH_SHORT);

					toast.show();
				} else {
					message_button[1] = (byte) ((Data.getInstance()
							.GetEmptyspeed() + 10) / 256);
					message_button[0] = (byte) ((Data.getInstance()
							.GetEmptyspeed() + 10) % 256);
					sendMessage_fromat(0x33, 2, message_button);
				}

			}
		});

		Empty_speed_dec.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ((Data.getInstance().GetEmptyspeed() <= 50)) {
					Toast toast = Toast.makeText(getActivity(), "已到最xiao值",
							Toast.LENGTH_SHORT);

					toast.show();
				} else {
					message_button[1] = (byte) ((Data.getInstance()
							.GetEmptyspeed() - 10) / 256);
					message_button[0] = (byte) ((Data.getInstance()
							.GetEmptyspeed() - 10) % 256);
					sendMessage_fromat(0x33, 2, message_button);
				}

			}
		});
		Auto_run_volume_inc.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ((Data.getInstance().GetAutoRunVolume() > 1500)) {
					Toast toast = Toast.makeText(getActivity(), "已到最大值",
							Toast.LENGTH_SHORT);

					toast.show();
				} else {
					message_button[1] = (byte) ((Data.getInstance()
							.GetAutoRunVolume() + 10) / 256);
					message_button[0] = (byte) ((Data.getInstance()
							.GetAutoRunVolume() + 10) % 256);
					sendMessage_fromat(0x35, 2, message_button);
				}

			}
		});

		Auto_run_volume_dec.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ((Data.getInstance().GetAutoRunVolume() <= 300)) {
					Toast toast = Toast.makeText(getActivity(), "已到最xiao值",
							Toast.LENGTH_SHORT);

					toast.show();
				} else {
					message_button[1] = (byte) ((Data.getInstance()
							.GetAutoRunVolume() - 10) / 256);
					message_button[0] = (byte) ((Data.getInstance()
							.GetAutoRunVolume() - 10) % 256);
					sendMessage_fromat(0x35, 2, message_button);
				}

			}
		});
		MaxWash_volume_inc.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ((Data.getInstance().GetMaxWashVolume() > 1500)) {
					Toast toast = Toast.makeText(getActivity(), "已到最大值",
							Toast.LENGTH_SHORT);

					toast.show();
				} else {
					message_button[1] = (byte) ((Data.getInstance()
							.GetMaxWashVolume() + 50) / 256);
					message_button[0] = (byte) ((Data.getInstance()
							.GetMaxWashVolume() + 50) % 256);
					sendMessage_fromat(0x34, 2, message_button);
				}

			}
		});
		MaxWash_volume_dec.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ((Data.getInstance().GetMaxWashVolume() <= 300)) {
					Toast toast = Toast.makeText(getActivity(), "已到最xiao值",
							Toast.LENGTH_SHORT);

					toast.show();
				} else {
					message_button[1] = (byte) ((Data.getInstance()
							.GetMaxWashVolume() - 50) / 256);
					message_button[0] = (byte) ((Data.getInstance()
							.GetMaxWashVolume() - 50) % 256);
					sendMessage_fromat(0x34, 2, message_button);
				}

			}
		});
		Data.getInstance().addListener(this);
		return view;
	}

	private CheckBox findViewById(int alarmLouyeSwitch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dataChanged() {
		if (Data.getInstance().getBowl() == 225) {
			Bwol_text_view.setText("225");
		} else if (Data.getInstance().getBowl() == 125) {
			Bwol_text_view.setText("125");
		}
		Fill_speed_text_view.setText(String.valueOf(Data.getInstance()
				.GetFillspeed()));
		Wash_speed_text_view.setText(String.valueOf(Data.getInstance()
				.GetWashspeed()));
		Empty_speed_text_view.setText(String.valueOf(Data.getInstance()
				.GetEmptyspeed()));
		Fill_speed_text_view.setText(String.valueOf(Data.getInstance()
				.GetFillspeed()));
		Auto_run_volume_text_view.setText(String.valueOf(Data.getInstance()
				.GetAutoRunVolume()));
		Max_wash_volume_text_view.setText(String.valueOf(Data.getInstance()
				.GetMaxWashVolume()));

	}

}