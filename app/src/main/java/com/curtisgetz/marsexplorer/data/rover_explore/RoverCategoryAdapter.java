package com.curtisgetz.marsexplorer.data.rover_explore;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoverCategoryAdapter extends RecyclerView.Adapter {


    private List<RoverExploreCategory> mCategoryList;
    private CategoryClickListener mClickListener;

    public interface CategoryClickListener{
        void onCategoryClick(int clickedPos);
    }


    public RoverCategoryAdapter(CategoryClickListener clickListener){
        this.mClickListener = clickListener;
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
        RoverExploreCategory category = mCategoryList.get(position);
        ((CategoryViewHolder) holder).mTextView.setText(category.getmTitleText());
        Picasso.get().load(category.getmImageResId()).into(((CategoryViewHolder) holder).mImageView);
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
