package com.curtisgetz.marsexplorer.ui.explore;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class RoverManifestVMFactory extends ViewModelProvider.NewInstanceFactory {

    private Application mApplication;
    private final int mRoverIndex;

    RoverManifestVMFactory(int roverIndex, Application application){
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
