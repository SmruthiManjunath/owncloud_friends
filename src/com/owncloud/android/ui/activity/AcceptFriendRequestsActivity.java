
/*
 * 
 * 
 * VillageShare
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
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.owncloud.android.AccountUtils;
import com.owncloud.android.R;

public class AcceptFriendRequestsActivity extends Activity {
   
    Account accountname;
    ListView listView1;
    String username;
    String url;
    friendArrayAdapter friendadapter;
    ArrayList<String> receivedFriendshipRequestArray;
    JSONArray jary1;
    String TAG="AcceptFriendRequestsActivity";
    @Override
    public void onCreate(Bundle SavedInstanceState){
        
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.accept_friendstab);
        
        listView1 = (ListView)findViewById(R.id.listViewAccept);
        receivedFriendshipRequestArray = new ArrayList<String>();
        friendadapter = new friendArrayAdapter(this,R.layout.listacceptremove,receivedFriendshipRequestArray);
        Log.d("oncreate ","in function adapte");
        listView1.setAdapter(friendadapter);
        Log.d("accept frd","setclickadn");
        
        accountname = AccountUtils.getCurrentOwnCloudAccount(getBaseContext());
        String vals[] = accountname.toString().split("[@=,]");
        username = vals[1];
        url = vals[2];
        JSONObject obj1 = new JSONObject();
        final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("CURRENTUSER", username));
        Log.d("sdnsehriwuaehrowa object ",params.toString());

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
                Log.d("Http esponse"," "+response.getStatusLine().toString());
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                    HttpEntity entityresponse = response.getEntity();
                    String jsonentity = EntityUtils.toString(entityresponse);
                    JSONObject obj = new JSONObject(jsonentity);
                    JSONObject obj1 = (JSONObject) obj.get("data");
                    Log.d("response tewo aewrwq"," "+obj1.get("receivedFriendshipRequests"));
                   
                    jary1 = obj1.getJSONArray("receivedFriendshipRequests");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            notifyDataChanged();
                        }
                    }); 
                    //receivedFriendshipRequestArray.clear();
                    Log.d(TAG,"Finished http request");
                    for(int i = 0; i<jary1.length();i++){
                       Log.d("valu f",jary1.getString(i));
                        receivedFriendshipRequestArray.add(jary1.getString(i));
                        
                        
                    }
                    
                   if(jary1.length()==0){
                       
                       runOnUiThread(new Runnable() {
                           public void run() {
                        TextView frndTxt = (TextView)findViewById(R.id.defaultaccept);
                        //receivedFriendshipRequestArray.add("Yiu have no pending friend requests ");
                        frndTxt.setText("You have no pending friend requests");
                           }
                       }); 
                    }
                    
                    //display();
                        
                    //adapter.
                    
                }
                else
                {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //listView.setAdapter(adapter);
                            Toast.makeText(AcceptFriendRequestsActivity.this,"Sorry unable to add friend, check internet connection and try after sometime", Toast.LENGTH_LONG).show();
                            //holder.frndtxt.setText(sentFriendshipRequestArray(i));
                            //Log.d("qiwehjqwpe "," in display");

                       }
                   }); 
                    //Toast.makeText(AddFriendsActivity.this,"Sorry unable to add friend, check internet connection and try after sometime", Toast.LENGTH_LONG).show();
                   Log.d("onCreate ","Not able to display");
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
        
        
        
       
        
    }

    protected void notifyDataChanged() {
        friendadapter.notifyDataSetChanged();
    }  
        
        void display(){
             runOnUiThread(new Runnable() {
                public void run() {
                    friendadapter.addAll(receivedFriendshipRequestArray);
                    Log.d("display","In displa");
           //stuff that updates ui
                    //holder.frndtxt.setText(sentFriendshipRequestArray(i));
                    //Log.d("qiwehjqwpe "," in display");

               }
           }); 
           
            
        
        }
        
        
        public void handler1(View v){
            final int position =listView1.getPositionForView((View) v.getParent());
            //Log.d("on click", "Title clicked, row %d"+position);
            final String str = ((TextView)((View)v.getParent()).findViewById(R.id.acceptfrndtxt)).getText().toString();
            //Log.d("on click",str);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                   HttpPost post = new HttpPost("http://"+url+"/owncloud/index.php/apps/friends/acceptfriendrequest");
                   HttpEntity entity;
                   
                   
                   final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                   params.add(new BasicNameValuePair("CURRENTUSER", username));
                   params.add(new BasicNameValuePair("ACCEPTFRIEND",str));
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
                                //friendadapter.remove(s);
                                friendadapter.add(s);
                                notifyDataChanged();
                                Log.d("rem ",str+" ");
                                Toast.makeText(AcceptFriendRequestsActivity.this, "You accepted friend request successfully", Toast.LENGTH_SHORT).show();
                       //stuff that updates ui

                           }
                       });
                        
                        }
                        /*runOnUiThread(new Runnable() {
                            public void run() {
                                listView.setAdapter(adapter);
                                //holder.frndtxt.setText(sentFriendshipRequestArray(i));
                                //Log.d("qiwehjqwpe "," in display");

                           }
                       }); 
              
                    }*/
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
                }*/catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                   
                }
                };
                new Thread(runnable).start();
        }
        
        public void handler2(View v){
            final int position =listView1.getPositionForView((View) v.getParent());
            //Log.d("on click", "Title clicked, row %d"+position);
            final String str = ((TextView)((View)v.getParent()).findViewById(R.id.acceptfrndtxt)).getText().toString();
            //Log.d("on click",str);
            Log.d("handler2 ",str);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                   HttpPost post = new HttpPost("http://"+url+"/owncloud/index.php/apps/friends/removefriendrequest");
                   HttpEntity entity;
                   
                   
                   final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                   params.add(new BasicNameValuePair("CURRENTUSER", username));
                   params.add(new BasicNameValuePair("SENTORRECEIVED", "received"));
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
                                friendadapter.remove(s);
                                //notifyDataSetChanged();
                                Log.d("rem ",str+" ");
                                Toast.makeText(AcceptFriendRequestsActivity.this, "You removed friend request successfully", Toast.LENGTH_SHORT).show();
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
            Log.d("getView"," in getvi");
            //Log.d("getview","in getvi");
            holder = new friendRowholder();
            holder.frndPos = Objects.get(position);
            holder.acceptfrndButton = (Button)findViewById(R.id.acceptbtn);
            //holder.acceptfrndButton.setOnClickListener(handler1);
            holder.removefrndButton = (Button)findViewById(R.id.removebtn);
            holder.frndtxt = (TextView)row.findViewById(R.id.acceptfrndtxt);
            
            if(row.getTag()==null){
            row.setTag(holder);
            //Log.d("jarey",jary.toString());
            /*for(int i =0;i<jary.length();i++){
                Log.d("********************************","v"+i);
                 holder.frndtxt.setText(jary.toString());*/
            String text = receivedFriendshipRequestArray.get(position);
            Log.d("ine pos",text);
            holder.frndtxt.setText(text);
           // adapter.addAll(sentFriendshipRequestArray);
            //holder.frndtxt.setText();
           //i++;
            return row;
            } else{
                holder.frndtxt.setText("You have no pending friend requests");
                return null;
            }
            //Log.d("getView","after holder creation");
            
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

}
