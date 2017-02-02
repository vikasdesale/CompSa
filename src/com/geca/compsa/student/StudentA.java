package com.geca.compsa.student;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.geca.compsa.R;
import com.geca.compsa.student.StudentAchive.DownloadJSONFileAsync;

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
import android.widget.LinearLayout;
import android.widget.TextView;

public class StudentA extends Activity {
	  TextView year;
      TextView title;
      TextView description;
      TextView winners; 
      String MemberID;
      String url = "http://noticeb.net46.net/getStudById.php";
      String strMemberID = "";
  	String strYear = "";
  	String strTitle = "";
  	String strDescription = "";
  	String strWinner = "";
  	JSONObject c;
    LinearLayout contLayout;
  	public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    private DownloadJSONFileAsync c1;
  	String resultServer;
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stud_achive_details);
	   contLayout = (LinearLayout) findViewById(R.id.content);
        contLayout.setVisibility(View.GONE);
         year = (TextView)findViewById(R.id.y1);
         title = (TextView)findViewById(R.id.title1);
      	 description = (TextView)findViewById(R.id.desc1);
         winners = (TextView)findViewById(R.id.winners);
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
                	         mProgressDialog.setIndeterminate(false);
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
					strTitle = c.getString("Title");
					strYear = c.getString("Year");
	    			strDescription = c.getString("Description");
	    			strWinner = c.getString("Winner");
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
		
				year.setText(strYear);
				title.setText("Title:- "+strTitle+" ");	title.setPadding(10, 5,10,5);
				description.setText("Description:-\n\t "+strDescription);
				description.setPadding(10, 5,10, 5);
				winners.setText("Winners:-\n\t"+strWinner);
				winners.setPadding(10, 5,10, 5);
			}
		//	else
			//{
				//year.setText(" -");
				//title.setText("- ");
				//description.setText(" -");
				//winners.setText("- ");
		//	}
							 dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
					          removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
						   
					          contLayout.setVisibility(View.VISIBLE);  
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
