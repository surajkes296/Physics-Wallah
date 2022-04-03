package com.example.physicswallah;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class PhysicsLoader extends AsyncTaskLoader<List<PhysicsWallah>> {
    private String LOG_TAG=PhysicsLoader.class.getName();
    private String mUrl;
    public PhysicsLoader(Context context, String url) {
        super(context);
        mUrl=url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.v(LOG_TAG,"onStartLoading() exectued....");

    }
    /**
     * This is on a background thread.
     */
    @Override
    public List<PhysicsWallah> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response.
        List<PhysicsWallah> earthquakes = QueryUtils.fetchResponseFromInternet(mUrl);
        Log.v(LOG_TAG,"loadInBackground() exectued....");
        return earthquakes;
    }
}