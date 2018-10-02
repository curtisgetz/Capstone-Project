package com.curtisgetz.marsexplorer.ui.explore;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import android.content.Context;
import android.util.Log;

import com.curtisgetz.marsexplorer.data.MarsRepository;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;


public class RoverManifestViewModel extends ViewModel {


    private final static String TAG = RoverManifestViewModel.class.getSimpleName();


    private LiveData<RoverManifest> mManifest;
    private MarsRepository mRepository;



    RoverManifestViewModel(int roverIndex, Application application){
        Log.i(TAG, "Loading RoverManifest from database by index");
       // this.mRepository = new MarsRepository(application);
        this.mRepository =  new MarsRepository(application );
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

    public void updateManifest(RoverManifest roverManifest){
        mRepository.updateManifest(roverManifest);
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "ViewModel Cleared");
        super.onCleared();
    }



}
