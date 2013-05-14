/*
 * 
 * 
 * VillageShare
 */

package com.owncloud.android.ui.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.owncloud.android.AccountUtils;
import com.owncloud.android.R;

public class YourFriendsActivity extends Activity{
    
    Account accountname;
    ListView listView;
    friendArrayAdapter adapter;
    ArrayList<String> friendNames;
    @Override
    public void onCreate(Bundle SavedInstanceState){
        
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.your_friendstab);
        
        listView = (ListView)findViewById(R.id.listview);
        //listView = (ListView)findViewById(R.id.listViewRequests);
        //tv = (TextView)findViewById(R.id.tv1);
        //Button addBtn = Button);
        //setContentView(addBtn);
        //arrayAdapter = new ArrayAdapter<String>(AddFriendsActivity.this,R.id.tv1,friendNames);
        friendNames = new ArrayList<String>();
        adapter = new friendArrayAdapter(this,android.R.layout.simple_list_item_1,friendNames);
        listView.setAdapter(adapter);
        accountname = AccountUtils.getCurrentOwnCloudAccount(getBaseContext());
        String vals[] = accountname.toString().split("[@=,]");
        String username = vals[1];
        final String url = vals[2];
        JSONObject obj1 = new JSONObject();
        final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("CURRENTUSER", username+"@"+url));
        Log.d("sdn object ",params.toString());
        
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                
               HttpPost post = new HttpPost("http://"+url+"/owncloud/index.php/apps/friends/friendlist");
               
               HttpEntity entity;
            try {
                entity = new UrlEncodedFormEntity(params,"utf-8");
                HttpClient client = new DefaultHttpClient();
                post.setEntity(entity);
                HttpResponse response = client.execute(post);
                Log.d("Http esponse"," "+response.getStatusLine().toString());
                //Log.d("Http esponse"," "+response.toString());
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                   //adapter.add(val);
                    //Toast.makeText(YourFriendsActivity.this,"Sorry unable to add friend, check internet connection and try after sometime", Toast.LENGTH_LONG).show();
                   Log.d("Return","Successful");
                }
                else
                {
                    //Toast.makeText(YourFriendsActivity.this,"Sorry unable to add friend, check internet connection and try after sometime", Toast.LENGTH_LONG).show();
                    Log.d("Return","unable to get data");
                }
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } /*catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/ catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
               
            }
            };
            new Thread(runnable).start();

   
        }
    
    
    private class friendArrayAdapter extends ArrayAdapter<String>{
        
        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
        public friendArrayAdapter(Context context,int textViewId,List<String> Objects){
            super(context,textViewId,Objects);
            for (int i = 0; i < Objects.size(); ++i) {
                mIdMap.put(Objects.get(i), i);
              }
        }
        
       
    }

}
