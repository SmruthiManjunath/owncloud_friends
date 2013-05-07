package com.owncloud.android.ui.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.AsyncTask;

public class PostFriendsToServer  extends AsyncTask<JSONObject, Integer, Long> {

    private static final int TIMEOUT_MILLISEC = 10;

   
    @Override
    protected Long doInBackground(JSONObject... jobj) {
        // TODO Auto-generated method stub
        DefaultHttpClient client = new DefaultHttpClient();
        
        
        String url = FacebookSync.url;
        HttpPost post = new HttpPost(url);
        //StringEntity se = new StringEntity(jsonArray.toString(),HTTP.UTF_8);
       ArrayList<NameValuePair> ne = new ArrayList<NameValuePair>();
       ne.add(new NameValuePair("Friendlist",jobj.toString()));
        try {
            StringEntity entity = new StringEntity(ne.toString(),HTTP.UTF_8);
            post.setEntity(entity);
            //post.setEntity(new UrlEncodedFormEntity((List<? extends org.apache.http.NameValuePair>) ne));
            HttpResponse response = client.execute(post);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        return null;
    }


}
