package com.curtisgetz.marsexplorer.ui.explore;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.rover_explore.RoverExploreCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExploreCategoryAdapter extends RecyclerView.Adapter {

    private final static String TAG = ExploreCategoryAdapter.class.getSimpleName();

    private List<RoverExploreCategory> mCategoryList;
    private LayoutInflater mInflater;
    private ExploreCategoryClick mClickListener;

    public interface ExploreCategoryClick{
        void onCategoryClick(int categoryIndex);
    }


    public ExploreCategoryAdapter(LayoutInflater mInflater, ExploreCategoryClick mClickListener) {
        this.mInflater = mInflater;
        this.mClickListener = mClickListener;
    }

    public void setData(List<RoverExploreCategory> categories){
        mCategoryList = new ArrayList<>(categories);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.mars_explore_list_item, parent, false );

        return new ExploreCategoryViewHolder(view, mClickListener) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ExploreCategoryViewHolder viewHolder = (ExploreCategoryViewHolder) holder;
        viewHolder.setItem(mCategoryList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mCategoryList == null)  return 0;
        return mCategoryList.size();
    }


    class ExploreCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        @BindView(R.id.mars_explore_cardview_image)
        ImageView mImageView;
        @BindView(R.id.mars_explore_cardview_text)
        TextView mTextView;

        private ExploreCategoryClick mCatClickListener;
        private RoverExploreCategory mCategory;

        public ExploreCategoryViewHolder(@NonNull View itemView, ExploreCategoryClick clickListener) {
            super(itemView);
            this.mCatClickListener = clickListener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void setItem(RoverExploreCategory category){
            this.mCategory = category;
            mTextView.setText(mCategory.getmTitleText());
            Picasso.get().load(category.getmImageResId()).into(mImageView);
        }

        @Override
        public void onClick(View view) {
            mCatClickListener.onCategoryClick(mCategory.getmCatIndex());

        }

    }


}
