package com.curtisgetz.marsexplorer.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.curtisgetz.marsexplorer.data.room.AppDataBase;
import com.curtisgetz.marsexplorer.data.room.MarsDao;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;
import com.curtisgetz.marsexplorer.utils.AppExecutors;
import com.curtisgetz.marsexplorer.utils.IndexUtils;
import com.curtisgetz.marsexplorer.utils.JsonUtils;
import com.curtisgetz.marsexplorer.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MarsRepository {

    private MarsDao mMarsDao;
    private LiveData<List<RoverManifest>> mAllManifest;
    public MutableLiveData<RoverManifest> mRoverManifest = new MutableLiveData<>();
    private final static int[] ROVER_INDICES = IndexUtils.ROVER_INDICES;

    public MarsRepository(Application application){
        AppDataBase dataBase = AppDataBase.getInstance(application);
        mMarsDao = dataBase.marsDao();
        mAllManifest = mMarsDao.loadAllRoverManifests();
    }

    public LiveData<List<RoverManifest>> getAllManifest() {
        return mAllManifest;
    }

    public MutableLiveData<RoverManifest> getRoverManifest(final int roverIndex){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mRoverManifest.postValue(mMarsDao.loadRoverManifestByIndex(roverIndex));
            }
        });
        return mRoverManifest;
    }


    public void insertManifest(final RoverManifest roverManifest){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.insertRoverManifest(roverManifest);
            }
        });
    }

    public void updateManifest(final RoverManifest roverManifest){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.updateRoverManifest(roverManifest);
            }
        });
    }

    public void downloadManifestsFromNetwork(final Context context){
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                for(int roverIndex : ROVER_INDICES) {
                    try {
                        URL manifestUrl = NetworkUtils.buildManifestUrl(context, roverIndex);
                        String jsonResponse = NetworkUtils.getResponseFromHttpUrl(manifestUrl);
                        RoverManifest manifest = JsonUtils.getRoverManifest(roverIndex, jsonResponse);
                        Log.d(TAG, "Adding Manifest To Database");
                         mMarsDao.insertRoverManifest(manifest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
