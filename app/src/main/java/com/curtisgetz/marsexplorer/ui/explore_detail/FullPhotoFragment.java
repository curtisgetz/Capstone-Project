package com.curtisgetz.marsexplorer.ui.explore_detail;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.curtisgetz.marsexplorer.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullPhotoFragment extends Fragment {

    private final static String TAG = FullPhotoFragment.class.getSimpleName();

    private String mPhotoUrl;
    private ArrayList<String> mUrls;

    @Nullable
    @BindView(R.id.photo_share_fab) FloatingActionButton mShareFab;

    @BindView(R.id.photo_viewpager) ViewPager mViewPager;
    FullPhotoAdapter mAdapter;
    private int mStartingPos;

    public FullPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        //mPhotoUrl = getArguments().getString("url_page");

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

        if(savedInstanceState == null) {
            Bundle bundle = getArguments();
           // mPhotoUrl = bundle.getString(getString(R.string.photo_url_extra));
            mUrls = bundle.getStringArrayList(getString(R.string.url_list_extra));
            mStartingPos = bundle.getInt(getString(R.string.clicked_photo_pos_extra));
        }else {
            mUrls = savedInstanceState.getStringArrayList(getString(R.string.url_list_saved));
            mStartingPos = savedInstanceState.getInt(getString(R.string.starting_pos_saved));
           // mPhotoUrl = savedInstanceState.getString(getString(R.string.photo_url_saved_key));
        }

        mAdapter = new FullPhotoAdapter(getChildFragmentManager(), getActivity());
        mViewPager.setAdapter(mAdapter);
        mAdapter.setData(mUrls);
        mViewPager.setCurrentItem(mStartingPos);

        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(getString(R.string.url_list_saved), mUrls);
        outState.putInt(getString(R.string.starting_pos_saved), mStartingPos);
    }


    @OnClick(R.id.photo_share_fab)
    public void onFabClick(){
        //todo change share text
        Intent intentToShare = ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setText("Here is a photo from Mars!" + "\n\n" + mUrls.get(mViewPager.getCurrentItem()))
                .getIntent();

        PackageManager packageManager = getActivity().getPackageManager();
        if(intentToShare.resolveActivity(packageManager)!= null){
            startActivity(intentToShare);
        }

    }





}
