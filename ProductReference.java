package com.example.nielsenproject;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;

public class ProductReference extends Activity implements TextToSpeech.OnInitListener{
	Thread t = Thread.currentThread();
	ImageButton product,upc,description,health;
	boolean b1=false,b2=false,b3=false,b4=false;
	boolean button1=false,button2=false,button3=false,button4=false;
	float screenWidth,screenHeight;
	private TextToSpeech tts;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_reference);
		tts = new TextToSpeech(this, this);
		
		/***********************Using Description*********************************************/
		/*************************************************************************************/
		
		description=(ImageButton)findViewById(R.id.button4);
		description.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				b2=true;
				new Thread(speechThread,"speaking").start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		});
		description.setOnClickListener(new OnClickListener() {
       		
       		public void onClick(View v) {
       			// TODO Auto-generated method stub
       			button2=true;
       			new Thread(speechThread,"Speaking").start();
       			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
       		//	speakOut();
       			
       			Intent i=new Intent(ProductReference.this,ProductEntry.class);
       			Constant.fromDescription=1;
       			Constant.fromUPC=0;
       			Constant.fromFeature=0;
       			Constant.fromHealth=0;
       			finish();
       			startActivity(i);
       		}
		});
		
		/**************************Product Entry***********************************************/
		/**************************************************************************************/
		upc=(ImageButton)findViewById(R.id.button5);
		
		upc.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				b1=true;
				new Thread(speechThread,"speaking").start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		});
		upc.setOnClickListener(new OnClickListener() {
       		
       		public void onClick(View v) {
       			// TODO Auto-generated method stub
       			button1=true;
       			new Thread(speechThread,"Speaking").start();
       			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
     
       			Intent i=new Intent(ProductReference.this,ProductFeature.class);
       			Constant.fromDescription=0;
       			Constant.fromUPC=1;
       			Constant.fromFeature=0;
       			Constant.fromHealth=0;
       			finish();
       			startActivity(i);
       		}
		});
		
		/********************************** health *******************************************/
		/*************************************************************************************/
		health=(ImageButton)findViewById(R.id.button6);
		
		health.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				b3=true;
				new Thread(speechThread,"speaking").start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		});
		health.setOnClickListener(new OnClickListener() {
       		
       		public void onClick(View v) {
       			// TODO Auto-generated method stub
       			button3=true;
       			new Thread(speechThread,"Speaking").start();
       			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
       		//	speakOut();
       			
       			Intent i=new Intent(ProductReference.this,UPCEntry.class);
       			Constant.fromDescription=0;
       			Constant.fromUPC=0;
       			Constant.fromFeature=0;
       			Constant.fromHealth=1;
       			finish();
       			startActivity(i);
       		}
		});
		
		/********************************** product *********************************************/
		/*************************************************************************************/
		product=(ImageButton)findViewById(R.id.button7);
		
		product.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				b4=true;
				new Thread(speechThread,"speaking").start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		});
		product.setOnClickListener(new OnClickListener() {
       		
       		public void onClick(View v) {
       			// TODO Auto-generated method stub
       			button4=true;
       			new Thread(speechThread,"Speaking").start();
       			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
       			
       			Intent i=new Intent(ProductReference.this,ProductFeature.class);
       			Constant.fromDescription=0;
       			Constant.fromUPC=0;
       			Constant.fromFeature=1;
       			Constant.fromHealth=0;
       			finish();
       			startActivity(i);
       		}
		});
		
	}
	
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK){
	        	Intent i=new Intent(ProductReference.this,AfterMain.class);
	   			finish();
	   			startActivity(i);
	        }
	        	
	        return true;
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private Runnable speechThread = new Runnable(){
		public void run(){
			Log.v("button",""+b1+b2+b3+b4+button1+button2+button3+button4);
		if(Thread.currentThread().isInterrupted()){
			Thread.currentThread().stop();
			return;
		}
		if(b1){
			String text = "Search with UPC ";
			tts.setLanguage(Locale.UK);
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			b1=false;
		}
		else if(b2){
			String text = "Search with Description";
			tts.setLanguage(Locale.UK);
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			b2=false;
		}
		else if(b3){
			String text = "Here is button to search product with health information";
			tts.setLanguage(Locale.UK);
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			b3=false;
		}
		else if(b4){
			String text = "Here is button to search products with all its characteristics";
			tts.setLanguage(Locale.UK);
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			b4=false; 
		}
		else if(button1){
			String text = "You have clicked button to seach using UPC number";
			tts.setLanguage(Locale.UK);
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			button1=false;
		}
		else if(button2){
			String text = "You have clicked button to search using product description";
			tts.setLanguage(Locale.UK);
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			button2=false;
		}
		else if(button3){
			String text = "You have clicked button to search for health information ";
			tts.setLanguage(Locale.UK);
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			button3=false;
		}
		else if(button4){
			String text = "You have clicked button for product characteristics";
			tts.setLanguage(Locale.UK);
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			button4=false;
		}
		else{
			String text = "Here are four Buttons to search the products depending on your search criteria";
			tts.setLanguage(Locale.UK);
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			String text1 = "Buttons are as follow first one is search by unique UPC number second one searches with product " +
					"description" +
			" third one searches with the same configuration but with health information" +
			"and the last one describes all the caracteristics of the product";
			tts.speak(text1, TextToSpeech.QUEUE_FLUSH, null);
		}
	}
	};
	@Override
	public void onDestroy() {
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub

		if (status == TextToSpeech.SUCCESS) {
			int result = tts.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} else {
				new Thread(speechThread,"Speaking").start();
			}
		} else {
			Log.e("TTS", "Initilization Failed!");
		}
		
	}
	

}
