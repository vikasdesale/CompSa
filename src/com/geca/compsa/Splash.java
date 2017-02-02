package com.geca.compsa;



import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class Splash extends Activity {


	
	@Override
	protected void onCreate(Bundle vrockskbh) {
		// TODO Auto-generated method stub
		super.onCreate(vrockskbh);

		setContentView(R.layout.splash);
      
		Thread timer = new Thread() {

			@Override
			public void run() {
				try {   ImageView iv1=(ImageView) findViewById(R.id.imageView1);
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
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent openMain = new Intent("com.geca.compsa.MainActivity");
					startActivity(openMain);
					finish();
				}
			}

		};
		timer.start();
	}


}
