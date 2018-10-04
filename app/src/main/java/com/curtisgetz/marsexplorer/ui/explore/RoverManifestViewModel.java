package com.curtisgetz.marsexplorer.ui.explore;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import android.content.Context;
import android.util.Log;

import com.curtisgetz.marsexplorer.data.MarsRepository;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.concurrent.ThreadLocalRandom;


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


    public String validateSolInRange(String solInput){
        if(mManifest.getValue() == null) return HelperUtils.DEFAULT_SOL_NUMBER;
        int solInputNumber;
        try {
            solInputNumber = Integer.parseInt(solInput);
        }catch (NumberFormatException e){
            return HelperUtils.DEFAULT_SOL_NUMBER;
        }
        int maxSol = mManifest.getValue().getMaxSolint();
        int minSol = mManifest.getValue().getMinSolInt();
        if(solInputNumber < minSol){
            //if entered sol is lower than min, set it to the min
            return String.valueOf(minSol);
        }else if(solInputNumber > maxSol){
            //if entered sol is greater than max sol, set to max
            return String.valueOf(maxSol);
        }else {
            //if sol is in range, return the same sol back
            return solInput;
        }
    }

    public String getRandomSol(){
        if(mManifest.getValue() == null) return HelperUtils.DEFAULT_SOL_NUMBER;
        int minSol = mManifest.getValue().getMinSolInt();
        int maxSol = mManifest.getValue().getMaxSolint();
        int random = ThreadLocalRandom.current().nextInt((minSol), (maxSol+1));
        return String.valueOf(random);
    }




    @Override
    protected void onCleared() {
        Log.d(TAG, "ViewModel Cleared");
        super.onCleared();
    }



}
