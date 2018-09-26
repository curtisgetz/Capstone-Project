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
import com.curtisgetz.marsexplorer.utils.IndexUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoverCategoryAdapter extends RecyclerView.Adapter {


    private List<RoverExploreCategory> mCategoryList;
    private CategoryClickListener mClickListener;
    private SolClickListener mSolButtonClick;
    private final static int PHOTO_CATEGORY = IndexUtils.ROVER_PICTURES_CAT_INDEX;


    public interface CategoryClickListener{
        void onCategoryClick(int clickedPos);
    }
    public interface SolClickListener {
        void onSolButtonClick(int clickedPos);
    }


    public RoverCategoryAdapter(CategoryClickListener clickListener, SolClickListener solClickListener){
        this.mClickListener = clickListener;
        this.mSolButtonClick = solClickListener;
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

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rover_explore_list_item, parent,  false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //if view is for photos, then show the edit text and buttons
        RoverExploreCategory category = mCategoryList.get(position);
        int photoVis;
        if(category.getmCatIndex() == PHOTO_CATEGORY){
            photoVis = View.VISIBLE;
        }else {
            photoVis = View.GONE;
        }
        setupSolSearchViews(photoVis, holder);
        ((CategoryViewHolder) holder).mTextView.setText(category.getmTitleText());
        Picasso.get().load(category.getmImageResId()).into(((CategoryViewHolder) holder).mImageView);
    }

    @Override
    public int getItemCount() {
        if(mCategoryList == null) return 0;
        return mCategoryList.size();
    }

    private void setupSolSearchViews(int visibility, final RecyclerView.ViewHolder holder){
        //todo get edittext input properly
        //set visibility of Views for searching pictures
        ((CategoryViewHolder) holder).mSolEdit.setVisibility(visibility);
        ((CategoryViewHolder) holder).mSolRandBtn.setVisibility(visibility);
        ((CategoryViewHolder) holder).mSolSeachBtn.setVisibility(visibility);
        ((CategoryViewHolder) holder).mSolSearchLabel.setVisibility(visibility);
        //if views are visible then set click listeners
        if(((CategoryViewHolder) holder).mSolEdit.getVisibility() == View.VISIBLE){
            //click submit button when 'Done' is pressed on soft keyboard
            ((CategoryViewHolder) holder).mSolEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                    if(action == EditorInfo.IME_ACTION_DONE){
                        String input = ((CategoryViewHolder) holder).mSolEdit.getText().toString();
                        if(input.isEmpty()) input = "2";
                        mSolButtonClick.onSolButtonClick(Integer.parseInt(input));
                    }
                    return false;
                }
            });
            ((CategoryViewHolder) holder).mSolSeachBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String input = ((CategoryViewHolder) holder).mSolEdit.getText().toString();
                    if(input.isEmpty()) input = "2";
                    mSolButtonClick.onSolButtonClick(Integer.parseInt(input));
                }
            });
            ((CategoryViewHolder) holder).mSolRandBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String input = ((CategoryViewHolder) holder).mSolEdit.getText().toString();
                    if(input.isEmpty()) input = "2";
                    mSolButtonClick.onSolButtonClick(Integer.parseInt(input));
                }
            });
        }
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.rover_explore_cardview_image)
        ImageView mImageView;
        @BindView(R.id.rover_explore_cardview_text)
        TextView mTextView;
        @BindView(R.id.sol_edit_text)
        EditText mSolEdit;
        @BindView(R.id.search_sol_button)
        Button mSolSeachBtn;
        @BindView(R.id.random_sol_button)
        Button mSolRandBtn;
        @BindView(R.id.sol_search_label)
        TextView mSolSearchLabel;

        CategoryViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mClickListener.onCategoryClick(getAdapterPosition());
        }
    }


}
