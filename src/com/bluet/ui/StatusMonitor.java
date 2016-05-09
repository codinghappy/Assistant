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

public class StatusMonitor extends BaseFragment {
	Button begin;
	Button stop;
	ImageView image_view;
	TextView  Fill_text_view;
	int status = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.status_monitor, null, false);
        Fill_text_view= (TextView) view.findViewById(R.id.fill_ML);
        Fill_text_view.setText("65536 mL");
        image_view = (ImageView) view.findViewById(R.id.imageView_status);
        switch(status){
          case 1: //进血
     	       image_view.setImageResource(R.drawable.icon_record_normal);
     	       break;
          case 2:
     	       image_view.setImageResource(R.drawable.blood_in);
     	       break;
          case 3:
     	       image_view.setImageResource(R.drawable.control_print);
     	       break;
          case 4:
     	       image_view.setImageResource(R.drawable.blood_in);
     	       break;
          }
        
        begin = (Button) view.findViewById(R.id.begin);
        begin.setOnClickListener(new OnClickListener() {
        	 public void onClick(View v) {
        		// Toast toast=Toast.makeText(getActivity(), "zhangfeng", Toast.LENGTH_SHORT);
        		// toast.show();

                
                 status++;
                 if(status == 5)status=1;
        	 }
        });
        
        image_view.setOnClickListener(new OnClickListener() {
       	 public void onClick(View v) {
       		Toast toast=Toast.makeText(getActivity(), "点击了图片", Toast.LENGTH_SHORT);
       		toast.show();
       	 }
       });
        
        getActivity().getWindow().setTitle("自体血液回收机");
        //getActivity().getWindow().getWindowManager();
        return view;
    }

}