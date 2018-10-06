package com.curtisgetz.marsexplorer.ui.explore_detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.WeatherDetail;
import com.curtisgetz.marsexplorer.ui.info.InfoDialogFragment;
import com.curtisgetz.marsexplorer.utils.InformationUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MarsWeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MarsWeatherFragment extends Fragment implements WeatherDetailsAdapter.DetailInfoClick{

    private final static String TAG = MarsWeatherFragment.class.getSimpleName();

    @BindView(R.id.weather_detail_recycler)
    RecyclerView mWeatherRecycler;
    @BindView(R.id.sol_and_date_text)
    TextView mSolTitle;
    @BindView(R.id.weather_progress)
    ProgressBar mWeatherProgress;
    private WeatherDetailsAdapter mAdapter;
    private Unbinder mUnBinder;

    // private OnFragmentInteractionListener mListener;
    private WeatherViewModel mViewModel;

    public MarsWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mars_weather, container, false);
        mUnBinder = ButterKnife.bind(this,  view);
        showProgress();
        mAdapter = new WeatherDetailsAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mWeatherRecycler.setLayoutManager(layoutManager);
        mWeatherRecycler.setAdapter(mAdapter);
        mViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        mViewModel.getWeather().observe(this, new Observer<List<WeatherDetail>>() {
            @Override
            public void onChanged(@Nullable List<WeatherDetail> weatherDetails) {
                mAdapter.setData(weatherDetails);
                updateTitle();
            }
        });




        return view;
    }

    private void updateTitle() {
       List<WeatherDetail> weatherDetails = mViewModel.getWeather().getValue();
       if(weatherDetails != null){
           String curiosity = getString(R.string.curiosity_rover) + " " + getString(R.string.rover_string);
           String solTag = getString(R.string.sol);
           String title = ( curiosity + " - " + solTag + " - " +  mViewModel.getWeather().getValue().get(0).getmSol());
           mSolTitle.setText(title);
       }
       hideProgress();
    }


    private void showProgress(){
        mWeatherProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgress(){
        mWeatherProgress.setVisibility(View.GONE);
    }


    @OnClick(R.id.weather_title_cardview)
    public void onTitleInfoClick(){
        InformationUtils.getInfoFragment(InformationUtils.WEATHER_INFO)
                .show(getActivity().getSupportFragmentManager(), InformationUtils.class.getSimpleName());
    }


    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onDetailInfoClick(int inforIndex) {
        Log.d(TAG, String.valueOf(inforIndex));
        InformationUtils.getInfoFragment(inforIndex).show(
                getActivity().getSupportFragmentManager(), InfoDialogFragment.class.getSimpleName());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }
}
