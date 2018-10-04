package com.curtisgetz.marsexplorer.data.rover_explore;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoverCategoryAdapter extends RecyclerView.Adapter {

    private final static String TAG = RoverCategoryAdapter.class.getSimpleName();

    private List<RoverExploreCategory> mCategoryList;
   // private CategoryClickListener mClickListener;
    //private SolClickListener mSolButtonClick;
    private CategoryClickListener mClickListener;
    private LayoutInflater mInflater;
    private final static int PHOTO_CATEGORY = HelperUtils.ROVER_PICTURES_CAT_INDEX;


    public interface CategoryClickListener{
        void onCategoryClick(int clickedPos);
        void onSolSearchClick(String solNumber);
        void onRandomSolClick();
    }



    public RoverCategoryAdapter(LayoutInflater inflater, CategoryClickListener clickListener){
       this.mClickListener = clickListener;
       this.mInflater = inflater;
       // this.mSolButtonClick = solClickListener;
    }

    public void setData(List<RoverExploreCategory> categories){
        this.mCategoryList = new ArrayList<>(categories);
        notifyDataSetChanged();
    }

    public void clearData(){
        this.mCategoryList.clear();
        notifyItemRangeRemoved(mCategoryList.size(), mCategoryList.size());
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.rover_explore_list_item, parent,  false);

        return new CategoryViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //if view is for photos, then show the edit text and buttons
        CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
        viewHolder.setItem(mCategoryList.get(position));




        RoverExploreCategory category = mCategoryList.get(position);
       /* int photoVis;
        if(category.getmCatIndex() == PHOTO_CATEGORY){
            photoVis = View.VISIBLE;
        }else {
            photoVis = View.GONE;
        }
        setupSolSearchViews(photoVis, holder);*/
        /*((CategoryViewHolder) holder).mTextView.setText(category.getmTitleText());
        Picasso.get().load(category.getmImageResId()).into(((CategoryViewHolder) holder).mImageView);*/
    }

    @Override
    public int getItemCount() {
        if(mCategoryList == null) return 0;
        return mCategoryList.size();
    }





    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.rover_explore_cardview_image)
        ImageView mImageView;
        @BindView(R.id.rover_explore_cardview_text)
        TextView mTextView;
        @BindView(R.id.sol_edit_text)
        EditText mSolEdit;
        @BindView(R.id.search_sol_button)
        Button mSolSearchBtn;
        @BindView(R.id.random_sol_button)
        Button mSolRandBtn;
        @BindView(R.id.sol_search_label)
        TextView mSolSearchLabel;

        public CategoryClickListener mCatClickListener;
        public RoverExploreCategory mCategory;

        public CategoryViewHolder(View itemView, CategoryClickListener listener){
            super(itemView);
            this.mCatClickListener = listener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            mCatClickListener.onCategoryClick(getAdapterPosition());
        }


        void setItem(RoverExploreCategory category){
            this.mCategory = category;
            mTextView.setText(category.getmTitleText());
            Picasso.get().load(category.getmImageResId()).into(mImageView);
            int photoVis;
            if(category.getmCatIndex() == PHOTO_CATEGORY){
                photoVis = View.VISIBLE;
            }else {
                photoVis = View.GONE;
            }
            setupSolSearchViews(photoVis);
        }


        private void setupSolSearchViews(int visibility){
            //todo get edittext input properly
            //set visibility of Views for searching pictures
            mSolEdit.setVisibility(visibility);
            mSolRandBtn.setVisibility(visibility);
            mSolSearchBtn.setVisibility(visibility);
            mSolSearchLabel.setVisibility(visibility);
            //if views are visible then set click listeners
            if(mSolEdit.getVisibility() == View.VISIBLE){
                //click submit button when 'Done' is pressed on soft keyboard
                //confirm input is a number, if not set it to 200
                mSolEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                        if(action == EditorInfo.IME_ACTION_DONE){
                            mCatClickListener.onSolSearchClick(mSolEdit.getText().toString());
                        }
                        return false;
                    }
                });
                mSolSearchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCatClickListener.onSolSearchClick(mSolEdit.getText().toString());
                    }
                });
                mSolRandBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCatClickListener.onRandomSolClick();
                    }
                });
            }
        }



    }


}
