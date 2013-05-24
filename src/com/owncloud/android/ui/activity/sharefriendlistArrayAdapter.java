package com.owncloud.android.ui.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.owncloud.android.R;


import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


 public class sharefriendlistArrayAdapter extends ArrayAdapter<String> {
         
         int i=0;
         //HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
         List<String> Objects;
         Context context;
         int layoutResourceId;
         friendRowholder holder;
         public sharefriendlistArrayAdapter(Context context,int layoutResourceId,List<String> Objects){
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
             //holder.removefrndButton = (Button)findViewById(R.id.removebtn1);
             holder.frndtxt = (TextView)row.findViewById(R.id.shareitm);
             //display();
             //if(row.getTag()== null){
                 if(row.getTag()==null){
                 row.setTag(holder);
             //Log.d("jarey",jary.toString());
             /*for(int i =0;i<jary.length();i++){
                 Log.d("********************************","v"+i);
                  holder.frndtxt.setText(jary.toString());*/
             String text = FileDisplayActivity.sharefriendList.get(position);
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
            //Button acceptfrndButton;
            //Button removefrndButton;
            
            
        }
         
        
     }
    

