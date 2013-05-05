/*
 * 
 * Village Shaer
 */

package com.owncloud.android.ui.activity;



import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.owncloud.android.R;

public class TabLayoutActivity extends TabActivity{
    
    TabHost mTabHost;
   
    @Override
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.friend_tab);
        
        
        mTabHost = getTabHost();
        TabHost.TabSpec spec1,spec2,spec3,spec4;
        Intent intent;
        intent = new Intent(this,YourFriendsActivity.class);
        spec1 = mTabHost.newTabSpec("YourFriends")
                         .setIndicator("Your Friends")
                         .setContent(intent);
        mTabHost.addTab(spec1);
        
        intent = new Intent(this,AddFriendsActivity.class);
        spec2 = mTabHost.newTabSpec("AddFriends")
                         .setIndicator("Add Friends")
                         .setContent(intent);
        mTabHost.addTab(spec2);
        
        intent = new Intent(this,AcceptFriendRequestsActivity.class);
        spec3 = mTabHost.newTabSpec("AcceptFriendsRequests")
                         .setIndicator("Accept Friend Requests")
                         .setContent(intent);
        mTabHost.addTab(spec3);
        
        intent = new Intent(this,FacebookSync.class);
        spec4 = mTabHost.newTabSpec("FriendsActivity")
                         .setIndicator("Friends Activity")
                         .setContent(intent);
        mTabHost.addTab(spec4);
        
       /*ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        */
        
        
       
        
        
    }
}
