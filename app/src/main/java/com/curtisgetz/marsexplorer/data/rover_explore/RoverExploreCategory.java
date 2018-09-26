package com.curtisgetz.marsexplorer.data.rover_explore;

public class RoverExploreCategory {

    private int mCatIndex;
    private String mTitleText;
    private int mImageResId;

    public RoverExploreCategory(){

    }

    public RoverExploreCategory(String mTitleText, int mImageResId, int catIndex) {
        this.mTitleText = mTitleText;
        this.mImageResId = mImageResId;
        this.mCatIndex = catIndex;
    }


    public String getmTitleText() {
        return mTitleText;
    }

    public void setmTitleText(String mTitleText) {
        this.mTitleText = mTitleText;
    }

    public int getmImageResId() {
        return mImageResId;
    }

    public void setmImageResId(int mImageResId) {
        this.mImageResId = mImageResId;
    }

    public int getmCatIndex() {
        return mCatIndex;
    }

    public void setmCatIndex(int mCatIndex) {
        this.mCatIndex = mCatIndex;
    }
}
