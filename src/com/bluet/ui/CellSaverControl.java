package com.bluet.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.bluet.massistant.BaseFragment;
import com.bluet.massistant.R;
import com.bluet.utils.Data;
import com.bluet.utils.Data.DataChangeListener;


public class CellSaverControl extends BaseFragment implements DataChangeListener {
	Button fill;
	byte[] message_fill = new byte[8];
	Button wash;
	byte[] message_wash = new byte[8];
	Button empty;
	Button Pump_inc;
	Button pump_dec;
	Button control_pause;
	Button control_stop;
	TextView  WorkState_text_view;
 //Button pump_dec;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cell_saver_control, null, false);
        WorkState_text_view=(TextView) view.findViewById(R.id.control_work_state_show);
        

        fill = (Button) view.findViewById(R.id.fill);
        fill.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			
				message_fill[0] = (byte) 0x50;//tag
				message_fill[1] = (byte) 0x02;
				message_fill[2] = (byte) 0x02;
				message_fill[3] = (byte) 0x09;
				message_fill[4] = (byte) 0x0B;
				message_fill[5] = (byte) 0x00;
				message_fill[6] = (byte) 0xCC;
				message_fill[7] = (byte) 0x33;
				sendMessage(message_fill);
			}
		});
        
        wash = (Button) view.findViewById(R.id.wash);
        wash.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				message_wash[0] = (byte) 0x50;
				message_wash[1] = (byte) 0x02;
				message_wash[2] = (byte) 0x02;
				message_wash[3] = (byte) 0x0A;
				message_wash[4] = (byte) 0x09;
				message_wash[5] = (byte) 0x00;
				message_wash[6] = (byte) 0xCC;
				message_wash[7] = (byte) 0x33;
				sendMessage(message_wash);
			}
		});
        empty =(Button) view.findViewById(R.id.empty);
        empty.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				message_wash[0] = (byte) 0x50;
				message_wash[1] = (byte) 0x02;
				message_wash[2] = (byte) 0x02;
				message_wash[3] = (byte) 0x0B;
				message_wash[4] = (byte) 0x09;
				message_wash[5] = (byte) 0x00;
				message_wash[6] = (byte) 0xCC;
				message_wash[7] = (byte) 0x33;
				sendMessage(message_wash);
			}
		});
        Pump_inc =(Button) view.findViewById(R.id.control_pump_inc);
        Pump_inc.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				message_wash[0] = (byte) 0x50;
				message_wash[1] = (byte) 0x02;
				message_wash[2] = (byte) 0x02;
				message_wash[3] = (byte) 0x07;
				message_wash[4] = (byte) 0x09;
				message_wash[5] = (byte) 0x00;
				message_wash[6] = (byte) 0xCC;
				message_wash[7] = (byte) 0x33;
				sendMessage(message_wash);
			}
		});
        pump_dec =(Button) view.findViewById(R.id.control_pump_dec);
        pump_dec.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				message_wash[0] = (byte) 0x50;
				message_wash[1] = (byte) 0x02;
				message_wash[2] = (byte) 0x02;
				message_wash[3] = (byte) 0x08;
				message_wash[4] = (byte) 0x09;
				message_wash[5] = (byte) 0x00;
				message_wash[6] = (byte) 0xCC;
				message_wash[7] = (byte) 0x33;
				sendMessage(message_wash);
			}
		});
        control_pause =(Button) view.findViewById(R.id.control_pause);
        control_pause.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				message_wash[0] = (byte) 0x50;
				message_wash[1] = (byte) 0x02;
				message_wash[2] = (byte) 0x02;
				message_wash[3] = (byte) 0x02;
				message_wash[4] = (byte) 0x09;
				message_wash[5] = (byte) 0x00;
				message_wash[6] = (byte) 0xCC;
				message_wash[7] = (byte) 0x33;
				sendMessage(message_wash);
			}
		});
        control_stop =(Button) view.findViewById(R.id.control_stop);
        control_stop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				message_wash[0] = (byte) 0x50;
				message_wash[1] = (byte) 0x02;
				message_wash[2] = (byte) 0x02;
				message_wash[3] = (byte) 0x03;
				message_wash[4] = (byte) 0x09;
				message_wash[5] = (byte) 0x00;
				message_wash[6] = (byte) 0xCC;
				message_wash[7] = (byte) 0x33;
				sendMessage(message_wash);
			}
		});
        Data.getInstance().addListener(this);
        return view;
    }

    @Override
	public void dataChanged() {
    	WorkState_text_view.setText(String.valueOf(Data.getInstance().getWork_State()));
//        switch(Data.getInstance().getWork_State()){
//        case 0:
//        	WorkState_text_view.setText("222222222222");
//           break;
//        case 1:
//        	WorkState_text_view.setText("00000000");
//        	break;
//        case 2:
//        	WorkState_text_view.setText("33333333333333");
//        	break;
//        default:break;
//        }
    }
}