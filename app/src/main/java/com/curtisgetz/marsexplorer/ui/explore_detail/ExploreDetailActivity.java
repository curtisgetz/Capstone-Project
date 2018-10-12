package com.curtisgetz.marsexplorer.ui.explore_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MarsFact;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_science.RoverScienceFragment;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import butterknife.ButterKnife;

public class ExploreDetailActivity extends AppCompatActivity {

    private final static String TAG = ExploreDetailActivity.class.getSimpleName();

    private int mExploreIndex;
    private String mCurrentSol;
    private int mRoverIndex;


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


                mExploreIndex = intent.getIntExtra(getString(R.string.explore_index_extra_key), HelperUtils.CURIOSITY_ROVER_INDEX);
                Log.d(TAG, String.valueOf(mExploreIndex));
                Bundle bundle = new Bundle();

                switch (mExploreIndex){
                    case HelperUtils.MARS_WEATHER_CAT_INDEX:
                        MarsWeatherFragment weatherFragment = new MarsWeatherFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.explore_detail_container, weatherFragment).commit();
                        break;
                    case HelperUtils.MARS_FACTS_CAT_INDEX:
                        MarsFactsFragment factsFragment = new MarsFactsFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.explore_detail_container, factsFragment).commit();
                        break;
                    case HelperUtils.ROVER_SCIENCE_CAT_INDEX:
                        mRoverIndex = intent.getIntExtra(getString(R.string.rover_index_extra), HelperUtils.CURIOSITY_ROVER_INDEX);
                        bundle.putInt(getString(R.string.rover_index_extra), mRoverIndex);
                        RoverScienceFragment scienceFragment = new RoverScienceFragment();
                        scienceFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.explore_detail_container, scienceFragment).commit();
                        break;
                    case HelperUtils.CURIOSITY_ROVER_INDEX:
                  //  case HelperUtils.OPPORTUNITY_ROVER_INDEX:
                    case HelperUtils.ROVER_PICTURES_CAT_INDEX:
                        mCurrentSol = intent.getStringExtra(getString(R.string.sol_number_extra_key));
                        mRoverIndex = intent.getIntExtra(getString(R.string.rover_index_extra), HelperUtils.CURIOSITY_ROVER_INDEX);
                        bundle.putInt(getString(R.string.rover_index_extra), mRoverIndex);
                        bundle.putString(getString(R.string.sol_number_extra_key), mCurrentSol);
                        RoverPhotosFragment photosFragment = new RoverPhotosFragment();
                        photosFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.explore_detail_container, photosFragment).commit();
                        break;

                }




            }else {
                mExploreIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key), HelperUtils.CURIOSITY_ROVER_INDEX);
                mCurrentSol = savedInstanceState.getString(getString(R.string.sol_number_saved_key), HelperUtils.DEFAULT_SOL_NUMBER );
            }


        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.rover_index_saved_key), mExploreIndex);
        outState.putString(getString(R.string.sol_number_saved_key), mCurrentSol);
    }
}
