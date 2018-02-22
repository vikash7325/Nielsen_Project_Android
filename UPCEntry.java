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

public class UPCEntry extends Activity implements TextToSpeech.OnInitListener{
	Button search,blindupc,blindpno,blindpsize;
	ImageButton audio,audiopno,audiopsize;
	EditText upc,pageno,pagesize;
	String upcValue,pagenoValue,pagesizeValue;
	String upctemp="";
	private TextToSpeech tts;
	boolean b1=false,b2=false,b3=false,b4=false,button1=false,button2=false,button3=false,button4=false;
	boolean upcAudioClicked=false,pageNoAudioClicked=false,pageSizeAudioClicked=false;
	Thread t = new Thread();

	protected static final int RESULT_SPEECH = 1;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upc_entry);
		tts = new TextToSpeech(this, this);

		/**************************** EDITTEXT DESCRIPTION ***********************************/
		/*************************************************************************************/
		upc=(EditText)findViewById(R.id.EditText5);


		/*****************************Page NUMBER ********************************************/
		/*************************************************************************************/
		pageno=(EditText)findViewById(R.id.EditText6);


		/***************************** PAGE SIZE *********************************************/
		/*************************************************************************************/
		pagesize=(EditText)findViewById(R.id.EditText7);

		/*****************************Search Button ******************************************/
		/*************************************************************************************/
		search=(Button)findViewById(R.id.searchButton2);

		/****************************Audio****************************************************/
		/*************************************************************************************/
		audio=(ImageButton)findViewById(R.id.imagebutton1);
		audiopno=(ImageButton)findViewById(R.id.image1);
		audiopsize=(ImageButton)findViewById(R.id.image11);
		

		/***************************** UPC Blind *********************************************/
		/*************************************************************************************/
		blindupc=(Button)findViewById(R.id.blindupc1);

		/*****************************Search Button ******************************************/
		/*************************************************************************************/
		blindpno=(Button)findViewById(R.id.blindpno1);

		/****************************Audio****************************************************/
		/*************************************************************************************/
		blindpsize=(Button)findViewById(R.id.blindpsize1);
		
		

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
		
		
		blindpno.setOnLongClickListener(new OnLongClickListener() {
			
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
		
		
		blindpno.setOnClickListener(new OnClickListener() {

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
		
		blindpsize.setOnLongClickListener(new OnLongClickListener() {
			
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
		
		blindpsize.setOnClickListener(new OnClickListener() {

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
				new Thread(speechThread,"speaking").start();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

	@Override
	public void onDestroy(){
		super.onDestroy();
		if (tts != null) {
    		tts.stop();
    		tts.shutdown();
    	}

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
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if (status == TextToSpeech.SUCCESS) {
			int result = tts.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
						Log.e("TTS", "This Language is not supported");
			}
			else{
				new Thread(speechThread,"Speaking").start();
			}
		}
		else{
			Log.e("TTS", "Initilization Failed!");
		}		
	}
	
	private Runnable speechThread = new Runnable(){
		public void run(){
			Log.v("flag",""+b1+b2+b3+b4+button1+button2+button3+button4);
			if(b1){
				String text = "Click here to enter upc code";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b1=false;
			}
			else if(b2){
				String text = "Click here to enter page number";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b2=false;
			}
			else if(b3){
				String text = "Click here to enter page size";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b3=false;
			}
			else if(b4){
				String text = "Click here to search the product";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b4=false;
			}
			else if(button1){
				String text = "Speak to enter upc code";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button1=false;
			}
			else if(button2){
				String text = "speak to enter page no";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button2=false;
			}
			else if(button3){
				String text = "speak to enter page size";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button3=false;
			}
			else if(button4){
				String text = "Please Wait While we Process Your Result";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button4=false;
			}
			else{
				String text = "Here one needs to enter UPC number and page number and page sizes. Long click on top left" +
						"corner to enter UPC code. Long click on bottom left and bottom right to enter page number and page" +
						"size";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			}
		}
	};
}
