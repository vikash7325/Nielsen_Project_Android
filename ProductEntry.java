package com.example.nielsenproject;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ProductEntry extends Activity implements TextToSpeech.OnInitListener{
	Button search,blinddesc,blindpno,blindpsize;;
	ImageButton audio,pno,psize;
	EditText description,pageno,pagesize;
	String descriptionValue,pagenoValue,pagesizeValue;
	private TextToSpeech tts;
	boolean b1=false,b2=false,b3=false,b4=false;
	boolean button1=false,button2=false,button3=false,button4=false;
	boolean descAudioClicked=false,pageNoAudioClicked=false,pageSizeAudioClicked=false;
	String[] stringDescription = {"Lotion","Skin Care","Moisturizer","Sun Screen","Soap","Freeman Ultra Hand","Cream","Ointment",
			"powder","Baby Oil","Tooth Paste","Tooth Cleanser","Tooth Powder"};
	
	Thread t = new Thread();


	protected static final int RESULT_SPEECH = 1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_input);
		tts = new TextToSpeech(this, this);
                      


		/**************************** EDITTEXT DESCRIPTION ***********************************/
		/*************************************************************************************/
		description=(EditText)findViewById(R.id.EditText2);

		
		/*****************************Page NUMBER ********************************************/
		/*************************************************************************************/
		pageno=(EditText)findViewById(R.id.EditText3);


		/***************************** PAGE SIZE *********************************************/
		/*************************************************************************************/
		pagesize=(EditText)findViewById(R.id.EditText4);

		/*****************************Search Button ******************************************/
		/*************************************************************************************/
		search=(Button)findViewById(R.id.searchButton1);

		/****************************Audio****************************************************/
		/*************************************************************************************/
		audio=(ImageButton)findViewById(R.id.imagebutton);
		pno=(ImageButton)findViewById(R.id.imagebutton11);
		psize=(ImageButton)findViewById(R.id.imagebutton111);
		

		/*****************************Blind Button1 ******************************************/
		/*************************************************************************************/
		blinddesc=(Button)findViewById(R.id.blinddescription);
		
		blinddesc.setOnLongClickListener(new OnLongClickListener() {
			
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
		
		
		blinddesc.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				button1=true;
				descAudioClicked=true;
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					description.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
		

		/*****************************Blind Button2 ******************************************/
		/*************************************************************************************/
		blindpno=(Button)findViewById(R.id.blindpno);
		
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
				button2=true;
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
		

		/*****************************Blind Button3 ******************************************/
		/*************************************************************************************/
		blindpsize=(Button)findViewById(R.id.blindpsize);
		
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
				button3=true;
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
		
		

		audio.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				descAudioClicked=true;
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
				
				try {
					startActivityForResult(intent, RESULT_SPEECH);
					description.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
		
		pno.setOnClickListener(new OnClickListener() {

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
		
		psize.setOnClickListener(new OnClickListener() {

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
				
				descriptionValue=description.getText().toString();
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
				String desc="";
				if(descriptionValue.length()>0){
					desc = descriptionValue.replaceFirst(" ", "");
				}
				if((descriptionValue==null)||(desc.matches(""))){
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! You did not enter any string",
							Toast.LENGTH_SHORT);
					t.show();
					description.setText("");
					Log.d("first",pagenoValue);
				}
				else if(haveSpecialChar(descriptionValue)){
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Only alpha Numerics and spaces are allowed",
							Toast.LENGTH_SHORT);
					t.show();
					description.setText("");
				}
				else{
					Constant.ConstDescription=descriptionValue;
					Intent i=new Intent(getApplicationContext(),ProductDetails.class);
					finish();
					startActivity(i);
					Log.d("first",pagenoValue);
				}
			}
		});

	}

	/**********************************Back Button***********************************************************/
	/********************************************************************************************************/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			Intent i=new Intent(ProductEntry.this,ProductReference.class);
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
				description.setText(text.get(0));
				if(descAudioClicked){
					description.setText(text.get(0));
					descAudioClicked=false;
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
				String text = "Here is edittext for product description";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b1=false;
			}
			else if(b2){
				String text = "Here is edittext for page number";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b2=false;
			}
			else if(b3){
				String text = "Here is edittext for product description";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b3=false;
			}
			else if(b4){
				String text = "Here is search button";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b4=false;
			}
			else if(button1){
				String text = "Speak to enter product description";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button1=false;
			}
			else if(button2){
				String text = "Speak to enter page number";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button2=false;
			}
			else if(button3){
				String text = "Speak to enter page size";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button3=false;
			}
			else if(button4){
				String text = "Please Wait while we process your results";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button4=false;
			}
			else{
				String text = "Here one needs to enter product description and page number and page sizes";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			}
		}
	};
}
