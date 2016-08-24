package com.bluet.massistant;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

public class Utils {
	private static final String SERVER = "121.42.193.103";
	// private static final String SERVER = "192.168.0.106";
	private static final String PORT = "3000";
	private static final String ASSISTANT_FILE_PATH = "/Assistant_Data/";
    private static String full_path_ = "";
    
    public static String getFullPath() {
        return full_path_;
    }
    
    public static void setFullPath(String full_path) {
    	full_path_ = full_path;
    }
    
	public static String GetDataDir() {
		return Environment.getExternalStorageDirectory().toString().concat(ASSISTANT_FILE_PATH);
	}
	public static boolean MakeDir(String dir) {
		File back_dir = new File(dir);
    	if(!back_dir.exists()){
    		  return back_dir.mkdirs();
    	}
    	
    	return true;
	}
    public static void RunTaskOnWorkThread(final File file, final Handler handler) {
        new Thread(){
            @Override
            public void run(){
                post(file.toString(), "http://" + SERVER +":" + PORT +"/post", handler);
            }
            }.start();
    }
    public static void UploadFile(File file, Handler hander) {
        RunTaskOnWorkThread(file, hander);
   }
    public static void post(String pathToOurFile,String urlServer, Handler handler) {
          HttpClient httpclient = new DefaultHttpClient();
          httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
           
          HttpPost httppost = new HttpPost(urlServer);
          File file = new File(pathToOurFile);
          String dir_name = file.getParentFile().getName();
          MultipartEntity mpEntity = new MultipartEntity(); 
          ContentBody cbFile = new FileBody(file);
          mpEntity.addPart("userfile", cbFile);
          try {
			mpEntity.addPart("dirname", new StringBody(dir_name, Charset.forName("UTF-8")));
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
       
          httppost.setEntity(mpEntity);
          System.out.println("executing request " + httppost.getRequestLine());
           
          HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
        	Message msg = handler.obtainMessage();
        	msg.what = 0;
        	handler.sendMessage(msg);
            e1.printStackTrace();
            return;
        }
        
        if(response.getStatusLine().getStatusCode() == 200) {
        	backUpFile(file);
        }
       
        httpclient.getConnectionManager().shutdown();
        return;
        }

	private static void backUpFile(File file) {
		String back_path = file.getParentFile().toString() + "_back";
		File back_dir = new File(back_path);
		if(!back_dir.exists()){
			back_dir.mkdirs();
		}
		file.renameTo(new File(back_dir + File.separator + file.getName()));
	}
    
	public static void backDirector(String rootPath, Handler handler) {
		File root = new File(rootPath);
		if (!root.exists() || root.isFile())
		   return;
		
		for(File file : root.listFiles()) {
			if (file.isDirectory() && !file.getName().endsWith("_back")) {
				for (File dataFile : file.listFiles()) {
					Utils.UploadFile(dataFile, handler);
				}
			}
		}
	}
	
    public static void PostFile(File file) {
            HttpURLConnection con;  
            URL url = null;
            try {
                url = new URL("http://115.28.178.225:3000/post");
            } catch (MalformedURLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            String boundary = java.util.UUID.randomUUID().toString(); 
            try {  

                con = (HttpURLConnection) url.openConnection();  

                con.setConnectTimeout(5000);  

                con.setDoInput(true);  

                con.setDoOutput(true);  

                con.setUseCaches(false);  

                con.setRequestMethod("POST");  

                con.setRequestProperty("Connection", "Keep-Alive");  

                con.setRequestProperty("Charset", "UTF-8");  

                con.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);  


                 DataOutputStream ds = new DataOutputStream(con.getOutputStream());  

                 FileInputStream fStream = new FileInputStream(file);  


                  int bufferSize = 1024;  

                  byte[] buffer = new byte[bufferSize];  



                  int length = -1;  


                  while((length = fStream.read(buffer)) != -1) {  


                    ds.write(buffer, 0, length);  

                  }   

                  fStream.close();   

                  ds.flush();  

                  ds.close();  
        } catch(Exception e) {
            e.printStackTrace();
        }
        }
    }
