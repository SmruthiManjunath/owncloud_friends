/*
 * VillageShare
 * 
 * 
 */

package com.owncloud.android.ui.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.owncloud.android.AccountUtils;
import com.owncloud.android.R;
import com.owncloud.android.authenticator.AccountAuthenticator;
import com.owncloud.android.utils.OwnCloudVersion;

public class AddFriendsActivity extends Activity implements OnClickListener, OnItemSelectedListener{
   
    
    EditText friendName;
    Button Add;
    Account accountname;
    Object location;
    TextView textView;
    TextView tv;
    ListView listView;
    Spinner s1;
    JSONArray jary;
    String url;
    String username;
    friendArrayAdapter adapter;

    ArrayList<String> friendNames;
    String TAG="AddFriendsActivity";
    @Override
    public void onCreate(Bundle SavedInstanceState){
        
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.add_friendstab);
        
        listView = (ListView)findViewById(R.id.listview);
        
        //listView = (ListView)findViewById(R.id.listViewRequests);
        //tv = (TextView)findViewById(R.id.tv1);
        //Button addBtn = Button);
        //setContentView(addBtn);
        //arrayAdapter = new ArrayAdapter<String>(AddFriendsActivity.this,R.id.tv1,friendNames);
        friendNames = new ArrayList<String>();
        adapter = new friendArrayAdapter(this,R.layout.removeyourfriends,friendNames);
        listView.setAdapter(adapter);
        AccountManager am = AccountManager.get(this);
        Account account = AccountUtils.getCurrentOwnCloudAccount(this);
        OwnCloudVersion ocv = new OwnCloudVersion(am.getUserData(account, AccountAuthenticator.KEY_OC_VERSION));
        String[] url1 = (am.getUserData(account, AccountAuthenticator.KEY_OC_BASE_URL)).split("/");
        url = url1[2];
        //accountname = AccountUtils.getCurrentOwnCloudAccount(getBaseContext());
        String vals[] = account.toString().split("[=,]");
        username = vals[1];
        
