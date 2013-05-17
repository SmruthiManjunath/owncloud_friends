/*
 * 
 * 
 * VillageShare
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

import com.owncloud.android.AccountUtils;
import com.owncloud.android.R;

public class YourFriendsActivity extends Activity{
    
    Account accountname;
    ListView listView;
    friendlistArrayAdapter adapter;
    ArrayList<String> friendNames;
    JSONArray jary;
    @Override
    public void onCreate(Bundle SavedInstanceState){
        
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.your_friendstab);
        
        listView = (ListView)findViewById(R.id.yourlistview);
        //listView = (ListView)findViewById(R.id.listViewRequests);
        //tv = (TextView)findViewById(R.id.tv1);
        //Button addBtn = Button);
        //setContentView(addBtn);
        //arrayAdapter = new ArrayAdapter<String>(AddFriendsActivity.this,R.id.tv1,friendNames);
        friendNames = new ArrayList<String>();
        adapter = new friendlistArrayAdapter(this,R.layout.removeyourfriends,friendNames);
        
        accountname = AccountUtils.getCurrentOwnCloudAccount(getBaseContext());
        String vals[] = accountname.toString().split("[@=,]");
        String username = vals[1];
        final String url = vals[2];
        JSONObject obj1 = new JSONObject();
        final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("CURRENTUSER", username));
        Log.d("sdn object ",params.toString());
        
        
        //getTabWidget().getChildAt(1).setOnClickListener(this);
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
                    HttpEntity entityresponse = response.getEntity();
                    String jsonentity = EntityUtils.toString(entityresponse);
                    JSONObject obj = new JSONObject(jsonentity);
                    JSONObject obj1 = (JSONObject) obj.get("data");
                    Log.d("response tewo aewrwqer***********************************"," "+jsonentity);
                    Log.d("response tewo aewrwq"," "+obj1.get("friendships"));
                   
                    jary = obj1.getJSONArray("friendships");
                    //sentFriendshipRequestArray.addAll(jary);
                    for(int i = 0; i<jary.length();i++){//sentFriendshipRequestArray.size();i++)
                        //JSONObject jobj = jary.getJSONObject(i);
                       //Log.d("valu f",jary.getString(i));//Log.d("value f ",sentFriendshipRequestArray.get(i));
                        friendNames.add(jary.getString(i));
                    }
                    display();
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
    }
   
    void display(){
        runOnUiThread(new Runnable() {
           public void run() {
               listView.setAdapter(adapter);
               adapter.addAll(friendNames);
               Log.d("display","In displa");
      //stuff that updates ui
               //holder.frndtxt.setText(sentFriendshipRequestArray(i));
               //Log.d("qiwehjqwpe "," in display");

          }
      }); 
      
       
   
   }
    
    
    private class friendlistArrayAdapter extends ArrayAdapter<String>{
        
        int i=0;
        //HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
        List<String> Objects;
        Context context;
        int layoutResourceId;
        friendRowholder holder;
        public friendlistArrayAdapter(Context context,int layoutResourceId,List<String> Objects){
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


}
