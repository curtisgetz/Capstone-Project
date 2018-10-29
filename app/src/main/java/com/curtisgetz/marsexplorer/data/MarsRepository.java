package com.curtisgetz.marsexplorer.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.curtisgetz.marsexplorer.data.room.AppDataBase;
import com.curtisgetz.marsexplorer.data.room.MarsDao;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;
import com.curtisgetz.marsexplorer.utils.AppExecutors;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.JsonUtils;
import com.curtisgetz.marsexplorer.utils.NetworkUtils;

import java.net.URL;
import java.util.List;



public class MarsRepository {

    private final static String TAG = MarsRepository.class.getSimpleName();

    private MarsDao mMarsDao;
    private static MarsRepository sInstance;

    public static MarsRepository getInstance(Application application){
        if(sInstance == null){
            sInstance = new MarsRepository(application);
        }
        return sInstance;
    }


    private MarsRepository(Application application){
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
                for(int roverIndex : HelperUtils.ROVER_INDICES) {
                    try {
                        URL manifestUrl = NetworkUtils.buildManifestUrl(context, roverIndex);
                        String jsonResponse = NetworkUtils.getResponseFromHttpUrl(manifestUrl);
                        RoverManifest manifest = JsonUtils.getRoverManifest(roverIndex, jsonResponse);
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
        final MutableLiveData<Cameras> cameras = new MutableLiveData<>();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL solRequestUrl = NetworkUtils.buildPhotosUrl(context, index, sol);
                    String jsonResponse = NetworkUtils.getResponseFromHttpUrl(solRequestUrl);
                    cameras.postValue(JsonUtils.getCameraUrls(index, jsonResponse));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return cameras;
    }


    public LiveData<List<WeatherDetail>> getLatestWeather(final Context context){
        final MutableLiveData<List<WeatherDetail>> weatherDetail = new MutableLiveData<>();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try{
                    URL weatherUrl = NetworkUtils.buildWeatherUrl();
                    String jsonResponse = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
                    weatherDetail.postValue(JsonUtils.getWeatherDetail(context, jsonResponse));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return weatherDetail;
    }





    public void addExploreTypesToDB(final List<MainExploreType> exploreTypes){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.insertExploreTypeList(exploreTypes);
            }
        });
    }

    public void saveFavoritePhoto(final FavoriteImage image){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.insertFavoriteImage(image);
            }
        });
    }
    public LiveData<List<MainExploreType>> getAllExploreTypes(){
        return mMarsDao.loadAllExploreTypes();
    }


    public void deleteFavoriteImage(final FavoriteImage favoriteImage){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.deleteFavorite(favoriteImage);
            }
        });
    }

    public LiveData<List<FavoriteImage>> getAllFavorites(){
        return mMarsDao.loadAllFavorites();
    }

    public void deleteAllFavorites(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.deleteAllFavorites();
            }
        });
    }


    public void insertTweet(final Tweet tweet){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMarsDao.insertTweet(tweet);
            }
        });
    }




}
