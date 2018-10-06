package com.curtisgetz.marsexplorer.ui.explore_detail;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.Cameras;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoverPhotosFragment extends Fragment implements RoverPhotosAdapter.PhotoClickListener {

    private final static String TAG = RoverPhotosFragment.class.getSimpleName();

    private String mSol;
    private int mRoverIndex;
    private CamerasViewModel mViewModel;

    @BindView(R.id.photos_fhaz_recyclerview) RecyclerView mFhazRecyclerView;
    @BindView(R.id.photos_rhaz_recyclerview) RecyclerView mRhazRecyclerView;
    @BindView(R.id.photos_mast_recyclerview) RecyclerView mMastRecyclerView;
    @BindView(R.id.photos_chemcam_recyclerview) RecyclerView mChemcamRecyclerView;
    @BindView(R.id.photos_mahli_recyclerview) RecyclerView mMahliRecyclerView;
    @BindView(R.id.photos_mardi_recyclerview) RecyclerView mMardiRecyclerView;
    @BindView(R.id.photos_navcam_recyclerview) RecyclerView mNavcamRecyclerView;
    @BindView(R.id.photos_pancam_recyclerview) RecyclerView mPancamRecyclerView;
    @BindView(R.id.photos_minites_recyclerview) RecyclerView mMinitesRecyclerView;

    @BindView(R.id.photos_fhaz_label) TextView mFhazLabel;
    @BindView(R.id.photos_rhaz_label) TextView mRhazLabel;
    @BindView(R.id.photos_mast_label) TextView mMastLabel;
    @BindView(R.id.photos_chemcam_label) TextView mChemcamLabel;
    @BindView(R.id.photos_mahli_label) TextView mMahliLabel;
    @BindView(R.id.photos_mardi_label) TextView mMardiLabel;
    @BindView(R.id.photos_navcam_label) TextView mNavcamLabel;
    @BindView(R.id.photos_pancam_label) TextView mPancamLabel;
    @BindView(R.id.photos_minites_label) TextView mMinitesLabel;

    @BindView(R.id.rover_photos_main_progress) ProgressBar mMainProgress;
    @BindView(R.id.rover_photos_title) TextView mTitleText;

    private RoverPhotosAdapter mFhazAdapter;
    private RoverPhotosAdapter mRhazAdapter;
    private RoverPhotosAdapter mMastAdapter;
    private RoverPhotosAdapter mChemcamAdapter;
    private RoverPhotosAdapter mMahliAdapter;
    private RoverPhotosAdapter mMardiAdapter;
    private RoverPhotosAdapter mNavcamAdapter;
    private RoverPhotosAdapter mPancamAdapter;
    private RoverPhotosAdapter mMinitesAdapter;

    //TODO Try moving more logic into ViewModels


    //private RecyclerView.RecycledViewPool mViewPool;

    public RoverPhotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rover_photos, container, false);
        ButterKnife.bind(this, view);

        hideAllViews();
        showMainProgress();

        if(savedInstanceState == null){
            Bundle bundle = getArguments();
            mSol = bundle.getString(getString(R.string.sol_number_extra_key));
            mRoverIndex = bundle.getInt(getString(R.string.explore_index_extra_key));
        }else {
            mSol = savedInstanceState.getString(getString(R.string.sol_number_saved_key),
                    HelperUtils.DEFAULT_SOL_NUMBER);
            mRoverIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key),
                    HelperUtils.CURIOSITY_ROVER_INDEX);
        }

        setRoverTitle();
        CamerasVMFactory factory = new CamerasVMFactory(getActivity().getApplication(), mRoverIndex, mSol);
        mViewModel = ViewModelProviders.of(this, factory).get(CamerasViewModel.class);

        mViewModel.getCameras().observe(this, new Observer<Cameras>() {
            @Override
            public void onChanged(@Nullable Cameras cameras) {
                setupUI();
            }
        });


        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.sol_number_saved_key), mSol);
        outState.putInt(getString(R.string.rover_index_saved_key), mRoverIndex);
    }

    private void updateUI(){
        String message = mViewModel.getCameras().getValue().getMAHLI().get(0);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private LinearLayoutManager createLayoutManager(){
        return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    }


    private void setupUI(){
        //if camera has any images then setup adapter and show views.

        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_FHAZ_INDEX) != null){
            mFhazAdapter = new RoverPhotosAdapter(this);
            mFhazRecyclerView.setLayoutManager(createLayoutManager());
            mFhazRecyclerView.setAdapter(mFhazAdapter);
            mFhazAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_FHAZ_INDEX));
            mFhazRecyclerView.setVisibility(View.VISIBLE);
            mFhazLabel.setVisibility(View.VISIBLE);
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_RHAZ_INDEX) != null){
            mRhazAdapter = new RoverPhotosAdapter(this);
            mRhazRecyclerView.setLayoutManager(createLayoutManager());
            mRhazRecyclerView.setAdapter(mRhazAdapter);
            mRhazAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_RHAZ_INDEX));
            mRhazRecyclerView.setVisibility(View.VISIBLE);
            mRhazLabel.setVisibility(View.VISIBLE);
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MAST_INDEX) != null){
            mMastAdapter = new RoverPhotosAdapter(this);
            mMastRecyclerView.setLayoutManager(createLayoutManager());
            mMastRecyclerView.setAdapter(mMastAdapter);
            mMastAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MAST_INDEX));
            mMastRecyclerView.setVisibility(View.VISIBLE);
            mMastLabel.setVisibility(View.VISIBLE);
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_CHEMCAM_INDEX) != null){
            mChemcamAdapter = new RoverPhotosAdapter(this);
            mChemcamRecyclerView.setLayoutManager(createLayoutManager());
            mChemcamRecyclerView.setAdapter(mChemcamAdapter);
            mChemcamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_CHEMCAM_INDEX));
            mChemcamRecyclerView.setVisibility(View.VISIBLE);
            mChemcamLabel.setVisibility(View.VISIBLE);
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MAHLI_INDEX) != null){
            mMahliAdapter = new RoverPhotosAdapter(this);
            mMahliRecyclerView.setLayoutManager(createLayoutManager());
            mMahliRecyclerView.setAdapter(mMahliAdapter);
            mMahliAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MAHLI_INDEX));
            mMahliRecyclerView.setVisibility(View.VISIBLE);
            mMahliLabel.setVisibility(View.VISIBLE);

        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MARDI_INDEX) != null){
            mMardiAdapter = new RoverPhotosAdapter(this);
            mMardiRecyclerView.setLayoutManager(createLayoutManager());
            mMardiRecyclerView.setAdapter(mMardiAdapter);
            mMardiAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MARDI_INDEX));
            mMardiRecyclerView.setVisibility(View.VISIBLE);
            mMardiLabel.setVisibility(View.VISIBLE);
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_NAVCAM_INDEX) != null){
            mNavcamAdapter = new RoverPhotosAdapter(this);
            mNavcamRecyclerView.setLayoutManager(createLayoutManager());
            mNavcamRecyclerView.setAdapter(mNavcamAdapter);
            mNavcamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_NAVCAM_INDEX));
            mNavcamRecyclerView.setVisibility(View.VISIBLE);
            mNavcamLabel.setVisibility(View.VISIBLE);
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_PANCAM_INDEX) != null){
            mPancamAdapter = new RoverPhotosAdapter(this);
            mPancamRecyclerView.setLayoutManager(createLayoutManager());
            mPancamRecyclerView.setAdapter(mPancamAdapter);
            mPancamAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_PANCAM_INDEX));
            mPancamRecyclerView.setVisibility(View.VISIBLE);
            mPancamLabel.setVisibility(View.VISIBLE);
        }
        if(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MINITES_INDEX) != null){
            mMinitesAdapter = new RoverPhotosAdapter(this);
            mMinitesRecyclerView.setLayoutManager(createLayoutManager());
            mMinitesRecyclerView.setAdapter(mMinitesAdapter);
            mMinitesAdapter.setData(mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MINITES_INDEX));
            Log.d(TAG, mViewModel.getImageUrlsForCamera(HelperUtils.CAM_MINITES_INDEX).get(0));
            mMinitesRecyclerView.setVisibility(View.VISIBLE);
            mMinitesLabel.setVisibility(View.VISIBLE);
        }
        hideMainProgress();
        updateTitleText();
    }

    private void setRoverTitle(){
        String roverName = HelperUtils.getRoverNameByIndex(getContext(), mRoverIndex);
        mTitleText.setText(roverName);
    }

    private void updateTitleText(){
        String roverName = HelperUtils.getRoverNameByIndex(getContext(), mRoverIndex);
        Cameras cameras = mViewModel.getCameras().getValue();
        String earthDate = cameras.getEarthDate();
        String title = TextUtils.join(" - ", new String[] {roverName, mSol, earthDate});
        mTitleText.setText(title);
    }


    private void hideAllViews(){
        mFhazLabel.setVisibility(View.GONE);
        mFhazRecyclerView.setVisibility(View.GONE);
        mRhazLabel.setVisibility(View.GONE);
        mRhazRecyclerView.setVisibility(View.GONE);
        mMastLabel.setVisibility(View.GONE);
        mMastRecyclerView.setVisibility(View.GONE);
        mChemcamLabel.setVisibility(View.GONE);
        mChemcamRecyclerView.setVisibility(View.GONE);
        mMahliLabel.setVisibility(View.GONE);
        mMahliRecyclerView.setVisibility(View.GONE);
        mMardiLabel.setVisibility(View.GONE);
        mMardiRecyclerView.setVisibility(View.GONE);
        mNavcamLabel.setVisibility(View.GONE);
        mNavcamRecyclerView.setVisibility(View.GONE);
        mPancamLabel.setVisibility(View.GONE);
        mPancamRecyclerView.setVisibility(View.GONE);
        mMinitesLabel.setVisibility(View.GONE);
        mMinitesRecyclerView.setVisibility(View.GONE);
    }

    private void showAllViews(){
        mFhazLabel.setVisibility(View.VISIBLE);
        mFhazRecyclerView.setVisibility(View.VISIBLE);
        mRhazLabel.setVisibility(View.VISIBLE);
        mRhazRecyclerView.setVisibility(View.VISIBLE);
        mMastLabel.setVisibility(View.VISIBLE);
        mMastRecyclerView.setVisibility(View.VISIBLE);
        mChemcamLabel.setVisibility(View.VISIBLE);
        mChemcamRecyclerView.setVisibility(View.VISIBLE);
        mMahliLabel.setVisibility(View.VISIBLE);
        mMahliRecyclerView.setVisibility(View.VISIBLE);
        mMardiLabel.setVisibility(View.VISIBLE);
        mMardiRecyclerView.setVisibility(View.VISIBLE);
        mNavcamLabel.setVisibility(View.VISIBLE);
        mNavcamRecyclerView.setVisibility(View.VISIBLE);
        mPancamLabel.setVisibility(View.VISIBLE);
        mPancamRecyclerView.setVisibility(View.VISIBLE);
        mMinitesLabel.setVisibility(View.VISIBLE);
        mMinitesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideMainProgress(){
        mMainProgress.setVisibility(View.GONE);
    }

    private void showMainProgress(){
        mMainProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPhotoClick(List<String> url, View view, int clickedPos) {
        ArrayList<String> urls = new ArrayList<>(url);

        FullPhotoFragment photoFragment = new FullPhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(getString(R.string.url_list_extra),urls);
        bundle.putInt(getString(R.string.clicked_photo_pos_extra), clickedPos);
       // bundle.putString(getString(R.string.photo_url_extra), url);
        photoFragment.setArguments(bundle);
       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
.addSharedElement(view, ViewCompat.getTransitionName(view))
*/

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.explore_detail_container, photoFragment,
                        FullPhotoFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();



    }
}
