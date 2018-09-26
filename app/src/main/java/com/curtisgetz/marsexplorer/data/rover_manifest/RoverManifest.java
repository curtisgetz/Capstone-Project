package com.curtisgetz.marsexplorer.data.rover_manifest;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.curtisgetz.marsexplorer.utils.IndexUtils;

@Entity(tableName = "roverManifest")
public class RoverManifest {

    @PrimaryKey
    private int mRoverIndex;
    private String mRoverName;
    private String mLaunchDate;
    private String mLandingDate;
    private String mStatus;
    private String mMaxSol;
    private String mMaxDate;
    private String mTotalPhotos;


    public RoverManifest(int mRoverIndex, String mRoverName, String mLaunchDate, String mLandingDate,
                         String mStatus, String mMaxSol, String mMaxDate, String mTotalPhotos) {

        this.mRoverIndex = mRoverIndex;
        this.mRoverName = mRoverName;
        this.mLaunchDate = mLaunchDate;
        this.mLandingDate = mLandingDate;
        this.mStatus = mStatus;
        this.mMaxSol = mMaxSol;
        this.mMaxDate = mMaxDate;
        this.mTotalPhotos = mTotalPhotos;
    }

    public String getSolRange(){
        String minSol = "";
        switch(mRoverIndex){
            case IndexUtils.CURIOSITY_ROVER_INDEX:
                minSol = String.valueOf(IndexUtils.CURIOSITY_SOL_START);
                break;
            case IndexUtils.OPPORTUNITY_ROVER_INDEX:
                minSol = String.valueOf(IndexUtils.OPPORTUNITY_SOL_START);
                break;
            case IndexUtils.SPIRIT_ROVER_INDEX:
                minSol = String.valueOf(IndexUtils.SPIRIT_SOL_START);
                break;
        }
        return minSol + " - " + mMaxSol;
    }


    public int getRoverIndex() {
        return mRoverIndex;
    }

    public void setRoverIndex(int mRoverIndex) {
        this.mRoverIndex = mRoverIndex;
    }

    public String getRoverName() {
        return mRoverName;
    }

    public void setRoverName(String mRoverName) {
        this.mRoverName = mRoverName;
    }

    public String getLaunchDate() {
        return mLaunchDate;
    }

    public void setLaunchDate(String mLaunchDate) {
        this.mLaunchDate = mLaunchDate;
    }

    public String getLandingDate() {
        return mLandingDate;
    }

    public void setLandingDate(String mLandingDate) {
        this.mLandingDate = mLandingDate;
    }

    public String getStatus() {
        return mStatus;
    }


    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getMaxSol() {
        return mMaxSol;
    }

    public void setMaxSol(String mMaxSol) {
        this.mMaxSol = mMaxSol;
    }

    public String getMaxDate() {
        return mMaxDate;
    }

    public void setMaxDate(String mMaxDate) {
        this.mMaxDate = mMaxDate;
    }

    public String getTotalPhotos() {
        return mTotalPhotos;
    }

    public void setTotalPhotos(String mTotalPhotos) {
        this.mTotalPhotos = mTotalPhotos;
    }
}
