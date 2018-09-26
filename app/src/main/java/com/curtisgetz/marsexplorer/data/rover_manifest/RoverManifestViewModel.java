package com.curtisgetz.marsexplorer.data.rover_manifest;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import android.content.Context;
import android.util.Log;

import com.curtisgetz.marsexplorer.data.MarsRepository;



public class RoverManifestViewModel extends ViewModel {


    private final static String TAG = RoverManifestViewModel.class.getSimpleName();
    private LiveData<RoverManifest> mManifest;
    private MarsRepository mRepository;


    public RoverManifestViewModel(int roverIndex, Application application){
        Log.i(TAG, "Loading RoverManifest from database by index");
        this.mRepository = new MarsRepository(application);
        this.mManifest = mRepository.getRoverManifest(roverIndex);
    }

    public void setManifest(LiveData<RoverManifest> manifest){
        this.mManifest = manifest;
    }

    public LiveData<RoverManifest> getManifest() {
        Log.i(TAG, "Getting RoverManifest from ViewModel");
        return mManifest;
    }


    public void downloadNewManifests(Context context){
        Log.d(TAG, "Starting downloadNewManifests");
        mRepository.downloadManifestsFromNetwork(context);
    }



}