        JSONObject obj1 = new JSONObject();
        final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("CURRENTUSER", username));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                
               HttpPost post = new HttpPost("http://"+url+"/owncloud/index.php/apps/friends/getfriendrequest");
               
               HttpEntity entity;
               try {
                   entity = new UrlEncodedFormEntity(params,"utf-8");
                   HttpClient client = new DefaultHttpClient();
                   post.setEntity(entity);
                   HttpResponse response = client.execute(post);
                   Log.d(TAG,"Fetching friend list from server");
                   
                   if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                       HttpEntity entityresponse = response.getEntity();
                       String jsonentity = EntityUtils.toString(entityresponse);
                       JSONObject obj = new JSONObject(jsonentity);
                       JSONObject obj1 = (JSONObject) obj.get("data");
                       
                       jary = obj1.getJSONArray("sentFriendshipRequests");
                       friendNames.clear();
                       for(int i = 0; i<jary.length();i++){
                           friendNames.add(jary.getString(i));
                           Log.d(TAG,jary.getString(i));
                       }
                       runOnUiThread(new Runnable() {
                           public void run() {
                               notifyDataChanged();
                           }
                       }); 
                       if(jary.length() == 0){
                           //TextView frndTxt = (TextView)findViewById(R.id.yourfrndtxt);
                           runOnUiThread(new Runnable() {
                               public void run() {
                            TextView frndTxt = (TextView)findViewById(R.id.defaultadd);
                            //receivedFriendshipRequestArray.add("Yiu have no pending friend requests ");
                            frndTxt.setText("You have not added any friends");
                               }
                           }); 
                           //frndTxt.setText("You have no friends");
                       }else {
                           runOnUiThread(new Runnable() {
                               public void run() {
                            TextView frndTxt = (TextView)findViewById(R.id.defaultadd);
                            //receivedFriendshipRequestArray.add("Yiu have no pending friend requests ");
                            frndTxt.setVisibility(0);
                               }
                           }); 
                       }
                       
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
               } catch (JSONException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }
               
                  
               }
            };
            new Thread(runnable).start();
            Add = (Button)findViewById(R.id.btn1);
            friendName = (EditText)findViewById(R.id.edttext1);
            s1 = (Spinner)findViewById(R.id.spinner_addfriends);
            s1.setOnItemSelectedListener(this);
        Add.setOnClickListener(this);
        
    }
    protected void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        String val1 = friendName.getText().toString();
        // clean up when you change it to AyncTask
        if(val1.equals("")){
            Toast.makeText(AddFriendsActivity.this, "Please enter a friends name", Toast.LENGTH_LONG).show();
        }
        else {
            final String val = val1+"@"+location;
        Log.d("Asked for friendship request",val);
        
            
            JSONObject obj1 = new JSONObject();
            final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("CURRENTUSER", username));
            params.add(new BasicNameValuePair("REQUESTEDFRIEND",val));
            Log.d("sdn object ",params.toString());
  
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    
                   HttpPost post = new HttpPost("http://"+url+"/owncloud/index.php/apps/friends/friendrequest");
                   
                   HttpEntity entity;
                   try {
                       entity = new UrlEncodedFormEntity(params,"utf-8");
                       HttpClient client = new DefaultHttpClient();
                       post.setEntity(entity);
                       HttpResponse response = client.execute(post);
                       Log.d(TAG,"Fetching friend list from server");
                       
                       if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                           //adapter.add(val);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    adapter.add(val);
                                    friendName.setText("");
                                    Toast.makeText(AddFriendsActivity.this, "You requested "+val+" to add as friend", Toast.LENGTH_SHORT).show();
                           //stuff that updates ui

                               }
                           });
                           
                        }
                        else
                        {
                            //Toast.makeText(AddFriendsActivity.this,"Sorry unable to add friend, check internet connection and try after sometime", Toast.LENGTH_LONG).show();
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
    }
    
    
    public void handler1(View v){
        final int position =listView.getPositionForView((View) v.getParent());
        //Log.d("on click", "Title clicked, row %d"+position);
        final String str = ((TextView)((View)v.getParent()).findViewById(R.id.yourfrndtxt)).getText().toString();
        //Log.d("on click",str);
        Log.d("handler ",str);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               HttpPost post = new HttpPost("http://"+url+"/owncloud/index.php/apps/friends/removefriendrequest");
               HttpEntity entity;
               
               
               final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
               params.add(new BasicNameValuePair("CURRENTUSER", username));
               params.add(new BasicNameValuePair("SENTORRECEIVED", "sent"));
               params.add(new BasicNameValuePair("FRIEND",str));
            try {
                entity = new UrlEncodedFormEntity(params,"utf-8");
                HttpClient client = new DefaultHttpClient();
                post.setEntity(entity);
                HttpResponse response = client.execute(post);
                Log.d("Http esponse"," "+response.getStatusLine().toString());
                //Log.d("Http esponse"," "+response.toString());
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                    
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String s = Integer.toString(position);
                            adapter.remove(s);
                            //notifyDataSetChanged();
                            friendNames.remove(position);
                            Log.d("rem ",s+" ");
                            Toast.makeText(AddFriendsActivity.this, "You removed friend successfully", Toast.LENGTH_SHORT).show();
                            
                   //stuff that updates ui

                       }
                   });
                    
                    }
                    
                else
                {
                    //Toast.makeText(AddFriendsActivity.this,"Sorry unable to add friend, check internet connection and try after sometime", Toast.LENGTH_LONG).show();
                    Log.d("in re"," could not remove" );
                }
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } /*catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
               
            }
            };
            new Thread(runnable).start();
    }
    
    private class friendArrayAdapter extends ArrayAdapter<String>{
        
        int i=0;
        //HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
        List<String> Objects;
        Context context;
        int layoutResourceId;
        friendRowholder holder;
        public friendArrayAdapter(Context context,int layoutResourceId,List<String> Objects){
            super(context,layoutResourceId,Objects);
            this.context = context;
            this.layoutResourceId = layoutResourceId;
            this.Objects = Objects;
            
        }
        
        @Override
        public View getView(int position, View convertView,ViewGroup parent){
            View row = convertView;
            //friendRowholder holder = null;
            //Log.d("getView ","here in getv");
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId,parent,false);
            //Log.d("getview","in getvi");
            holder = new friendRowholder();
            holder.frndPos = Objects.get(position);
            //Log.d("getView","after holder creation");
            //holder.acceptfrndButton = (Button)findViewById(R.id.acceptbtn);
            //holder.acceptfrndButton.setOnClickListener(handler1);
            holder.removefrndButton = (Button)findViewById(R.id.removebtn1);
            holder.frndtxt = (TextView)row.findViewById(R.id.yourfrndtxt);
            //display();
            //if(row.getTag()== null){
                if(row.getTag()==null){
                row.setTag(holder);
            //Log.d("jarey",jary.toString());
            /*for(int i =0;i<jary.length();i++){
                Log.d("********************************","v"+i);
                 holder.frndtxt.setText(jary.toString());*/
            String text = friendNames.get(position);
           Log.d("positi ",Integer.toString(position));
            holder.frndtxt.setText(text);
            //((BaseAdapter)getListAdapter()).notifyDataSetChanged();
            return row;
                }
                else{
                    return null;
                }
                
           // adapter.addAll(sentFriendshipRequestArray);
            //holder.frndtxt.setText();
           //i++;
            
        }
        
        
       /* public OnClickListener handler1 = new OnClickListener(){

            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final int position =listView.getPositionForView((View) v.getParent());
                Log.d("on click", "Title clicked, row %d"+position);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                       HttpPost post = new HttpPost("http://"+url+"/owncloud/index.php/apps/friends/acceptfriendrequest");
                       HttpEntity entity;
                       
                       
                       final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                       params.add(new BasicNameValuePair("CURRENTUSER", username+"@"+url));
                       //params.add(new BasicNameValuePair("ACCEPTFRIEND",))
                    try {
                        entity = new UrlEncodedFormEntity(params,"utf-8");
                        HttpClient client = new DefaultHttpClient();
                        post.setEntity(entity);
                        HttpResponse response = client.execute(post);
                        Log.d("Http esponse"," "+response.getStatusLine().toString());
                        //Log.d("Http esponse"," "+response.toString());
                        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                            HttpEntity entityresponse = response.getEntity();
                            String jsonentity = EntityUtils.toString(entityresponse);
                            JSONObject obj = new JSONObject(jsonentity);
                            JSONObject obj1 = (JSONObject) obj.get("data");
                            Log.d("response tewo aewrwqer***********************************"," "+jsonentity);
                            //Log.d("response tewo aewrwq"," "+obj1.get("sentFriendshipRequests"));
                           
                            jary = obj1.getJSONArray("sentFriendshipRequests");
                            //sentFriendshipRequestArray.addAll(jary);
                            for(int i = 0; i<jary.length();i++){//sentFriendshipRequestArray.size();i++)
                                //JSONObject jobj = jary.getJSONObject(i);
                               //Log.d("valu f",jary.getString(i));//Log.d("value f ",sentFriendshipRequestArray.get(i));
                                sentFriendshipRequestArray.add(jary.getString(i));
                                
                                //display(jary.getString(i));
                            }
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    listView.setAdapter(adapter);
                                    //holder.frndtxt.setText(sentFriendshipRequestArray(i));
                                    //Log.d("qiwehjqwpe "," in display");

                               }
                           }); 
                           
                            
                            display();
                                
                            //adapter.
                            
                        }
                        else
                        {
                            //Toast.makeText(AddFriendsActivity.this,"Sorry unable to add friend, check internet connection and try after sometime", Toast.LENGTH_LONG).show();
                        }
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } *//*catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }*//* catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                       
                    }
                    };
                    new Thread(runnable).start();
                
            }
            
        };
        */
        
       public class friendRowholder{
           String frndPos;
           TextView frndtxt;
           Button acceptfrndButton;
           Button removefrndButton;
           
           
       }
        
       
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        location = parent.getItemAtPosition(position);
        //Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
        
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        
    }
}
