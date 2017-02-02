package com.geca.compsa.sponsors;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import com.geca.compsa.event.EventDetails.DownloadJSONFileAsync;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Offer extends Activity {
	
	 TextView toffer;
	 String url = "http://noticeb.net46.net/getOfferById.php";
	 String MemberID;	String resultServer; JSONObject c;  String strOffer = "";
		public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
	    private ProgressDialog mProgressDialog;
	    private DownloadJSONFileAsync c1;
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soffer);
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
       toffer = (TextView)findViewById(R.id.listView1);
       Intent intent= getIntent();
       MemberID = intent.getStringExtra("MemberID"); 
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

       @SuppressWarnings("deprecation")
    protected void onPreExecute() {
        	super.onPreExecute();

        	showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        	
        
       	
        }
        
       @Override
       protected Void doInBackground(String... params34) {
           // TODO Auto-generated method stub
    		
    				
    	   List<NameValuePair> params = new ArrayList<NameValuePair>();
           params.add(new BasicNameValuePair("sMemberID", MemberID));
           resultServer  = getHttpPost(url,params);
      	
    		  
    			
    			try {
    				c = new JSONObject(resultServer);
    				strOffer = c.getString("Offer");
    		
    			
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		

    	return null;
       }
       @SuppressWarnings("deprecation")
    		protected void onPostExecute(Void unused) {
    			// When Finish Show Content
    		 LinearLayout l= (LinearLayout) findViewById(R.id.list);
			 l.setVisibility(View.VISIBLE);	
		
				toffer.setText(" "+strOffer);
    		
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
	

    
}
