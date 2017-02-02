package com.geca.compsa.student;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;

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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.geca.compsa.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;

public class StudentCommitte extends Activity {
    private ProgressDialog mProgressDialog;
     ListView lstView1; Button btnSearch;
     private DownloadJSONFileAsync c;
    ArrayList<HashMap<String, String>> MyArrList;
 @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studetails);
	    LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error);
	    LinearLayout contLayout = (LinearLayout) findViewById(R.id.content);
	   if (!com.geca.compsa.NetworkUtil.isNetworkConnected(this)){
		           
		       errorLayout.setVisibility(View.VISIBLE);
		           contLayout.setVisibility(View.GONE);

		        }else{
		lstView1 = (ListView)findViewById(R.id.listView1); 
		mProgressDialog = new ProgressDialog(this);
	            
      		  mProgressDialog.setMessage("Loading..Please Wait..");
      	         mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      	         mProgressDialog.setIndeterminate(true);
      	         mProgressDialog.setCancelable(true); 
           mProgressDialog.show();
     
		  // Download JSON File	
       	c=new DownloadJSONFileAsync();
    	c.execute();
       lstView1.setAdapter(new ImageAdapter(this));
		      }
		
	}

    
    // Show All Content
    public void ShowAllContent()
    {
        // listView1
  
        lstView1.setAdapter(new ImageAdapter(StudentCommitte.this,MyArrList));
        
    }
    
    
    
    
	public class ImageAdapter extends BaseAdapter {
		private Context context;
	        private ArrayList<HashMap<String, String>> MyArr = new ArrayList<HashMap<String,String>>();

	        public ImageAdapter(Context c) 
	        {
	        	// TODO Auto-generated method stub
	            context = c;
	          
	        }
	 
	        public ImageAdapter(Context c, ArrayList<HashMap<String, String>> myArrList) 
	        {
	        	// TODO Auto-generated method stub
	            context = c;
	            MyArr = myArrList;
	        }
	 
	        public int getCount() {
	        	// TODO Auto-generated method stub
	            return MyArr.size();
	        }
	 
	        public Object getItem(int position) {
	        	// TODO Auto-generated method stub
	            return position;
	        }
	 
	        public long getItemId(int position) {
	        	// TODO Auto-generated method stub
	            return position;
	        }
	        
	        public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
	   
				
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 
			 
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.stud_activity_column, null); 
				}
		     	// ColPosition
						TextView txtPosition1 = (TextView) convertView.findViewById(R.id.ColImgPos);
						txtPosition1.setPadding(0, 0, 0, 0);
						txtPosition1.setText(""+MyArr.get(position).get("Id"));
			
	        	 
				// ColNamr
				TextView txtPosition = (TextView) convertView.findViewById(R.id.ColImgID);
				txtPosition.setPadding(10, 0, 0, 0);
				txtPosition.setText(""+MyArr.get(position).get("Name"));
				
				// ColDesignation
				TextView txtPicName = (TextView) convertView.findViewById(R.id.ColImgDesc);
				txtPicName.setPadding(50, 0, 0, 0);
				txtPicName.setText(""+ MyArr.get(position).get("Designation"));
			 
				return convertView;
					
			}

	    } 
	    
	 public class DownloadJSONFileAsync extends AsyncTask<String, Void, Void> {

		   protected void onPreExecute() {
	        	super.onPreExecute();
	       	
	        }
	        
		   @Override
	       protected Void doInBackground(String... params) {
	           // TODO Auto-generated method stub
				String url = "http://noticeb.net46.net/getstudentc.php";
				try {
					JSONArray data = new JSONArray(getJSONUrl(url,null));
		              MyArrList = new ArrayList<HashMap<String, String>>();
					  HashMap<String, String> map;
					for (int i = 0; i < data.length(); i++) {
						JSONObject c = data.getJSONObject(i);
						map = new HashMap<String, String>();
						map.put("Id", c.getString("Id"));
						map.put("Name", c.getString("Name"));
						map.put("Designation", c.getString("Designation"));
						MyArrList.add(map);
					
						
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    		return null;
		   }
		   protected void onPostExecute(Void unused) {
			// When Finish Show Content
			   ShowAllContent(); 
				mProgressDialog.dismiss();	 
	 
		}
	       protected void onCancelled() {
		        super.onCancelled();

		      c.cancel(true);
		    }
		   
		    
 }
		   
	  
	   
	
	   
	   /*** Get JSON Code from URL ***/
		public String getJSONUrl(String url,List<NameValuePair> params) {
			StringBuilder str = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) { // Download OK
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						str.append(line);
					}
				} else {
					Log.e("Log", "Failed to download file..");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str.toString();
		}
	    
	    
	  
		
		 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
