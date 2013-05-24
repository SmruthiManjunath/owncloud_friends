package com.owncloud.android.ui.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.owncloud.android.R;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TryingAsync extends AsyncTask<String, Integer , ArrayList<String>> {

    ArrayAdapter<String> adapter;
    ArrayList<String> sharefriendList;
    String TAG = "TryingAsync";
    AutoCompleteTextView textview;
    @Override
    protected ArrayList<String> doInBackground(String... urls) {
        // TODO Auto-generated method stub
        String url = urls[2];
        String username = urls[1];
        ArrayList<String> sharefriendList = new ArrayList<String>();
        Log.d(" Seriously :(",url);
        HttpPost post = new HttpPost("http://"+url+"/owncloud/index.php/apps/friends/friendlist");
        final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("CURRENTUSER", username));
        HttpEntity entity;
     try {
         entity = new UrlEncodedFormEntity(params,"utf-8");
         HttpClient client = new DefaultHttpClient();
         post.setEntity(entity);
         HttpResponse response = client.execute(post);
         String friendArray[] = null;
         //Log.d(TAG,"Fetching friend list from server");
         
         if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
             HttpEntity entityresponse = response.getEntity();
             String jsonentity = EntityUtils.toString(entityresponse);
             JSONObject obj = new JSONObject(jsonentity);
             JSONObject obj1 = (JSONObject) obj.get("data");
             
             JSONArray jary = obj1.getJSONArray("friendships");
             //sharefriendList.clear();
             for(int i = 0; i<jary.length();i++){
             sharefriendList.add(jary.getString(i));
                 Log.d("TAG",jary.getString(i));
             }
                  //adapter.addAll(sharefriendList);    
             //Log.d(TAG," after list population");    
             /*if(jary.length() == 0){
                 runOnUiThread(new Runnable() {
                     public void run() {
                  TextView frndTxt = (TextView)findViewById(R.id.shareitm);
                  
                  frndTxt.setText("You have no friends");
                     }
                 }); 
                 
             }*/
         }
        /* else
         {
             Log.d("Return","unable to get data");
         }*/
     } catch (UnsupportedEncodingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
     } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
     } catch (JSONException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
     }
     
     
        //return null;
     return sharefriendList;
    }
    
   
    @Override
    protected void onPostExecute(ArrayList<String> result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        Log.d("Tired :(",result.get(0));
    }
    
        //textview = new AutoCompleteTextView();
      /* textView
        textview.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(android.R.layout.simple_dropdown_item_1line,sharefriendList);
        Log.d("Now I am exhausted ",anchor+" ");
        textview.setOnItemClickListener(new OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // TODO Auto-generated method stub
                    Log.d(TAG,"got clicked");
                        //Toast.makeText(FileDisplayActivity.this, "Got cclicked",Toast.LENGTH_SHORT);
                    }
                
    
}); */
        
}
