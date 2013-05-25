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
import com.owncloud.android.ui.activity.AddFriendsActivity;

/**
 * @author moment
 *
 */
public class AddFriendsActivityTest extends ActivityInstrumentationTestCase2<AddFriendsActivity>{

	/**
	 * @param name
	 */
	
	Solo solo;
	String TAG = "Testing YourFriendsActivity";
	public AddFriendsActivityTest() {
		super(AddFriendsActivity.class);
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
		
		
		/*assertTrue(this.solo.waitForText("hxhxc"));
		assertTrue(solo.searchText("hxhxc"));
		
		assertTrue(this.solo.waitForText("smri"));
		assertTrue(solo.searchText("smri"));
		
		assertTrue(this.solo.waitForText("samantha"));
		assertTrue(solo.searchText("samantha"));
		
		assertTrue(this.solo.waitForText("user35"));
		assertTrue(solo.searchText("user35"));*/
	
	ListView listview = (ListView)solo.getView(R.id.listview);
	View listElement = listview.getChildAt(0);
	String[] arr={"user35","ruby","bailey"};
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
		ListView listview = (ListView)solo.getView(R.id.listview);
		View listElement = listview.getChildAt(0);
		solo.clickOnView((listElement).findViewById(R.id.removebtn1));
		
		assertTrue(this.solo.waitForText("You removed friend successfully"));
		assertTrue(solo.searchText("You removed friend successfully"));
		
	}
	
	public void testaddBtn(){
		ListView listview = (ListView)solo.getView(R.id.listview);
		View listElement = listview.getChildAt(0);
		EditText frndName = (EditText)solo.getView(R.id.edttext1);
		solo.enterText(frndName, "bailey");
		solo.clickOnButton("Add Friend");
		assertTrue(this.solo.waitForText("You requested bailey to add as friend"));
		assertTrue(solo.searchText("You requested bailey to add as friend"));
		
		//int size = listview.getAdapter().getCount();
		assertTrue(this.solo.waitForText("bailey"));
		
	}
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
