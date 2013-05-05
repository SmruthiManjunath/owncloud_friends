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
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
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
import com.owncloud.android.R;


public class FacebookSync extends Activity implements OnClickListener,DialogInterface.OnClickListener{

    String APP_ID;
    Facebook facebook;
    ImageView facebook_sync;
    TextView frnds,add_frnds,pending_request;
    TextView welcome,namefr;
    SharedPreferences sher;
    AsyncFacebookRunner asyncRunner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(android.os.Build.VERSION.SDK_INT>9){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        }
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.sync_page);
        
      
        /*sher = getPreferences(MODE_PRIVATE);
        String access_token = sher.getString("access_token",null);
        Long expires = sher.getLong("access_expires",0); */
        
        APP_ID = getString(R.string.APP_ID);
        facebook = new Facebook(APP_ID);
        
        asyncRunner = new AsyncFacebookRunner(facebook);
        
        
        facebook_sync = (ImageView) findViewById(R.id.setup_sync);
        frnds = (TextView) findViewById(R.id.frnds);
        //namefr.setText(" ");
        facebook_sync.setOnClickListener(this);
        /*if(access_token != null){
        -   facebook.setAccessToken(access_token);
        }
        if(expires!=0){
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
            URL img_url = null;
            
            String JsonObject;
            try {
                JsonObject = facebook.request("me");
                obj = Util.parseJson(JsonObject);
                String id = obj.optString("id");
                String name = obj.optString("name");
                
                //welcome.setText("welcome, "+name);
                Bundle params = new Bundle();
                params.putString("fields", "name,id");
                asyncRunner.request("me/friends",params,"GET",new listener(), null);
                img_url = new URL("http://graph.facebook.com/"+id+"/friends?fields=name");
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
                 /*Editor editor = sher.edit();
                 editor.putString("access_token", facebook.getAccessToken());
                 editor.putLong("access_expires", facebook.getAccessExpires());
                 editor.commit(); */
                 Toast.makeText(FacebookSync.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                 loginfacebook();
             }
             
             @Override
             public void onCancel() {
                 // TODO Auto-generated method stub
                 Toast.makeText(FacebookSync.this, "Login Cancelled", Toast.LENGTH_SHORT).show();
             }
         });
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

    @Override
    public void onComplete(String response, Object state) {
        // TODO Auto-generated method stub
         JSONObject data;
         JSONArray friendsData;
        try {
            data = Util.parseJson(response);
            friendsData = data.getJSONArray("data");
            for (int i = 0; i < friendsData.length(); i++) {
                 JSONObject friend = friendsData.getJSONObject(i);
                 //mDbAdapter.addFriend(friend.getString("name"),
                         //friend.getString("id"));
                 Log.w("tag ", friend.optString("name") + " id "+friend.optString("id"));
             }
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

