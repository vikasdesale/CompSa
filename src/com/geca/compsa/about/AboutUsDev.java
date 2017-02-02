package com.geca.compsa.about;

import com.geca.compsa.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu.OnMenuItemClickListener;

@SuppressLint("NewApi") public class AboutUsDev extends Activity implements OnMenuItemClickListener {


    String Urlfacebook="https://www.facebook.com/compsa";
    String Urlgoogleplus="https://plus.google.com/compsa";

    ImageView share,facebook,googleplus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_dev);
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
    
	            share=(ImageView) findViewById(R.id.iShare);
	            facebook=(ImageView) findViewById(R.id.iFacebook);
	            googleplus=(ImageView) findViewById(R.id.iGoogle);
	            share.setOnClickListener(new View.OnClickListener() {
	                @Override
	                public void onClick(View v) {
	                    Intent i = new Intent(Intent.ACTION_SEND);
	                    i.setType("text/plain");
	                    i.putExtra(Intent.EXTRA_SUBJECT, "Compsa");
	                    String sAux = "\nGet the official Compsa app for latest updates and information about Compsa Department\n\n";
	                    sAux = sAux + "https://play.google.com/store/apps/details?id=com.geca.compsa \n\n";
	                    i.putExtra(Intent.EXTRA_TEXT, sAux);
	                    startActivity(Intent.createChooser(i, "Choose one"));
	                }
	            });
	            facebook.setOnClickListener(new View.OnClickListener() {
	                @Override
	                public void onClick(View v) {
	                    Intent i = new Intent(Intent.ACTION_VIEW);
	                    i.setData(Uri.parse(Urlfacebook));
	                    startActivity(i);
	                }
	            });
	            googleplus.setOnClickListener(new View.OnClickListener() {
	                @Override
	                public void onClick(View v) {
	                    Intent i = new Intent(Intent.ACTION_VIEW);
	                    i.setData(Uri.parse(Urlgoogleplus));
	                    startActivity(i);
	                }
	            });
	}
	  public boolean onMenuItemClick(MenuItem item) {
    	  switch(item.getItemId()){
   

          case R.id.item_about:
        	  Intent j11 =new Intent(AboutUsDev.this, com.geca.compsa.about.AboutUsDev.class);
              startActivity(j11);
              break;

      }
      return true;
	}
   

}
