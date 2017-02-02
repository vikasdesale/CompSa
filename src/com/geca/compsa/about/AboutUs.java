package com.geca.compsa.about;

import com.geca.compsa.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AboutUs extends Activity {
	 TextView t,tv,tv1,tv2;
	 ScrollView scroll1,scroll2;
	 Handler handler;
	 Runnable r,r1;
	    int  i;
	    int n;
	 String i1,i2;
	 ScrollView rootLayout ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
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
		rootLayout = (ScrollView) findViewById(R.id.root_layout);
	        LinearLayout aboutGeca = (LinearLayout) findViewById(R.id.aboutgecal);
	        aboutGeca.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	               
	            	 setContentView(R.layout.aboutg);
	                 i1=getString(R.string.ageca);
	                  t=(TextView) findViewById(R.id.trans1);
	                  tv1=(TextView) findViewById(R.id.t1);
	                     scroll1=(ScrollView) findViewById(R.id.scroll1);
	                     n=i1.length();
	                      handler=new Handler();
	                     i=0;
	               try{	  String fontPath = "fonts/TIMES.TTF";
            		  String fontPath2 = "fonts/National Cartoon.ttf";
	                  Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
	                  Typeface tf1 = Typeface.createFromAsset(getAssets(), fontPath2);
	                  t.setTypeface(tf,Typeface.ITALIC);
	                  tv1.setTypeface(tf1,Typeface.BOLD);
	               }catch(Exception e){}  r=new Runnable(){
	                
	                    public void run(){
	                      
	                    	 try{
	                 
	                    	
	        	                 
	                    	 String m=""+i1.charAt(i);
	                    	 CharSequence l=m;
	                    	 t.append(l);
	                        scroll1.fullScroll(View.FOCUS_DOWN);
	                        handler.postDelayed(this,10);
	                   
	                       
	        	               i++; 
	                    	 }catch(StringIndexOutOfBoundsException e)
	                    	 {
	                    		 
	                    	 }
	                    }
	               
	                };
	                handler.postDelayed(r,30);
    	             if(i>=n)
    	            	 handler.removeCallbacks(r);
    	            	 
	            }
	        });
	        LinearLayout aboutCompsa = (LinearLayout) findViewById(R.id.aboutcompsal);
	        aboutCompsa.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                
	            	 setContentView(R.layout.aboutc);
	                 i2=getString(R.string.acomp);
	                  tv=(TextView) findViewById(R.id.trans2);
	                  scroll2=(ScrollView) findViewById(R.id.scroll2);
	                  tv2=(TextView) findViewById(R.id.t2);
	                  n=i2.length();
	                      handler=new Handler();
	                    i=0;
	               	   try{ String fontPath3= "fonts/TIMES.TTF";
           		        String fontPath4 = "fonts/National Cartoon.ttf";
	                  Typeface tf = Typeface.createFromAsset(getAssets(), fontPath3);
	                  Typeface tf1 = Typeface.createFromAsset(getAssets(), fontPath4);
	                  tv.setTypeface(tf,Typeface.ITALIC);
	                  tv2.setTypeface(tf1,Typeface.BOLD);
	               	   }catch(Exception e){}r=new Runnable(){
	                    
	                    public void run(){
	                      
	                    	 try{
	                    		
	    
	        	          	 String m=""+i2.charAt(i);
	                    	 CharSequence l=m;
	                    	 tv.append(l);
	                        scroll2.fullScroll(View.FOCUS_DOWN);
	                        handler.postDelayed(this,5);
	                      
	        	                  i++;
	                    	 }catch(StringIndexOutOfBoundsException e)
	                    	 {
	                    		 
	                    	 }
	                    	 
	                    }
	               
	                };
	                handler.postDelayed(r,5);
	                
	                if(i>=n)
   	            	 handler.removeCallbacks(r);
                  
	         
	            }
	            
	        });
	}

}
