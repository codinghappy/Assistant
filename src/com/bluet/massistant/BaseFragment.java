package com.bluet.massistant;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BaseFragment extends Fragment {
    private String title;
    private int iconId;
    private String full_path_;
    private static final int HTTP_SERVER_REFUED = 0;
    protected HomeFragmentActivity homeFragment;

    protected Handler handler = new Handler() {
        public void handleMessage(Message msg) {   
            switch (msg.what) {   
                 case HTTP_SERVER_REFUED:   
              	   ShowToast("服务器未开启");
                      break;   
            }   
            super.handleMessage(msg);   
       } 
    };
    
    public Handler getHandler() {
  	  return handler;
    }
    public void ShowToast(String content) {
  	  Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
    
    public void setHomeFragment(HomeFragmentActivity fragment) {
    	homeFragment = fragment;
    }
    
    public HomeFragmentActivity getHomeFragment() {
    	return homeFragment;
    }
    
    public void sendMessage(byte[] message) {
    	homeFragment.getBluetoothClient().sendHead();
		homeFragment.getBluetoothClient().sendMessage(message);
		//homeFragment.getBluetoothClient().sendTail();
    }
    public void sendMessage_fromat(int contentTag,int contentLen,byte[] message) {
    	homeFragment.getBluetoothClient().sendHead();
		homeFragment.getBluetoothClient().sendContent(contentTag, contentLen, message);
		homeFragment.getBluetoothClient().sendTail();
    }  
    public void setTitle() {
    	getActivity().getWindow().setTitle("自体血液回收机");
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null, false);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(getTitle());
        return view;
    }

	public void dataChanged() {
		
	}
}

