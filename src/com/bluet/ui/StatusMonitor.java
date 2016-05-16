package com.bluet.ui;

import com.bluet.massistant.BaseFragment;
import com.bluet.massistant.R;
import com.bluet.massistant.R.drawable;
import com.bluet.massistant.R.id;
import com.bluet.massistant.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
	Button begin;
	Button stop;	
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

    TextView  Run_state_view;
	EditText  mETScanConsumables;
	EditText  mETScanPatient;
	Button mScanConsumables;
	Button mScanPatient;
	int status = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.status_monitor, null, false);
        Fill_text_view= (TextView) view.findViewById(R.id.fill_ML);
        Fill_text_view.setText("- - -" );
        
        Wash_text_view=(TextView) view.findViewById(R.id.wash_ML); 
        Wash_text_view.setText("- - -" );
              
        Empty_text_view=(TextView) view.findViewById(R.id.empty_ML);
        Empty_text_view.setText("- - -" );
        
        Bowl_text_view=(TextView) view.findViewById(R.id.Set_BOWL);
        Bowl_text_view.setText("杯型：---");
        Mode_text_view=(TextView) view.findViewById(R.id.Work_mode);
        Mode_text_view.setText("模式：---");
        WorkState_text_view=(TextView) view.findViewById(R.id.work_state);
        WorkState_text_view.setText(" ");
        Run_state_view= (TextView) view.findViewById(R.id.Run_state);
        image_status1 = (ImageView) view.findViewById(R.id.imageView_status1);
        image_status2 = (ImageView) view.findViewById(R.id.imageView_status2);
        image_status3 = (ImageView) view.findViewById(R.id.imageView_status3);
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
        
        image_status1.setOnClickListener(new OnClickListener() {
       	 public void onClick(View v) {
       		Toast toast=Toast.makeText(getActivity(), "点击了图片", Toast.LENGTH_SHORT);
       		toast.show();
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
        
        getActivity().getWindow().setTitle("自体血液回收机");
        Data.getInstance().addListener(this);
        return view;
    }
	@Override
	public void dataChanged() {
		// TODO Auto-generated method stub
		
		Fill_text_view.setText(Data.getInstance().GetInfo_Fill() );
		Wash_text_view.setText(Data.getInstance().allInfo_wash() );
	    Empty_text_view.setText(Data.getInstance().allInfo_empty() );
	    switch(Data.getInstance().getRun_State()){
	    case 0 :
	    	Run_state_view.setText("  ");
	    	break;
	    case 1:
	    	Run_state_view.setText("暂停中");
	    	begin.getBackground();
	    	break;
	    case 2:
	    	Run_state_view.setText("暂停中");
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
			
			image_status1.setImageResource(R.drawable.imageblood);
			image_status2.setImageResource(R.drawable.image_arrow_left);
			image_status3.setImageResource(R.drawable.imagebwol);
			
			break;
		case 1:
			WorkState_text_view.setText("预冲");
			image_status1.setImageResource(R.drawable.imagenacl);
			image_status2.setImageResource(R.drawable.image_arrow_left);			
			image_status3.setImageResource(R.drawable.imagebwol);
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
        	 break;
         case SCANN_PATIENT:
        	 mETScanPatient.setText(result);
        	 break;
         }
	}

}