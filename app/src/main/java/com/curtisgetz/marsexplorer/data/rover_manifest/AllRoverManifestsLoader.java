package com.curtisgetz.marsexplorer.data.rover_manifest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.curtisgetz.marsexplorer.utils.IndexUtils;
import com.curtisgetz.marsexplorer.utils.JsonUtils;
import com.curtisgetz.marsexplorer.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AllRoverManifestsLoader extends AsyncTaskLoader<List<RoverManifest>>{

    private final static String TAG = AllRoverManifestsLoader.class.getSimpleName();

    private final static int[] mRoverIndices = {IndexUtils.CURIOSITY_ROVER_INDEX,
            IndexUtils.OPPORTUNITY_ROVER_INDEX, IndexUtils.SPIRIT_ROVER_INDEX};



    public AllRoverManifestsLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public List<RoverManifest> loadInBackground() {
        List<RoverManifest> roverManifests = new ArrayList<>();

        for(int rover : mRoverIndices){
            try{
                URL manifestUrl = NetworkUtils.buildManifestUrl(getContext(), rover);
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(manifestUrl);
                RoverManifest manifest = JsonUtils.getRoverManifest(rover, jsonResponse);
                roverManifests.add(manifest);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return roverManifests;
    }

    @Override
    public void deliverResult(@Nullable List<RoverManifest> data) {
        super.deliverResult(data);
    }
}
