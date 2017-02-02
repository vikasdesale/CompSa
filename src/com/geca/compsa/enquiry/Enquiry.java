package com.geca.compsa.enquiry;





import com.geca.compsa.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

@SuppressLint("NewApi") public class Enquiry extends Activity implements OnMenuItemClickListener {
	// Progress Dialog
	private ProgressDialog pDialog;
	EditText eName;
	EditText eEmail;
	EditText eContact;
	EditText eDescription;
   Button submit;
	// url to create new product
	private static String url_send = "http://noticeb.net46.net/enquiry.php";
    private CreateEnquiry c=null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enquiry);
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
	        	// Edit Text
	    		eName = (EditText) findViewById(R.id.eName);
	    		eEmail = (EditText) findViewById(R.id.eEmail);
	    		eContact = (EditText) findViewById(R.id.eContact);
	    	   eDescription = (EditText) findViewById(R.id.eDescrip);
	    	    /*Button btnabout=(Button)findViewById(R.id.btn_aboutus);
	            btnabout.setOnClickListener(new View.OnClickListener() {

	    			@Override
	    			public void onClick(View arg0) {
	    		         try{
	    		        	 PopupMenu popupMenu = new PopupMenu(Enquiry.this, arg0);
	    						popupMenu.setOnMenuItemClickListener(Enquiry.this);
	    						popupMenu.inflate(R.menu.item);
	    						popupMenu.show();
	    		         }catch(Exception e){}
	    		         }
	    			
	    		});*/
	    		// Create button
	   		Button btnSubmit = (Button) findViewById(R.id.bSubmit);

	   		// button click event
	   		btnSubmit.setOnClickListener(new View.OnClickListener() {

	   			@Override
	   			public void onClick(View view) {
	   				pDialog = new ProgressDialog(Enquiry.this);
					pDialog.setMessage("Submitting....");
					pDialog.setIndeterminate(false);
					pDialog.setCancelable(false);
					pDialog.show();
					c=new CreateEnquiry();
	   				c.execute();
	   			
	   				
                  
	   			}
	   		});

	
	
	}

	/**
	 * Background Async Task to Create new product
	 * */
	class CreateEnquiry extends AsyncTask<String,Integer, String> {

		/**
		 * Creating product
		 * */
		int code;
		protected String doInBackground(String... args) {
		try{
			InputStream is=null;
			String result=null;
			String line=null;
	
		    String	Name =eName.getText().toString();
		    String	Email =eEmail.getText().toString();
		    String	Contact =eContact.getText().toString();
		    String  Description=eDescription.getText().toString();
			

				// Building Parameters
	   			List<NameValuePair> params = new ArrayList<NameValuePair>();
	   			params.add(new BasicNameValuePair("Name", Name));
	   			params.add(new BasicNameValuePair("Email", Email));
	   			params.add(new BasicNameValuePair("Contact",Contact));
	   			params.add(new BasicNameValuePair("Description", Description));
			// request method is POST
			// defaultHttpClient
	   			try {
	   				DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url_send);
			httpPost.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
		
				is = httpEntity.getContent();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 try
		        {
		            BufferedReader reader = new BufferedReader
					(new InputStreamReader(is,"iso-8859-1"),8);
		            StringBuilder sb = new StringBuilder();
		            while ((line = reader.readLine()) != null)
			    {
		                sb.append(line + "\n");
		            }
		            is.close();
		            result = sb.toString();
			    Log.e("pass 2", "connection success ");
			}
		        catch(Exception e)
			{
		            Log.e("Fail 2", e.toString());
			}     
		       
			try
			{
		            JSONObject json_data = new JSONObject(result);
		            code=(json_data.getInt("code"));
					
		    
			}
			catch(Exception e)
			{
		            Log.e("Fail 3", e.toString());
			}
		}catch(Exception e){}
			return null;
		}
		 @Override
	        public void onProgressUpdate(Integer... progress) {
		 }
	       
	         
	        	
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
	        if(code==1)
            {
		Toast.makeText(Enquiry.this, "Submitted Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
		 Toast.makeText(Enquiry.this, "Sorry, Try Again",Toast.LENGTH_LONG).show();
            }
		}

		 protected void onCancelled() {
		        super.onCancelled();

		      c.cancel(true);

		        // ask if user wants to try again
		    }
	}
	  public boolean onMenuItemClick(MenuItem item) {
	  	  switch(item.getItemId()){
	 

	        case R.id.item_about:
	      	  Intent j11 =new Intent(Enquiry.this, com.geca.compsa.about.AboutUsDev.class);
	            startActivity(j11);
	            break;

	    }
	    return true;
		}
}

		
		
	
	