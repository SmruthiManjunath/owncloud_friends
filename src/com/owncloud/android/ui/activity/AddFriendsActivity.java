/*
 * VillageShare
 * 
 * 
 */

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
import org.json.JSONObject;

import android.accounts.Account;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.owncloud.android.AccountUtils;
import com.owncloud.android.R;

public class AddFriendsActivity extends Activity implements OnClickListener{
   
    
    EditText friendName;
    Button Add;
    Account accountname;
    TextView textView;
    TextView tv;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> friendNames;
    @Override
    public void onCreate(Bundle SavedInstanceState){
        
        super.onCreate(SavedInstanceState);
        
        TextView textView = new TextView(this);
        textView.setText("Add Friends");
        
       listView = (ListView)findViewById(R.id.listViewRequests);
        tv = (TextView)findViewById(R.id.tv1);
        //Button addBtn = Button);
        //setContentView(addBtn);
        setContentView(R.layout.add_friendstab);
        arrayAdapter = new ArrayAdapter<String>(AddFriendsActivity.this,R.id.tv1,friendNames);
        friendNames = new ArrayList<String>();
        friendName = (EditText)findViewById(R.id.edttext1);
        Add = (Button)findViewById(R.id.btn1);
        Add.setOnClickListener(this);
        String val = friendName.getText().toString();
        Log.d("Asked for friendship request",val);
        
        
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        final String val = friendName.getText().toString();
        if(val == null){
            Toast.makeText(AddFriendsActivity.this, "Please enter a friends name", Toast.LENGTH_SHORT).show();
        }
        else {
        Log.d("Asked for friendship request",val);
        
            final String PARAM_USERNAME="Username";
            final String PARAM_FRIENDS="friends";
            //String url = FacebookSync.url;
            accountname = AccountUtils.getCurrentOwnCloudAccount(getBaseContext());
            //String uid = accountname.toString();
            String vals[] = accountname.toString().split("[@=,]");
            String username = vals[1];
            final String url = vals[2];
           JSONObject obj1 = new JSONObject();
           
           //Context context = new Context();
            //Context str = this.getApplicationContext();
           //String str =FacebookSync.getContext().toString();
           //Log.d("tag", );
           //AccountUtils.getCurrentOwnCloudAccount(getApplicationContext);
            //JSONArray friendsData = interests.getJSONArray("data");
            //obj1.putOpt("Username", "Smruthi Manjunath");
            final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("currentUser", username));//FacebookSync.accountname.toString()));
            params.add(new BasicNameValuePair("requestedFriend",val));
            /*for(int i=0;i<interests.length;i++){
            Log.d("tayhsd giaejrpwqjrpqjwr[ ", interests.getJSONObject(i).toString());
            } */
            //HttpEntity entity =  new UrlEncodedFormEntity(params);
            //final HttpPost post = new HttpPost(UPDATE_INTERESTS_URI);
            
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    
                   HttpPost post = new HttpPost("http://"+url+"/owncloud/index.php/apps/friends/friendRequest");
                   
                   HttpEntity entity;
                try {
                    entity = new UrlEncodedFormEntity(params,"utf-8");
                    HttpClient client = new DefaultHttpClient();
                    post.setEntity(entity);
                    HttpResponse response = client.execute(post);
                    Log.d("Http esponse"," "+response.getStatusLine().toString());
                    if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                        //tv = new TextView(AddFriendsActivity.this);
                       //friendNames.add(val);
                       arrayAdapter.add(val);
                       
                    }
                    else
                    {
                        Toast.makeText(AddFriendsActivity.this,"Sorry unable to add friend, check internet connection and try after sometime", Toast.LENGTH_LONG).show();
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
                   
                  
            
            /*post.setHeader("Content-type", "application/json");
            post.setHeader("Accept", "application/json");
          JSONObject obj = new JSONObject();
          obj.put("username", "abcd");
          obj.put("password", "1234");
            post.setEntity(new StringEntity(obj.toString(), "UTF-8"));
            HttpResponse response = client.execute(post);  */
            
            
            /*nameValuePairs.add(new BasicNameValuePair("param1","EKOEWK"));
            nameValuePairs.add(new BasicNameValuePair("param2","KMWEKMR"));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
            //post.setEntity(new UrlEncodedFormEntity(ne, "utf-8"));
            //post.setEntity(entity);
            //post.setEntity(new UrlEncodedFormEntity((List<? extends org.apache.http.NameValuePair>) ne));
            HttpResponse response = client.execute(post);*/
            
            
                }
                };
                new Thread(runnable).start();
            
        
        
        
    }
    }
}
