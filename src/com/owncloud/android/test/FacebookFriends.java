/**
 * VillageShare
 * 
 */
package com.owncloud.android.test;
import com.owncloud.android.R;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.ImageView;

import com.owncloud.android.ui.activity.FacebookSync;
import com.jayway.android.robotium.solo.Solo;
import junit.framework.TestCase;

/**
 * @author Smruthi Manjunath
 *
 */
public class FacebookFriends extends ActivityInstrumentationTestCase2<FacebookSync> {

	/**
	 * @param name
	 */
	Solo solo;
	String TAG = "Testing tryappTest for facebook login";
	public FacebookFriends(String name) {
		super(FacebookSync.class);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		Log.d(TAG,solo.toString());
	}
	public void testButtonClickBlackBox(){
		ImageView imageview1 = (ImageView)solo.getView(R.id.facebook_sync);
		solo.clickOnView(imageview1);
		
		//assertTrue(this.solo.waitForText("Successfully logged in!"));
		//assertTrue(solo.searchText("Successfully logged in!"));
		//assertTrue(this.solo.)
		//solo.clickOnView(imageview1);
		
		assertTrue(this.solo.waitForText("You have been logged out"));
		assertTrue(solo.searchText("You have been logged out"));
		
	}
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
