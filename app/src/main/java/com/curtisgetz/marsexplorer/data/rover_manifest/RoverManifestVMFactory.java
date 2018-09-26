package com.curtisgetz.marsexplorer.data.rover_manifest;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.curtisgetz.marsexplorer.data.room.AppDataBase;

public class RoverManifestVMFactory extends ViewModelProvider.NewInstanceFactory {

    //private final AppDataBase mDb;
//    private final Context mApplicationContext;
    private Application mApplication;
    private final int mRoverIndex;

    public RoverManifestVMFactory(int roverIndex, Application application){
        //this.mDb = dataBase;
        this.mApplication = application;
        this.mRoverIndex = roverIndex;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RoverManifestViewModel(mRoverIndex, mApplication);
    }
}
