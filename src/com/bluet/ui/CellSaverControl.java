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
	Button coin;
	Button control_return;
	Button control_mode;
	Button control_print;
	Button Pump_inc;
	Button pump_dec;
	Button control_pause;
	Button control_stop;
	TextView  WorkState_text_view;
	TextView Bowl_text_view;
	TextView Mode_text_view;
	TextView Run_state_view;
	
 //Button pump_dec;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cell_saver_control, null, false);
        WorkState_text_view  = (TextView) view.findViewById(R.id.control_work_state_show);
        Bowl_text_view = (TextView) view.findViewById(R.id.control_bwol_state_show);
        Mode_text_view = (TextView) view.findViewById(R.id.control_mode_state_show);
        Run_state_view = (TextView) view.findViewById(R.id.control_Run_state);
        control_mode=(Button) view.findViewById(R.id.button_mode);
        control_mode.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			
				message_fill[0] = (byte) 0x50;//tag
				message_fill[1] = (byte) 0x02;
				message_fill[2] = (byte) 0x02;
				message_fill[3] = (byte) 0x06;
				message_fill[4] = (byte) 0x0B;
				message_fill[5] = (byte) 0x00;
				message_fill[6] = (byte) 0xCC;
				message_fill[7] = (byte) 0x33;
				sendMessage(message_fill);
			}
		});
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
    	//WorkState_text_view.setText(String.valueOf(Data.getInstance().getWork_State()));
        switch(Data.getInstance().getWork_State()){
        case 0:
        	WorkState_text_view.setText("待机");
           break;
        case 1:
        	WorkState_text_view.setText("预冲");
        	break;
        case 2:
        	WorkState_text_view.setText("进血");
        	break;
        case 3:
        	WorkState_text_view.setText("清洗");
        	break;
        case 4:
        	WorkState_text_view.setText("清空");
        	break;
        default:break;
        }
	    if(Data.getInstance().getBowl()==225){
	    	Bowl_text_view.setText("杯型：225");	
	    }
	    else if (Data.getInstance().getBowl()==125){
	    	Bowl_text_view.setText("杯型：125");
	    }
	    switch(Data.getInstance().getwork_mode()){
	    case 1:
	    	Mode_text_view.setText("模式：自动");
	    	break;
	    case 2:
	    	Mode_text_view.setText("模式：手动");
	    	break;
	    case 3:
	    	Mode_text_view.setText("模式：应急");
	    	break;
	    case 4:
	    	Mode_text_view.setText("模式：全血");
	    	break;
	    	default:break;
	    }
	    switch(Data.getInstance().getRun_State()){
	    case 0 :
	    	Run_state_view.setText("  ");
	    	break;
	    case 1:
	    	Run_state_view.setText("暂停中");
	    	
	    	break;
	    case 2:
	    	Run_state_view.setText("暂停中");
	    	break;
	    	default:break;
	    
	    }
    }
}