package com.curtisgetz.marsexplorer.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;

import java.util.List;

@Dao
public interface MarsDao {

    //Main Explore Type
    @Query("SELECT * FROM exploretypes ORDER BY mTypeIndex")
    List<MainExploreType> loadAllExploreTypes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertExploreType(MainExploreType exploreType);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateExploreType(MainExploreType exploreType);


    // Rover Manifests
    @Query("SELECT * FROM roverManifest ORDER BY mRoverIndex")
    LiveData<List<RoverManifest>> loadAllRoverManifests();

    @Query("SELECT * FROM roverManifest WHERE mRoverIndex = :roverIndex")
    RoverManifest loadRoverManifestByIndex(int roverIndex);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRoverManifest(RoverManifest roverManifest);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRoverManifest(RoverManifest roverManifest);


}
