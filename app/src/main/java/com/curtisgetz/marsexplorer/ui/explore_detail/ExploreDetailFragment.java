package com.curtisgetz.marsexplorer.ui.explore_detail;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curtisgetz.marsexplorer.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExploreDetailFragment extends Fragment {

    public ExploreDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore_detail, container, false);
    }
}
