package com.curtisgetz.marsexplorer.data.rover_manifest;



import android.content.Context;
import android.util.Log;

import com.curtisgetz.marsexplorer.data.room.AppDataBase;
import com.curtisgetz.marsexplorer.utils.AppExecutors;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.JsonUtils;
import com.curtisgetz.marsexplorer.utils.NetworkUtils;
import com.firebase.jobdispatcher.JobService;

import java.net.URL;




public class RoverManifestJobService extends JobService   {

    private final static String TAG = RoverManifestJobService.class.getSimpleName();
    private final static int[] mRoverIndices = HelperUtils.ROVER_INDICES;

    private AppDataBase mDb;


    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        Log.d(TAG, "Starting Job");
        mDb = AppDataBase.getInstance(this);

        final Context context = getApplicationContext();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                for (int roverIndex : mRoverIndices) {
                    try {
                        URL manifestUrl = NetworkUtils.buildManifestUrl(context, roverIndex);
                        String jsonResponse = NetworkUtils.getResponseFromHttpUrl(manifestUrl);
                        RoverManifest manifest = JsonUtils.getRoverManifest(roverIndex, jsonResponse);
                        Log.d(TAG, "Adding Manifest To Database");
                        mDb.marsDao().insertRoverManifest(manifest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                jobFinished(job, false);
            }
        });

        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }


}


