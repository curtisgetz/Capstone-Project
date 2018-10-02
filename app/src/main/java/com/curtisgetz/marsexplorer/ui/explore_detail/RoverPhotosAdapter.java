package com.curtisgetz.marsexplorer.ui.explore_detail;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.curtisgetz.marsexplorer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoverPhotosAdapter extends RecyclerView.Adapter {


    private PhotoClickListener mClickListener;
    private List<String> mPhotoUrls;



    public interface PhotoClickListener{
        void onPhotoClick(String url, View view);
    }


    public RoverPhotosAdapter(PhotoClickListener listener) {
        this.mClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rover_photos_item, parent, false);

        return new RoverPhotosViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final String photoUrl = mPhotoUrls.get(position);
        //TODO add error and default photos

        Picasso.get().load(photoUrl).into(((RoverPhotosViewHolder) holder).mPhotoIv);

    }

    @Override
    public int getItemCount() {
        if(mPhotoUrls == null) return 0;
        return mPhotoUrls.size();
    }


    public void setData(List<String> photoUrls){
        mPhotoUrls = photoUrls;
        notifyDataSetChanged();
    }

    public void clearData(){
        mPhotoUrls.clear();
        notifyDataSetChanged();
    }

    class RoverPhotosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rover_photo_imageview)
        ImageView mPhotoIv;
        public RoverPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String url = mPhotoUrls.get(getAdapterPosition());
            ViewCompat.setTransitionName(view, url);
            mClickListener.onPhotoClick(url, view);
        }



    }


}
