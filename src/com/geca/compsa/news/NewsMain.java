package com.geca.compsa.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.geca.compsa.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


@SuppressLint("NewApi") public class NewsMain extends Activity implements OnMenuItemClickListener  {
    
	  private ListView lstView;
	    private ImageAdapter imageAdapter;
	    private ProgressDialog mProgressDialog;
        private LoadContentFromServer c;
	    ArrayList<HashMap<String, Object>> MyArrList = new ArrayList<HashMap<String, Object>>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        // ProgressBar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.news);
		
		 LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error);
		 LinearLayout contLayout = (LinearLayout) findViewById(R.id.content);
			if (!com.geca.compsa.NetworkUtil.isNetworkConnected(this)){
		           
		        errorLayout.setVisibility(View.VISIBLE);
		           contLayout.setVisibility(View.GONE);
		  }else{

	        lstView = (ListView)findViewById(R.id.listView1); 
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
	            mProgressDialog = new ProgressDialog(this);
	            
      		  mProgressDialog.setMessage("Loading..Please Wait..");
      	         mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      	         mProgressDialog.setIndeterminate(true);
      	         mProgressDialog.setCancelable(true); 
           mProgressDialog.show();
     
	
        // ListView and imageAdapter
    	lstView = (ListView) findViewById(R.id.listView1);
    	lstView.setClipToPadding(false);
        
        imageAdapter = new ImageAdapter(getApplicationContext()); 
        
        lstView.setAdapter(imageAdapter);

		// OnClick Item
		lstView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> myAdapter, View myView,
					int position, long mylng) {

				String sID = MyArrList.get(position).get("Id")
						.toString();
				String sTitle= MyArrList.get(position).get("Title")
						.toString();
				String sDate= MyArrList.get(position).get("Date")
						.toString();
				
	             Intent newActivity = new Intent(NewsMain.this,NewsDetailActivity.class);
				newActivity.putExtra("nID", sID);
				newActivity.putExtra("nTitle", sTitle);
				newActivity.putExtra("nDate", sDate);
				startActivity(newActivity);
		          
			}
	        
			
		});
	       
        setProgressBarIndeterminateVisibility(true); 
        c= new LoadContentFromServer();
        c.execute(); 
		
     
	}
    }

    
    class LoadContentFromServer extends AsyncTask<Object, Integer, Object> {

        @Override
        protected Object doInBackground(Object... params) {

        	
        	String url = "http://noticeb.net46.net/getNews.php";
        	
          	JSONArray data;
    			try {
    				data = new JSONArray(getJSONUrl(url));
    				
    		    	MyArrList = new ArrayList<HashMap<String, Object>>();
    				HashMap<String, Object> map;
    				
    				for(int i = 0; i < data.length(); i++){
    	                JSONObject c = data.getJSONObject(i);
    	    			map = new HashMap<String, Object>();
    	    			// Thumbnail Get ImageBitmap To Object
    	    			map.put("Id", c.getString("Id"));
						map.put("Title", c.getString("Title"));
						map.put("Date", c.getString("Date"));
    	    			MyArrList.add(map);
    	    			publishProgress(i);
    	    				
    				}
    	    		
    				
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
 
            return null;
        }

        @Override
        public void onProgressUpdate(Integer... progress) {
     
        	imageAdapter.notifyDataSetChanged();
         	  mProgressDialog.dismiss();
        	
        }
        
        @Override
        protected void onPostExecute(Object result) {
            setProgressBarIndeterminateVisibility(false); // When Finish
            
     
        }
        protected void onCancelled() {
	        super.onCancelled();

	      c.cancel(true);
	    }
    }	
  	
	 
    class ImageAdapter extends BaseAdapter {

        private Context mContext; 

        public ImageAdapter(Context context) { 
            mContext = context; 
        } 

        public int getCount() { 
            return MyArrList.size();    
        } 

        public Object getItem(int position) { 
            return MyArrList.get(position); 
        } 

        public long getItemId(int position) { 
            return position; 
        } 

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
			 
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.news_column, null); 
			}
			
        		// ColPosition
				TextView txtPosition1 = (TextView) convertView.findViewById(R.id.Id);
				txtPosition1.setPadding(0, 0, 0, 0);
				txtPosition1.setText(""+MyArrList.get(position).get("Id"));
	
 	 
		// ColNamr
		TextView txtPosition = (TextView) convertView.findViewById(R.id.title);
		txtPosition.setPadding(0, 0, 0, 0);
		txtPosition.setText(""+MyArrList.get(position).get("Title"));
		
		// ColDesignation
		TextView txtPicName = (TextView) convertView.findViewById(R.id.date);
		txtPicName.setPadding(0, 0, 0, 0);
		txtPicName.setText(""+ MyArrList.get(position).get("Date"));
		 LinearLayout l= (LinearLayout) findViewById(R.id.list);
		 l.setVisibility(View.VISIBLE);	
     	return convertView;
				
		}
        
    }
    
    /*** Get JSON Code from URL ***/
  	public String getJSONUrl(String url) {
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
    public boolean onMenuItemClick(MenuItem item) {
    	  switch(item.getItemId()){
   

          case R.id.item_about:
        	  Intent j11 =new Intent(NewsMain.this, com.geca.compsa.about.AboutUsDev.class);
              startActivity(j11);
              break;

      }
      return true;
  	}
}
