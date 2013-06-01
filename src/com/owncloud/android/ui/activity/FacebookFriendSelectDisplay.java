/**
 * 
 */
package com.owncloud.android.ui.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.owncloud.android.R;


/**
 * @author smruthi
 *
 */
public class FacebookFriendSelectDisplay extends Activity{
    
    PopupWindow popupWindow; 
    String TAG = "FacebookFriendSelectDisplay";
    facebookfriendArrayAdapter facebookfriendadapter;
    Button bt;
    String friendArray;
    ListView listView;
    ArrayList<String> friendList = new ArrayList<String>();
    JSONArray friendListToPost;
    static int k = 0;
    Toast toast;
    JSONArray jary;
    
    //final layout btnOpenPopup = 
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.selectfacebookfriend);
        //popUp = new PopupWindow(this);
        
        /*LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
        View popupView = layoutInflater.inflate(R.layout.selectfacebookfriend, null);
        popupWindow = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
        popupWindow.setFocusable(true);
        */
        
        
        /*LayoutInflater inflater = (LayoutInflater) FacebookFriendSelectDisplay.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(R.layout.selectfacebookfriend,
                (ViewGroup) findViewById(R.id.selection_pop));*/
        // create a 300px width and 470px height PopupWindow
        //popUp = new PopupWindow(layout, 300, 470, true);
        friendListToPost = new JSONArray();
        // display the popup in the center
        //popUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
        listView = (ListView)findViewById(R.id.friendlist1);
        //listView = (ListView) popupView.findViewById(R.id.friendlist1);
        Log.i(TAG,"heeeeeeeeeeeeeeeeeeeeeeeeeeeeeere");
        
        Intent i = getIntent();
        if(getIntent().hasExtra("friendName")== true){
            Log.d(TAG,"passed the arraylist");
        }
        //friendArray = getIntent().getExtras().getStringArrayList("friendName");
        friendArray = i.getStringExtra("friendName");
        try {
            jary = new JSONArray(friendArray);
            JSONObject obj;
            for(int j =0;j<jary.length();j++){
                obj = jary.getJSONObject(j);
                friendList.add(obj.optString("name"));
                
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        Log.d(TAG,"herrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr "+friendList.size());
        if(friendList.size()!=0){
        facebookfriendadapter = new facebookfriendArrayAdapter(this,R.layout.friendrow,friendList);
        listView.setAdapter(facebookfriendadapter);
        
        
        /*findViewById(R.id.selection_pop).post(new Runnable() {
            public void run() {
                bt = (Button)findViewById(R.id.donebtn);
                bt.setVisibility(1);
              popupWindow.showAtLocation(findViewById(R.id.selection_pop), Gravity.CENTER, 0, 0);
              
            }
         }); */
       // popupWindow.showAtLocation(FacebookFriendSelectDisplay.this, 20, 20, 10);
        //popupWindow.showAsDropDown(btnOpenPopup, 20, -5);
        //popupWindow.showAtLocation(findViewById(R.id.), gravity, x, y)
        }
        //popUp.setContentView(R.layout.selectfacebookfriend);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        finish();
    }
    public void onDone(View v){
        Log.d(TAG+"wekjw",friendListToPost.length()+" ");
        PushToServerAsync pushdata = new PushToServerAsync();
        pushdata.execute(friendListToPost);
        onDestroy();
        //LocalActivityManager manager = getLocalActivityManager();
        //String currentTag = mTabHost.getCurrentTabTag();
        //Log.d(TAG+"jeewjqqjpqejpq ",currentTag);
        //Class<? extends Activity> currentClass = manager.getCurrentActivity().getClass();
        //getApplication().destroyActivity(FacebookFriendSelectDisplay.class.toString(), true);
        //FacebookFriendSelectDisplay.finish();
        //popupWindow.dismiss();
    }
    public void onCheckboxClicked(View v){
        final int position =listView.getPositionForView((View) v.getParent());
        
        ((CheckBox)((View)v.getParent()).findViewById(R.id.isSelected)).setChecked(true);
        boolean chk1 = ((CheckBox)((View)v.getParent()).findViewById(R.id.isSelected)).isChecked();
        //friendListToPost = new JSONArray();
        if(chk1 == true){
            try {
                     Log.d(TAG,position+" ");
                     friendListToPost.put(jary.getJSONObject(position));//jary.getJSONObject(position);
                     k++;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                
            }
        }
        
        
        
    }
    private class facebookfriendArrayAdapter extends ArrayAdapter<String>{
        
        int i=0;
        List<String> Objects;
        Context context;
        int layoutResourceId;
        friendRowholder holder;
        public facebookfriendArrayAdapter(Context context,int layoutResourceId,List<String> Objects){
            super(context,layoutResourceId,Objects);
            this.context = context;
            this.layoutResourceId = layoutResourceId;
            this.Objects = Objects;
            
        }
        
        @Override
        public View getView(int position, View convertView,ViewGroup parent){
            View row = convertView;
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId,parent,false);
            Log.d("getView"," in getvi");
            holder = new friendRowholder();
            holder.frndPos = Objects.get(position);
            holder.checkbox = (CheckBox)row.findViewById(R.id.isSelected);
            holder.frndNametxt = (TextView)row.findViewById(R.id.friend_row);
            
            if(row.getTag()==null){
            row.setTag(holder);
            String text = friendList.get(position);
            Log.d("ine pos",text);
            holder.frndNametxt.setText(text);
            return row;
            } else{
                holder.frndNametxt.setText("You have no pending friend requests");
                return null;
            }
           
        }
        
       public class friendRowholder{
           String frndPos;
           TextView frndNametxt;
           CheckBox checkbox;
           
           
       }
        
       
    }


    
    
}
