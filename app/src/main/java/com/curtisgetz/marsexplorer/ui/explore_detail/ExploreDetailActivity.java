package com.curtisgetz.marsexplorer.ui.explore_detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.solver.widgets.Helper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_science.RoverScienceFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.tweets.TweetsFragment;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ExploreDetailActivity extends AppCompatActivity implements FullPhotoPagerFragment.FullPhotoPagerInteraction{

    private final static String TAG = ExploreDetailActivity.class.getSimpleName();

    private int mExploreCatIndex;
    private String mCurrentSol;
    private int mRoverIndex;
    private boolean showFavoriteMenu;

    @BindView(R.id.explore_detail_activity_coordinator)
    CoordinatorLayout mCoordinatorLayout;
   // @BindView(R.id.explore_activity_toolbar)
    //Toolbar mToolBar;


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

                mExploreCatIndex = intent.getIntExtra(getString(R.string.explore_index_extra_key), HelperUtils.MARS_WEATHER_CAT_INDEX);
                Log.d(TAG, String.valueOf(mExploreCatIndex));
                //create bundle for detail fragment
                Bundle bundle = new Bundle();

                switch (mExploreCatIndex){
                    case HelperUtils.MARS_WEATHER_CAT_INDEX:
                        //if Mars Weather was selected, create MarsWeatherFragment.
                        MarsWeatherFragment weatherFragment = new MarsWeatherFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.explore_detail_container, weatherFragment).commit();
                        break;
                    case HelperUtils.MARS_FACTS_CAT_INDEX:
                        //if Mars Facts was selected, create MarsFacts Fragment
                        MarsFactsFragment factsFragment = MarsFactsFragment.newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.explore_detail_container, factsFragment).commit();
                        break;
                    case HelperUtils.MARS_FAVORITES_CAT_INDEX:
                        //if Favorites was selected, create FavoritePhotoFragment
                        FavoritePhotosFragment photoFragment = FavoritePhotosFragment.newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.explore_detail_container, photoFragment).commit();
                        break;
                    //Info and science screens will use same fragment but will load different info in RoverScienceFragment
                    // based on the exploreIndex (as defined in HelperUtils.)
                    case HelperUtils.ROVER_INFO_CAT_INDEX:
                    case HelperUtils.ROVER_SCIENCE_CAT_INDEX:
                        mRoverIndex = intent.getIntExtra(getString(R.string.rover_index_extra), HelperUtils.CURIOSITY_ROVER_INDEX);
                        bundle.putInt(getString(R.string.rover_index_extra), mRoverIndex);
                        bundle.putInt(getString(R.string.explore_index_extra_key), mExploreCatIndex);
                        RoverScienceFragment scienceFragment = new RoverScienceFragment();
                        scienceFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.explore_detail_container, scienceFragment).commit();
                        break;

                    case HelperUtils.ROVER_PICTURES_CAT_INDEX:

                        mCurrentSol = intent.getStringExtra(getString(R.string.sol_number_extra_key));
                        mRoverIndex = intent.getIntExtra(getString(R.string.rover_index_extra), HelperUtils.CURIOSITY_ROVER_INDEX);
                        bundle.putInt(getString(R.string.rover_index_extra), mRoverIndex);
                        bundle.putString(getString(R.string.sol_number_extra_key), mCurrentSol);
                        RoverPhotosFragment roverPhotosFragment = RoverPhotosFragment.newInstance(this, mRoverIndex, mCurrentSol);

                        RoverPhotosFragment photosFragment = new RoverPhotosFragment();
                        photosFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.explore_detail_container, roverPhotosFragment).commit();
                        break;

                    case HelperUtils.ROVER_TWEETS_CAT_INDEX:
                        TweetsFragment tweetsFragment = TweetsFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.explore_detail_container,
                                tweetsFragment).commit();
                }

            }else {
                mExploreCatIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key), HelperUtils.MARS_WEATHER_CAT_INDEX);
                mCurrentSol = savedInstanceState.getString(getString(R.string.sol_number_saved_key), HelperUtils.DEFAULT_SOL_NUMBER );
                showFavoriteMenu = savedInstanceState.getBoolean(getString(R.string.fav_menu_boolean_saved_key), false);
                mRoverIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key), HelperUtils.CURIOSITY_ROVER_INDEX);
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.rover_index_saved_key), mExploreCatIndex);
        outState.putString(getString(R.string.sol_number_saved_key), mCurrentSol);
        outState.putBoolean(getString(R.string.fav_menu_boolean_saved_key), showFavoriteMenu);
        outState.putInt(getString(R.string.rover_index_saved_key), mRoverIndex);
    }


    @Override
    public void callDisplaySnack(String message) {
        //call display snack method on FullPhotoFragment. This will allow the coordinatorlayout
        //to handle the snack display and moving the FAB
        FullPhotoFragment photoFragment = (FullPhotoFragment) getSupportFragmentManager()
                .findFragmentByTag(FullPhotoFragment.class.getSimpleName());

        if (photoFragment != null) {
            photoFragment.displaySnack(message);
        }
    }

    @Override
    public String getDateString() {
        FullPhotoFragment photoFragment = (FullPhotoFragment) getSupportFragmentManager()
                .findFragmentByTag(FullPhotoFragment.class.getSimpleName());

        if (photoFragment != null) {
            return photoFragment.getDate();
        } else {
            return null;
        }
    }

    @Override
    public int getRoverIndex() {
        FullPhotoFragment photoFragment = (FullPhotoFragment) getSupportFragmentManager()
                .findFragmentByTag(FullPhotoFragment.class.getSimpleName());

        if (photoFragment != null) {
            return photoFragment.getRover();
        } else {
            return -1;
        }
    }

}
