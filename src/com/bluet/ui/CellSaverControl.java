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
	protected static final byte[] PumpINCKey  ={0x02 ,0x07};
	protected static final byte[] PumpDECKey  ={0x02 ,0x08};
	protected static final byte[] PauseKey ={0x02 ,0x02};
	protected static final byte[] StopKey ={0x02 ,0x03};
	
	 byte[] message_fill = new byte[8];
	final byte[] fill_key ={0x02,0x09};
	final byte[] ModeKey ={0x02 ,0x06};
	final byte[] WashKey ={0x02 ,0x0A};
	final byte[] EmptyKey ={0x02 ,0x0B};
	final byte[] CionKey ={0x02 ,0x0C};
	final byte[] ReturnKey ={0x02 ,0x0D};
	Button fill;
	Button wash;
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
				sendMessage_fromat(0X50, 2, ModeKey);
			}
		});
        fill = (Button) view.findViewById(R.id.fill);
        fill.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendMessage_fromat(0X50, 2, fill_key);
			}
		});
        
        wash = (Button) view.findViewById(R.id.wash);
        wash.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendMessage_fromat(0X50, 2, WashKey);
			}
		});
        empty =(Button) view.findViewById(R.id.empty);
        empty.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendMessage_fromat(0X50, 2, EmptyKey);
			}
		});
        coin =(Button) view.findViewById(R.id.coin);
        coin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendMessage_fromat(0X50, 2, CionKey);
				
			}
		});
        control_return =(Button) view.findViewById(R.id.control_return);
        control_return.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendMessage_fromat(0X50, 2, ReturnKey);
			}
		});
        Pump_inc =(Button) view.findViewById(R.id.control_pump_inc);
        Pump_inc.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendMessage_fromat(0X50, 2, PumpINCKey);
			}
		});
        pump_dec =(Button) view.findViewById(R.id.control_pump_dec);
        pump_dec.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendMessage_fromat(0X50, 2, PumpDECKey);
			}
		});
        control_pause =(Button) view.findViewById(R.id.control_pause);
        control_pause.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendMessage_fromat(0X50, 2, PauseKey);
			}
		});
        control_stop =(Button) view.findViewById(R.id.control_stop);
        control_stop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendMessage_fromat(0X50, 2, StopKey);
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
        case 5:
        	WorkState_text_view.setText("浓缩");
        	break;
        case 6:
        	WorkState_text_view.setText("回血");
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