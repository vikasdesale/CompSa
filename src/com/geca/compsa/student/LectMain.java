package com.geca.compsa.student;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.geca.compsa.R;

import android.os.Bundle;
import android.os.Environment;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LectMain extends ListActivity implements
		OnClickListener {

	// Widget Button Declare
	Button btnLecD;
	  private int endYear;
	    private int endMonth;String  s2;
	    private int endDay;         
        String s3,s4;
	    private Calendar end;String s1;String arg2;
	    static final int DATE_DIALOG_ID = 0;
	    private int mYear,mMonth,mDay;
	    EditText editText;
	    private static final String APPLICATION_FOLDER_MANAGER = "CompSa";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lectmain);
       // Init Widget Button and set click listener
		 setDate();
         Button btnPicker=(Button) findViewById(R.id.bButton);
         btnPicker.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View v) {
                 // TODO Auto-generated method stub
                 showDialog(DATE_DIALOG_ID);

             }
         });
		btnLecD = (Button) findViewById(R.id.LecDialog);
		btnLecD.setOnClickListener(this);

	
	}
	protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);

        }

        return null;

    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
          editText.setText(new StringBuilder().append(mDay).append("-").append(mMonth+1).append("-").append(mYear));
          String s=(""+mDay+"-"+mMonth+"-"+mYear);
          createF(s);
        }

	

    };
    private void getFilename(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,APPLICATION_FOLDER_MANAGER);
        
        if(!file.exists()){
                file.mkdirs();
        }

  
}
    private void setDate() {
		// TODO Auto-generated method stub
        	  
        	Calendar c=Calendar.getInstance();
               mYear=c.get(Calendar.YEAR);
               mMonth=c.get(Calendar.MONTH);
               mDay=c.get(Calendar.DAY_OF_MONTH);
               //String dateFormat = "dd/MM/yyyy";
               editText = (EditText) findViewById(R.id.editText1);
               SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
               editText.setText( sdf.format(c.getTime()));
               getFilename();
		
	}
	private void createF(String s) {
		// TODO Auto-generated method stub
	    String filepath = Environment.getExternalStorageDirectory()+"/CompSa";
          File file = new File(filepath,s);
          
          if(!file.exists()){
                  file.mkdirs();
      			Toast.makeText(LectMain.this,
    					"File isCreated", Toast.LENGTH_SHORT).show();
          }else{
        	  
        	  
          }
          
	}

	@Override
	public void onClick(View v) {
		if (v == btnLecD) {

			// Create Object of Dialog class
			final Dialog lec = new Dialog(this);
			// Set GUI of login screen
			lec.setContentView(R.layout.lcreate);
			lec.setTitle("Day Lecture");

			// Init button of login GUI
			Button btnSave = (Button) lec.findViewById(R.id.btnSave);
			Button btnCancel = (Button) lec.findViewById(R.id.btnCancel);
			final EditText txtDatePic= (EditText)lec.findViewById(R.id.txtDatePick);
			final EditText txtLecName = (EditText)lec.findViewById(R.id.txtLecName);
	         txtDatePic.setText(new StringBuilder().append(mDay).append("-").append(mMonth+1).append("-").append(mYear));
			 txtDatePic.setEnabled(false);
			
	         // Attached listener for login GUI button
			btnSave.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
			
					if( txtLecName.getText().toString().trim().length() > 0)
					{
						
						String s=txtLecName.getText().toString().trim();
						String td=txtDatePic.getText().toString().trim();
		                 try {
							createTxt(s,td);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					// Redirect to dashboard / home screen.
					lec.dismiss();
					}
					else
					{
						Toast.makeText(LectMain.this,
								"Please enter Lecture Name", Toast.LENGTH_LONG).show();

					}
				}

				private void createTxt(String s, String td) throws IOException {
					// TODO Auto-generated method stub
					
				    String filepath1 = Environment.getExternalStorageDirectory()+"/CompSa/"+td;
				                 
			          File file = new File(filepath1,s);
			 
					if(!file.exists()){
			                  file.mkdirs();
			  				Toast.makeText(LectMain.this,
									"Lecture Created", Toast.LENGTH_LONG).show();  
			  				s2=filepath1+"/"+s+"/"+s+".txt";
			  				FileWriter f=new FileWriter(s2);
			  				 s1=filepath1+"/"+s+"/";
		
			  		          s2=filepath1+"/"+s+"/"+".txt";
			  		    	s3=filepath1+"/"+s;
			  				CreateList(s1,s2,s3);	
	                     
			          }else{
			        	      s2= filepath1+"/"+s+"/"+s+System.currentTimeMillis()+".txt";
			        		FileWriter f=new FileWriter(s2);
			        		s1=filepath1+"/"+s+"/";
			        		s3=filepath1+"/"+s;
						      
					
			        		CreateList(s1,s2,s3);	
							
			          }
			          }

			          
	
				

				
			
			});
			btnCancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					lec.dismiss();
				}
			});

			// Make dialog box visible.
			lec.show();
		}
	}
	public ArrayList<HashMap<String, String>> songsList;
	public void CreateList(final String s1,final String s2, String s3)throws IndexOutOfBoundsException,IllegalStateException  {
	     ListFiles plm = new ListFiles();
	        plm.setPath(s1);
	     songsList = new ArrayList<HashMap<String, String>>();

		ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();
	    s4=s3;
		// get all songs from sdcard
		this.songsList = plm.getPlayList();

		// looping through playlist
		for (int i = 0; i < songsList.size(); i++) {
			// creating new HashMap
			HashMap<String, String> song = songsList.get(i);

			// adding HashList to ArrayList
			songsListData.add(song);
		}

		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, songsListData,
				R.layout.mplaylist_item, new String[] { "songTitle" },
				new int[] { R.id.songTitle });

		setListAdapter(adapter);

		// selecting single ListView item
		final ListView lv = getListView();
		// listening to single listitem click
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)throws IndexOutOfBoundsException,IllegalStateException {
				// getting listitem index
				int songIndex = position;
			
			    	  // selected item
			
			    	//String val =(String) (lv.getItemAtPosition(position));
			  //  System.out.println("Cicked At.............. "+val); 
			    HashMap<String,String> map=(HashMap<String, String>) parent.getItemAtPosition(position);
                               String val=map.get("songTitle");
			    Intent in = new Intent(getApplicationContext(),
						NoteRecord.class);
				in.putExtra("path5",s1);
				
				in.putExtra("file5",val);
				in.putExtra("pathMusic",s4);
				startActivity(in);
		
				
			  
			}
		});

	}
	

}
