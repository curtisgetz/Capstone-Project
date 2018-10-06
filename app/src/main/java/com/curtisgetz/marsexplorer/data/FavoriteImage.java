package com.curtisgetz.marsexplorer.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class FavoriteImage {

    @PrimaryKey
    private int mId;
    private String mImageUrl;


    public FavoriteImage(int id, String imageUrl) {
        this.mId = id;
        this.mImageUrl = imageUrl;
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }


}
