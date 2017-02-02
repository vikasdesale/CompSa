package com.geca.compsa.event;

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


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventDetails extends Activity {
 Button link; 
 String text;
 TextView title;
TextView time;
 TextView date;
TextView desc;
String url = "http://noticeb.net46.net/getEventD.php";
	String strLink = "";
	String MemberID;
	String Title;
	String Time;
	String Date;
	String resultServer;
	 String strMemberID = "";
 	String strDescription = "";
 	JSONObject c;
 	LinearLayout contLayout;
  	public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    private DownloadJSONFileAsync c1;
    Intent intent;
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eve_details);
         link = (Button)findViewById(R.id.link);
    	 text=link.getText().toString();
    	 title = (TextView)findViewById(R.id.title);
    	 time = (TextView)findViewById(R.id.time);
    	 date = (TextView)findViewById(R.id.date);
    	 desc = (TextView)findViewById(R.id.desc);
         intent= getIntent();
    	 MemberID = intent.getStringExtra("MemberID"); 
    	Title = intent.getStringExtra("Title"); 
    	 Time = intent.getStringExtra("Time"); 
         Date = intent.getStringExtra("Date"); 

         // Download JSON File	
     	c1=new DownloadJSONFileAsync();
     	c1.execute();
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Uri uri = Uri.parse("http://"+text);
            	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            	startActivity(intent);
            }
        });
     
    
    
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
				strMemberID = c.getString("Id");
				strDescription = c.getString("Description");
				strLink = c.getString("Link");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	return null;
   }
   @SuppressWarnings("deprecation")
		protected void onPostExecute(Void unused) {
			// When Finish Show Content

		if(!strMemberID.equals(""))
		{
			//tMemberID.setText(strMemberID);
			title.setText(" "+Title);
			time.setText(" "+Time);
			date.setText(" "+Date);
			desc.setText("Description:\n\t "+strDescription);
		    link.setText(""+strLink);
		
		}
		else
		{
			title.setText("- ");
			time.setText("- ");
			date.setText("- ");
			desc.setText("- ");
			link.setText("- ");
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
	
	 
    
}
