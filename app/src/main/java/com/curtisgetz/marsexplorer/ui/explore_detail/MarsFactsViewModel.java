package com.curtisgetz.marsexplorer.ui.explore_detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.curtisgetz.marsexplorer.data.MarsFact;
import com.curtisgetz.marsexplorer.data.SingleLiveEvent;
import com.curtisgetz.marsexplorer.utils.RealtimeDatabaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class MarsFactsViewModel extends AndroidViewModel {

    private final static String TAG = MarsFactsViewModel.class.getSimpleName();

    /* DB node name is "facts"
    *First child in database corresponds to a day of the year.  Will try to load the fact
    * matching the current day of the year. If no matches try to load another fact.
    * Fragment will allow cycling through facts. Widget will show 'fact of the day'
    */

    private final static String DB_NODE_NAME = "facts";
    private int mQueryCount = 0;
    private final static int MAX_QUERY_COUNT = 365;
    private MutableLiveData<MarsFact> mFact = new MutableLiveData<>();
    private FirebaseDatabase mRealtimeDatabase;
    private DatabaseReference mFactNodeReference;
    private DatabaseReference mFactReference;
    private ValueEventListener mEventListener;
    private SingleLiveEvent<Boolean> mHitMaxQuery = new SingleLiveEvent<>();
    private String mFactsChildNode;

    public MarsFactsViewModel(@NonNull Application application) {
        super(application);
        //get the current day and assign to variable to use to query database
        mFactsChildNode = getCurrentDay();
        //get instance of Realtime DB
        mRealtimeDatabase = RealtimeDatabaseUtils.getDatabase();
        //get reference to facts node
        mFactNodeReference = mRealtimeDatabase.getReference(DB_NODE_NAME);
        //get fact child from realtimeDB (uses mFactsChildNode for the child node name
        // which corresponds to a day of the year)
        getFactChild();
    }

    private void getFactChild(){
        if(mEventListener != null) {
            mFactReference.removeEventListener(mEventListener);
        }

        mFactReference = mFactNodeReference.child(mFactsChildNode);
        Log.d(TAG, mFactNodeReference.getKey());
        mEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //reset query count on successful query
                    mQueryCount = 0;
                    mFact.postValue(dataSnapshot.getValue(MarsFact.class));
                }else {
                    //if no snapshot exists, get a random day and try loading again only until
                    // mQueryCount is below MAX QUERY COUNT. If query count is that high than there must be
                    // a problem. (can lower max query once there are more facts inDB)
                    if(mQueryCount >= MAX_QUERY_COUNT){
                        mHitMaxQuery.postValue(true);
                        return;
                    }
                    mFactsChildNode = getRandomDay();
                    getFactChild();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mQueryCount++;
        mFactReference.addValueEventListener(mEventListener);
    }


    public LiveData<MarsFact> getFact() {
        return mFact;
    }


    private String getCurrentDay(){
        return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
    }


    private String getRandomDay(){
        Random randomRand = new Random();
        //get random int between 1 and 365
        int randomDay = randomRand.nextInt(365) + 1;
        return String.valueOf(randomDay);

    }

    public void loadNewFact(){
        mFactsChildNode = getRandomDay();
        getFactChild();
    }

    public LiveData<Boolean> hitMaxQuery(){
        return mHitMaxQuery;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mFactReference.removeEventListener(mEventListener);

    }
}
