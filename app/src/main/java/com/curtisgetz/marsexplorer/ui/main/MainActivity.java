package com.curtisgetz.marsexplorer.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.MainExploreAdapter;
import com.curtisgetz.marsexplorer.data.room.AppDataBase;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifestJobService;
import com.curtisgetz.marsexplorer.ui.explore.RoverExploreActivity;
import com.curtisgetz.marsexplorer.utils.AppExecutors;
import com.curtisgetz.marsexplorer.utils.IndexUtils;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainExploreAdapter.ExploreClickListener {


//todo set to 2-4 times a day in final version

    private ArrayList<MainExploreType> mMainExploreTypeList;
    private MainExploreAdapter mAdapter;
    private AppDataBase mDb;
    private FirebaseJobDispatcher mJobDispatcher;

    @BindView(R.id.main_recyclerview)RecyclerView mExploreRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mDb = AppDataBase.getInstance(getApplicationContext());
        createMainList();
        mAdapter = new MainExploreAdapter(this);

        mExploreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExploreRecyclerView.setAdapter(mAdapter);

        getExploreTypes();

    }



////todo maybe change the way I add explore types
////------Add new explore types here------
//This is only for inserting new options, not updating current ones
    private void createMainList(){
        List<MainExploreType> mainExploreTypeList = new ArrayList<>(IndexUtils.getAllExploreTypes(this));
        addNewTypesToDB(mainExploreTypeList);
    }


    private void getExploreTypes(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAdapter.setData(mDb.marsDao().loadAllExploreTypes());
            }
        });
    }

    private void addNewTypesToDB(final List<MainExploreType> exploreTypes){
        if(exploreTypes == null) return;
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                for(MainExploreType type : exploreTypes){
                    mDb.marsDao().insertExploreType(type);
                }
            }
        });
    }

    @Override
    public void onExploreClick(int clickedPos) {
        Intent intent = new Intent(getApplicationContext(), RoverExploreActivity.class);
        MainExploreType exploreType = mAdapter.getExploreType(clickedPos);
        int exploreIndex = exploreType.getTypeIndex();
        if(exploreIndex == IndexUtils.MARS_EXPLORE_INDEX){
            Toast.makeText(this, "Explore Screen Coming Soon", Toast.LENGTH_SHORT).show();
        }else {
            intent.putExtra(getString(R.string.explore_extra), exploreIndex);
            startActivity(intent);
        }
    }



}
