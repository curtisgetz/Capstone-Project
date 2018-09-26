/*

package com.curtisgetz.marsexplorer.data.rover_manifest;

import android.content.Context;
import android.os.AsyncTask;

import com.curtisgetz.marsexplorer.utils.JsonUtils;
import com.curtisgetz.marsexplorer.utils.NetworkUtils;
import com.firebase.jobdispatcher.Constraint;

import java.net.URL;
import java.util.List;

public class AllRoverManifestAsynTask extends AsyncTask<Integer, Void, List<RoverManifest>>{


    public AllRoverManifestAsynTask() {

    }

    @Override
    protected List<RoverManifest> doInBackground(Integer... integers) {
        for(int rover : integers){
            try{
                URL manifestUrl = NetworkUtils.buildManifestUrl(, rover);
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(manifestUrl);
                RoverManifest manifest = JsonUtils.getRoverManifest(rover, jsonResponse);
                roverManifests.add(manifest);
            }catch (Exception e){
                e.printStackTrace();
            }
        }



        return null;
    }
}

*/
