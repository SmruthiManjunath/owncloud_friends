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
        spec4 = mTabHost.newTabSpec("Facebook")
                         .setIndicator("Facebook")
                         .setContent(intent);
        mTabHost.addTab(spec4);
        
        android.app.ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        setProgressBarIndeterminateVisibility(false);
        
       
        
        
    }
    public void onBackPressed(){
        finish();
    }
   @Override
   public boolean onCreateOptionsMenu(android.view.Menu menu){
       
       android.view.MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.main_menu, menu);
       
       //patchHiddenAccents(menu);
    return true;
       
   }
   
  @Override
  public boolean onOptionsItemSelected(android.view.MenuItem item){
      
      
      boolean retval = true;
      switch (item.getItemId()) {
          case R.id.action_sync_account: {
              
              
              //EditNameDialog dialog = EditNameDialog.newInstance(getString(R.string.uploader_info_dirname), "", this);
              //dialog.show(getSupportFragmentManager(), "createdirdialog");
              //break;
          }
          case android.R.id.home: {
              onBackPressed();
              break;
          }
   
      
  }
      return retval;
  }
}
