package com.curtisgetz.marsexplorer.utils;

import android.content.Context;

import com.curtisgetz.marsexplorer.R;

public final class InformationUtils {


    public final static int ERROR_LOADING_INFO = -1;
    public final static int SOL_RANGE_INFO = 0;



    public static String getInformationText(Context context,  int infoIndex){

        switch (infoIndex){
            case SOL_RANGE_INFO:
                return context.getResources().getString(R.string.sol_text_details);
            default:
                return context.getResources().getString(R.string.info_error_text);
        }
    }



}
