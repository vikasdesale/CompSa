package com.geca.compsa.gallery;

import java.util.ArrayList;
import java.util.List;

import com.geca.compsa.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;
@SuppressLint("NewApi") public class Gallery extends Activity implements
		OnItemClickListener, OnMenuItemClickListener {

	public static final String[] titles = new String[] { 
		"Campus Life","Department Life","Aurangabad Life","Send Picture"};

	

	ListView listView;
	List<RowItem> rowItems;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_main);
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
		rowItems = new ArrayList<RowItem>();
		for (int i = 0; i < titles.length; i++) {
			RowItem item = new RowItem(titles[i]);
			rowItems.add(item);
		}

		listView = (ListView) findViewById(R.id.card_listView);
		CustomBaseAdapterFaculties adapter = new CustomBaseAdapterFaculties(this, rowItems);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String url;
		int Id=(position + 1);
		String title=rowItems.get(position).toString();
	//	Toast toast = Toast.makeText(getApplicationContext(),
			//	"Item " + (position + 1) + ": " + rowItems.get(position),
			//	Toast.LENGTH_SHORT);
		//toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
		//toast.show();
		try{ Intent newActivity = new Intent(com.geca.compsa.gallery.Gallery.this,com.geca.compsa.gallery.GalleryDisp.class);
	        if(title=="Campus Life"){
	            url = "http://noticeb.net46.net/campusl.php";
	        	newActivity.putExtra("title",url);
	        	startActivity(newActivity);
	 	 }else if(title=="Department Life")     
	{  	         url = "http://noticeb.net46.net/departl.php";
	             newActivity.putExtra("title",url);
	             startActivity(newActivity);
	}else if(title=="Aurangabad Life") {    
		       url = "http://noticeb.net46.net/aurangabadl.php";
		         newActivity.putExtra("title",url);
		         startActivity(newActivity);
	}   else if(title=="Send Picture")  {
		String[] To={"vikasdesale2014@gmail.com"};
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,To);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				"Picture is send to Department of Computer Science");
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Hello Department of Computer Science & Engineering is one of the most best department in geca,my experience here in the form of picutres");
		startActivity(emailIntent);
	}
		}catch(Exception e){}
		
	}
	 public boolean onMenuItemClick(MenuItem item) {
	  	  switch(item.getItemId()){
	 

	        case R.id.item_about:
	      	  Intent j11 =new Intent(Gallery.this, com.geca.compsa.about.AboutUsDev.class);
	            startActivity(j11);
	            break;

	    }
	    return true;
		}
}