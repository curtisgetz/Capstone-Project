package com.curtisgetz.marsexplorer.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.curtisgetz.marsexplorer.BuildConfig;
import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.ui.explore.MarsExploreActivity;
import com.curtisgetz.marsexplorer.ui.explore.RoverExploreActivity;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainExploreAdapter.ExploreClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();
    private MainExploreAdapter mAdapter;



    @BindView(R.id.main_recyclerview)
    RecyclerView mExploreRecyclerView;

    //Remote Config
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private static final String TEST_REMOTE_CONFIG_KEY = "remote_test";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //mDb = AppDataBase.getInstance(getApplicationContext());
        //createMainList();
        mAdapter = new MainExploreAdapter(this);

        mExploreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExploreRecyclerView.setAdapter(mAdapter);
        setupRemoteConfig();
        setupViewModel();

    }


    private void setupViewModel() {
        ExploreTypeViewModel viewModel = ViewModelProviders.of(this).get(ExploreTypeViewModel.class);
        viewModel.addExploreTypesToDB(getApplicationContext());
        viewModel.getExploreTypes().observe(this, new Observer<List<MainExploreType>>() {
            @Override
            public void onChanged(@Nullable List<MainExploreType> exploreTypes) {
                mAdapter.setData(exploreTypes);
            }
        });

    }


    @Override
    public void onExploreClick(int clickedPos) {

        MainExploreType exploreType = mAdapter.getExploreType(clickedPos);
        int exploreIndex = exploreType.getTypeIndex();
        Intent intent;
        if (exploreIndex == HelperUtils.MARS_EXPLORE_INDEX) {
            //    Toast.makeText(this, "Explore Screen Coming Soon", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(), MarsExploreActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), RoverExploreActivity.class);
        }
        intent.putExtra(getString(R.string.explore_extra), exploreIndex);
        startActivity(intent);
    }


    private void setupRemoteConfig() {
        mFirebaseRemoteConfig=FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        fetchRemoteConfig();

    }

    private void fetchRemoteConfig() {
        //Fetch any new Remote Config values
        Log.d(TAG, String.valueOf(mFirebaseRemoteConfig.getString(TEST_REMOTE_CONFIG_KEY)));
        long cacheExpiration = 3600; // 1 hour in seconds.
        // If your app is using developer mode, cacheExpiration is set to 0, so each fetch will
        // retrieve values from the service.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Fetch Succeeded", Toast.LENGTH_LONG).show();
                            mFirebaseRemoteConfig.activateFetched();
                        }else {
                            Toast.makeText(MainActivity.this, "Fetch Failed", Toast.LENGTH_LONG).show();
                        }
                        Log.d(TAG, String.valueOf(mFirebaseRemoteConfig.getString(TEST_REMOTE_CONFIG_KEY)));
                    }
                });
    }


}
