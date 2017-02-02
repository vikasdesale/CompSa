package com.geca.compsa.news;


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
import com.geca.compsa.sponsors.Offer.DownloadJSONFileAsync;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsDetailActivity extends Activity {
	
	 TextView tTitle;
	 TextView tDate;
	 TextView tDesc;String ID; 
	 String Title;
	 String Date;
	 String resultServer ;
     String strID;
     String strDesc="";
 	String strIMGURL1="";
 	String strIMGURL2="";
	String url = "http://noticeb.net46.net/getNewsById.php";
 	JSONObject c;
	public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    private DownloadJSONFileAsync c1;
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        ImageView iv1=(ImageView) findViewById(R.id.imageView1);
        ImageView iv2=(ImageView) findViewById(R.id.imageView3);
        ImageView iv3=(ImageView) findViewById(R.id.imageView2);
        iv1.setBackgroundResource(R.anim.animation1);
        iv2.setBackgroundResource(R.anim.animation);
        iv3.setBackgroundResource(R.anim.animation2);
        AnimationDrawable anim1=(AnimationDrawable) iv1.getBackground();
        AnimationDrawable anim2=(AnimationDrawable) iv2.getBackground();
        AnimationDrawable anim3=(AnimationDrawable) iv3.getBackground();
            anim1.start();
            anim2.start();    
            anim3.start();
       	 tTitle = (TextView)findViewById(R.id.date);
         tDate = (TextView)findViewById(R.id.time);
        tDesc= (TextView)findViewById(R.id.desc);
    	Intent intent= getIntent();
   	 ID = intent.getStringExtra("nID"); 
   	Title = intent.getStringExtra("nTitle");
   	 Date = intent.getStringExtra("nDate"); 
        // Download JSON File	
    	c1=new DownloadJSONFileAsync();
    	c1.execute();
     
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
        int success=0;
        Bitmap bb1,bb2;
       @SuppressWarnings("deprecation")
    protected void onPreExecute() {
        	super.onPreExecute();

        	showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        	
        
       	
        }
        
       @Override
       protected Void doInBackground(String... params34) {
           // TODO Auto-generated method stub
    		
    		List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ID",ID));


         resultServer  = getHttpPost(url,params);
       	
        
    		try {
    			c = new JSONObject(resultServer);
    			strID = c.getString("Id");
    			strDesc=c.getString("Description");
    			strIMGURL1=c.getString("Img1");
    			strIMGURL2=c.getString("Img2");
    			
    			 bb1=loadBitmap((String) c.get("Img1"));
    			 bb2=loadBitmap((String) c.get("Img2"));
    		}catch(JSONException e){
    			
    		}
    	 
    		

    	return null;
       }
       @SuppressWarnings("deprecation")
    		protected void onPostExecute(Void unused) {
    			// When Finish Show Content

           tTitle.setText(""+Title);
           tDate.setText(""+Date);
    	   tDesc.setText(""+strDesc);

			ImageView imageView = (ImageView)findViewById(R.id.i1);
			imageView.getLayoutParams().height = 150;
			imageView.getLayoutParams().width = 140;
		
       	 try
       	 {
       		 imageView.setImageBitmap(bb1);
       	 } catch (Exception e) {
       		 // When Error
       		 imageView.setImageResource(android.R.drawable.ic_menu_report_image);
       	 }
			
		
       		ImageView imageView1 = (ImageView)findViewById(R.id.i2);
   			imageView1.getLayoutParams().height = 150;
   			imageView1.getLayoutParams().width = 140;
   			
   	
           	 try
           	 {
           		 imageView1.setImageBitmap(bb2);
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

    

	public String getHttpPost(String url,List<NameValuePair> params) {
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
	}
	
	   /***** Get Image Resource from URL (Start) *****/
			private static final String TAG = "ERROR";
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
		    }
			 /***** Get Image Resource from URL (End) *****/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
