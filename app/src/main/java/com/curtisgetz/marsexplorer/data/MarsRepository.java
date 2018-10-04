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
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.JsonUtils;
import com.curtisgetz.marsexplorer.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MarsRepository {

    private MarsDao mMarsDao;

    private MutableLiveData<Cameras> mCameras;
    //private LiveData<RoverManifest> mRoverManifest;
    private final static int[] ROVER_INDICES = HelperUtils.ROVER_INDICES;

    public MarsRepository(Application application){
        AppDataBase dataBase = AppDataBase.getInstance(application);
        mMarsDao = dataBase.marsDao();

    }



    public LiveData<RoverManifest> getRoverManifest(int index){
        return mMarsDao.loadRoverManifestByIndex(index);
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
                        return;
                    }
                }
            }
        });
    }


    public LiveData<Cameras> getCameras(final Context context, final int index, final String sol){
        mCameras = new MutableLiveData<>();
        List<String> list = new ArrayList<>();
        list.add("Asd");
        list.add("unknown");
        list.add("23");
        //todo testing parsing erros
       // mCameras.postValue(new Cameras(2, list, list, list, null, list, null, list, list, list, null));
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL solRequestUrl = NetworkUtils.buildPhotosUrl(context, index, sol);
                    String jsonResponse = NetworkUtils.getResponseFromHttpUrl(solRequestUrl);
                    mCameras.postValue(JsonUtils.getCameraUrls(index, jsonResponse));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return mCameras;
    }





}
