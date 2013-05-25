/**
 * 
 */
package com.owncloud.android.test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.jayway.android.robotium.solo.Solo;
import com.owncloud.android.R;
import com.owncloud.android.ui.activity.AcceptFriendRequestsActivity;
import junit.framework.TestCase;

/**
 * @author Smruthi Manjunath
 *
 */
public class AcceptFriendRequestsActivityTest extends ActivityInstrumentationTestCase2<AcceptFriendRequestsActivity> {

	/**
	 * @param name
	 */
	Solo solo;
	String TAG = "Testing AcceptFriendsActivity";
	public AcceptFriendRequestsActivityTest() {
		super(AcceptFriendRequestsActivity.class);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		Log.d(TAG,solo.toString());
	}

public void testingTheTab(){
		
	ListView listview = (ListView)solo.getView(R.id.listViewAccept);
	View listElement = listview.getChildAt(0);
	String[] arr={"user8","user9","user10"};
	if(listview.getAdapter().getCount() == 0){
		assertTrue(this.solo.waitForText("You have no pending friend requests"));
		assertTrue(this.solo.searchText("You have no pending friend requests"));
	}
	for(int i=0;i<listview.getAdapter().getCount();i++){
		assertTrue(this.solo.waitForText(arr[i]));
		assertTrue(solo.searchText(arr[i]));
	}
		
	}
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void testremoveBtn(){
		
		//Button removeBtn = (Button)solo.getView(R.id.removebtn1);
		ListView listview = (ListView)solo.getView(R.id.listViewAccept);
		View listElement = listview.getChildAt(0);
		//solo.clickOnView((listElement).findViewById(R.id.removebtn));
		//View num = (listElement).findViewById(R.id.removebtn);
		//solo.clickOnButton()
		solo.clickOnButton("Remove");
		assertTrue(this.solo.waitForText("You removed friend request successfully"));
		assertTrue(solo.searchText("You removed friend request successfully"));
		
	}
	
	public void testacceptBtn(){
		ListView listview = (ListView)solo.getView(R.id.listViewAccept);
		View listElement = listview.getChildAt(0);
		//solo.clickOnView((listElement).findViewById(R.id.acceptbtn));
		solo.clickOnButton("Accept");
		//solo.clickOnButton("Add Friend");
		//assertTrue(this.solo.waitForText("You requested bailey to add as friend"));
		assertTrue(this.solo.waitForText("You accepted friend request successfully"));
		assertTrue(solo.searchText("You accepted friend request successfully"));
		
		//int size = listview.getAdapter().getCount();
		
		
	}	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
