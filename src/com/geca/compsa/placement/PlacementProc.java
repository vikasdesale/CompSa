package com.geca.compsa.placement;

import com.geca.compsa.R;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class PlacementProc extends Activity {
	 TextView t,tv,tv1,tv2;
	 ScrollView scroll1,scroll2;
	 Handler handler;
	 Runnable r,r1;
	    int  i;
	    int n;
	 String i1,i2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.placementproc);
		
	                i1=getString(R.string.placep);
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

}
