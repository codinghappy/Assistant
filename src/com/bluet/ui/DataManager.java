package com.bluet.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.bluet.massistant.BaseFragment;
import com.bluet.massistant.R;
import com.bluet.utils.Data;


public class DataManager extends BaseFragment {
	private TextView debugInfo;
	private Button debug;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_manager, null, false);
        debugInfo = (TextView) view.findViewById(R.id.debug_info);
        debug = (Button) view.findViewById(R.id.button_debug);
        debug.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				debugInfo.setText(Data.allInfo());
			}
		});
        return view;
    }
	public TextView getDebugInfo() {
		return debugInfo;
	}
	public void setDebugInfo(TextView debugInfo) {
		this.debugInfo = debugInfo;
	}

}