package com.curtisgetz.marsexplorer.data;

public class WeatherDetail {


    private int mId;
    private String mLabel;
    private String mValue;
    private int mInfoIndex;
    private String mSol;


    public WeatherDetail(String mLabel, String mValue, int infoIndex, String sol) {
        this.mLabel = mLabel;
        this.mValue = mValue;
        this.mInfoIndex = infoIndex;
        this.mSol = sol;
    }


    public WeatherDetail(String mLabel, String mValue) {
        this.mLabel = mLabel;
        this.mValue = mValue;
    }


    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmLabel() {
        return mLabel;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

    public int getmInfoIndex() {
        return mInfoIndex;
    }

    public void setmInfoIndex(int mInfoIndex) {
        this.mInfoIndex = mInfoIndex;
    }

    public String getmSol() {
        return mSol;
    }

    public void setmSol(String mSol) {
        this.mSol = mSol;
    }
}
