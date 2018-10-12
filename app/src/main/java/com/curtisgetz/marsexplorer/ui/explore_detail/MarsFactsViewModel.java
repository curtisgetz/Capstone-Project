package com.curtisgetz.marsexplorer.ui.explore_detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.curtisgetz.marsexplorer.data.MarsFact;
import com.curtisgetz.marsexplorer.utils.RealtimeDatabaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class MarsFactsViewModel extends AndroidViewModel {

    private final static String TAG = MarsFactsViewModel.class.getSimpleName();
    private final static String DB_NODE_NAME = "facts";
    private MutableLiveData<MarsFact> mFact = new MutableLiveData<>();
    private LiveData<MarsFact> mObservableFact;
    private FirebaseDatabase mRealtimeDatabase;
    private DatabaseReference mFactNodeReference;
    private DatabaseReference mFactReference;
    private ValueEventListener mEventListener;

    public MarsFactsViewModel(@NonNull Application application) {
        super(application);
        mRealtimeDatabase = RealtimeDatabaseUtils.getDatabase();
        mFactNodeReference = mRealtimeDatabase.getReference(DB_NODE_NAME);
        mFactReference = mFactNodeReference.child(getCurrentDay());
        mEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.d(TAG, "onDataChanged snapshot exists");
                    mFact.postValue(dataSnapshot.getValue(MarsFact.class));
                }else {
                    Log.d(TAG, "onDataChanged snapshot does not exist");
                    //getRandomDay();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mFactReference.addValueEventListener(mEventListener);
//        mFactReference.addListenerForSingleValueEvent(singleEventListener);
    }

    public LiveData<MarsFact> getFact() {
        return mFact;
    }

    private String getCurrentDay(){
        String dayOfYear = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + 1);
        return dayOfYear;
    }

    private String getRandomDay(){
        //bound is exclusive so set to 366 to include 365
        int random = ThreadLocalRandom.current().nextInt(1, 366);
        return null;

    }

    public void getNewFact(){

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mFactReference.removeEventListener(mEventListener);

    }
}
