package com.curtisgetz.marsexplorer.ui.explore_detail.favorites;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.curtisgetz.marsexplorer.data.MarsRepository;

import java.util.List;


//May need a better solution if number of favorite photos gets too high.
//Need to look into proper handling for large data sets rather than loading all favorites
//into view model
public class FavoriteViewModel extends AndroidViewModel {

    private final static String TAG = FavoriteViewModel.class.getSimpleName();


    private MarsRepository mRepository;
    private LiveData<List<FavoriteImage>> mFavorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "ViewModel Created");
        mRepository = MarsRepository.getInstance(application);
        mFavorites = mRepository.getAllFavorites();
    }

    public void loadFavorites(){
        Log.d(TAG, "Load Favorites in View Model");
//        mFavorites = mRepository.getAllFavorites();
    }
    public LiveData<List<FavoriteImage>> getFavorites() {
        Log.d(TAG, "Get Favorites from View Model");
        return mFavorites;
    }

    public void deleteFavoriteImage(FavoriteImage favoriteImage){
        mRepository.deleteFavoriteImage(favoriteImage);
    }
    public void saveFavoriteImage(String url, String dateString, int roverIndex){
        if(!isAlreadyFavorite(url)) {
            FavoriteImage favoriteImage = new FavoriteImage(url, dateString, roverIndex);
            mRepository.saveFavoritePhoto(favoriteImage);
        }
    }

    public void removeAlreadyFavorite(String url){
        if(mFavorites.getValue() == null){
            return;
        }
        for(FavoriteImage image : mFavorites.getValue()){
            if(url.equalsIgnoreCase(image.getImageUrl())){
                deleteFavoriteImage(image);
                return;
            }
        }
    }


    public boolean isAlreadyFavorite(String url){
        //if photo url is already in favorites, return TRUE
        //List<FavoriteImage> favoriteImages = mFavorites.getValue();
        Log.d(TAG, "Checking if photo is already a favorite");
        if(mFavorites.getValue() == null) {
            Log.d(TAG, "favs is null");
            return false;
        }

        Log.d(TAG, String.valueOf(mFavorites.getValue().size()));

        for(FavoriteImage image : mFavorites.getValue()){
            if(url.equalsIgnoreCase(image.getImageUrl())){
                Log.d(TAG, " TRUE _ " + url + " - " + image.getImageUrl());
                return true;
            }
        }
        return false;
    }

    public void deleteAllFavorites(){
        mRepository.deleteAllFavorites();
    }

    public String getDate(int position){
        if(mFavorites.getValue() == null) return null;
        return mFavorites.getValue().get(position).getDateString();
    }

    public int getRoverIndex(int position){
        if(mFavorites.getValue() == null) return -1;
        return mFavorites.getValue().get(position).getRoverIndex();
    }

}
