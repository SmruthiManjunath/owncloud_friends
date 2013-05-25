package com.owncloud.android.ui.adapter;

import java.util.List;

import com.owncloud.android.R;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ShareFriendListAdapter extends BaseAdapter implements ListAdapter{

    List<String> Objects;
    Context context;
    int layoutResourceId;
    friendRowholder holder;
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
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
       
        holder.frndtxt = (TextView)row.findViewById(R.id.shareitm);
        //display();
        //if(row.getTag()== null){
            if(row.getTag()==null){
            row.setTag(holder);
        //Log.d("jarey",jary.toString());
        /*for(int i =0;i<jary.length();i++){
            Log.d("********************************","v"+i);
             holder.frndtxt.setText(jary.toString());*/
        //String text = friendNames.get(position);
       //Log.d("positi ",Integer.toString(position));
        //holder.frndtxt.setText(text);
        //((BaseAdapter)getListAdapter()).notifyDataSetChanged();
        return row;
            }
            else{
                return null;
            }
 
    }
    public class friendRowholder{
        String frndPos;
        TextView frndtxt;
        Button acceptfrndButton;
        Button removefrndButton;
        
        
    }
    
}
