package com.curtisgetz.marsexplorer.ui.explore_detail;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullPhotoFragment extends Fragment   {

    private final static String TAG = FullPhotoFragment.class.getSimpleName();


    private List<String> mUrls = new ArrayList<>();

    @Nullable
    @BindView(R.id.photo_share_fab) FloatingActionButton mShareFab;
    @BindView(R.id.full_photo_coordinator_layout) CoordinatorLayout mCoordinator;
    @BindView(R.id.photo_viewpager) ViewPager mViewPager;
    FullPhotoAdapter mAdapter;
    private int mStartingPos;
    private int mRoverIndex;
    private String mDateString;
    private boolean mIsFavorites;

    private FavoriteViewModel mViewModel;


    public static FullPhotoFragment newInstance(Context context, ArrayList<String> urls, int startingPos,
                                                int roverIndex, String dateString){

        Log.d(TAG, "Getting New Instance");
        FullPhotoFragment fragment = new FullPhotoFragment();
        Bundle bundle = new Bundle();
        //bundle.putBoolean(context.getString(R.string.is_favorites_key), isFavorites);
        bundle.putStringArrayList(context.getResources().getString(R.string.url_list_extra), urls);
        bundle.putInt(context.getString(R.string.clicked_photo_pos_extra), startingPos);
        bundle.putInt(context.getString(R.string.rover_index_extra), roverIndex);
        bundle.putString(context.getString(R.string.date_string_extra), dateString);
        fragment.setArguments(bundle);
        return fragment;
    }


    public FullPhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if(activity != null){
            mViewModel = ViewModelProviders.of(activity).get(FavoriteViewModel.class);
            mViewModel.getFavorites().observe(this, new Observer<List<FavoriteImage>>() {
                @Override
                public void onChanged(@Nullable List<FavoriteImage> favoriteImages) {
                    if(mIsFavorites){
                        if(favoriteImages == null) return;
                        List<String> urls = new ArrayList<>();
                        for(FavoriteImage image : favoriteImages){
                            urls.add(image.getImageUrl());
                        }


                        mAdapter.setData(urls);
                    }
                }
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null && getArguments() != null) {
            Bundle bundle = getArguments();
            mIsFavorites = bundle.getBoolean(getString(R.string.is_favorites_key), false);
            if(!mIsFavorites){

            }
            mRoverIndex = bundle.getInt(getString(R.string.rover_index_extra));
            mUrls = bundle.getStringArrayList(getString(R.string.url_list_extra));
            mStartingPos = bundle.getInt(getString(R.string.clicked_photo_pos_extra));
            mDateString = bundle.getString(getString(R.string.date_string_extra), getString(R.string.unknown_date));
        }else if(savedInstanceState != null) {
            mUrls = savedInstanceState.getStringArrayList(getString(R.string.url_list_saved));
            mStartingPos = savedInstanceState.getInt(getString(R.string.starting_pos_saved));
            mRoverIndex = savedInstanceState.getInt(getString(R.string.rover_index_saved_key));
            mDateString = savedInstanceState.getString(getString(R.string.date_string_extra));
        }
        //postponeEnterTransition();
       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));

        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_photo, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView ");


        mAdapter = new FullPhotoAdapter(getChildFragmentManager(), getActivity());
        mViewPager.setAdapter(mAdapter);
        mAdapter.setData(mUrls);
        mViewPager.setCurrentItem(mStartingPos);

        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
       // ArrayList<String> urlList = new ArrayList<>(mUrls);
        outState.putStringArrayList(getString(R.string.url_list_saved),  new ArrayList<>(mUrls));
        outState.putInt(getString(R.string.starting_pos_saved), mStartingPos);
        outState.putInt(getString(R.string.rover_index_saved_key), mRoverIndex);
    }


    @OnClick(R.id.photo_share_fab)
    public void onFabClick(){
        FragmentActivity activity = getActivity();
        if(activity == null) return;

        String roverName = HelperUtils.getRoverNameByIndex(getContext(), mRoverIndex);
        String shareMessage = getString(R.string.share_pic_text,
                roverName, mDateString, mUrls.get(mViewPager.getCurrentItem()));

        Intent intentToShare = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setText(shareMessage)
                .getIntent();
        if(intentToShare.resolveActivity(activity.getPackageManager())!= null){
            startActivity(intentToShare);
        }

    }

    public String getDate(){
        return mDateString;
    }

    public int getRover(){
        return mRoverIndex;
    }

    public void displaySnack(String message){
        final Snackbar snackbar = Snackbar.make(mCoordinator, message, Snackbar.LENGTH_LONG);
        snackbar.setAction(getString(R.string.snackbar_dismiss), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }



}
