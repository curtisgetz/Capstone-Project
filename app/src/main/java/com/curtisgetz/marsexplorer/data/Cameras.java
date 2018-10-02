package com.curtisgetz.marsexplorer.data;


import android.util.Log;

import com.curtisgetz.marsexplorer.utils.IndexUtils;

import java.util.List;

/**
 * Created by curti on 2/19/2018.
 */

public class Cameras {

    private final static String TAG = Cameras.class.getSimpleName();

    private int mRoverIndex;
    private List<String> mFHAZ, mRHAZ, mNAVCAM, mMAST, mCHEMCAM, mMAHLI, mMARDI, mPANCAM, mMINITES;
    private String mEarthDate;

    public Cameras(int index, List<String> fhaz, List<String> rhaz, List<String> navcam, List<String> mast, List<String> chemcam, List<String> mahli,
                   List<String> mardi, List<String> pancam, List<String> minites, String earthDate){
        mRoverIndex = index;
        mFHAZ = fhaz;
        mRHAZ = rhaz;
        mNAVCAM = navcam;

        mMAST = mast;
        mCHEMCAM = chemcam;
        mMAHLI = mahli;
        mMARDI = mardi;

        mPANCAM = pancam;
        mMINITES = minites;

        mEarthDate = earthDate;
    }


    public List<String> getFHAZ() { return mFHAZ;}
    public List<String> getRHAZ() {return mRHAZ;}
    public List<String> getNAVCAM() {return mNAVCAM;}

    public List<String> getMAST() {return mMAST;}
    public List<String> getCHEMCAM() {return mCHEMCAM;}
    public List<String> getMAHLI() {return mMAHLI;}
    public List<String> getMARDI() {return mMARDI;}

    public List<String> getPANCAM() {return mPANCAM;}
    public List<String> getMINITES() {return mMINITES;}

    public String getEarthDate() {
        return mEarthDate;
    }

    public int getRoverIndex() {
        return mRoverIndex;
    }

    public void setRoverIndex(int mRoverIndex) {
        this.mRoverIndex = mRoverIndex;
    }

    public void setmFHAZ(List<String> mFHAZ) {
        this.mFHAZ = mFHAZ;
    }

    public void setmRHAZ(List<String> mRHAZ) {
        this.mRHAZ = mRHAZ;
    }

    public void setmNAVCAM(List<String> mNAVCAM) {
        this.mNAVCAM = mNAVCAM;
    }

    public void setmMAST(List<String> mMAST) {
        this.mMAST = mMAST;
    }

    public void setmCHEMCAM(List<String> mCHEMCAM) {
        this.mCHEMCAM = mCHEMCAM;
    }

    public void setmMAHLI(List<String> mMAHLI) {
        this.mMAHLI = mMAHLI;
    }

    public void setmMARDI(List<String> mMARDI) {
        this.mMARDI = mMARDI;
    }

    public void setmPANCAM(List<String> mPANCAM) {
        this.mPANCAM = mPANCAM;
    }

    public void setmMINITES(List<String> mMINITES) {
        this.mMINITES = mMINITES;
    }

    public void setEarthDate(String mEarthDate) {
        this.mEarthDate = mEarthDate;
    }

    public int[] getActiveCameras(){

        int number = 0;
        if(mFHAZ.size() > 0) {
            number++;

        }
        return null;
    }

    public boolean isCameraActive(int cameraIndex){

        switch (cameraIndex){
            case IndexUtils.CAM_FHAZ_INDEX:
                return (mFHAZ.size() > 0);
            case IndexUtils.CAM_RHAZ_INDEX:
                Log.d(TAG, String.valueOf(mRHAZ.size()));
                return (mRHAZ.size() > 0);
            case IndexUtils.CAM_MAST_INDEX:
                return (mMAST.size() > 0);
            case IndexUtils.CAM_CHEMCAM_INDEX:
                return (mCHEMCAM.size() > 0);
            case IndexUtils.CAM_MAHLI_INDEX:
                return (mMAHLI.size() > 0);
            case IndexUtils.CAM_MARDI_INDEX:
                return (mMARDI.size() > 0);
            case IndexUtils.CAM_NAVCAM_INDEX:
                return (mNAVCAM.size() > 0);
            case IndexUtils.CAM_PANCAM_INDEX:
                return (mPANCAM.size() > 0);
            case IndexUtils.CAM_MINITES_INDEX:
                return (mMINITES.size() > 0);
            default:
                return false;
        }
    }

    boolean[] getEmptyCameras(int size){
        boolean[] emptyCamIndex = new boolean[size];
        //Check each camera list size. If 0 then mark index as 'true'
        if (mFHAZ.size() == 0) emptyCamIndex[0] = true;
        if (mRHAZ.size() == 0) emptyCamIndex[1] = true;
        if (mNAVCAM.size() == 0) emptyCamIndex[2] = true;
        if (size == 7) {
            //For Curiosity Rover Cameras
            if (mMAST.size() == 0) emptyCamIndex[3] = true;
            if (mCHEMCAM.size() == 0) emptyCamIndex[4] = true;
            if (mMAHLI.size() == 0) emptyCamIndex[5] = true;
            if (mMARDI.size() == 0) emptyCamIndex[6] = true;
        } else {
            //For other rovers
            if (mPANCAM.size() == 0) emptyCamIndex[3] = true;
            if (mMINITES.size() == 0) emptyCamIndex[4] = true;
        }

        return emptyCamIndex;
    }



}