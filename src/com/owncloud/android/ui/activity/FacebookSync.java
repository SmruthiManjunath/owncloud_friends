/*
 * 
 * 
 * 
 * VillageShare
 * 
 */
package com.owncloud.android.ui.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.owncloud.android.AccountUtils;
import com.owncloud.android.R;


public class FacebookSync extends Activity implements OnClickListener,DialogInterface.OnClickListener{

    String APP_ID;
    Facebook facebook;
    ImageView facebook_sync;
    TextView frnds,add_frnds,pending_request;
    TextView welcome,namefr;
    SharedPreferences sher;
    AsyncFacebookRunner asyncRunner;
    public static String username; // = ((TextView).findViewById(R.id.user_input)).getText().toString().trim();
    public static String url;// = ((TextView)findViewById(R.id.host_URL)).getText().toString().trim();
    public static String id;
    String access_token;
    Long expires;
    String name;
    public static Account accountname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(android.os.Build.VERSION.SDK_INT>9){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        //Context str = getApplicationContext();
        Context gettingApplicationContext = getBaseContext();
        
        accountname = AccountUtils.getCurrentOwnCloudAccount(gettingApplicationContext);
        //Log.d("Application Context",str2.toString());
        Log.d("account name",accountname.toString());
        super.onCreate(savedInstanceState);
        }
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.sync_page);
        
      
        
        
        APP_ID = getString(R.string.APP_ID);
        facebook = new Facebook(APP_ID);
        //url = ((TextView)findViewById(R.id.host_URL)).getText().toString().trim();
        //Log.d("url ewr",url);
        //username = ((TextView)findViewById(R.id.user_input)).getText().toString().trim();
        //Log.d("username sdkf",username);
        asyncRunner = new AsyncFacebookRunner(facebook);
        
        
        facebook_sync = (ImageView) findViewById(R.id.setup_sync);
        frnds = (TextView) findViewById(R.id.frnds);
        //namefr.setText(" ");
        sher = getPreferences(MODE_PRIVATE);
        access_token = sher.getString("access_token",null);
        expires = sher.getLong("access_expires",0); 
        if(access_token != null){
            Log.d("Tagdew","stored access tokens----------------------------------");
           facebook.setAccessToken(access_token);
        }
        if(expires!=0){
            Log.d("jehdiwef","stored expsdjnfowfneke---------------");
            facebook.setAccessExpires(expires);
        }
        
        facebook_sync.setOnClickListener(this);
      /*  if(access_token != null){
            Log.d("Tagdew","stored access tokens----------------------------------");
           facebook.setAccessToken(access_token);
        }
        if(expires!=0){
            Log.d("jehdiwef","stored expsdjnfowfneke---------------");
            facebook.setAccessExpires(expires);
        }*/
        
        //SyncDialog d = new SyncDialog();
        
        
        
        //d.onCreateDialog();
        //loginfacebook();
    }
    
    @SuppressWarnings("deprecation")
    public void loginfacebook(){
        if(facebook.isSessionValid()){
            JSONObject obj = null;
            URL friends_url = null;
            
            String JsonObject;
            try {
                JsonObject = facebook.request("me");
                obj = Util.parseJson(JsonObject);
                id = obj.optString("id");
                name = obj.optString("name");
                
                //welcome.setText("welcome, "+name);
                Bundle params = new Bundle();
                params.putString("fields", "name,id");
                asyncRunner.request("me/friends",params,"GET",new listener(), null);
                friends_url = new URL("http://graph.facebook.com/"+id+"/friends?fields=name");
                //asyncRunner.request("me/friends?fields=id,name",new RequestListener())
                /*JSONArray jsonarray = obj.optJSONArray("");
                
                for(int i=0;i<jsonarray.length();i++)
                {
                    JSONObject obj1 = jsonarray.getJSONObject(i);
                    
                    String namef = obj1.getString("name");
                    //namefr.setText("friendae, "+namef);
                Log.w("frnd "+namef, namef);
                }*/
                //facebook_sync.setImageResource(R.drawable.com_facebook_logo);
                
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FacebookError e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            
            
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(facebook.isSessionValid()){
           
                //facebook.logout(this);
                Toast.makeText(FacebookSync.this, "You re logged in", Toast.LENGTH_SHORT).show();
                showDialog(0);
        }
        else {
            
            
            //chkBeforeLogging();
            showDialog(0);
            Toast.makeText(FacebookSync.this, "Before logging in", Toast.LENGTH_SHORT).show();
            
        }
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        facebook.authorizeCallback(requestCode, resultCode, data);
    }

    @Override
    public void onClick(DialogInterface arg0, int arg1) {
        // TODO Auto-generated method stub
        
    }
    
   public Dialog onCreateDialog(int id){
       Dialog dialog = null;
       switch(id){
       case 0: android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setTitle("Facebook Sync")
       .setCancelable(false)
       .setMessage("The sync will occur with the user currently logged in to Facebook.  If there is no logged in user, you will be prompted to log in.  Please confirm that someone else is not logged into Facebook .  Then press OK to continue.")
       .setPositiveButton("OK",new DialogInterface.OnClickListener(){

     @Override
     public void onClick(DialogInterface dialog, int which) {
         // TODO Auto-generated method stub
         Toast.makeText(FacebookSync.this, "Activity will continue",Toast.LENGTH_LONG).show();
         
         if(!facebook.isSessionValid()){
         facebook.authorize(FacebookSync.this, new String[] {"email","read_friendlists"},new DialogListener() {
             
             @Override
             public void onFacebookError(FacebookError e) {
                 // TODO Auto-generated method stub
                 Toast.makeText(FacebookSync.this, "Sorry unable to log you in! Please try later", Toast.LENGTH_SHORT).show();
             }
             
             @Override
             public void onError(DialogError e) {
                 // TODO Auto-generated method stub
                 Toast.makeText(FacebookSync.this, "Sorry Unable to log you in!", Toast.LENGTH_SHORT).show();
             }
             
             @Override
             public void onComplete(Bundle values) {
                 // TODO Auto-generated method stub
                 Editor editor = sher.edit();
                 editor.putString("access_token", facebook.getAccessToken());
                 editor.putLong("access_expires", facebook.getAccessExpires());
                 editor.commit(); 
                 Log.d("qwoqjoqwqowioqwnwoqr",facebook.getAccessToken());
                 Log.d("wjhkfrewjwerhiwrehwerih"," "+facebook.getAccessExpires());
                 Toast.makeText(FacebookSync.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                 loginfacebook();
             }
             
             @Override
             public void onCancel() {
                 // TODO Auto-generated method stub
                 Toast.makeText(FacebookSync.this, "Login Cancelled", Toast.LENGTH_SHORT).show();
             }
         });
         }
         loginfacebook();
     }
        
    })
    .setNegativeButton("Cancel",new DialogInterface.OnClickListener(){

     @Override
     public void onClick(DialogInterface dialog, int which) {
         // TODO Auto-generated method stub
         //loginfacebook();
     }
        
    });//.showDialog();
    //Toast.makeText(SyncDialog.this, "Activity will continue",Toast.LENGTH_LONG).show();
    dialog = builder.create();
    //dialog.show();
       }
    return dialog;
      
   }
    
    
    
    /*
     * 
     * 
     * 
     * 
     * 
     */
    /*final class CancelOnClickListener implements
    DialogInterface.OnClickListener {
    public void onClick(DialogInterface dialog, int which) {
        
    Toast.makeText(FacebookSync.this, "Activity will continue",Toast.LENGTH_LONG).show();
    }
  }

  final class OkOnClickListener implements
      DialogInterface.OnClickListener {
    public void onClick(DialogInterface dialog, int which) {
        loginfacebook();
    //SyncDialog.this.finish();
    } 
  } */
    
    
        
}

class listener implements RequestListener{

    
    
    
    void postToServer(JSONArray json1)
    {
        //HttpClient client = new DefaultHttpClient();
        //HttpPost post = new HttpPost("http://128.111.52.151/owncloud/index.php/apps/friends/getandroid");
        final String PARAM_USERNAME="Username";
        final String PARAM_FRIENDS="friends";
        String url = FacebookSync.url;
        String username = "Smruthi Manjunath";
        //StringEntity se = new StringEntity(jsonArray.toString(),HTTP.UTF_8);
       List<NameValuePair> ne = new ArrayList<NameValuePair>();
       List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
       JSONObject obj1 = new JSONObject();
       
       //Context context = new Context();
        //Context str = this.getApplicationContext();
       //String str =FacebookSync.getContext().toString();
       //Log.d("tag", );
       //AccountUtils.getCurrentOwnCloudAccount(getApplicationContext);
        //JSONArray friendsData = interests.getJSONArray("data");
        //obj1.putOpt("Username", "Smruthi Manjunath");
        final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("USERNAME", "Smruthi Manjunath"));//FacebookSync.accountname.toString()));
        params.add(new BasicNameValuePair("FRIENDS", json1.toString()));
        /*for(int i=0;i<interests.length;i++){
        Log.d("tayhsd giaejrpwqjrpqjwr[ ", interests.getJSONObject(i).toString());
        } */
        //HttpEntity entity =  new UrlEncodedFormEntity(params);
        //final HttpPost post = new HttpPost(UPDATE_INTERESTS_URI);
        
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                
               HttpPost post = new HttpPost("http://128.111.52.151/owncloud/index.php/apps/friends/androidjk");
               
               HttpEntity entity;
            try {
                entity = new UrlEncodedFormEntity(params);
                HttpClient client = new DefaultHttpClient();
                post.setEntity(entity);
                HttpResponse response = client.execute(post);
                Log.d("Http esponse"," "+response.getStatusLine().toString());
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
    @Override
    public void onComplete(String response, Object state) {
        // TODO Auto-generated method stub
         final JSONObject data;
         JSONArray friendsData;
         final Handler handler;
         final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
         
        try {
            data = Util.parseJson(response);
            friendsData = data.getJSONArray("data");
            //JSONArray
            final PostFriendsToServer ps = new PostFriendsToServer();
            for (int i = 0; i < friendsData.length(); i++) {
                 //JSONObject friend = friendsData.getJSONObject(i);
                 //mDbAdapter.addFriend(friend.getString("name"),
                         //friend.getString("id"));
                 
                 //Log.d("tayhsd g ", friendsData.getJSONObject(i).toString());
                 //Log.w("tag ", friend.optString("name") + " id "+friend.optString("id"));
                 
                 
             }
            
            //ps.doInBackground(friendsData);
        
            //ps.onPostExecute(friendsData);
            postToServer(friendsData);
            //NameValuePair ne = new NameValuePair("Friendlist",friendsData.toString());
            //params.add(new BasicNameValuePair("UserName",FacebookSync.username));
            //params.add(new BasicNameValuePair("Friendlist",friendsData.toString()));
            //params.add(ne);
            
            
            
            
            //ps.doInBackground(params);
            
        } catch (FacebookError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
         //mDbAdapter.deleteAllFriends();

         
    }

    @Override
    public void onIOException(IOException e, Object state) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onFileNotFoundException(FileNotFoundException e, Object state) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onMalformedURLException(MalformedURLException e, Object state) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onFacebookError(FacebookError e, Object state) {
        // TODO Auto-generated method stub
        
    }
    
}

