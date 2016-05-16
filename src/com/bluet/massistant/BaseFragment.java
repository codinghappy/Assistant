package com.bluet.massistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseFragment extends Fragment {
    private String title;
    private int iconId;
    
    protected HomeFragmentActivity homeFragment;
    
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

