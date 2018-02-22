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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

public class Compare extends Activity implements TextToSpeech.OnInitListener,OnItemSelectedListener{
	Button search,blindupc1,blindupc2,feature;
	ImageButton audio1,audio2;
	EditText upc1,upc2;
	String upcValue1,upcValue2;
	String upctemp="";
	private TextToSpeech tts;
	boolean b1=false,b2=false,b3=false,button1=false,button2=false,button3=false;
	boolean upc2Clicked=false,upc1Clicked=false;
	private String[] state = { "General Description","Health Based","Detail Features" };
	Thread t = new Thread();
	Spinner criteriaSpinner;
	protected static final int RESULT_SPEECH = 1;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compare);
		tts = new TextToSpeech(this, this);
		
		 criteriaSpinner = (Spinner) findViewById(R.id.searchcriteria);
		  ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
		    android.R.layout.simple_spinner_item, state);
		  adapter_state
		    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  criteriaSpinner.setAdapter(adapter_state);
		  criteriaSpinner.setOnItemSelectedListener(this);

		  Log.v("error here","present");
		/**************************** EDITTEXT DESCRIPTION ***********************************/
		/*************************************************************************************/
		upc1=(EditText)findViewById(R.id.firstupcentry);
		upc2=(EditText)findViewById(R.id.secondupcentry);
		 Log.v("eor here","present");

		/*****************************Search Button ******************************************/
		/*************************************************************************************/
		search=(Button)findViewById(R.id.comparesearch);
		feature=(Button)findViewById(R.id.dummyblindbutton);
		 Log.v("eporror here","present");

		/****************************Audio****************************************************/
		/*************************************************************************************/
		audio1=(ImageButton)findViewById(R.id.imagecompare1);
		audio2=(ImageButton)findViewById(R.id.imagecompare2);

		/***************************** UPC Blind *********************************************/
		/*************************************************************************************/
		blindupc1=(Button)findViewById(R.id.blindfirstupc);
		blindupc2=(Button)findViewById(R.id.blindsecondupc);

		audio1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				upc1Clicked=true;
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					upc1.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
		
		audio2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				upc2Clicked=true;
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					upc2.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
		
		blindupc1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				button1=true;
				upc1Clicked=true;
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					upc1.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
		
		blindupc1.setOnLongClickListener(new OnLongClickListener() {
			
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
		
		
		blindupc2.setOnLongClickListener(new OnLongClickListener() {
			
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
		
		
		blindupc2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				upc2Clicked=true;
				button2=true;
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					upc2.setText("");
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
		search.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				upcValue1=upc1.getText().toString();
				upcValue2=upc2.getText().toString();
				button3=true;
				new Thread(speechThread,"speaking").start();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//make default value of page no and page size
				if((upcValue1==null)||(upcValue1=="")){
					//create a toast
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Null values are allowed",
							Toast.LENGTH_SHORT);
					t.show();
					return;
				}
				else{
						Constant.compareupc1=upcValue1;
				}
				//make default value of  page size
				if((upcValue2==null)||(upcValue2=="")){
					//raise a toast
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Null Values are not allowed",
							Toast.LENGTH_SHORT);
					t.show();
					return;
				}
				else{
						Constant.compareupc2=upcValue2;
				}
				Constant.fromCompare=1;
				Intent i=new Intent(getApplicationContext(),ProductDetails.class);
				finish();
				startActivity(i);
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {
				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); 
				if(upc1Clicked){
					upc1.setText(text.get(0));
					upc1Clicked=false;
				}
				if(upc2Clicked){
					upc2.setText(text.get(0));
					upc2Clicked=false;
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
			Log.v("flag",""+b1+b2+b3+button1+button2+button3);
			if(b1){
				String text = "Click here to enter first UPC code";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b1=false;
			}
			else if(b2){
				String text = "Click here to enter second UPC number";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b2=false;
			}
			else if(b3){
				String text = "Click here to compare the product";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				b3=false;
			}
			else if(button1){
				String text = "Speak to enter first UPC code";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button1=false;
			}
			else if(button2){
				String text = "speak to enter second UPC code";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button2=false;
			}
			else if(button3){
				String text = "Please Wait While we Process Your Result";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				button3=false;
			}
			else{
				String text = "Here one needs to enter UPC number of two products. Long click on top left" +
						"corner to enter first UPC code. Long click on top right to enter second UPC code"+
						"Click on compare to compare the products";
				tts.setLanguage(Locale.UK);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			}
		}
	};

	@Override
	 public void onItemSelected(AdapterView<?> parent, View view, int position,
			   long id) {
		// TODO Auto-generated method stub
		criteriaSpinner.setSelection(position);
		  String selState = (String) criteriaSpinner.getSelectedItem();
		  if(selState.equals(state[0])){
			  Constant.fromCompareDesc=1;
			  Constant.fromCompareFeature=0;
			  Constant.fromCompareHealth=0;
			  Log.v("selected value",""+Constant.fromCompareDesc);
		  }
		  else if(selState.equals(state[1])){
			  Constant.fromCompareHealth=1;
			  Constant.fromCompareDesc=0;
			  Constant.fromCompareFeature=0;
			  Log.v("selected value",""+Constant.fromCompareHealth);
		  }
		  else{
			  Constant.fromCompareFeature=1;
			  Constant.fromCompareDesc=0;
			  Constant.fromCompareHealth=0;
			  Log.v("selected value",""+Constant.fromCompareFeature);
		  }
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
