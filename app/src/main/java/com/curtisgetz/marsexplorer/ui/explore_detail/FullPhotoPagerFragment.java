package com.curtisgetz.marsexplorer.ui.explore_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.OnSwipeListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullPhotoPagerFragment extends Fragment implements View.OnTouchListener {
    private final static String TAG = FullPhotoPagerFragment.class.getSimpleName();



    @BindView(R.id.rover_photo_full_imageview)
    ImageView mImageView;

    private GestureDetectorCompat mGestureDetector;


//todo animate swipe up to close
    public static Fragment newInstance(String url) {
        FullPhotoPagerFragment fullPhotoPagerFragment = new FullPhotoPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(HelperUtils.PHOTO_PAGER_URL_EXTRA, url);
        fullPhotoPagerFragment.setArguments(bundle);
        return fullPhotoPagerFragment;
    }

    public FullPhotoPagerFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGestureDetector = new GestureDetectorCompat(getContext(), new OnSwipeListener(){
            @Override
            public boolean onSwipe(Direction direction) {
                if(direction == Direction.up){
                 //todo have fragment go back on up swipe
                    //mImageView.animate().translationY(mImageView.getHeight());
                    getActivity().onBackPressed();
                    Log.d(TAG, "SWIPED UP");
                }
                return true;
            }
        });

        /* postponeEnterTransition();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));

        }*/
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_photo_pager_item, container, false);
        ButterKnife.bind(this, view);
        view.setOnTouchListener(this);
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImageView.setTransitionName(url);
        }*/

        Bundle bundle = getArguments();
        if(bundle == null) {
            displayErrorImage();
        }else {
            String url = bundle.getString(HelperUtils.PHOTO_PAGER_URL_EXTRA);
            //Picasso will throw exception with empty string. Should never be empty but do final check
            if(url == null || url.isEmpty()){
                displayErrorImage();
            }else {
                Picasso.get().load(url)
                        .error(R.drawable.marsimageerror)
                        .placeholder(R.drawable.marsplaceholderfull)
                        .noFade()
                        .into(mImageView);
            }
        }

        return view;
    }

    private void displayErrorImage(){
        Picasso.get().load(R.drawable.marsimageerror).into(mImageView);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }




}
