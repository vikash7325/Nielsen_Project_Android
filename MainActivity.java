package com.example.nielsenproject;

import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageButton;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener{
	Dialog dialog;
	ImageButton button1;
	private TextToSpeech tts;
	Thread t = new Thread();
	float screenWidth=400,screenHeight=800;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		tts = new TextToSpeech(this, this);
		
		
	}
	@Override
	protected void onStart() {
	    super.onStart();
	    
	}
	@Override
	public void onPause(){
		super.onPause();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){
		float x;
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
				x=event.getX();
				Log.d("y",""+x);
				Log.d("screen",""+screenWidth);
				if(x<(screenWidth/2)){
					Intent i=new Intent(MainActivity.this,AfterMain.class);
					finish();
					startActivity(i);
				}
				else{
					MainActivity.this.finish();
				}
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
				speakOut();
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
	private void speakOut() {
		String text = "Welcome to product search application. Click on left half to enter the application " +
				"and right half to exit ";
		tts.setLanguage(Locale.UK);
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

}

