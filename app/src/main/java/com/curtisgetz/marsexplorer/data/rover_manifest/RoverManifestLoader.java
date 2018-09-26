package com.curtisgetz.marsexplorer.data.rover_manifest;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.curtisgetz.marsexplorer.utils.JsonUtils;
import com.curtisgetz.marsexplorer.utils.NetworkUtils;

import java.net.URL;

public class RoverManifestLoader extends AsyncTaskLoader<RoverManifest>{

    private static final String TAG = RoverManifestLoader.class.getSimpleName();

    private int mRoverIndex;


    public RoverManifestLoader(Context context, int mRoverIndex) {
        super(context);
        this.mRoverIndex = mRoverIndex;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public RoverManifest loadInBackground() {
        Log.d(TAG, "Load In Background Running");
        RoverManifest roverManifest;
        try {
            URL manifestUrl = NetworkUtils.buildManifestUrl(getContext(), mRoverIndex);
            Log.i(TAG, String.valueOf(manifestUrl));
            String jsonResponse = NetworkUtils.getResponseFromHttpUrl(manifestUrl);
            Log.i(TAG, jsonResponse);
            roverManifest = JsonUtils.getRoverManifest(mRoverIndex, jsonResponse);
            if(roverManifest == null) Log.e(TAG, "RoverManifest Null");
            Log.i(TAG, roverManifest.getStatus());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return roverManifest;
    }






}
