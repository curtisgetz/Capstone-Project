package com.curtisgetz.marsexplorer.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.Tweet;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;


@Database(entities = {MainExploreType.class, RoverManifest.class, FavoriteImage.class, Tweet.class},
        version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {



    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "marsdb";
    private static AppDataBase sInstance;

    public static AppDataBase getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,
                        AppDataBase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract MarsDao marsDao();

}
