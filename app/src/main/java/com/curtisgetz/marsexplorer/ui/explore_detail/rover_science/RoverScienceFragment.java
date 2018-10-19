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
            //get rover and category index. Use these to get correct list of science or rover info.
            int roverIndex = bundle.getInt(getString(R.string.rover_index_extra));
            int exploreCat = bundle.getInt(getString(R.string.explore_index_extra_key));
            mScienceList = new ArrayList<>(HelperUtils.getScienceList(getContext(), roverIndex,exploreCat));

        }
        if(!(mScienceList == null || mScienceList.size() == 0)){
            mAdapter = new RoverSciencePagerAdapter(getChildFragmentManager());
            mViewPager.setAdapter(mAdapter);
            mAdapter.setData(mScienceList);
            mTabLayout.setupWithViewPager(mViewPager);
        }

        return view;
    }



}
