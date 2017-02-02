package com.geca.compsa;

import java.util.Random;
import com.flaviofaria.kenburnsview.KenBurnsView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;



public class MainActivity extends Activity  implements View.OnClickListener,android.widget.PopupMenu.OnMenuItemClickListener {

	
	 int[] photos={R.drawable.photo5a, R.drawable.photo12,R.drawable.photo2aa,R.drawable.photo5,R.drawable.photo2,R.drawable.photo9,R.drawable.photo2a};
	    KenBurnsView imageView;
	    Button about,faculties,students,gallery,study,news,placement,events,map,sponsors,enquiry,moreapps;
	    private boolean isInFront;
		ImageButton btnabout;
	    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      

              //  ImageLoader.getInstance().clearMemoryCache();
     
            //ImageLoader.getInstance().clearDiskCache();
        
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
    
            btnabout= (ImageButton) findViewById(R.id.btnabout);
            imageView =(KenBurnsView)findViewById(R.id.kenBurns);
            about = (Button)findViewById(R.id.btn_aboutus);
            faculties = (Button)findViewById(R.id.btn_faculties);
            students = (Button)findViewById(R.id.btn_students);
            gallery = (Button)findViewById(R.id.btn_gallery);
            study = (Button)findViewById(R.id.btn_study);
            news = (Button)findViewById(R.id.btn_news);
            placement = (Button)findViewById(R.id.btn_placement);
            events = (Button)findViewById(R.id.btn_events);
           // map = (Button) findViewById(R.id.btn_map);
            sponsors = (Button)findViewById(R.id.btn_sponsors);
            enquiry = (Button)findViewById(R.id.btn_enquiry);
          //  moreapps = (Button)findViewById(R.id.btn_moreapps);
            about.setOnClickListener(this);
            faculties.setOnClickListener(this);
            students.setOnClickListener(this);
            gallery.setOnClickListener(this);
            study.setOnClickListener(this);
            news.setOnClickListener(this);
            placement.setOnClickListener(this);
            events.setOnClickListener(this);
            sponsors.setOnClickListener(this);
            enquiry.setOnClickListener(this);

            btnabout.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    		         try{
    		        	 PopupMenu popupMenu = new PopupMenu(MainActivity.this, arg0);
    						popupMenu.setOnMenuItemClickListener(MainActivity.this);
    						popupMenu.inflate(R.menu.item);
    						popupMenu.show();
    		         }catch(Exception e){}
    		         }
    			
    		});

    	
            //handler to change images after certain time
            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {

                @Override
				public void run() {
                    // change images randomly
                    Random ran=new Random();
                    int i=ran.nextInt(photos.length);


                    //to avoid out of memory errors
                    if (isInFront)
                    imageView.setImageResource(photos[i]);
                    Drawable oriDrawable = imageView.getDrawable();

                    // set callback to null
                    oriDrawable.setCallback(null);
                    System.gc();

                    i++;
                    if(i>photos.length-1)
                    {
                        i=0;
                    }
                    handler.postDelayed(this, 8000);  //for interval...
                }
            };
            handler.postDelayed(runnable, 10000); //for initial delay..


         
        }


        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.btn_aboutus:
                     Intent j =new Intent(this,com.geca.compsa.about.AboutUs.class);
                       startActivity(j);
                    break;
                case R.id.btn_faculties:
                    Intent j1 =new Intent(MainActivity.this, com.geca.compsa.facuties.FacultiesMain.class);
                   startActivity(j1);
                    break;
                case R.id.btn_students:
               	 Intent l =new Intent(MainActivity.this, com.geca.compsa.student.StudentTab.class);
                 startActivity(l);
                    break;
                case R.id.btn_gallery:
                    Intent m =new Intent(MainActivity.this,com.geca.compsa.gallery.Gallery.class);
                   startActivity(m);
                    break;
                case R.id.btn_study:
                	 Intent k =new Intent(MainActivity.this, com.geca.compsa.admission.AdmissionActivity.class);
                     startActivity(k);
                    break;
                case R.id.btn_news:
                	Intent o =new Intent(MainActivity.this, com.geca.compsa.news.NewsMain.class);
                    startActivity(o);
                  
                    break;
                case R.id.btn_placement:
                	 Intent n =new Intent(MainActivity.this,com.geca.compsa.placement.PlacementTab.class);
                     startActivity(n);
                    break;
                case R.id.btn_events:
                	  Intent j11=new Intent(MainActivity.this, com.geca.compsa.event.Events.class);
                      startActivity(j11);
                    break;
             //   case R.id.btn_map:
                   // Intent k =new Intent(getActivity(), AboutActivity.class);
                   // startActivity(k);
                   // break;
                case R.id.btn_sponsors:
                   Intent m1 =new Intent(MainActivity.this, com.geca.compsa.sponsors.Sponsors.class);
                   startActivity(m1);
                    break;
                case R.id.btn_enquiry:
                    Intent l1 =new Intent(MainActivity.this, com.geca.compsa.enquiry.Enquiry.class);
                    startActivity(l1);
                    break;
               // case R.id.btn_moreapps:
                   // Intent n =new Intent(getActivity(), FeedbackActivity.class);
                   // startActivity(n);
                //    break;
                
            }
        }
        @Override
        public void onResume() {
            super.onResume();
            isInFront = true;
        }

        @Override
        public void onPause() {
            super.onPause();
            isInFront = false;
        }
        public boolean onMenuItemClick(MenuItem item) {
        	  switch(item.getItemId()){
       
   
              case R.id.item_about:
            	  Intent j11 =new Intent(MainActivity.this, com.geca.compsa.about.AboutUsDev.class);
                  startActivity(j11);
                  break;
   
          }
          return true;
    	}
       
    
}
