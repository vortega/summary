package com.vortega.myml.summary.restclient;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * Created by vortega on 31/05/14.
 */
public class MlApiAsyncClient extends AsyncTask<String, Void, JSONObject> {

    AsyncTaskListener callbacks;

    public MlApiAsyncClient(MlRestClient wrapper) {
        this.callbacks = (AsyncTaskListener) wrapper;
    }

    public JSONObject doInBackground(String... itemStrs) {
        String apiUrl = itemStrs[0];
        HttpClient client = new DefaultHttpClient();

        HttpGet request = new HttpGet(apiUrl);

        // Get the response
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String resp = "";

        try {
            resp = client.execute(request, responseHandler);
        } catch (Exception e) {
            Log.e("AsyncTask", "Problema Search", e);
        }

        JSONObject json = new JSONObject(resp);

        return json;
    }

    protected void onPostExecute(String items) {
        super.onPostExecute(items);
        callbacks.processResponse(items);
    }
}