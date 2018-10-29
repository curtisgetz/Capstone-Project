package com.curtisgetz.marsexplorer.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.Tweet;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;

import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;

@Dao
public interface MarsDao {

    //Main Explore Type
    @Query("SELECT * FROM exploretypes ORDER BY mTypeIndex")
    LiveData<List<MainExploreType>> loadAllExploreTypes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExploreType(MainExploreType exploreType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExploreTypeList(List<MainExploreType> exploreTypes);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateExploreType(MainExploreType exploreType);


    // Rover Manifests
    @Query("SELECT * FROM roverManifest ORDER BY mRoverIndex")
    LiveData<List<RoverManifest>> loadAllRoverManifests();

    @Query("SELECT * FROM roverManifest WHERE mRoverIndex = :roverIndex")
    LiveData<RoverManifest> loadRoverManifestByIndex(int roverIndex);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRoverManifest(RoverManifest roverManifest);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRoverManifest(RoverManifest roverManifest);


    //Favorite Photos

    @Query("SELECT * FROM favoriteimage ORDER BY mId")
    LiveData<List<FavoriteImage>> loadAllFavorites();

    @Query("SELECT * FROM favoriteimage WHERE mImageUrl == :url")
    LiveData<FavoriteImage> loadFavoriteByUrl(String url);

    @Query("SELECT * FROM favoriteimage WHERE mId == :id")
    LiveData<FavoriteImage> loadFavoriteById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavoriteImage(FavoriteImage favoriteImage);

    @Delete
    void deleteFavorite(FavoriteImage favoriteImage);

    @Query("DELETE FROM favoriteimage")
    void deleteAllFavorites();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTweet(Tweet tweet);


}
