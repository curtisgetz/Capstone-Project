package com.curtisgetz.marsexplorer.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.room.AppDataBase;
import com.curtisgetz.marsexplorer.utils.AppExecutors;
import com.curtisgetz.marsexplorer.utils.IndexUtils;

import java.util.ArrayList;
import java.util.List;

public class ExploreTypeViewModel  extends AndroidViewModel {

    private final static String TAG = ExploreTypeViewModel.class.getSimpleName();

    private AppDataBase mDb;
    private LiveData<List<MainExploreType>> mExploreTypes;

    public ExploreTypeViewModel(@NonNull Application application) {
        super(application);
        AppDataBase dataBase = AppDataBase.getInstance(application);
        mDb = dataBase;
        mExploreTypes = dataBase.marsDao().loadAllExploreTypes();

    }


    public LiveData<List<MainExploreType>> getExploreTypes() {
        return mExploreTypes;
    }


    public void addExploreTypesToDB(Context context){
        //get explore types in IndexUtils.
        final List<MainExploreType> mainExploreTypeList = new ArrayList<>(IndexUtils.getAllExploreTypes(context));

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                for(MainExploreType type : mainExploreTypeList){
                    mDb.marsDao().insertExploreType(type);
                }
            }
        });
    }

}
