package com.curtisgetz.marsexplorer.ui.explore_detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.FavoriteImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter{


    private List<FavoriteImage> mFavorites;
    private FavoriteClickListner mClickListner;

    public interface FavoriteClickListner{
        void onPhotoClick(List<String> urls, int pos);
    }

    public FavoritesAdapter(FavoriteClickListner clickListner) {
        mClickListner = clickListner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.favorite_photo_item, parent, false);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String photoUrl = mFavorites.get(position).getImageUrl();

        //Picasso will throw exception with blank string. Do check as fallback
        if(!photoUrl.isEmpty()){
            Picasso.get().load(photoUrl)
                    .placeholder(R.drawable.marsplaceholderfull)
                    .error(R.drawable.marsimageerror)
                    .into(((FavoriteViewHolder) holder).mImageView);
        }else {
            Picasso.get().load(R.drawable.marsimageerror)
                    .placeholder(R.drawable.marsplaceholderfull)
                    .fit()
                    .into(((FavoriteViewHolder) holder).mImageView);
        }

    }

    @Override
    public int getItemCount() {
        if(mFavorites == null)return 0;
        return mFavorites.size();
    }


    public void setData(List<FavoriteImage> favoriteImages){
        mFavorites = new ArrayList<>(favoriteImages);
        notifyDataSetChanged();
    }

    private List<String> getUrlsOfFavorites(){
        if(mFavorites == null) return null;
        List<String> urls = new ArrayList<>();
        for(FavoriteImage image:mFavorites){
            urls.add(image.getImageUrl());
        }
        return urls;
    }


    class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.favorite_photo_imageview)
        ImageView mImageView;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            //send List of urls and position for ViewPager use
            mClickListner.onPhotoClick(getUrlsOfFavorites(), getAdapterPosition());
        }
    }

}
