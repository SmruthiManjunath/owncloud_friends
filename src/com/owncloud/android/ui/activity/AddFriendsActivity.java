/*
 * VillageShare
 * 
 * 
 */

package com.owncloud.android.ui.activity;

import com.owncloud.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.TextView;

public class AddFriendsActivity extends Activity{
   
    @Override
    public void onCreate(Bundle SavedInstanceState){
        
        super.onCreate(SavedInstanceState);
        
        TextView textView = new TextView(this);
        textView.setText("Add Friends");
        
        
        //Button addBtn = Button);
        //setContentView(addBtn);
        setContentView(R.layout.add_friendstab);
    }

}
