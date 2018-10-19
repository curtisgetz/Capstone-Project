package com.curtisgetz.marsexplorer.ui.explore_detail;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.curtisgetz.marsexplorer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullPhotoAdapter extends FragmentPagerAdapter{

  //  private Context mContext;
    private List<String> mUrls;


    @BindView(R.id.rover_photo_full_imageview)
     ImageView mImageView;

    public FullPhotoAdapter(FragmentManager fm, Context context) {
        super(fm);
       // this.mContext = context;
    }

    public void setData(List<String> urls){
        this.mUrls = urls;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        if(mUrls == null) return 0;
        return mUrls.size();
    }



    @Override
    public Fragment getItem(int position) {
        String url = mUrls.get(position);
        return FullPhotoPagerFragment.newInstance(url);
    }


    public String getUrl(int position){
        return mUrls.get(position);
    }


}
