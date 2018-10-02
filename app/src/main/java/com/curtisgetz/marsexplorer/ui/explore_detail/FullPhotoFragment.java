package com.curtisgetz.marsexplorer.ui.explore_detail;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.curtisgetz.marsexplorer.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullPhotoFragment extends Fragment {

    private final static String TAG = FullPhotoFragment.class.getSimpleName();

    private String mPhotoUrl;
    @BindView(R.id.rover_photo_full_imageview)ImageView mPhotoImageView;
    @BindView(R.id.photo_share_fab) FloatingActionButton mShareFab;

    public FullPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_photo, container, false);
        ButterKnife.bind(this, view);





        if(savedInstanceState == null) {
            Bundle bundle = getArguments();
            mPhotoUrl = bundle.getString(getString(R.string.photo_url_extra));
        }else {
            mPhotoUrl = savedInstanceState.getString(getString(R.string.photo_url_saved_key));
        }



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPhotoImageView.setTransitionName(mPhotoUrl);
        }
        //todo add error and placeholders
        Picasso.get().load(mPhotoUrl).noFade().into(mPhotoImageView, new Callback() {
            @Override
            public void onSuccess() {
                startPostponedEnterTransition();

            }

            @Override
            public void onError(Exception e) {
                startPostponedEnterTransition();
            }
        });

    }


}
