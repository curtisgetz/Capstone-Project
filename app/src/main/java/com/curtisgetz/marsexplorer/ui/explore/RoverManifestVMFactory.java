package com.curtisgetz.marsexplorer.ui.explore;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.curtisgetz.marsexplorer.ui.explore.RoverManifestViewModel;

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
