package com.example.nielsenproject;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class AfterMain extends Activity implements TextToSpeech.OnInitListener {
	Thread t = Thread.currentThread();
	ImageButton first,second,exit;
	float screenWidth,screenHeight;
	private TextToSpeech tts;
	boolean button1=false;
	boolean button2=false;
	boolean button3=false;
	boolean b1=false;
	boolean b2=false;
	boolean b3=false;
	boolean threadInterrupted=false;
	Thread newThread = new Thread();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.after_main);
		tts = new TextToSpeech(this, this);


		/********************************Product Reference***************************************/
		/****************************************************************************************/

		first=(ImageButton)findViewById(R.id.imageButton1);

		first.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				b1=true;
				new Thread(speechThread,"speaking").start();
				
				return true;
			}
		});
		first.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				button1=true;
				new Thread(speechThread,"Speaking").start();
				Intent i=new Intent(AfterMain.this,ProductReference.class);
				finish();
				startActivity(i);
			}
		});

		/****************************** Compare *************************************/
		/****************************************************************************/
		
		second=(ImageButton)findViewById(R.id.imageButton100);

		second.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				b2=true;
				new Thread(speechThread,"speaking").start();
				return true;
			}
		});
		second.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				button2=true;
				new Thread(speechThread,"Speaking").start();
				Intent i=new Intent(AfterMain.this,Compare.class);
				finish();
				startActivity(i);
			}
		});



		/*************************************Exit Button*****************************************/
		/*****************************************************************************************/
		exit=(ImageButton)findViewById(R.id.imageButton2);

		exit.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				b3=true;
				new Thread(speechThread,"speaking").start();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		});
		exit.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				button3=true;
				new Thread(speechThread,"Speaking").start();
				AlertDialog.Builder builder1 = new AlertDialog.Builder(AfterMain.this); 
				builder1.setMessage("Are You Sure??");
				builder1.setCancelable(false);
				builder1.setNegativeButton("Yes", new DialogInterface.OnClickListener() { 
					public void onClick(DialogInterface dialog, int id) { 
						AfterMain.this.finish();
					} 
				});
				builder1.setPositiveButton("No", new DialogInterface.OnClickListener() { 
					public void onClick(DialogInterface dialog, int id) {   
						dialog.cancel(); 
					}
				});
				AlertDialog alert = builder1.create();
				alert.show();
			}								
		});	

	}

	/**********************************Back Button***********************************************************/
	/********************************************************************************************************/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			Intent i=new Intent(AfterMain.this,MainActivity.class);
			finish();
			startActivity(i);
		}

		return true;
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
				//speakOut();
				new Thread(speechThread,"Speaking").start();
			}
		} else {
			Log.e("TTS", "Initilization Failed!");
		}

	}

	@Override
	public void onDestroy() {
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private Runnable speechThread = new Runnable(){
		public void run(){
			Log.v("button",""+b1+b2+button1+button2);
			if(b1){
				String text = "Here is search button";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b1=false;
			}
			else if(b2){
				String text = "Here is compare button";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b2=false;
			}
			else if(b3){
				String text = "Here is exit button";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b3=false;
			}
			else if(button1){
				String text = "You have clicked search button";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button1=false;
			}
			else if(button2){
				String text = "You have clicked compare button";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button2=false;
			}
			else if(button3){
				String text = "You have clicked exit button";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button3=false;
			}
			else{
				String text = "Here are three Buttons Search. compare.  and Exit ";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				String text1 = "Click on Search to go to next screen. Click On compare to compare two" +
						"products. Click on exit to come out of the application";
				tts.speak(text1, TextToSpeech.QUEUE_FLUSH, null);
			}
			threadInterrupted=true;
		}
	};


}
