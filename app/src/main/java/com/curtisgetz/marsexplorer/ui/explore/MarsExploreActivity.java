package com.curtisgetz.marsexplorer.ui.explore;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.rover_explore.ExploreCategory;
import com.curtisgetz.marsexplorer.ui.MarsBaseActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.ExploreDetailActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.favorites.FavoritePhotosFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.mars_facts.MarsFactsFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.mars_weather.MarsWeatherFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.FullPhotoFragment;
import com.curtisgetz.marsexplorer.ui.explore_detail.rover_photos.FullPhotoPagerFragment;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarsExploreActivity extends MarsBaseActivity implements
        ExploreCategoryAdapter.ExploreCategoryClick , MarsFactsFragment.FactsInteraction
        ,FullPhotoPagerFragment.FullPhotoPagerInteraction {


    @BindView(R.id.mars_explore_categories_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.explore_mars_title)
    TextView mTitleText;
    @BindView(R.id.mars_explore_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    private ExploreCategoryAdapter mAdapter;
    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_explore);
        ButterKnife.bind(this);
        isTwoPane = (findViewById(R.id.mars_explore_sw600_land) != null);

        mAdapter = new ExploreCategoryAdapter(getLayoutInflater(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        populateUI();
    }


    private void populateUI(){
        List<ExploreCategory> categories = HelperUtils.getExploreCategories(this, HelperUtils.MARS_EXPLORE_INDEX);
        mAdapter.setData(categories);
    }



    @Override
    public void onCategoryClick(int categoryIndex) {
        if(isNetworkAvailable()){
            //if two pane then open fragment in detail view
            if(isTwoPane){
                Fragment fragment;
                switch (categoryIndex){
                    case HelperUtils.MARS_WEATHER_CAT_INDEX:
                        fragment = MarsWeatherFragment.newInstance();
                        break;
                    case HelperUtils.MARS_FACTS_CAT_INDEX:
                        fragment = MarsFactsFragment.newInstance();
                        break;
                    case HelperUtils.MARS_FAVORITES_CAT_INDEX:
                        fragment = FavoritePhotosFragment.newInstance();
                        break;
                    default:
                        return;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.explore_detail_container,
                        fragment).commit();
            //if not two plane then open Explore Detail Activity
            }else {
            Intent intent = new Intent(getApplicationContext(), ExploreDetailActivity.class);
            intent.putExtra(getString(R.string.explore_index_extra_key), categoryIndex);
            intent.putExtra(getString(R.string.parent_activity_tag_extra), this.getClass().getSimpleName());
            startActivity(intent);
            }
        }

    }

    private boolean isNetworkAvailable()  {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if((networkInfo != null && networkInfo.isConnected())){
            return true;
        }else {
            Snackbar.make(mCoordinatorLayout, R.string.internet_required,
                    Snackbar.LENGTH_LONG).show();
            return false;
        }
    }


    @Override
    public void displaySnack(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();

    }
    @Override
    public void callDisplaySnack(String message) {
        //call display snack method on FullPhotoFragment. This will allow the coordinatorlayout
        //to handle the snack display and moving the FAB
        FullPhotoFragment photoFragment = (FullPhotoFragment) getSupportFragmentManager()
                .findFragmentByTag(FullPhotoFragment.class.getSimpleName());

        if (photoFragment != null) {
            photoFragment.displaySnack(message);
        }
    }

    @Override
    public String getDateString() {
        FullPhotoFragment photoFragment = (FullPhotoFragment) getSupportFragmentManager()
                .findFragmentByTag(FullPhotoFragment.class.getSimpleName());

        if (photoFragment != null) {
            return photoFragment.getDate();
        } else {
            return null;
        }
    }

    @Override
    public int getRoverIndex() {
        FullPhotoFragment photoFragment = (FullPhotoFragment) getSupportFragmentManager()
                .findFragmentByTag(FullPhotoFragment.class.getSimpleName());

        if (photoFragment != null) {
            return photoFragment.getRover();
        } else {
            return -1;
        }
    }
}
