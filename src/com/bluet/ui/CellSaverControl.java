package com.bluet.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluet.massistant.BaseFragment;
import com.bluet.massistant.R;


public class CellSaverControl extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cell_saver_control, null, false);
        getActivity().getWindow().setTitle("血液回收机");
        return view;
    }

}