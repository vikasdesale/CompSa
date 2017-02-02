package com.geca.compsa.student;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.geca.compsa.R;
import com.geca.compsa.news.NewsDetailActivity.DownloadJSONFileAsync;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ZoomControls;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) public class TimeTable extends Activity {

	String url = "http://noticeb.net46.net/getTimeTable.php";
	    private ProgressDialog mProgressDialog;	
	    public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
	    private DownloadJSONFileAsync c1;
	    ArrayList<HashMap<String, Object>> MyArrList = new ArrayList<HashMap<String, Object>>();
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        // ProgressBar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.stimetable);
		
		 LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error);
		 LinearLayout contLayout = (LinearLayout) findViewById(R.id.content);
			if (!com.geca.compsa.NetworkUtil.isNetworkConnected(this)){
		           
		        errorLayout.setVisibility(View.VISIBLE);
		           contLayout.setVisibility(View.GONE);
		  }else{
			  final ImageView imageView = (ImageView)findViewById(R.id.timeimg);

       
     ZoomControls   zoom = (ZoomControls) findViewById(R.id.zoomControls1);
     zoom.setOnZoomInClickListener(new OnClickListener() {
			
 		@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			
 			float x = imageView.getScaleX();
 			float y = imageView.getScaleY();
 			
 			imageView.setScaleX((float) (x+0.50));
 			imageView.setScaleY((float) (y+0.50));
 		}
 	});
  
         zoom.setOnZoomOutClickListener(new View.OnClickListener() {
 			
 		@Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			
  
 			float x =imageView.getScaleX();
 			float y = imageView.getScaleY();
 			
 			imageView.setScaleX((float) (x-0.50));
 			imageView.setScaleY((float) (y-0.50));
 		}
 	});
    
		    // Download JSON File	
	    	c1=new DownloadJSONFileAsync();
	    	c1.execute();
     }
		
  }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DIALOG_DOWNLOAD_JSON_PROGRESS:
        	mProgressDialog = new ProgressDialog(this);
      
                		  mProgressDialog.setMessage("Loading..Please Wait..");
                	         mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                	         mProgressDialog.setIndeterminate(true);
                	         mProgressDialog.setCancelable(true); 
                     mProgressDialog.show();
               
        	
            return mProgressDialog;

     
           
        default:
            return null;
        }
    }

    public class DownloadJSONFileAsync extends AsyncTask<String, Void, Void> {

        Bitmap bb1;
       @SuppressWarnings("deprecation")
    protected void onPreExecute() {
        	super.onPreExecute();

        	showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        	
        
       	
        }
        
       @Override
       protected Void doInBackground(String... params34) {
           // TODO Auto-generated method stub
    		
/*
       	final String MemberID ="1"; 

   		List<NameValuePair> params = new ArrayList<NameValuePair>();
           params.add(new BasicNameValuePair("sMemberID", MemberID));

        
           
       	String resultServer  = getHttpPost(url,params);
      	
           String strMemberID = "";
       	String strIMGURL="";
       	JSONObject c;
   		try {
   			c = new JSONObject(resultServer);
   			strMemberID = c.getString("Id");
   			strIMGURL=c.getString("TimeImg");
       	
   			bb1=loadBitmap((String) c.get("TimeImg"));
 	
    		
    
    		}catch(JSONException e){
    			
    		}
    	 
    		
*/
    	return null;
       }
       @SuppressWarnings("deprecation")
    		protected void onPostExecute(Void unused) {
    			// When Finish Show Content

         

	
    		// ColImage
			ImageView imageView = (ImageView)findViewById(R.id.timeimg);

       	 try
       	 {
       		 imageView.setImageResource(R.drawable.timetable);
       	 } catch (Exception e) {
       		 // When Error
       		 imageView.setImageResource(android.R.drawable.ic_menu_report_image);
       	 }
			

    						 dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
    				          removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
    					   
    				        
    		}
    	       protected void onCancelled() {
    		        super.onCancelled();

    		      c1.cancel(true);
    		    }
    		   
    		    
     }


    
/*	public String getHttpPost(String url,List<NameValuePair> params) {
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { // Status OK
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
			} else {
				Log.e("Log", "Failed to download result..");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}*/
	
	   /***** Get Image Resource from URL (Start) *****/
		/*	private static final String TAG = "ERROR";
			private static final int IO_BUFFER_SIZE = 4 * 1024;
			public static Bitmap loadBitmap(String url) {
			    Bitmap bitmap = null;
			    InputStream in = null;
			    BufferedOutputStream out = null;

			    try {
			        in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

			        final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			        out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
			        copy(in, out);
			        out.flush();

			        final byte[] data = dataStream.toByteArray();
			        BitmapFactory.Options options = new BitmapFactory.Options();
			        //options.inSampleSize = 1;

			        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
			    } catch (IOException e) {
			        Log.e(TAG, "Could not load Bitmap from: " + url);
			    } finally {
			        closeStream(in);
			        closeStream(out);
			    }

			    return bitmap;
			}

			 private static void closeStream(Closeable stream) {
			        if (stream != null) {
			            try {
			                stream.close();
			            } catch (IOException e) {
			                android.util.Log.e(TAG, "Could not close stream", e);
			            }
			        }
			    }
			 
			 private static void copy(InputStream in, OutputStream out) throws IOException {
		        byte[] b = new byte[IO_BUFFER_SIZE];
		        int read;
		        while ((read = in.read(b)) != -1) {
		            out.write(b, 0, read);
		        }
		    }*/
			 /***** Get Image Resource from URL (End) *****/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
   
}
