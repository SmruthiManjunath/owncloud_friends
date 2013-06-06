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
 * @author Smruthi Manjunath
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
        
        friendListToPost = new JSONArray();
        // display the popup in the center
        //popUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
        
        listView = (ListView)findViewById(R.id.friendlist1);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //listView = (ListView) popupView.findViewById(R.id.friendlist1);
        Log.i(TAG,"heeeeeeeeeeeeeeeeeeeeeeeeeeeeeere");
        
        Intent i = getIntent();
        if(getIntent().hasExtra("friendName")== true){
            Log.d(TAG,"passed the arraylist");
        }
        //friendArray = getIntent().getExtras().getStringArrayList("friendName");
        friendArray = i.getStringExtra("friendName");
        friendList.add("FacebookSync with All");
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
        //facebookfriendadapter = new facebookfriendArrayAdapter(this,android.R.layout.simple_list_item_checked,friendList);
        listView.setAdapter(facebookfriendadapter);
        
        
      
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        finish();
    }
    public void onDone(View v){
        Log.d(TAG+"wekjw",friendListToPost.length()+" ");
        
        
        
        for(int i = 0;i<friendListToPost.length();i++){
            try {
                Log.d("Printign array contents before pushing to the server ",friendListToPost.getJSONObject(i).toString());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        PushToServerAsync pushdata = new PushToServerAsync();
        JSONArray jaryPush = new JSONArray();
        jaryPush.put(friendListToPost);
        //pushdata.execute(jaryPush);
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
        friendRowholder holder;
        //((CheckBox)((View)v.getParent()).findViewById(R.id.isSelected)).setChecked(true);
        boolean chk1 = ((CheckBox)((View)v.getParent()).findViewById(R.id.isSelected)).isChecked();
        Log.i(TAG,chk1+" ");
        if(chk1 == true){
            Log.i(TAG,"in check");
            //((CheckBox)((View)v.getParent()).findViewById(R.id.isSelected)).setChecked(true);
        }else{
            Log.i(TAG,"in uncheck");
            for(int i=0;i<friendListToPost.length();i++){
                try {
                    //Log.d("Deleteing th euser",friendListToPost.getJSONObject(i).toString());
                    //Log.d("Deleteing th hjuytrrtty",position+" "+jary.getJSONObject(position-1).toString());
                    
                    if(friendListToPost.getJSONObject(i).optString("id") == (jary.getJSONObject(position-1)).optString("id")){
                        Log.d("Deleteing th euser",friendListToPost.getJSONObject(i).toString());
                        
                        //JSONObject obj1 = friendListToPost.getJSONObject(i);
                        
                        //j1.
                        
                        //obj1.remove("id");
                        //obj1.remove("name");
                        //JSONArray names = JSONObject.names();
                        //obj1.getJSONArray(Integer.toString(i));
                    }
                    
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            ((CheckBox)((View)v.getParent()).findViewById(R.id.isSelected)).setChecked(false);
        }
        Log.d(TAG,position+" ");
        //friendListToPost = new JSONArray();
       
        if(chk1 == true && position == 0){
            //ViewHolder vHolder = null;
            int size = listView.getChildCount();
            
            for(int i = 1;i<size;i++){
                   //listView.setItemChecked(i, true);
                Log.d(TAG,i+" ");
                View v1 = facebookfriendadapter.getView(i,v,listView);
                   holder = (friendRowholder) v1.getTag();
                   //CheckBox ch = (CheckBox)v1.findViewById(R.id.isSelected);
                   //Log.i(TAG,ch.toString());
                   //ch.toggle();
                  // boolean checked = ch.isChecked();
                 // holder.checkbox=(CheckBox)v1.findViewById(R.id.isSelected);
                   
                   //holder.checkbox.setChecked(true);
                   /*if(checked== true){
                       Log.i(TAG,"in check");
                       ((CheckBox)v1.findViewById(R.id.isSelected)).setChecked(true);
                   }else{
                       Log.i(TAG,"in uncheck");
                       ((CheckBox)v1.findViewById(R.id.isSelected)).setChecked(false);
                   } */
                  
                   holder.checkbox.setChecked(true);
                   //Log.d(TAG, i+" "+ch.isChecked()+" ");
                   //ch.setChecked(!checked);
                   //ch.setEnabled(true);
                   //ch.setChecked(true);
                   
                  //listView.setItemChecked(i, true);
                  v1.setTag(holder);
                   //facebookfriendadapter.(i, true);
                  // ch.setTag(ch);
                //Log.d(TAG, i+" "+checked+" ");
               
            }
            facebookfriendadapter.notifyDataSetChanged();
            friendListToPost.put(jary);
        }else if(chk1== true){
            Log.d(TAG,position+" ");
            try {
                
                friendListToPost.put(jary.getJSONObject(position-1));//(jary.getJSONObject(position));
                Log.i(TAG,"position "+position+" i+ "+k);
                k++;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//jary.getJSONObject(position);
            
        }
        else if (chk1 == false){
            
            
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
            //Log.d("getView"," in getvi");
            holder = new friendRowholder();
            
            
            holder.frndPos = Objects.get(position);
            holder.checkbox = (CheckBox)row.findViewById(R.id.isSelected);
            holder.frndNametxt = (TextView)row.findViewById(R.id.friend_row);
            
            if(row.getTag()==null){
            row.setTag(holder);
            String text = friendList.get(position);
            //Log.d("ine pos",text);
            holder.frndNametxt.setText(text);
           
            return row;
            } else{
                Log.i(TAG,"here");
                if(holder.checkbox.isChecked() == true && position == 0){
                   for(int i = 0; i<facebookfriendadapter.getCount();i++){
                       listView.setItemChecked(i, true);
                   }
                    
                }
            return row;    
            }
            
           
        }
        
       
        
       
    }

    public class friendRowholder{
        String frndPos;
        TextView frndNametxt;
        CheckBox checkbox;
        
        
    }
    
    
}
