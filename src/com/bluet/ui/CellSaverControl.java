package com.bluet.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bluet.massistant.BaseFragment;
import com.bluet.massistant.R;


public class CellSaverControl extends BaseFragment {
	Button fill;
	byte[] message_fill = new byte[8];
	
	Button wash;
	byte[] message_wash = new byte[8];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cell_saver_control, null, false);
        
        fill = (Button) view.findViewById(R.id.fill);
        fill.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				message_fill[0] = (byte) 0x01;
				message_fill[1] = (byte) 0x09;
				message_fill[2] = (byte) 0x01;
				message_fill[3] = (byte) 0x00;
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
				message_wash[0] = (byte) 0x01;
				message_wash[1] = (byte) 0x07;
				message_wash[2] = (byte) 0x01;
				message_wash[3] = (byte) 0x01;
				message_wash[4] = (byte) 0x09;
				message_wash[5] = (byte) 0x00;
				message_wash[6] = (byte) 0xCC;
				message_wash[7] = (byte) 0x33;
				sendMessage(message_wash);
			}
		});
        
        return view;
    }

}