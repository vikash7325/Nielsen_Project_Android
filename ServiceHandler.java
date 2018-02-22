package com.example.nielsenproject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.util.EntityUtils;

import android.util.Log;
 
public class ServiceHandler {
 
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
 
    public ServiceHandler() {
 
    }
 
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method) {
    	Log.d("Inside MakeService Call: ", "> " + url);
        return this.makeServiceCall(url, method, null);
    }
 
    public String makeServiceCall(String url, int method,
            List<NameValuePair> params) {
        try {
            // http client
           // DefaultHttpClient httpClient = new DefaultHttpClient();
        	MySSLSocketFactory my;
        	my = new MySSLSocketFactory(null);
        	HttpClient httpClient = my.getNewHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
        	Log.d("MakeService ", "> " + url);
             
            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
     
                httpResponse = httpClient.execute(httpPost);
 
            } else if (method == GET) {
                // appending params to url
            	Log.d("Call: ", "> " + url);
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
                Log.d("MResponse: ", "> " + url);
                httpGet.setHeader("Accept", "application/json");
                httpGet.setHeader("Content-type", "application/json");
     
                httpResponse = httpClient.execute(httpGet);                
                Log.d("My Response: ", "> " + url);
 
            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
            Log.d("Log Response: ", "> " + response);
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        	Log.d("InMake: ", "> " + url);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        	Log.d("InDMake: ", "> " + url);
        } catch (IOException e) {
            e.printStackTrace();
        	Log.d("IMakeService Call: ", "> " + url);
        	Log.d("IMakeService Call: ", "> " + response);
        } catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        return response;
 
    }
}