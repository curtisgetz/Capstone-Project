package com.curtisgetz.marsexplorer.ui.explore_detail.tweets;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.Tweet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TweetsFragment extends Fragment {

    private Unbinder mUnBinder;
    private TweetAdapter mAdapter;
    private TweetViewModel mViewModel;

    @BindView(R.id.tweets_recycler)
    RecyclerView mTweetRecycler;

   // private OnFragmentInteractionListener mListener;

    public TweetsFragment() {
        // Required empty public constructor

    }


    public static TweetsFragment newInstance( ) {
        return new TweetsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        mAdapter = new TweetAdapter();
        //set up ViewModel
        if(activity != null) {
            mViewModel = ViewModelProviders.of(activity).get(TweetViewModel.class);
            mViewModel.getTweets().observe(activity, new Observer<List<Tweet>>() {
                @Override
                public void onChanged(@Nullable List<Tweet> tweets) {
                    mAdapter.setData(tweets);
                }
            });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_tweets, container, false);
         mUnBinder = ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
         mTweetRecycler.setLayoutManager(layoutManager);
         mTweetRecycler.setAdapter(mAdapter);

         return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

}
