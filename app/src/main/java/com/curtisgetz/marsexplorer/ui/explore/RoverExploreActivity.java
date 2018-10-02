package com.curtisgetz.marsexplorer.ui.explore;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.room.AppDataBase;
import com.curtisgetz.marsexplorer.data.rover_explore.RoverCategoryAdapter;
import com.curtisgetz.marsexplorer.data.rover_explore.RoverExploreCategory;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;
import com.curtisgetz.marsexplorer.ui.explore_detail.ExploreDetailActivity;
import com.curtisgetz.marsexplorer.ui.info.InfoDialogFragment;
import com.curtisgetz.marsexplorer.utils.AppExecutors;
import com.curtisgetz.marsexplorer.utils.IndexUtils;
import com.curtisgetz.marsexplorer.utils.InformationUtils;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoverExploreActivity extends AppCompatActivity implements
        RoverCategoryAdapter.CategoryClickListener, RoverCategoryAdapter.SolClickListener {

    private final static String TAG = RoverExploreActivity.class.getSimpleName();

    private RoverCategoryAdapter mAdapter;
    private int mRoverIndex;
    //todo set up two pane logic
    private boolean isTwoPane;
    private RoverManifestViewModel mViewModel;
    private static boolean hasDownloadedManifests = false;
    private boolean toggle;

    @BindView(R.id.rover_options_recycler) RecyclerView mCategoryRecycler;
    @BindView(R.id.explore_master_title_text) TextView mTitleText;
    @BindView(R.id.mission_status_text)TextView mMissionStatusTv;
    @BindView(R.id.launch_date_text) TextView mLaunchTv;
    @BindView(R.id.landing_date_text)TextView mLandingTv;
    @BindView(R.id.sol_range_text) TextView mSolRangeTv;
    @BindView(R.id.manifest_loading) ProgressBar mManifestProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        ButterKnife.bind(this);
        mAdapter = new RoverCategoryAdapter(this, this);
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

        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }else {
            mRoverIndex = intent.getIntExtra(getString(R.string.explore_extra), -1);
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
               mViewModel.downloadNewManifests(getApplicationContext());
               hasDownloadedManifests = true;
            }
        }
    }

    private void populateUI(int roverIndex){
        String roverName = IndexUtils.getRoverNameByIndex(this, roverIndex);
        String titleString = roverName + " " + getString(R.string.rover_string);
        mTitleText.setText(titleString);
        List<RoverExploreCategory> roverExploreCategories = IndexUtils.getRoverCategories(this, roverIndex);
        mAdapter.setData(roverExploreCategories);
    }

    @Override
    public void onCategoryClick(int clickedPos) {

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
      // todo change date to String
        mMissionStatusTv.setText(IndexUtils.capitalizeFirstLetter(roverManifest.getStatus()));
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


    private boolean checkSolInput(String solInput){
        if(solInput.isEmpty()){
            return false;
        }else {
            try{
                Integer.parseInt(solInput);
                return true;
            }catch (NumberFormatException e){
                e.printStackTrace();
                return false;
            }
        }

    }


    @Override
    public void onSolButtonClick(String solNumber) {
        //confirm sol input is a number
        if(!checkSolInput(solNumber)){
            Toast.makeText(this, "Please Enter a valid number", Toast.LENGTH_SHORT).show();
        }else {
            String toast = "Button Clicked " + String.valueOf(solNumber);
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();

        }
        Intent intent = new Intent(this, ExploreDetailActivity.class);
        intent.putExtra("rover_index_extra", mRoverIndex);
        intent.putExtra("sol_number_extra", solNumber);
        startActivity(intent);

       /*if (!toggle) {
*
                RoverManifest newManifest = new RoverManifest(1, "Test", "11-15-1982",
                        "01-12-1984", "acTIve", "666", "09-11-2018", "2313");
                mViewModel.updateManifest(newManifest);
                toggle = true;
            } else {
                toggle = false;
                mViewModel.downloadNewManifests(getApplicationContext());
            }*/
    }


}
