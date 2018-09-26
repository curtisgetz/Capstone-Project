package com.curtisgetz.marsexplorer.data;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "exploretypes")
public class MainExploreType {

    @PrimaryKey
    private int mTypeIndex;
    private String mText;
    private int mImageID;


    public MainExploreType(int mTypeIndex, String mText, int mImageID) {
        this.mTypeIndex = mTypeIndex;
        this.mText = mText;
        this.mImageID = mImageID;
    }

/*

    @Ignore
    public MainExploreType(String mText, int mImageID ) {
        this.mText = mText;
        this.mImageID = mImageID;
    }
*/


    public int getTypeIndex() {
        return mTypeIndex;
    }

    public void setTypeIndex(int mTypeIndex) {
        this.mTypeIndex = mTypeIndex;
    }


    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public int getImageID() {
        return mImageID;
    }

    public void setImageID(int mImageID) {
        this.mImageID = mImageID;
    }
}
