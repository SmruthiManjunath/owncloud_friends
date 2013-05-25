/**
 * 
 */
package com.owncloud.android.test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.jayway.android.robotium.solo.Solo;
import com.owncloud.android.R;
import com.owncloud.android.ui.activity.YourFriendsActivity;
/**
 * @author Smruthi Manjunath
 *
 */
public class YourFriendsTest extends ActivityInstrumentationTestCase2<YourFriendsActivity> {

	Solo solo;
	String TAG = "Testing YourFriendsActivity";
	/**
	 * @param name
	 */
	public YourFriendsTest() {
		super(YourFriendsActivity.class);
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
		
		//ImageView imageview1 = (ImageView)solo.getView(R.id.);
		//solo.clickOnView(imageview1);
		//ViewGroup Tabs = (ViewGroup) solo.getView(android.R.id.tabs);
		//View yourfrndsView = Tabs.getChildAt(0);
		
		//solo.clickOnView(yourfrndsView);
		//solo.clickOnText("YourFriends");
		//solo.getCurrentViews();
		//Log.d(TAG,solo.getCurrentViews().toString());
		//solo.clickInList(0);
		ListView listview = (ListView)solo.getView(R.id.listViewAccept);
		View listElement = listview.getChildAt(0);
		String[] arr={"user8","user9","user10"};
		if(listview.getAdapter().getCount() == 0){
			assertTrue(this.solo.waitForText("You have no friends"));
			assertTrue(this.solo.searchText("You have no friends"));
		}
		
		for(int i=0;i<listview.getAdapter().getCount();i++){
			assertTrue(this.solo.waitForText(arr[i]));
			assertTrue(solo.searchText(arr[i]));
		}
		/*assertTrue(this.solo.waitForText("user29"));
		assertTrue(solo.searchText("user29"));
		//solo.goBack();
		//solo.clickInList(1);
		assertTrue(this.solo.waitForText("user32"));
		assertTrue(solo.searchText("user32"));*/
		//solo.goBack();
		//assertTrue(this.solo.waitForText("Successfully logged in!"));
		//assertTrue(solo.searchText("Successfully logged in!"));
		//assertTrue(this.solo.)
		//solo.clickOnView(imageview1);
		
		//assertTrue(this.solo.waitForText("You have been logged out"));
		//assertTrue(solo.searchText("You have been logged out"));
	}
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void testremoveBtn(){
		
		//Button removeBtn = (Button)solo.getView(R.id.removebtn1);
		ListView listview = (ListView)solo.getView(R.id.yourlistview);
		View listElement = listview.getChildAt(0);
		solo.clickOnView((listElement).findViewById(R.id.removebtn1));
		
		assertTrue(this.solo.waitForText("You removed friend successfully"));
		assertTrue(solo.searchText("You removed friend successfully"));
		
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
