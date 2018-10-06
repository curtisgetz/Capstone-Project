package com.curtisgetz.marsexplorer.ui.explore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.rover_explore.RoverExploreCategory;
import com.curtisgetz.marsexplorer.ui.explore_detail.ExploreDetailActivity;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarsExploreActivity extends AppCompatActivity implements ExploreCategoryAdapter.ExploreCategoryClick{


    @BindView(R.id.mars_explore_categories_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.explore_mars_title)
    TextView mTitleText;

    private ExploreCategoryAdapter mAdapter;
    //private int mExploreIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_explore);
        ButterKnife.bind(this);

        mAdapter = new ExploreCategoryAdapter(getLayoutInflater(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);


        populateUI();
    }


    private void populateUI(){
        List<RoverExploreCategory> categories = HelperUtils.getExploreCategories(this, HelperUtils.MARS_EXPLORE_INDEX);
        mAdapter.setData(categories);
    }



    @Override
    public void onCategoryClick(int categoryIndex) {
        Intent intent = new Intent(getApplicationContext(), ExploreDetailActivity.class);
        switch (categoryIndex){
            case HelperUtils.MARS_WEATHER_CAT_INDEX:
                intent.putExtra(getString(R.string.explore_index_extra_key), categoryIndex);
                startActivity(intent);
                break;
        }

    }
}
