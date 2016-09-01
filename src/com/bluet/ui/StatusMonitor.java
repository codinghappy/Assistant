package com.bluet.ui;

import java.io.UnsupportedEncodingException;

import com.bluet.massistant.BaseFragment;
import com.bluet.massistant.R;
import com.bluet.massistant.Timer;


import android.R.string;
import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;
import com.bluet.utils.Data;
import com.example.qr_codescan.MainActivity;
import com.example.qr_codescan.MipcaActivityCapture;

public class StatusMonitor extends BaseFragment implements Data.DataChangeListener {
	private final static int SCANNIN_CONSUMABLES = 10;
	private final static int SCANN_PATIENT = 20;
	private final static int TAG_SEND_BUTTON = 0X50;
	
	private Timer mAutoSav;
	private int  shift_time_flag=0;
	
	Button begin;
	Button stop;	
	Button control_contiune;
	byte[] message_button = new byte[8];
	ImageView image_status1;
	ImageView image_status2;
	ImageView image_status3;
	
	TextView  Fill_text_view;
	TextView  Wash_text_view;
	TextView  Empty_text_view;
	TextView  Bowl_text_view;
	TextView  Mode_text_view;
	TextView  WorkState_text_view;
	TextView  Pump_speed_text_view;

    TextView  Run_state_view;
	EditText  mETScanConsumables;
	EditText  mETScanPatient;
	Button     mScanConsumables;
	Button    mScanPatient;
	int status = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.status_monitor, null, false);
        Fill_text_view= (TextView) view.findViewById(R.id.fill_ML);
        Fill_text_view.setText("65535" );
        
        Wash_text_view=(TextView) view.findViewById(R.id.wash_ML); 
        Wash_text_view.setText("12345" );
              
        Empty_text_view=(TextView) view.findViewById(R.id.empty_ML);
        Empty_text_view.setText("12345" );
        
        Bowl_text_view=(TextView) view.findViewById(R.id.Set_BOWL);
        Bowl_text_view.setText("225");
        Mode_text_view=(TextView) view.findViewById(R.id.Work_mode);
        Mode_text_view.setText("自动");
        WorkState_text_view=(TextView) view.findViewById(R.id.work_state);
        WorkState_text_view.setText("待机");
        Run_state_view= (TextView) view.findViewById(R.id.Run_state);
        image_status1 = (ImageView) view.findViewById(R.id.imageView_status1);
        image_status2 = (ImageView) view.findViewById(R.id.imageView_status2);
        image_status3 = (ImageView) view.findViewById(R.id.imageView_status3);
		image_status1.setImageResource(R.drawable.imageblood);
		image_status2.setImageResource(R.drawable.image_arrow_left);
		image_status3.setImageResource(R.drawable.imagebwol);
		Pump_speed_text_view = (TextView) view.findViewById(R.id.pump_speed);
		Pump_speed_text_view.setText("400");
        stop =(Button)  view.findViewById(R.id.button_stop);
        stop.setOnClickListener(new OnClickListener() {
        	 public void onClick(View v) {  				
        		 message_button[0] = (byte) 0x02;
        		 message_button[1] = (byte) 0x03;				
				sendMessage_fromat(TAG_SEND_BUTTON, 2, message_button);
        	 }        	 
        });
        
        begin = (Button) view.findViewById(R.id.begin);
        begin.setOnClickListener(new OnClickListener() {
        	 public void onClick(View v) {  				
        		 message_button[0] = (byte) 0x02;
        		 message_button[1] = (byte) 0x02;
        		 sendMessage_fromat(TAG_SEND_BUTTON, 2, message_button);        
        	 }
        	 
        });
        control_contiune=(Button) view.findViewById(R.id.button_contiue);
        control_contiune.setOnClickListener(new OnClickListener() {
       	 public void onClick(View v) {  				
    		 message_button[0] = (byte) 0x02;
    		 message_button[1] = (byte) 0x02;
    		 sendMessage_fromat(TAG_SEND_BUTTON, 2, message_button);             

       	 }
       	 
       });       
        WorkState_text_view.setOnClickListener(new OnClickListener() {
       	 public void onClick(View v) {
       		Toast toast=Toast.makeText(getActivity(), "请在设备操作进行设备操控", Toast.LENGTH_SHORT);
       		toast.show();
       		mAutoSav.restart();
       		//mAutoSav.stop();
       	 }
       });
       // mAutoSav.restart();
		mAutoSav = new Timer(800, new Runnable() {
			public void run() {
			    switch(Data.getInstance().getRun_State()){
			    case 0 :
			    	Run_state_view.setText("  ");
			    	begin.setVisibility(begin.GONE);
			    	control_contiune.setVisibility(control_contiune.GONE);
			    	break;
			    case 1:
			    	Run_state_view.setText("暂停中");
					if(shift_time_flag==0){
						Run_state_view.setText("暂停中");
						shift_time_flag=1;
					}
					else{
						Run_state_view.setText("   ");
						shift_time_flag=0;					
					}
			    	begin.setVisibility(begin.GONE);
			    	control_contiune.setVisibility(control_contiune.GONE);
			    	break;
			    case 2:
			    	Run_state_view.setText("暂停中");
					if(shift_time_flag==0){
						Run_state_view.setText("暂停中");
						shift_time_flag=1;
					}
					else{
						Run_state_view.setText("   ");
						shift_time_flag=0;					
					}		
			    	begin.setVisibility(begin.GONE);
			    	control_contiune.setVisibility(control_contiune.VISIBLE);
			    	break;
			    	default:break;
			    
			    }
				
			}
		});	

        mScanConsumables = (Button) view.findViewById(R.id.scan_consumables);
        mScanConsumables.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub  
            	Intent intent = new Intent();
				intent.setClass(getActivity(), MipcaActivityCapture.class);
				StatusMonitor.this.startActivityForResult(intent, SCANNIN_CONSUMABLES);
            }
        });
        mETScanConsumables = (EditText) view.findViewById(R.id.edit_consumables);
        mETScanPatient = (EditText) view.findViewById(R.id.edit_patient);
        mScanPatient = (Button) view.findViewById(R.id.scan_patient);
        mScanPatient.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub  
            	Intent intent = new Intent();
				intent.setClass(getActivity(), MipcaActivityCapture.class);
				StatusMonitor.this.startActivityForResult(intent, SCANN_PATIENT);
            }
        });
        
        Data.getInstance().addListener(this);
        return view;
    }
	protected void runOnUiThread(Runnable runnable) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dataChanged()    {
		// TODO Auto-generated method stub
		
		Fill_text_view.setText(Data.getInstance().getHardVersen() );
		
		
		Wash_text_view.setText(Data.getInstance().GetInfo_wash() );
	    Empty_text_view.setText(Data.getInstance().GetInfo_empty() );
	    Pump_speed_text_view.setText(String.valueOf(Data.getInstance().getPump_speed()));
//	    switch(Data.getInstance().getRun_State()){
//	    case 0 :
//	    	Run_state_view.setText("  ");
//	    	begin.setVisibility(begin.VISIBLE);
//	    	control_contiune.setVisibility(control_contiune.GONE);
//	    	break;
//	    case 1:
//	    	Run_state_view.setText("暂停中");
//	    	begin.setVisibility(begin.GONE);
//	    	control_contiune.setVisibility(control_contiune.VISIBLE);
//	    	break;
//	    case 2:
//	    	Run_state_view.setText("暂停中");
//
//	    	begin.setVisibility(begin.GONE);
//	    	control_contiune.setVisibility(control_contiune.VISIBLE);
//	    	break;
//	    	default:break;
//	    
//	    }

	    //WorkState_text_view.setText(" ");
	    if(Data.getInstance().getBowl()==225){
	    	Bowl_text_view.setText("225");	
	    }
	    else if (Data.getInstance().getBowl()==125){
	    	Bowl_text_view.setText("125");
	    }
	    switch(Data.getInstance().getwork_mode()){
	    case 1:
	    	Mode_text_view.setText("自动");
	    	break;
	    case 2:
	    	Mode_text_view.setText("手动");
	    	break;
	    case 3:
	    	Mode_text_view.setText("应急");
	    	break;
	    case 4:
	    	Mode_text_view.setText("全血");
	    	break;
	    	default:break;
	    }
		switch(Data.getInstance().getWork_State()){
		case 0:
			WorkState_text_view.setText("待机");			
			image_status1.setImageResource(R.drawable.imageblood);
			image_status2.setImageResource(R.drawable.image_arrow_left);
			image_status3.setImageResource(R.drawable.imagebwol);
			Pump_speed_text_view.setText("300");
			break;
		case 1:
			WorkState_text_view.setText("预冲");
			image_status1.setImageResource(R.drawable.imagenacl);
			image_status2.setImageResource(R.drawable.image_arrow_left);			
			image_status3.setImageResource(R.drawable.imagebwol);
			Pump_speed_text_view.setText("300");
			break;
		case 2:
			WorkState_text_view.setText("进血");
			image_status1.setImageResource(R.drawable.imageblood);
			image_status2.setImageResource(R.drawable.image_arrow_left);
			image_status3.setImageResource(R.drawable.imagebwol);
			break;
		case 3:
			WorkState_text_view.setText("清洗");
			image_status1.setImageResource(R.drawable.imagenacl);
			image_status2.setImageResource(R.drawable.image_arrow_left);
			image_status3.setImageResource(R.drawable.imagebwol);
			break;
		case 4:
			WorkState_text_view.setText("清空");
			
			image_status1.setImageResource(R.drawable.image_rbc);
			image_status2.setImageResource(R.drawable.image_arrow_right);
			image_status3.setImageResource(R.drawable.imagebwol);
			break;
		case 5:
			WorkState_text_view.setText("浓缩");
			
			image_status1.setImageResource(R.drawable.image_rbc);
			image_status2.setImageResource(R.drawable.image_arrow_left);
			image_status3.setImageResource(R.drawable.imagebwol);
			break;
		case 6:
			WorkState_text_view.setText("回血");
			
			image_status1.setImageResource(R.drawable.image_rbc);
			image_status2.setImageResource(R.drawable.image_arrow_left);
			image_status3.setImageResource(R.drawable.imagebwol);
			break;

			
		default:break;
		}

		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (resultCode != Activity.RESULT_OK)
	            return;
		 Bundle bundle = data.getExtras();
		 String result = bundle.getString("result");
         switch(requestCode) {
         case SCANNIN_CONSUMABLES:
        	 mETScanConsumables.setText(result); 
        	 Data.getInstance().setPatient_name(result.getBytes());
        	 break;
         case SCANN_PATIENT:
        	 mETScanPatient.setText(result);
        	 break;
         }
	}

}