package com.curtisgetz.marsexplorer.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.ui.explore.MarsExploreActivity;
import com.curtisgetz.marsexplorer.ui.explore.RoverExploreActivity;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainExploreAdapter.ExploreClickListener {


//todo set to 2-4 times a day in final version


    private MainExploreAdapter mAdapter;
    //private AppDataBase mDb;


    @BindView(R.id.main_recyclerview)RecyclerView mExploreRecyclerView;

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

        setupViewModel();

    }




    private void setupViewModel(){
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
        if(exploreIndex == HelperUtils.MARS_EXPLORE_INDEX){
        //    Toast.makeText(this, "Explore Screen Coming Soon", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(), MarsExploreActivity.class);
        }else {
            intent = new Intent(getApplicationContext(), RoverExploreActivity.class);
        }
        intent.putExtra(getString(R.string.explore_extra), exploreIndex);
        startActivity(intent);
    }



}
