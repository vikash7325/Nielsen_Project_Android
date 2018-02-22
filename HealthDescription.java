package com.example.nielsenproject;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

public class HealthDescription extends Activity implements TextToSpeech.OnInitListener{
	Button search,blindupc,blindpageno,blindpagesize;
	ImageButton audio,audiopno,audiopsize;
	EditText upc,pageno,pagesize;
	String upcValue,pagenoValue,pagesizeValue;
	TextToSpeech tts;
	String upctemp="";
	boolean b1=false,b2=false,b3=false,b4=false,button1=false,button2=false,button3=false,button4=false;
	boolean upcAudioClicked=false,pageNoAudioClicked=false,pageSizeAudioClicked=false;

	protected static final int RESULT_SPEECH = 1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productfeature);
		tts = new TextToSpeech(this, this);

		/**************************** EDITTEXT DESCRIPTION ***********************************/
		/*************************************************************************************/
		upc=(EditText)findViewById(R.id.EditText8);


		/*****************************Page NUMBER ********************************************/
		/*************************************************************************************/
		pageno=(EditText)findViewById(R.id.EditText9);


		/***************************** PAGE SIZE *********************************************/
		/*************************************************************************************/
		pagesize=(EditText)findViewById(R.id.EditText10);

		/*****************************Search Button ******************************************/
		/*************************************************************************************/
		search=(Button)findViewById(R.id.searchButton3);

		/****************************Audio****************************************************/
		/*************************************************************************************/
		audio=(ImageButton)findViewById(R.id.imagebutton2);
		audiopno=(ImageButton)findViewById(R.id.imagebutton22);
		audiopsize=(ImageButton)findViewById(R.id.imagebutton222);
		
		blindupc=(Button)findViewById(R.id.blindfeature1);
		blindpageno=(Button)findViewById(R.id.blindpnofeature);
		blindpagesize=(Button)findViewById(R.id.blindpsizefeature);
		

		audio.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				upcAudioClicked=true;
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					upc.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
		
		audiopno.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				pageNoAudioClicked=true;
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					pageno.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
		
		audiopsize.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				pageSizeAudioClicked=true;
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					pagesize.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
		
		blindupc.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				button1=true;
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				
				upcAudioClicked=true;
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					upc.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
		
		blindupc.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				b1=true;
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
		
		
		blindpageno.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				b2=true;
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
		
		
		blindpageno.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				pageNoAudioClicked=true;
				button2=true;
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					pageno.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
		
		blindpagesize.setOnLongClickListener(new OnLongClickListener() {
			
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
		
		blindpagesize.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				pageSizeAudioClicked=true;
				button3=true;
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					pagesize.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
		
		
		search.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				b4=true;
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
		search.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				upcValue=upc.getText().toString();
				pagenoValue=pageno.getText().toString();
				pagesizeValue=pagesize.getText().toString();

				button4=true;
				//make default value of page no and page size
				if((pagenoValue==null)||(pagenoValue=="")){
					//change to default
					Constant.ConstPageNumber=1;
					pageno.setText("1");
				}
				else if(isNumeric(pagenoValue)==false){
					//change to default
					Constant.ConstPageNumber=1;
					pageno.setText("1");
				}
				else if(((pagenoValue.length()>9))){
					Toast t = Toast.makeText(getApplicationContext(),
       	                "Please enter Valid value",
       	                         Toast.LENGTH_SHORT);
       	            t.show();
       	            pageno.setText("");
				}
				else{
					//give the value to constant
					if(isNumeric(pagenoValue)){
						Constant.ConstPageNumber=(Integer.parseInt(pagenoValue));
					}
					else{
						Constant.ConstPageNumber=1;
						pageno.setText("1");
					}
				}

				//make default value of  page size
				if((pagesizeValue==null)||(pagesizeValue=="")){
					//change to default
					Constant.ConstPageSize=10;
					pagesize.setText("10");
				}
				else if(isNumeric(pagesizeValue)==false){
					//change to default
					Constant.ConstPageSize=10;
					pagesize.setText("10");
				}
				else if(((pagesizeValue.length()>9))){
					Toast t = Toast.makeText(getApplicationContext(),
       	                "Please enter Valid value",
       	                         Toast.LENGTH_SHORT);
       	            t.show();
       	            pagesize.setText("");
				}
				else{
					//give the value to constant
					if(isNumeric(pagesizeValue)){
						Constant.ConstPageSize=(Integer.parseInt(pagesizeValue));
					}
					else{
						Constant.ConstPageSize=10;
						pagesize.setText("10");
					}
				}
				
       			if(upcValue.length()>0){
       				upctemp = upcValue.replaceFirst(" ", "");
       			}
				if((upcValue==null)||(upctemp.matches(""))){
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! You did not enter any input",
							Toast.LENGTH_SHORT);
					t.show();

				}
				else if(haveSpecialChar(upcValue)){
       				Toast t = Toast.makeText(getApplicationContext(),
	       	                "Opps! Only Numerics and spaces are allowed",
	       	                         Toast.LENGTH_SHORT);
	       	            t.show();
	       	            upc.setText("");
       			}
				else if(((upcValue.length()>13)||((upcValue.length()>13)))){
						Toast t = Toast.makeText(getApplicationContext(),
	       	                "Please enter Valid value",
	       	                         Toast.LENGTH_SHORT);
	       	            t.show();
	       	            upc.setText("");
				}
				else{
					Constant.ConstUPC=upcValue;
					Intent i=new Intent(getApplicationContext(),ProductDetails.class);
					finish();
					startActivity(i);
				}
			}
		});

	}

	/**********************************Back Button***********************************************************/
	/********************************************************************************************************/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			Intent i=new Intent(getApplicationContext(),ProductReference.class);
			finish();
			startActivity(i);
		}
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

	boolean haveSpecialChar(String description){
		String temp = description.replaceAll(" ","");
		boolean flag=false;
		int len = temp.length();
		for(int i=0;i<len;i++){
			if(!(Character.isLetterOrDigit(description.charAt(i)))){
				if((Character.isSpace(description.charAt(i)))){
					continue;
				}
				flag=true;
				break;
			}
			flag=false;
		}
		return flag;
	}
	boolean isNumeric(String description){
		int len=description.length();
		boolean flag=false;
		for(int i=0;i<len;i++){
			if(!(Character.isDigit(description.charAt(i)))){
				flag=false;
				break;
			}
			flag=true;
		}
		return flag;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);  
				if(upcAudioClicked){
					upc.setText(text.get(0));
					upcAudioClicked=false;
				}
				if(pageNoAudioClicked){
					pageno.setText(text.get(0));
					pageNoAudioClicked=false;
				}
				if(pageSizeAudioClicked){
					pagesize.setText(text.get(0));
					pageSizeAudioClicked=false;
				}
			}
			break;
		}

		}
	}

	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub
		
	}
}


