
/*
 * 
 * 
 * VillageShare
 * 
 */
package com.owncloud.android.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AcceptFriendRequestsActivity extends Activity{
    
    @Override
    public void onCreate(Bundle SavedInstanceState){
        
        super.onCreate(SavedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("Accept Friend Request");
        setContentView(textView);
    }
}
