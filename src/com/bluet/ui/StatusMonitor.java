package com.bluet.ui;

import com.bluet.massistant.BaseFragment;
import com.bluet.massistant.R;
import com.bluet.massistant.R.drawable;
import com.bluet.massistant.R.id;
import com.bluet.massistant.R.layout;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;
import com.bluet.utils.Data;;

public class StatusMonitor extends BaseFragment implements Data.DataChangeListener {
	Button begin;
	Button stop;	
	byte[] message_button = new byte[8];
	ImageView image_view;
	TextView  Fill_text_view;
	TextView  Wash_text_view;
	TextView  Empty_text_view;
	TextView  Bowl_text_view;
	TextView  Mode_text_view;
	TextView  WorkState_text_view;
	int status = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.status_monitor, null, false);
        Fill_text_view= (TextView) view.findViewById(R.id.fill_ML);
        Fill_text_view.setText("- - -"+"mL");
        
        Wash_text_view=(TextView) view.findViewById(R.id.wash_ML); 
        Wash_text_view.setText("- - -"+"mL");
              
        Empty_text_view=(TextView) view.findViewById(R.id.empty_ML);
        Empty_text_view.setText("- - -" );
        
        Bowl_text_view=(TextView) view.findViewById(R.id.Set_BOWL);
        Bowl_text_view.setText("杯型：---");
        Mode_text_view=(TextView) view.findViewById(R.id.Work_mode);
        Mode_text_view.setText("模式：---");
        WorkState_text_view=(TextView) view.findViewById(R.id.Work_state);
        WorkState_text_view.setText(" ");
        image_view = (ImageView) view.findViewById(R.id.imageView_status);
        stop =(Button)  view.findViewById(R.id.button_stop);
        stop.setOnClickListener(new OnClickListener() {
        	 public void onClick(View v) {  				
        		 message_button[0] = (byte) 0x50;
        		 message_button[1] = (byte) 0x02;
        		 message_button[2] = (byte) 0x02;
        		 message_button[3] = (byte) 0x03;
        		 message_button[4] = (byte) 0x09;
        		 message_button[5] = (byte) 0x00;
        		 message_button[6] = (byte) 0xCC;
        		 message_button[7] = (byte) 0x33;
				sendMessage(message_button);              
 
        	 }
        	 
        });
        begin = (Button) view.findViewById(R.id.begin);
        begin.setOnClickListener(new OnClickListener() {
        	 public void onClick(View v) {  				
        		 message_button[0] = (byte) 0x50;
        		 message_button[1] = (byte) 0x02;
        		 message_button[2] = (byte) 0x02;
        		 message_button[3] = (byte) 0x02;
        		 message_button[4] = (byte) 0x09;
        		 message_button[5] = (byte) 0x00;
        		 message_button[6] = (byte) 0xCC;
        		 message_button[7] = (byte) 0x33;
 				sendMessage(message_button);             
 
        	 }
        	 
        });
        
        image_view.setOnClickListener(new OnClickListener() {
       	 public void onClick(View v) {
       		Toast toast=Toast.makeText(getActivity(), "点击了图片", Toast.LENGTH_SHORT);
       		toast.show();
       	 }
       });
        
        getActivity().getWindow().setTitle("自体血液回收机");
        Data.getInstance().addListener(this);
        return view;
    }
	@Override
	public void dataChanged() {
		// TODO Auto-generated method stub
		Fill_text_view.setText(Data.getInstance().GetInfo_Fill()+"mL");
		Wash_text_view.setText(Data.getInstance().allInfo_wash()+"mL");
	    Empty_text_view.setText(Data.getInstance().allInfo_empty() );
	    switch(Data.getInstance().getWork_State()){
	    case 0 :
	    	WorkState_text_view.setText("运行中");
	    	break;
	    case 1:
	    	WorkState_text_view.setText("暂停中");
	    	break;
	    case 2:
	    	WorkState_text_view.setText("停止中");
	    	break;
	    	default:break;
	    
	    }
	    //WorkState_text_view.setText(" ");
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
		switch(Data.getInstance().getWork_State()){
		case 0:
			WorkState_text_view.setText("待机");
			image_view.setImageResource(R.drawable.control_print);
			break;
		case 1:
			WorkState_text_view.setText("预冲");
			image_view.setImageResource(R.drawable.icon_record_normal);
			break;
		case 2:
			WorkState_text_view.setText("进血");
			image_view.setImageResource(R.drawable.blood_in);
			break;
		case 3:
			WorkState_text_view.setText("清洗");
			image_view.setImageResource(R.drawable.icon_record_normal);
			break;
		case 4:
			WorkState_text_view.setText("清空");
			image_view.setImageResource(R.drawable.icon_record_normal);
			break;
		case 5:
			WorkState_text_view.setText("浓缩");
			image_view.setImageResource(R.drawable.icon_record_normal);
			break;
		case 6:
			WorkState_text_view.setText("回血");
			break;

			default:break;
		}

		
	}

}