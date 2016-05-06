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

public class StatusMonitor extends BaseFragment {
	Button begin;
	ImageView image_view;
	int status = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.status_monitor, null, false);
        image_view = (ImageView) view.findViewById(R.id.imageView_status);
        begin = (Button) view.findViewById(R.id.begin);
        begin.setOnClickListener(new OnClickListener() {
        	 public void onClick(View v) {
        		// Toast toast=Toast.makeText(getActivity(), "zhangfeng", Toast.LENGTH_SHORT);
        		// toast.show();

                 status++;
        		 if (status % 2 == 0) {
        		   image_view.setImageResource(R.drawable.icon_record_normal);
        		 } else {
        			image_view.setImageResource(R.drawable.blood_in);
        		 }
        	 }
        });
        
        image_view.setOnClickListener(new OnClickListener() {
       	 public void onClick(View v) {
       		Toast toast=Toast.makeText(getActivity(), "点击了图片", Toast.LENGTH_SHORT);
       		toast.show();
       	 }
       });
        getActivity().getWindow().setTitle("自体血液回收机");
        return view;
    }

}