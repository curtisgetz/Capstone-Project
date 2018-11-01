package com.curtisgetz.marsexplorer.utils;

import android.app.Application;

import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifestJobService;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class MarsExplorerApp extends Application{

    private final static String TAG = MarsExplorerApp.class.getSimpleName();
    //todo change times after testing = set to 2-4 times a day in final version
    private FirebaseJobDispatcher mJobDispatcher;
    private final static int MANIFEST_JOB_MIN = 30;
    private final static int MANIFEST_JOB_MAX= 360;




    public MarsExplorerApp() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        scheduleManifestJob();
    }


    private void scheduleManifestJob(){
        //todo add setting for enable/disable jobschedule
        mJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        //keep manifests up to date periodically but will also update on demand
        Job manifestJob = mJobDispatcher.newJobBuilder()
                .setService(RoverManifestJobService.class)
                .setTag(RoverManifestJobService.class.getSimpleName())
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setTrigger(Trigger.executionWindow(MANIFEST_JOB_MIN, MANIFEST_JOB_MAX))
                .setReplaceCurrent(true)
                .setConstraints(Constraint.ON_ANY_NETWORK, Constraint.DEVICE_CHARGING,
                        Constraint.ON_UNMETERED_NETWORK)
                .build();
        mJobDispatcher.mustSchedule(manifestJob);

    }




    private void cancelJob(){
        mJobDispatcher.cancel(RoverManifestJobService.class.getSimpleName());
    }



}
