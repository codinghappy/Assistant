package com.bluet.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
  protected static final int FILE_PATH_SELECT_CODE = 0;
  protected static final int FILE_SELECT_CODE = 1;
  private TextView debugInfo;
  private Button debug;
  private Button uploadFiles;
  private Button selectDir;
  private Button selectFile;
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
                            startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), FILE_PATH_SELECT_CODE);
                        } catch (android.content.ActivityNotFoundException ex) {
                        }
      			        }
		   });
        selectFile = (Button) view.findViewById(R.id.button_select_file);
        selectFile.setOnClickListener(new OnClickListener() {
      			public void onClick(View v) {
      				    Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
                        intent.setType("*/*"); 
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
       
                        try {
                            startActivityForResult( Intent.createChooser(intent, "Select a File to Read"), FILE_SELECT_CODE);
                        } catch (android.content.ActivityNotFoundException ex) {
                        }
      			        }
		   });
        
        uploadFiles = (Button) view.findViewById(R.id.button_upload_files);
        uploadFiles.setOnClickListener(new OnClickListener() {
      			public void onClick(View v) {
      				Utils.backDirector(Utils.GetDataDir());
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
	        case FILE_PATH_SELECT_CODE: {
	        	if(resultCode == -1 && "file".equalsIgnoreCase(data.getData().getScheme())) {
	              // Get the Uri of the selected file 
	              Uri uri = data.getData();
                  String path = uri.getPath();
                  File file = new File(path);
                  path = file.getParent();
                  Utils.setFullPath(path);
	        	}
	        }           
	        break;
	        case FILE_SELECT_CODE: {
	        	if(resultCode == -1 && "file".equalsIgnoreCase(data.getData().getScheme())) {
	              // Get the Uri of the selected file 
	              Uri uri = data.getData();
                  String path = uri.getPath();
                  try {
					FileInputStream fin = new FileInputStream(path);
					int len = fin.available();
					byte[] buffer = new byte[len];
					
					fin.read(buffer);
					String res = EncodingUtils.getString(buffer, "UTF-8");   
					debugInfo.setText(res);
					debugInfo.setMovementMethod(ScrollingMovementMethod.getInstance()); 
			        fin.close(); 
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	}
	        }           
	        break;
	    }
	super.onActivityResult(requestCode, resultCode, data);
	}
}