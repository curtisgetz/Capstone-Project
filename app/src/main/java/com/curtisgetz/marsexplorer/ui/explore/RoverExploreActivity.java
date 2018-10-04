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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.rover_explore.RoverCategoryAdapter;
import com.curtisgetz.marsexplorer.data.rover_explore.RoverExploreCategory;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;
import com.curtisgetz.marsexplorer.ui.explore_detail.ExploreDetailActivity;
import com.curtisgetz.marsexplorer.ui.info.InfoDialogFragment;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.InformationUtils;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoverExploreActivity extends AppCompatActivity implements
        RoverCategoryAdapter.CategoryClickListener  {

    private final static String TAG = RoverExploreActivity.class.getSimpleName();

    private RoverCategoryAdapter mAdapter;
    private int mRoverIndex;
    //todo set up two pane logic
    private boolean isTwoPane;
    private boolean hasInetAccess;
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
        mAdapter = new RoverCategoryAdapter(getLayoutInflater(), this);
        showManifestProgress();
        int layoutOrientation;
        if (isTwoPane) {
            layoutOrientation = LinearLayoutManager.VERTICAL;
        } else {
            layoutOrientation = LinearLayoutManager.HORIZONTAL;
        }
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, layoutOrientation, false);

        mCategoryRecycler.setLayoutManager(layoutManager);
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
            Log.d(TAG, "Downloading Manifest via " + TAG);
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

    @Override
    public void onCategoryClick(int clickedPos) {

    }

    @Override
    public void onSolSearchClick(String solNumber) {
           String validatedSol = mViewModel.validateSolInRange(solNumber);
           startExploreDetailActivity(validatedSol);
    }

    @Override
    public void onRandomSolClick() {
        startExploreDetailActivity(mViewModel.getRandomSol());
    }


    private void startExploreDetailActivity(String solNumber){
        if(isNetworkAvailable()) {
            Intent intent = new Intent(this, ExploreDetailActivity.class);
            intent.putExtra(getString(R.string.rover_index_extra_key), mRoverIndex);
            intent.putExtra(getString(R.string.sol_number_extra_key), solNumber);
            startActivity(intent);
        }
    }




    @OnClick(R.id.sol_info_clickbox)
    public void solInfo(){
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.info_index_key), InformationUtils.SOL_RANGE_INFO);
        InfoDialogFragment infoDialogFragment = new InfoDialogFragment();
        infoDialogFragment.setArguments(bundle);
        infoDialogFragment.show(getSupportFragmentManager(), InfoDialogFragment.class.getSimpleName());
    }

    private void populateManifestUI(){
        Log.i(TAG, "Manifest UI Updated");
        RoverManifest roverManifest = mViewModel.getManifest().getValue();
        if(roverManifest == null)return;
        hideManifestProgress();
      // todo change date to friendly String
        mMissionStatusTv.setText(HelperUtils.capitalizeFirstLetter(roverManifest.getStatus()));
        mLandingTv.setText(roverManifest.getLandingDate());
        mLaunchTv.setText(roverManifest.getLaunchDate());
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
}
