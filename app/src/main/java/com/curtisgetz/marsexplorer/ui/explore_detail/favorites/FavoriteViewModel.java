package com.curtisgetz.marsexplorer.ui.explore_detail.favorites;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.curtisgetz.marsexplorer.data.MarsRepository;

import java.util.List;


//May need a better solution if number of favorite photos gets too high.
//Need to look into proper handling for large data sets rather than loading all favorites
//into view model
public class FavoriteViewModel extends AndroidViewModel {


    private MarsRepository mRepository;
    private LiveData<List<FavoriteImage>> mFavorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        mRepository = MarsRepository.getInstance(application);
        mFavorites = mRepository.getAllFavorites();
    }

    public LiveData<List<FavoriteImage>> getFavorites() {
        return mFavorites;
    }

    private void deleteFavoriteImage(FavoriteImage favoriteImage){
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

        if(mFavorites.getValue() == null) {
            return false;
        }
        for(FavoriteImage image : mFavorites.getValue()){
            if(url.equalsIgnoreCase(image.getImageUrl())){
                return true;
            }
        }
        return false;
    }

    public void deleteAllFavorites(){
        mRepository.deleteAllFavorites();
    }

}
