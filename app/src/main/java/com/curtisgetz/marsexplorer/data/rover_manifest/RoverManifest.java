package com.curtisgetz.marsexplorer.data.rover_manifest;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.curtisgetz.marsexplorer.utils.HelperUtils;

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

    private int getMinSol(){
        switch(mRoverIndex){
            case HelperUtils.CURIOSITY_ROVER_INDEX:
                return HelperUtils.CURIOSITY_SOL_START;
            case HelperUtils.OPPORTUNITY_ROVER_INDEX:
                return HelperUtils.OPPORTUNITY_SOL_START;
            case HelperUtils.SPIRIT_ROVER_INDEX:
                 return HelperUtils.SPIRIT_SOL_START;
            default:
                //if no match then use 1 as sol start to be safe
                return HelperUtils.OPPORTUNITY_SOL_START;
        }
    }



    public String getSolRange(){
        String minSol = String.valueOf(getMinSol());
        /*switch(mRoverIndex){
            case HelperUtils.CURIOSITY_ROVER_INDEX:
                minSol = String.valueOf(HelperUtils.CURIOSITY_SOL_START);
                break;
            case HelperUtils.OPPORTUNITY_ROVER_INDEX:
                minSol = String.valueOf(HelperUtils.OPPORTUNITY_SOL_START);
                break;
            case HelperUtils.SPIRIT_ROVER_INDEX:
                minSol = String.valueOf(HelperUtils.SPIRIT_SOL_START);
                break;
        }*/
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

    public int getMinSolInt(){
        return  getMinSol();
    }

    public int getMaxSolint(){
        int maxSol;
        try{
            maxSol = Integer.parseInt(mMaxSol);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return HelperUtils.DEFAULT_MAX_SOL;
        }
        return maxSol;
    }


}
