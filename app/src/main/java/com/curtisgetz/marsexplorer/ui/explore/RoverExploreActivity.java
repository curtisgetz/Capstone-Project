package com.curtisgetz.marsexplorer.ui.explore;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.RoverScience;
import com.curtisgetz.marsexplorer.data.rover_explore.RoverExploreCategory;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;
import com.curtisgetz.marsexplorer.ui.MarsBaseActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.ExploreDetailActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.favorites.FavoritePhotosFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.mars_facts.MarsFactsFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.FullPhotoFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.FullPhotoPagerFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.RoverPhotosFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_science.RoverScienceFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.tweets.TweetsFragment;
import com.curtisgetz.marsexplorer.ui.info.InfoDialogFragment;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.InformationUtils;
import com.curtisgetz.marsexplorer.utils.JsonUtils;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoverExploreActivity extends MarsBaseActivity implements
        RoverCategoryAdapter.CategoryClickListener, FullPhotoPagerFragment.FullPhotoPagerInteraction  {

    private final static String TAG = RoverExploreActivity.class.getSimpleName();

    private RoverCategoryAdapter mAdapter;
    private int mRoverIndex;
    //todo set up two pane logic
    private boolean isTwoPane;
    private RoverManifestViewModel mViewModel;
    private static boolean hasDownloadedManifests = false;


    @BindView(R.id.rover_options_recycler) RecyclerView mCategoryRecycler;
    @BindView(R.id.explore_master_title_text) TextView mTitleText;
    @BindView(R.id.mission_status_text)TextView mMissionStatusTv;
    @BindView(R.id.launch_date_text) TextView mLaunchTv;
    @BindView(R.id.landing_date_text)TextView mLandingTv;
    @BindView(R.id.sol_range_text) TextView mSolRangeTv;
    @BindView(R.id.manifest_loading) ProgressBar mManifestProgress;
    @BindView(R.id.explore_detail_coordinatorlayout) CoordinatorLayout mCoordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_explore);
        ButterKnife.bind(this);

        isTwoPane = (findViewById(R.id.rover_explore_sw600land) != null);

        mAdapter = new RoverCategoryAdapter(getLayoutInflater(), this);
        showManifestProgress();


        mCategoryRecycler.setLayoutManager(getLayoutManager());
        mCategoryRecycler.setAdapter(mAdapter);

        if(savedInstanceState == null){
            Intent intent = getIntent();
            if (intent == null) {
                finish();
            }else {
                mRoverIndex = intent.getIntExtra(getString(R.string.explore_extra),
                        HelperUtils.CURIOSITY_ROVER_INDEX);
            }
        }else {
            mRoverIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key),
                    HelperUtils.CURIOSITY_ROVER_INDEX);
        }


        RoverManifestVMFactory factory = new RoverManifestVMFactory(mRoverIndex, getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(RoverManifestViewModel.class);
        mViewModel.getManifest().observe(this, new Observer<RoverManifest>() {
            @Override
            public void onChanged(@Nullable RoverManifest roverManifest) {
                populateManifestUI();
            }
        });
        populateUI(mRoverIndex);
        if(!hasDownloadedManifests) {
            //don't download new manifest every time. Data doesn't change often.
            mViewModel.downloadNewManifests(getApplicationContext());
            hasDownloadedManifests = true;
        }
    }

    private void populateUI(int roverIndex){
        String roverName = HelperUtils.getRoverNameByIndex(this, roverIndex);
        String titleString = roverName + " " + getString(R.string.rover_string);
        mTitleText.setText(titleString);
        List<RoverExploreCategory> roverExploreCategories = HelperUtils.getRoverCategories(this, roverIndex);
        mAdapter.setData(roverExploreCategories);
    }

    private LinearLayoutManager getLayoutManager(){
        int layoutOrientation;
        if (isTwoPane) {
            layoutOrientation = LinearLayoutManager.VERTICAL;
        } else {
            layoutOrientation = LinearLayoutManager.HORIZONTAL;
        }
        //set to HORIZONAL all the time, testing  todo
        layoutOrientation = LinearLayoutManager.HORIZONTAL;
        return new LinearLayoutManager(this, layoutOrientation, false);
    }
    @Override
    public void onCategoryClick(int catIndex) {

        if(isTwoPane){
            Fragment fragment;
            switch (catIndex){
                case HelperUtils.ROVER_SCIENCE_CAT_INDEX:
                case HelperUtils.ROVER_INFO_CAT_INDEX:
                    fragment = RoverScienceFragment.newInstance(this, mRoverIndex, catIndex);
                    break;
                case HelperUtils.MARS_FAVORITES_CAT_INDEX:
                    fragment = FavoritePhotosFragment.newInstance();
                    break;
                case HelperUtils.ROVER_TWEETS_CAT_INDEX:
                    fragment = TweetsFragment.newInstance();
                    break;
                default:
                    return;
            }
            startDetailFragment(fragment);
        }else {
            switch (catIndex){
                // don't allow click on photo category,   only the sol buttons
                case HelperUtils.ROVER_PICTURES_CAT_INDEX:
                    return;

                case HelperUtils.ROVER_TWEETS_CAT_INDEX:
                default:
                    //Send explore index and rover index to ExploreActivity
                    Intent intent = new Intent(getApplicationContext(), ExploreDetailActivity.class );
                    intent.putExtra(getString(R.string.explore_index_extra_key), catIndex);
                    intent.putExtra(getString(R.string.rover_index_extra), mRoverIndex);
                    intent.putExtra(getString(R.string.parent_activity_tag_extra), RoverExploreActivity.class.getSimpleName());
                    startActivity(intent);
            }
        }
    }



    @Override
    public void onSolSearchClick(String solNumber, int catIndex) {
        //validate sol input is in range then start ExploreDetailActivity
        String validatedSol = mViewModel.validateSolInRange(solNumber);
        if(isTwoPane){
            RoverPhotosFragment photosFragment = RoverPhotosFragment
                    .newInstance(this, mRoverIndex, solNumber);
            startDetailFragment(photosFragment);
        }else {
            startExploreDetailActivity(validatedSol, catIndex);
        }
    }

    @Override
    public void onRandomSolClick(int catIndex) {
        if(isTwoPane){
            RoverPhotosFragment photosFragment = RoverPhotosFragment
                    .newInstance(this, mRoverIndex, mViewModel.getRandomSol());
            startDetailFragment(photosFragment);
        }else {
            startExploreDetailActivity(mViewModel.getRandomSol(), catIndex);
        }
    }

    private void startDetailFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.explore_detail_container, fragment).commit();
    }

    private void startExploreDetailActivity(String solNumber, int catIndex){
        if(isNetworkAvailable()) {
                Intent intent = new Intent(getApplicationContext(), ExploreDetailActivity.class);
                intent.putExtra(getString(R.string.explore_index_extra_key), catIndex);
                intent.putExtra(getString(R.string.rover_index_extra), mRoverIndex);
                intent.putExtra(getString(R.string.sol_number_extra_key), solNumber);
                //add extra so ExploreDetailActivity knows it's parent and can enable up navigation
                intent.putExtra(getString(R.string.parent_activity_tag_extra), this.getClass().getSimpleName());
                startActivity(intent);
        }
    }


    @OnClick(R.id.sol_info_clickbox)
    public void solInfo(){
        InfoDialogFragment infoDialogFragment = InfoDialogFragment.newInstance(this, InformationUtils.SOL_RANGE_INFO);
        infoDialogFragment.show(getSupportFragmentManager(), InfoDialogFragment.class.getSimpleName());
    }

    private void populateManifestUI(){
        RoverManifest roverManifest = mViewModel.getManifest().getValue();
        if(roverManifest == null)return;
        hideManifestProgress();
        mMissionStatusTv.setText(HelperUtils.capitalizeFirstLetter(roverManifest.getStatus()));
        mLandingTv.setText(JsonUtils.getDateString(roverManifest.getLandingDate()));
        mLaunchTv.setText(JsonUtils.getDateString(roverManifest.getLaunchDate()));
        mSolRangeTv.setText(roverManifest.getSolRange());
    }


    private void showManifestProgress(){
        mManifestProgress.setVisibility(View.VISIBLE);
    }

    private void hideManifestProgress(){
        mManifestProgress.setVisibility(View.GONE);
    }


    private boolean isNetworkAvailable()  {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if((networkInfo != null && networkInfo.isConnected())){
            return true;
        }else {
            final Snackbar snackbar = Snackbar.make(mCoordinatorLayout, R.string.internet_required, Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.snackbar_dismiss), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
            return false;
        }
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
            Log.d(TAG, "getRoverIndex is NOT NULL" + String.valueOf(photoFragment.getRover()));
            return photoFragment.getRover();
        } else {
            Log.d(TAG, "getRoverIndex is NULL");
            return -1;
        }
    }


}
