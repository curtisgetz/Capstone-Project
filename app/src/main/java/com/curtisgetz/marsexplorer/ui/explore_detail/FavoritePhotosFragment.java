package com.curtisgetz.marsexplorer.ui.explore_detail;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.FavoriteImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritePhotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritePhotosFragment extends Fragment implements FavoritesAdapter.FavoriteClickListner{

    private final static String TAG = FavoritePhotosFragment.class.getSimpleName();


    @BindView(R.id.favorite_photos_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.favorite_photo_coordinator)
    CoordinatorLayout mCoordinator;

    private FavoriteViewModel mViewModel;
    private FavoritesAdapter mAdapter;
    private FullPhotoPagerFragment.FullPhotoPagerInteraction mListener;

    public FavoritePhotosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment FavoritePhotosFragment.
     */

    public static FavoritePhotosFragment newInstance() {
        return  new FavoritePhotosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
        if(context instanceof FullPhotoPagerFragment.FullPhotoPagerInteraction){
            mListener = (FullPhotoPagerFragment.FullPhotoPagerInteraction) context;
        }else{
            throw new RuntimeException(context.toString()
                    + " must implement FullPhotoPagerInteraction");
        }
        FragmentActivity activity = getActivity();
        if(activity != null) {
            mAdapter = new FavoritesAdapter(this);
            mViewModel = ViewModelProviders.of(activity).get(FavoriteViewModel.class);
            mViewModel.getFavorites().observe(this, new Observer<List<FavoriteImage>>() {
                @Override
                public void onChanged(@Nullable List<FavoriteImage> favoriteImages) {
                    Log.d(TAG, "onChanged");
                    mAdapter.setData(favoriteImages);
                }
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_photos, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView");
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_delete_favorites, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get id of menu item selected
        int id = item.getItemId();
        switch (id){
            case R.id.action_delete_all_favorites:
                confirmDeleteAll();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPhotoClick(List<String> urls, int pos) {
        FragmentActivity activity = getActivity();
        if(activity == null) return;

        String dateString = mListener.getDateString();
        int roverIndex = mListener.getRoverIndex();
        ArrayList<String> urlList = new ArrayList<>(urls);
        FullPhotoFragment photoFragment = FullPhotoFragment.newInstance(activity, urlList,
                pos, roverIndex, dateString);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.explore_detail_container, photoFragment,
                        FullPhotoFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    private void confirmDeleteAll(){
        Snackbar snackbar = Snackbar.make(mCoordinator, "Delete All?", Snackbar.LENGTH_LONG);
        snackbar.setAction("Confirm", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.deleteAllFavorites();
            }
        });
        snackbar.show();
    }

}
