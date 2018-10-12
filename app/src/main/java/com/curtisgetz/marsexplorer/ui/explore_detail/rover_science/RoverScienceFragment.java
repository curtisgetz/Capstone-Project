package com.curtisgetz.marsexplorer.ui.explore_detail.rover_science;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.RoverScience;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoverScienceFragment extends Fragment{

//    @BindView(R.id.science_text_view)
//    TextView mScienceText;

    private List<RoverScience> mScienceList;
    private RoverSciencePagerAdapter mAdapter;
    @BindView(R.id.rover_science_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    public RoverScienceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rover_science, container, false);
        ButterKnife.bind(this, view);

        if(savedInstanceState == null){
            Bundle bundle = getArguments();
            int roverIndex = bundle.getInt(getString(R.string.rover_index_extra));
            mScienceList = new ArrayList<>(HelperUtils.getScienceList(getContext(), roverIndex));

        }
        if(!(mScienceList == null || mScienceList.size() == 0)){
            mAdapter = new RoverSciencePagerAdapter(getChildFragmentManager());
            mViewPager.setAdapter(mAdapter);
            mAdapter.setData(mScienceList);
            mTabLayout.setupWithViewPager(mViewPager);
        }



//        mScienceText.setText(getString(R.string.mast_camera_details));
        //create clickable image
       // ClickableAreasImage clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(mCuriosityImage), this);
        //initialize clickable area list
      //  List<ClickableArea> clickableAreas = new ArrayList<>();
      //  RoverScience roverScience = new RoverScience("Test Data", "Test details");
        //define clickable areas
        //parameter values (pixels): (x coordinate, width, height) and assign an object to it
       // clickableAreas.add(new ClickableArea(500, 200, 125, 200, roverScience));

      //  clickableAreasImage.setClickableAreas(clickableAreas);
        return view;
    }



}
