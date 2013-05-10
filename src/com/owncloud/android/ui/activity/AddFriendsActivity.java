/*
 * VillageShare
 * 
 * 
 */

package com.owncloud.android.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.owncloud.android.R;

public class AddFriendsActivity extends Activity{
   
    
    EditText friendName;
    Button Add;
    
    @Override
    public void onCreate(Bundle SavedInstanceState){
        
        super.onCreate(SavedInstanceState);
        
        TextView textView = new TextView(this);
        textView.setText("Add Friends");
        
        
        //Button addBtn = Button);
        //setContentView(addBtn);
        setContentView(R.layout.add_friendstab);
        
        friendName = (EditText)findViewById(R.id.edttext1);
        Add = (Button)findViewById(R.id.btn1);
        
        
        
        
    }

}
