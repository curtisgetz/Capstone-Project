package com.curtisgetz.marsexplorer.ui.explore_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.utils.IndexUtils;

import butterknife.ButterKnife;

public class ExploreDetailActivity extends AppCompatActivity {

    private int mRoverIndex;
    private String mCurrentSol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent == null){
            finish();
        }else {
            if(savedInstanceState == null) {
                mRoverIndex = intent.getIntExtra(getString(R.string.rover_index_extra_key), IndexUtils.CURIOSITY_ROVER_INDEX);
                mCurrentSol = intent.getStringExtra(getString(R.string.sol_number_extra_key));

                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.rover_index_extra_key), mRoverIndex);
                bundle.putString(getString(R.string.sol_number_extra_key), mCurrentSol);
                RoverPhotosFragment photosFragment = new RoverPhotosFragment();
                photosFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.explore_detail_container, photosFragment).commit();

            }else {
                mRoverIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key), IndexUtils.CURIOSITY_ROVER_INDEX);
                mCurrentSol = savedInstanceState.getString(getString(R.string.sol_number_saved_key), IndexUtils.DEFAULT_SOL_NUMBER );
            }


        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.rover_index_saved_key), mRoverIndex);
        outState.putString(getString(R.string.sol_number_saved_key), mCurrentSol);
    }
}
