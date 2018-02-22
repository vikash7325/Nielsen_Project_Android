package com.example.nielsenproject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ProductDetails extends ListActivity implements TextToSpeech.OnInitListener{
	private TextToSpeech tts;
	private ProgressDialog pDialog;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	String pagesize="&pgSize=";
	String pagenumber="&pgNo=";
	String compare1url=null,compare2url=null;
	int pnumber;
	int psize;
	String description;
	static int errorFound=0;
	String outputData;
	Thread t = new Thread();
	MediaPlayer mp;

	// URL to get contacts JSON
	private static String API_KEY="2498-f89e3330-615a-4bb3-a03b-4bc3cc6c7b73";

	private String url = "https://nielsen.api.tibco.com:443/Products/v1/";
	private String url1 = "https://nielsen.api.tibco.com:443/Products/v1/";
	private String url2 = "https://nielsen.api.tibco.com:443/Products/v1/";

	/*******************************SPECIAL ONE***************************************************/
	/*********************************************************************************************/

	private static final String TAG_MESSAGE="Message";
	private static final String TAG_ERROR_MESSAGE="errorMessage";
	private static final String TAG_ERROR_MESSAGE_ID="MessageID";

	/****************************JSON Node Name For First Object*********************************/
	/*********************************************************************************************/
	private static final String TAG_HEADER="Header";
	private static final String TAG_API_VERSION="API_Version";
	private static final String TAG_API_NAME="API_Name";
	private static final String TAG_CONTENT_TYPE="Content_Type";
	private static final String TAG_UPC="UPC";
	private static final String TAG_DESCRIPTION="Description";
	private static final String TAG_ITEM_CODE="Item_Code";
	private static final String TAG_MODULE="Module";
	private static final String TAG_RANK="Rank";


	/****************************JSON Node Name For LAST Object***********************************/
	/*********************************************************************************************/
	private static final String TAG_SUMMARY="Summary";

	/**********************************Create JSONArray******************************************/
	/********************************************************************************************/

	JSONArray Product=null;

	/********************************Hashmap for ListView****************************************/
	/********************************************************************************************/

	ArrayList<HashMap<String,String>> productList;

	@Override
	public void onDestroy(){
		super.onDestroy();
		if ( pDialog!=null && pDialog.isShowing() ){
			pDialog.cancel();
		}
		if(mp.isPlaying()){
			mp.stop();
		}
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			Intent i=new Intent(ProductDetails.this,ProductReference.class);
			finish();
			startActivity(i);
		}

		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.before_product_details);
		tts = new TextToSpeech(this, this);
		mp=MediaPlayer.create(this,R.raw.iron_man);
		productList = new ArrayList<HashMap<String, String>>();

		/*************************Calling ASYNC Task To Get JSON**************************************/
		/*********************************************************************************************/
		new GetProductDetails().execute();
	}

	/********************* ASYNC Task Class To Get JSON By Making HTTP Call***********************/
	/*********************************************************************************************/

	private class GetProductDetails extends AsyncTask<Void, Void, Void>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("ppp","uuuu");

			/**************Showing Processing Dialog******************/
			/*********************************************************/
			String search="?search=";

			if(Constant.fromDescription==1){
				String str = Constant.ConstDescription.replaceAll(" ","%20");
				url=url+search+str;
				url=url+pagesize+Constant.ConstPageSize+pagenumber+Constant.ConstPageNumber;
				url=url+"&apikey="+API_KEY;
				Log.v("desc",url);
			}
			else if(Constant.fromUPC==1){
				String str = Constant.ConstUPC;
				url=url+search+str+pagesize+Constant.ConstPageSize+pagenumber+Constant.ConstPageNumber;
				url=url+"&apikey="+API_KEY;
				Log.v("upc",url);
			}
			else if(Constant.fromFeature==1){
				String str = Constant.ConstUPC;
				url=url+str+"?apikey="+API_KEY;
			}
			else if(Constant.fromCompare==1){
				compare1url=Constant.compareupc1;
				compare2url=Constant.compareupc2;
				if(Constant.fromCompareDesc==1){
					url1=url1+search+compare1url+pagesize+Constant.ConstPageSize+pagenumber+Constant.ConstPageNumber;
					url1=url1+"&apikey="+API_KEY;
					Log.v("com",url1);
					url2=url2+search+compare2url+pagesize+Constant.ConstPageSize+pagenumber+Constant.ConstPageNumber;
					url2=url2+"&apikey="+API_KEY;
					Log.v("comp",url2);
				}
				else if(Constant.fromCompareFeature==1){
					url1=url1+compare1url+"?apikey="+API_KEY;
					url2=url2+compare2url+"?apikey="+API_KEY;
				}
				else{
					url1=url1+compare1url+"/health?apikey="+API_KEY;
					url2=url2+compare2url+"/health?apikey="+API_KEY;
				}

			}
			else{
				String str = Constant.ConstUPC;
				url=url+str+"/health?apikey="+API_KEY;
			}
			pDialog = new ProgressDialog(ProductDetails.this);
			pDialog.setMessage("Please Wait...");
			mp.start();
			mp.setLooping(true);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			Log.d("Background","one");
			/********** Creating Service Handler Class Instance**********/
			/************************************************************/
			ServiceHandler sh = new ServiceHandler();
			//was added here
			String jsonStr=null;
			String jsonStr1=null;
			String jsonStr2=null;
			/*********Making A Request To URL and Getting Response*******/
			/************************************************************/
			Log.v("HER",url);
			Log.v("HE",url1);
			Log.v("H",url2);
			if(Constant.fromCompare==1){
				jsonStr1 = sh.makeServiceCall(url1, ServiceHandler.GET);
				jsonStr2= sh.makeServiceCall(url2, ServiceHandler.GET);
				jsonStr="NotNull";
			}
			else{
				jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
				jsonStr1="NotNull";
				jsonStr2="NotNull";
			}
			if(jsonStr==null){
				Constant.result=null;
				Log.d("Null response","true");
				return null;
			}
			if(jsonStr1==null){
				Constant.result=null;
				Log.d("Null response","true");
				return null;
			}
			if(jsonStr2==null){
				Constant.result=null;
				Log.d("Null response","true");
				return null;
			}
			if(jsonStr=="NotNull"){
				outputData=jsonStr1+jsonStr2;
			}
			else{
				outputData=jsonStr;
			}
			int errorIsPresent = jsonStr.indexOf("errorMessage");
			int errorIsPresent1 = jsonStr.indexOf("ErrorMessage");
			if(Constant.fromCompare==1){
				String dummy=jsonStr1;
				int errorOrNot = dummy.indexOf("errorMessage");
				int errorOrNot1 = dummy.indexOf("ErrorMessage");
				if(Constant.fromCompareDesc==1){
					if(errorOrNot>=0){
						errorFound=1;
						displayErrorMessage(jsonStr1);
						displayErrorMessage(jsonStr2);
					}
					else{
						Log.v("insicompare","compare1");
						getDescription(jsonStr1);
						Log.v("inde compare","compare1");
						getDescription(jsonStr2);
						Log.v("side compare","compare1");
					}
				}
				else if(Constant.fromCompareFeature==1){
					if(errorOrNot1>=0){
						errorFound=1;
						displayErrorMessage1(jsonStr1);
						displayErrorMessage1(jsonStr2);
					}
					else{
						Log.v("inpare","compare1");
						getCharacteristics(jsonStr1);
						Log.v("de compare","compare1");
						getCharacteristics(jsonStr2);
						Log.v("side compare","compare1");
					}
				}
				else{
					if(errorOrNot1>=0){
						errorFound=1;
						displayErrorMessage1(jsonStr1);
						displayErrorMessage1(jsonStr2);
					}
					else{
						Log.v("insicompare","compare1");
						getHealthInfo(jsonStr1);
						Log.v("inde compare","compare1");
						getHealthInfo(jsonStr2);
						Log.v("side compare","compare1");
					}
				}
				return null;
			}
			Log.d("Response: ", "> " + url);
			Log.d("Response: ", "> " + jsonStr);
			if (jsonStr!= null) {
				if(Constant.fromFeature==1){
					if(errorIsPresent1>=0){
						errorFound=1;
						displayErrorMessage1(jsonStr);
					}
					else{
						getCharacteristics(jsonStr);
					}

				}
				else if(Constant.fromHealth==1){
					if(errorIsPresent1>=0){
						errorFound=1;
						displayErrorMessage1(jsonStr);
					}
					else{
						getHealthInfo(jsonStr);
					}

				}
				else if(Constant.fromDescription==1){
					if(errorIsPresent>=0){
						errorFound=1;
						displayErrorMessage(jsonStr);
					}
					else{
						getDescription(jsonStr);
					}

				}
				else{
					if(errorIsPresent>=0){
						errorFound=1;
						displayErrorMessage(jsonStr);
					}
					else{
						getDescription(jsonStr);
					}

				}
			}
			else {
				Log.e("ServiceHandler","Couldn't get any data from the url");
			}
			return null;
		}

		void displayErrorMessage(String jsonString){
			Date date = new Date();
			String MessageID;
			String errorMessage;
			try {
				JSONObject jsonObj = new JSONObject(jsonString);

				JSONObject Header = jsonObj.getJSONObject(TAG_HEADER);

				String API_Version = Header.getString(TAG_API_VERSION);
				String API_Name = Header.getString(TAG_API_NAME);
				MessageID = Header.getString(TAG_ERROR_MESSAGE_ID);
				String Content_Type = Header.getString(TAG_CONTENT_TYPE);

				HashMap<String, String> header = new HashMap<String, String>();

				header.put("FEATURE1", "Version: "+API_Version);
				header.put("FEATURE2", "API_Name: "+API_Name);
				header.put("FEATURE3", "Date: "+dateFormat.format(date));
				header.put("FEATURE4", "MessageID: "+MessageID);
				header.put("FEATURE5", "Content_Type: "+Content_Type);
				Log.v("HRT","HRT");
				productList.add(header);

				JSONObject Message = jsonObj.getJSONObject(TAG_MESSAGE);
				errorMessage = Message.getString(TAG_ERROR_MESSAGE);
				HashMap<String, String> error = new HashMap<String, String>();

				error.put("FEATURE1", errorMessage);

				productList.add(error);

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		void displayErrorMessage1(String jsonString){
			Date date = new Date();
			String MessageID;
			String ErrorMessage;
			try {
				JSONObject jsonObj = new JSONObject(jsonString);

				JSONObject Header = jsonObj.getJSONObject(TAG_HEADER);

				String API_Version = Header.getString(TAG_API_VERSION);
				String API_Name = Header.getString(TAG_API_NAME);
				MessageID = Header.getString(TAG_ERROR_MESSAGE_ID);
				String Content_Type = Header.getString(TAG_CONTENT_TYPE);

				HashMap<String, String> header = new HashMap<String, String>();

				header.put(TAG_UPC, "Version: "+API_Version);
				header.put(TAG_DESCRIPTION, "API_Name: "+API_Name);
				header.put(TAG_RANK, "Date: "+dateFormat.format(date));
				header.put(TAG_MODULE, "MessageID: "+MessageID);
				header.put(TAG_ITEM_CODE, "Content_Type: "+Content_Type);
				productList.add(header);


				JSONObject Message = jsonObj.getJSONObject(TAG_MESSAGE);
				ErrorMessage = Message.getString("ErrorMessage");
				HashMap<String, String> error = new HashMap<String, String>();

				error.put(TAG_UPC, ErrorMessage);

				productList.add(error);

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		void getDescription(String jsonStr){
			try {
				JSONObject jsonObj = new JSONObject(jsonStr);
				if(Constant.fromCompare!=1){
					JSONObject Header = jsonObj.getJSONObject(TAG_HEADER);
					int count = Header.length();
					String[] name = new String[count];
					Iterator<String> myIter = Header.keys();
					List<String> sortKey = new ArrayList<String>();

					while(myIter.hasNext()){
						sortKey.add(myIter.next());
					}
					Collections.sort(sortKey);
					String[] tempo = new String[sortKey.size()];
					for(int k=0;k<sortKey.size();k++){
						tempo[k]=sortKey.get(k);
						Log.d("temp",tempo[k]);
						name[k]=Header.getString(tempo[k]);
						Log.d("name",name[k]);
					}
					Arrays.sort(tempo);
					HashMap<String, String> header = new HashMap<String, String>();
					for(int k=0;k<sortKey.size();k++){
						if(k==0)
							header.put("FEATURE1",tempo[k]+":"+name[k]);
						if(k==1)
							header.put("FEATURE2",tempo[k]+":"+name[k]);
						if(k==2)
							header.put("FEATURE3",tempo[k]+":"+name[k]);
						if(k==3)
							header.put("FEATURE4",tempo[k]+":"+name[k]);
						if(k==4)
							header.put("FEATURE5",tempo[k]+":"+name[k]);
						Log.d(sortKey.get(k),name[k]);
					}

					productList.add(header);
				}

				// Getting JSON Array node
				Product = jsonObj.getJSONArray("ProductDetails");


				// looping through All Contacts
				Log.d("countNo",""+Product.length());
				for (int i = 0; i < Product.length(); i++) {

					JSONObject temp = Product.getJSONObject(i);
					int sizeInsideArray;
					sizeInsideArray=temp.length();
					String[] nameValue = new String[sizeInsideArray];
					Log.v("inarray",""+sizeInsideArray);
					Iterator<String> Iter = temp.keys();
					List<String> sortKeyInsideArray = new ArrayList<String>();
					while(Iter.hasNext()){
						sortKeyInsideArray.add(Iter.next());
					}
					Collections.sort(sortKeyInsideArray);
					String[] temp1 = new String[sortKeyInsideArray.size()];
					for(int k=0;k<sortKeyInsideArray.size();k++){
						temp1[k]=sortKeyInsideArray.get(k);
						Log.d("temp",temp1[k]);
						nameValue[k]=temp.getString(temp1[k]);
						Log.d("name",nameValue[k]);
					}
					Arrays.sort(temp1);
					HashMap<String, String> storeData = new HashMap<String, String>();
					for(int k=0;k<sortKeyInsideArray.size();k++){
						if(k<sortKeyInsideArray.size()&&k==0)
							storeData.put("FEATURE1",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==1)
							storeData.put("FEATURE2",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==2)
							storeData.put("FEATURE3",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==3)
							storeData.put("FEATURE4",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==4)
							storeData.put("FEATURE5",temp1[k]+":"+nameValue[k]);
						Log.d(sortKeyInsideArray.get(k),nameValue[k]);
					}
					productList.add(storeData);
				}
				if(Constant.fromCompare!=1){
					JSONObject Summary = jsonObj.getJSONObject(TAG_SUMMARY);
					int records = Summary.length();
					String[] SummaryName = new String[records];
					Iterator<String> SummaryIter = Summary.keys();
					List<String> SummarySortKey = new ArrayList<String>();

					while(SummaryIter.hasNext()){
						SummarySortKey.add(SummaryIter.next());
					}
					Collections.sort(SummarySortKey);
					String[] temp = new String[SummarySortKey.size()];
					for(int k=0;k<SummarySortKey.size();k++){
						temp[k]=SummarySortKey.get(k);
						Log.d("temp",temp[k]);
						SummaryName[k]=Summary.getString(temp[k]);
						Log.d("name",SummaryName[k]);
					}
					Arrays.sort(temp);
					HashMap<String, String> summary = new HashMap<String, String>();
					for(int k=0;k<SummarySortKey.size();k++){
						if(k==0)
							summary.put("FEATURE1",temp[k]+":"+SummaryName[k]);
						if(k==1)
							summary.put("FEATURE2",temp[k]+":"+SummaryName[k]);
						if(k==2)
							summary.put("FEATURE3",temp[k]+":"+SummaryName[k]);
						if(k==3)
							summary.put("FEATURE4",temp[k]+":"+SummaryName[k]);
					}
					productList.add(summary);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		void getHealthInfo(String jsonStr){
			try {
				JSONObject jsonObj = new JSONObject(jsonStr);
				if(Constant.fromCompare!=1){
					JSONObject Header = jsonObj.getJSONObject(TAG_HEADER);
					int count = Header.length();
					String[] name = new String[count];
					Iterator<String> myIter = Header.keys();
					List<String> sortKey = new ArrayList<String>();

					while(myIter.hasNext()){
						sortKey.add(myIter.next());
					}
					Collections.sort(sortKey);
					String[] tempo = new String[sortKey.size()];
					for(int k=0;k<sortKey.size();k++){
						tempo[k]=sortKey.get(k);
						Log.d("temp",tempo[k]);
						name[k]=Header.getString(tempo[k]);
						Log.d("name",name[k]);
					}
					Arrays.sort(tempo);
					HashMap<String, String> header = new HashMap<String, String>();
					for(int k=0;k<sortKey.size();k++){
						if(k==0)
							header.put("FEATURE1",tempo[k]+":"+name[k]);
						if(k==1)
							header.put("FEATURE2",tempo[k]+":"+name[k]);
						if(k==2)
							header.put("FEATURE3",tempo[k]+":"+name[k]);
						if(k==3)
							header.put("FEATURE4",tempo[k]+":"+name[k]);
						if(k==4)
							header.put("FEATURE5",tempo[k]+":"+name[k]);
						Log.d(sortKey.get(k),name[k]);
					}

					productList.add(header);
				}
				// Getting JSON Array node
				Product = jsonObj.getJSONArray("Characteristics");


				// looping through All Contacts
				Log.d("countNo",""+Product.length());
				for (int i = 0; i < Product.length(); i++) {

					JSONObject temp = Product.getJSONObject(i);
					int sizeInsideArray;
					sizeInsideArray=temp.length();
					String[] nameValue = new String[sizeInsideArray];
					Log.v("inarray",""+sizeInsideArray);
					Iterator<String> Iter = temp.keys();
					List<String> sortKeyInsideArray = new ArrayList<String>();
					while(Iter.hasNext()){
						sortKeyInsideArray.add(Iter.next());
					}
					Collections.sort(sortKeyInsideArray);
					String[] temp1 = new String[sortKeyInsideArray.size()];
					for(int k=0;k<sortKeyInsideArray.size();k++){
						temp1[k]=sortKeyInsideArray.get(k);
						Log.d("temp",temp1[k]);
						nameValue[k]=temp.getString(temp1[k]);
						Log.d("name",nameValue[k]);
					}
					Arrays.sort(temp1);
					HashMap<String, String> storeData = new HashMap<String, String>();
					for(int k=0;k<sortKeyInsideArray.size();k++){
						if(k<sortKeyInsideArray.size()&&k==0)
							storeData.put("FEATURE1",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==1)
							storeData.put("FEATURE2",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==2)
							storeData.put("FEATURE3",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==3)
							storeData.put("FEATURE4",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==4)
							storeData.put("FEATURE5",temp1[k]+":"+nameValue[k]);
						Log.d(sortKeyInsideArray.get(k),nameValue[k]);
					}
					productList.add(storeData);
					//show product list

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		void getCharacteristics(String jsonStr){
			try {
				JSONObject jsonObj = new JSONObject(jsonStr);
				if(Constant.fromCompare!=1){
					JSONObject Header = jsonObj.getJSONObject(TAG_HEADER);
					int count = Header.length();
					String[] name = new String[count];
					Iterator<String> myIter = Header.keys();
					List<String> sortKey = new ArrayList<String>();

					while(myIter.hasNext()){
						sortKey.add(myIter.next());
					}
					Collections.sort(sortKey);
					String[] tempo = new String[sortKey.size()];
					for(int k=0;k<sortKey.size();k++){
						tempo[k]=sortKey.get(k);
						Log.d("temp",tempo[k]);
						name[k]=Header.getString(tempo[k]);
						Log.d("name",name[k]);
					}
					Arrays.sort(tempo);
					HashMap<String, String> header = new HashMap<String, String>();
					for(int k=0;k<sortKey.size();k++){
						if(k==0)
							header.put("FEATURE",tempo[k]+":"+name[k]);
						if(k==1)
							header.put("FEATURE2",tempo[k]+":"+name[k]);
						if(k==2)
							header.put("FEATURE3",tempo[k]+":"+name[k]);
						if(k==3)
							header.put("FEATURE4",tempo[k]+":"+name[k]);
						if(k==4)
							header.put("FEATURE5",tempo[k]+":"+name[k]);
						Log.d(sortKey.get(k),name[k]);
					}

					productList.add(header);
				}
				// Getting JSON Array node
				Product = jsonObj.getJSONArray("Characteristics");


				// looping through All Contacts
				Log.d("countNo",""+Product.length());
				for (int i = 0; i < Product.length(); i++) {

					JSONObject temp = Product.getJSONObject(i);
					int sizeInsideArray;
					sizeInsideArray=temp.length();
					String[] nameValue = new String[sizeInsideArray];
					Log.v("inarray",""+sizeInsideArray);
					Iterator<String> Iter = temp.keys();
					List<String> sortKeyInsideArray = new ArrayList<String>();
					while(Iter.hasNext()){
						sortKeyInsideArray.add(Iter.next());
					}
					Collections.sort(sortKeyInsideArray);
					String[] temp1 = new String[sortKeyInsideArray.size()];
					for(int k=0;k<sortKeyInsideArray.size();k++){
						temp1[k]=sortKeyInsideArray.get(k);
						Log.d("temp",temp1[k]);
						nameValue[k]=temp.getString(temp1[k]);
						Log.d("name",nameValue[k]);
					}
					Arrays.sort(temp1);
					HashMap<String, String> storeData = new HashMap<String, String>();
					String toStore="FEATURE";
					for(int k=0;k<sortKeyInsideArray.size();k++){
						toStore=toStore+1;
						if(k<sortKeyInsideArray.size()&&k==0)
							storeData.put("FEATURE1",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==1)
							storeData.put("FEATURE2",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==2)
							storeData.put("FEATURE3",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==3)
							storeData.put("FEATURE4",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==4)
							storeData.put("FEATURE5",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==5)
							storeData.put("FEATURE6",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==6)
							storeData.put("FEATURE7",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==7)
							storeData.put("FEATURE8",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==8)
							storeData.put("FEATURE9",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==9)
							storeData.put("FEATURE10",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==10)
							storeData.put("FEATURE11",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==11)
							storeData.put("FEATURE12",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==12)
							storeData.put("FEATURE13",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==13)
							storeData.put("FEATURE14",temp1[k]+":"+nameValue[k]);
						if(k<sortKeyInsideArray.size()&&k==14)
							storeData.put("FEATURE15",temp1[k]+":"+nameValue[k]);
						Log.d(sortKeyInsideArray.get(k),nameValue[k]);
					}
					productList.add(storeData);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}


		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing()){
				pDialog.dismiss();
				mp.stop();
			}

			if(Constant.result==null){
				Log.d("result","tjk");
				Toast t = Toast.makeText(getApplicationContext(),
						"Oops! Your device doesn't have Internet Connection",
						Toast.LENGTH_LONG);
				t.show();
				return;

			}
			if(Constant.fromFeature==1){
				if(errorFound==1){
					Log.v("Error Feature","inside");
					ListAdapter adapter = new SimpleAdapter(
							ProductDetails.this, productList,
							R.layout.product_details, new String[] {TAG_UPC,TAG_DESCRIPTION,TAG_RANK,
									TAG_MODULE,TAG_ITEM_CODE}, new int[] {R.id.upc,
									R.id.description, R.id.itemcode,R.id.module,R.id.rank});

					setListAdapter(adapter);
					errorFound=0;
				}
				else{
					ListAdapter adapter = new SimpleAdapter(
							ProductDetails.this, productList,
							R.layout.product_feature, new String[] {"FEATURE1","FEATURE2","FEATURE3",
									"FEATURE4","FEATURE5","FEATURE6","FEATURE7",
									"FEATURE8","FEATURE9","FEATURE10","FEATURE11","FEATURE12","FEATURE13","FEATURE14",
							"FEATURE15"}, new int[] {R.id.feature1,
									R.id.feature2, R.id.feature3,R.id.feature4,R.id.feature5,R.id.feature6,
									R.id.feature7,R.id.feature8,R.id.feature9,R.id.feature10,
									R.id.feature11, R.id.feature12,R.id.feature13,R.id.feature14,R.id.feature15});
					setListAdapter(adapter);
					errorFound=0;
				}
			}
			else if(Constant.fromHealth==1){
				Log.v("errorFound",""+errorFound);
				if(errorFound==1){
					Log.v("Inside Health","inside");
					ListAdapter adapter = new SimpleAdapter(
							ProductDetails.this, productList,
							R.layout.product_details, new String[] {TAG_UPC,TAG_DESCRIPTION,TAG_RANK,
									TAG_MODULE,TAG_ITEM_CODE}, new int[] {R.id.upc,
									R.id.description, R.id.itemcode,R.id.module,R.id.rank});

					setListAdapter(adapter);
					errorFound=0;
				}
				else{
					Log.v("Inside Main Health","Outside");
					ListAdapter adapter = new SimpleAdapter(
							ProductDetails.this, productList,
							R.layout.product_details, new String[] {"FEATURE1","FEATURE2","FEATURE3",
									"FEATURE4","FEATURE5"}, new int[] {R.id.upc,
									R.id.description, R.id.itemcode,R.id.module,R.id.rank});
					setListAdapter(adapter);
				}
			}
			else if(Constant.fromCompare==1){
				if(errorFound==1){
					Log.v("Error Feature","inside");
					ListAdapter adapter = new SimpleAdapter(
							ProductDetails.this, productList,
							R.layout.product_details, new String[] {TAG_UPC,TAG_DESCRIPTION,TAG_RANK,
									TAG_MODULE,TAG_ITEM_CODE}, new int[] {R.id.upc,
									R.id.description, R.id.itemcode,R.id.module,R.id.rank});

					setListAdapter(adapter);
					errorFound=0;
				}
				else if(Constant.fromCompareDesc==1){
					ListAdapter adapter = new SimpleAdapter(
							ProductDetails.this, productList,
							R.layout.product_details, new String[] {"FEATURE1","FEATURE2",
									"FEATURE3","FEATURE4","FEATURE5"}, new int[] { R.id.upc,
									R.id.description, R.id.itemcode,R.id.module,R.id.rank});
					setListAdapter(adapter);
					errorFound=0;
				}
				else if(Constant.fromCompareHealth==1){
					Log.v("Inside Main Health","Outside");
					ListAdapter adapter = new SimpleAdapter(
							ProductDetails.this, productList,
							R.layout.product_details, new String[] {"FEATURE1","FEATURE2","FEATURE3",
									"FEATURE4","FEATURE5"}, new int[] {R.id.upc,
									R.id.description, R.id.itemcode,R.id.module,R.id.rank});
					setListAdapter(adapter);
				}
				else{
					ListAdapter adapter = new SimpleAdapter(
							ProductDetails.this, productList,
							R.layout.product_feature, new String[] {"FEATURE1","FEATURE2","FEATURE3",
									"FEATURE4","FEATURE5","FEATURE6","FEATURE7",
									"FEATURE8","FEATURE9","FEATURE10","FEATURE11","FEATURE12","FEATURE13","FEATURE14",
							"FEATURE15"}, new int[] {R.id.feature1,
									R.id.feature2, R.id.feature3,R.id.feature4,R.id.feature5,R.id.feature6,
									R.id.feature7,R.id.feature8,R.id.feature9,R.id.feature10,
									R.id.feature11, R.id.feature12,R.id.feature13,R.id.feature14,R.id.feature15});
					setListAdapter(adapter);
					errorFound=0;
					
				}
			}
			else{
				ListAdapter adapter = new SimpleAdapter(
						ProductDetails.this, productList,
						R.layout.product_details, new String[] {"FEATURE1","FEATURE2",
								"FEATURE3","FEATURE4","FEATURE5"}, new int[] { R.id.upc,
								R.id.description, R.id.itemcode,R.id.module,R.id.rank});
				setListAdapter(adapter);
				errorFound=0;
			}

			//Reset All Data After This

			Constant.fromFeature=0;
			Constant.fromUPC=0;
			Constant.fromDescription=0;
			Constant.ConstDescription=null;
			Constant.ConstPageNumber=0;
			Constant.ConstPageSize=0;
			Constant.ConstUPC=null;
			Constant.fromCompare=0;
			Constant.fromCompareDesc=0;
			Constant.fromCompareFeature=0;
			Constant.fromCompareHealth=0;
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
			} else {
				new Thread(speechThread,"speaking").start();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			Log.e("TTS", "Initilization Failed!");
		}

	}
	private Runnable speechThread = new Runnable(){
		public void run(){
			String text = outputData;
			String readable=null;
			int errorOrNot=0,errorOrNot1=0;
			if(text!=null){
				readable = text.replaceAll("[\",{_\\[\\]}:]"," ");
				errorOrNot = readable.indexOf("ErrorMessage");
				errorOrNot1 = readable.indexOf("errorMessage");
				//Added Here
				Log.v("string",readable);
				tts.setLanguage(Locale.UK);
			}
			if(text==null){
				outputData="You seem to have connectivity problem";
				tts.speak(outputData,TextToSpeech.QUEUE_FLUSH, null);
				return;
			}
			if(errorOrNot>=0||errorOrNot1>=0){
				readable="No Product Is Found With The Given Description";
				tts.speak(readable,TextToSpeech.QUEUE_FLUSH, null);
			}
			else{
				Log.d("Total Output",""+Product.length());
				String noOfRecord ="Total Number Of Recored Being Displayed is"+Product.length();
				tts.speak(noOfRecord, TextToSpeech.QUEUE_FLUSH, null);
				tts.speak(readable,TextToSpeech.QUEUE_FLUSH,null);
			}
		}
	};
}

