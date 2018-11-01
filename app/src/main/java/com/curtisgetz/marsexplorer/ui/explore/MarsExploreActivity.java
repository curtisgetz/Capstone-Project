package com.curtisgetz.marsexplorer.ui.explore;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.rover_explore.RoverExploreCategory;
import com.curtisgetz.marsexplorer.ui.MarsBaseActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.ExploreDetailActivity;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarsExploreActivity extends MarsBaseActivity implements ExploreCategoryAdapter.ExploreCategoryClick{


    @BindView(R.id.mars_explore_categories_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.explore_mars_title)
    TextView mTitleText;
    @BindView(R.id.mars_explore_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

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
        if(isNetworkAvailable()){
            Intent intent = new Intent(getApplicationContext(), ExploreDetailActivity.class);
            intent.putExtra(getString(R.string.explore_index_extra_key), categoryIndex);
            intent.putExtra(getString(R.string.parent_activity_tag_extra), this.getClass().getSimpleName());
            startActivity(intent);
        }

    }

    private boolean isNetworkAvailable()  {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if((networkInfo != null && networkInfo.isConnected())){
            return true;
        }else {
            final Snackbar snackbar = Snackbar.make(mCoordinatorLayout, R.string.internet_required, Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.snackbar_dismiss), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
            return false;
        }
    }




}
