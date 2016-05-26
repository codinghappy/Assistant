package com.bluet.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluet.massistant.BaseFragment;
import com.bluet.massistant.R;
import com.bluet.utils.Data;
import com.bluet.utils.Data.DataChangeListener;


public class ParameterHome extends BaseFragment implements DataChangeListener {
	TextView  Fill_text_view;
	TextView  Bwol_text_view;
	TextView  Fill_speed_text_view;
	TextView  Wash_speed_text_view;
	TextView  Empty_speed_text_view;
	TextView  Auto_run_volume_text_view;
	TextView  Max_wash_volume_text_view;
    ImageView  param_setting;
    ImageView  alert_setting;
    RelativeLayout param_layout;
    RelativeLayout alert_layout;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.param_setting_home, null, false);
        Bwol_text_view=(TextView) view.findViewById(R.id.Set_pra_line1_Value);
        Bwol_text_view.setText("125");
        Fill_speed_text_view = (TextView) view.findViewById(R.id.Set_pra_line2_Value);
        Fill_speed_text_view.setText("300");
        Wash_speed_text_view = (TextView) view.findViewById(R.id.Set_pra_line3_Value);
        Empty_speed_text_view = (TextView) view.findViewById(R.id.Set_pra_line4_Value);
        Auto_run_volume_text_view = (TextView) view.findViewById(R.id.Set_pra_line5_Value);
        Max_wash_volume_text_view = (TextView) view.findViewById(R.id.Set_pra_line6_Value);

        param_setting = (ImageView) view.findViewById(R.id.ImageView01);
        param_layout = (RelativeLayout) view.findViewById(R.id.params_set_details);
        param_setting.setOnClickListener(new OnClickListener() {    
            public void onClick(View v) {    
            	alert_layout.setVisibility(RelativeLayout.GONE);
            	param_layout.setVisibility(RelativeLayout.VISIBLE);
            }    
        }); 
        alert_setting = (ImageView) view.findViewById(R.id.alarm_set_img);
        alert_layout = (RelativeLayout) view.findViewById(R.id.params_set_alert);
        alert_setting.setOnClickListener(new OnClickListener() {    
            public void onClick(View v) {
            	param_layout.setVisibility(RelativeLayout.GONE);
            	alert_layout.setVisibility(RelativeLayout.VISIBLE);
            }    
        }); 
        Data.getInstance().addListener(this);
        return view;
    }

    @Override
    public void dataChanged() {
    	 if(Data.getInstance().getBowl()==225){
    		Bwol_text_view.setText("225");	
 	    }
 	    else if (Data.getInstance().getBowl()==125){
 	    	Bwol_text_view.setText("125");
 	    }
    	 Fill_speed_text_view.setText(String.valueOf(Data.getInstance().GetFillspeed())); 
    	 Wash_speed_text_view.setText(String.valueOf(Data.getInstance().GetWashspeed()));
    	 Empty_speed_text_view.setText(String.valueOf(Data.getInstance().GetEmptyspeed()));
    	 Fill_speed_text_view.setText(String.valueOf(Data.getInstance().GetFillspeed())); 
    	 Auto_run_volume_text_view.setText(String.valueOf(Data.getInstance().GetWashspeed()));
    	 Max_wash_volume_text_view.setText(String.valueOf(Data.getInstance().GetEmptyspeed()));

    }

}