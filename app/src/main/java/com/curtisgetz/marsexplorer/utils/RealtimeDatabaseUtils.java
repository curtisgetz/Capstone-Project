package com.curtisgetz.marsexplorer.utils;



import com.curtisgetz.marsexplorer.data.MarsFact;
import com.google.firebase.database.FirebaseDatabase;

public class RealtimeDatabaseUtils {
//https://stackoverflow.com/questions/37448186/setpersistenceenabledtrue-crashes-app
    private static FirebaseDatabase mDatabase;

    private MarsFact mFact;

    public static FirebaseDatabase getDatabase(){
        if(mDatabase == null){
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

}
