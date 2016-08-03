package com.bluet.ui;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
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
import com.bluet.massistant.Utils;
import com.bluet.utils.Data;


public class DataManager extends BaseFragment {
  protected static final int FILE_SELECT_CODE = 0;
private TextView debugInfo;
  private Button debug;
  private Button uploadFiles;
  private Button selectDir;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_manager, null, false);

        debugInfo = (TextView) view.findViewById(R.id.debug_info);
        debug = (Button) view.findViewById(R.id.button_debug);
        debug.setOnClickListener(new OnClickListener() {
      			public void onClick(View v) {
      				debugInfo.setText(Data.getInstance().GetInfo_Fill());
      			}
		});
        
        selectDir = (Button) view.findViewById(R.id.button_select_dir);
        selectDir.setOnClickListener(new OnClickListener() {
      			public void onClick(View v) {
      				    Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
                        intent.setType("*/*"); 
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
       
                        try {
                            startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
                        } catch (android.content.ActivityNotFoundException ex) {
                        }
      			        }
		   });
        
        uploadFiles = (Button) view.findViewById(R.id.button_upload_files);
        uploadFiles.setOnClickListener(new OnClickListener() {
      			public void onClick(View v) {
      				        File root = new File(getFullPath());
                      File[] files = root.listFiles();
                      for (File file : files) {
                          file.getName();
                          Utils.UploadFile(file);
                      }
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)  {
	    switch (requestCode) {
	        case FILE_SELECT_CODE: {
	        	if(resultCode == -1 && "file".equalsIgnoreCase(data.getData().getScheme())) {
	              // Get the Uri of the selected file 
	              Uri uri = data.getData();
                  String path = uri.getPath();
                  File file = new File(path);
                  path = file.getParent();
                  setFullPath(path);
	        	}
	        }           
	        break;
	    }
	super.onActivityResult(requestCode, resultCode, data);
	}
}