package com.curtisgetz.marsexplorer.data.rover_explore;

public class RoverExploreCategory {

    private String mTitleText;
    private int mImageResId;

    public RoverExploreCategory(){

    }

    public RoverExploreCategory(String mTitleText, int mImageResId) {
        this.mTitleText = mTitleText;
        this.mImageResId = mImageResId;
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
}
