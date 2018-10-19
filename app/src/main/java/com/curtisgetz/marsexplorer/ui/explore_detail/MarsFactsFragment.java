package com.curtisgetz.marsexplorer.ui.explore_detail;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MarsFact;
import com.curtisgetz.marsexplorer.utils.RealtimeDatabaseUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarsFactsFragment extends Fragment {
    private final static String TAG = MarsFactsFragment.class.getSimpleName();


    private MarsFactsViewModel mViewModel;

    @BindView(R.id.fact_name_text)
    TextView mFactName;
    @BindView(R.id.fact_description_text)
    TextView mFactDescription;
    @BindView(R.id.fact_url_text)
    TextView mUrlText;

 //   private FirebaseDatabase mFirebaseDatabase;
  //  private DatabaseReference mFactsReference;

   // private final static String DB_NODE_NAME = "facts";
    //
    //First child in database corresponds to the day of the year.  Will try to load the fact
    // matching the current day of the year. If no matches try to load another fact.
    // Fragment will allow cycling through facts. Widget will show 'fact of the day'


    public static MarsFactsFragment newInstance(){
        return new MarsFactsFragment();
    }

    public MarsFactsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if(activity == null)return;
        mViewModel = ViewModelProviders.of(activity).get(MarsFactsViewModel.class);
        mViewModel.getFact().observe(this, new Observer<MarsFact>() {
            @Override
            public void onChanged(@Nullable MarsFact marsFact) {
                displayResults();
            }
        });
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get db reference
      // mFirebaseDatabase = RealtimeDatabaseUtils.getDatabase();
        //get reference to facts node
       // mFactsReference = mFirebaseDatabase.getReference(DB_NODE_NAME);
    /* */}

    //todo finish facts
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mars_facts, container, false);
        ButterKnife.bind(this, view);
        //loadTestData();
        MarsFact fact = new MarsFact(280, "New", "short", "full", "http:www.space-facts/Mars");
        //mFactsReference.child("2").setValue(fact);

        /*int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        Toast.makeText(getContext(), String.valueOf(dayOfYear), Toast.LENGTH_LONG).show();
        getFactFromDb(dayOfYear);*/

        return view;

    }


    private void displayResults(){
        MarsFact fact = getFactFromViewModel();
        if(fact == null) return;
        mFactName.setText(fact.getFactName());
        mFactDescription.setText(fact.getFullDescription());
        mFactDescription.append(fact.getFullDescription());
        mFactDescription.append(fact.getFullDescription());
        mUrlText.setText(fact.getUrl());
    }

    private MarsFact getFactFromViewModel(){
       return mViewModel.getFact().getValue();


    }

    //open link to source of fact
    @OnClick(R.id.fact_url_text)
    public void onUrlClick(){
        MarsFact fact = getFactFromViewModel();
        if(fact == null)return;
        Uri factWebpage = Uri.parse(fact.getUrl());
        Intent webIntent = new Intent(Intent.ACTION_VIEW, factWebpage);
        if(webIntent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivity(webIntent);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
