package com.curtisgetz.marsexplorer.utils;

import android.content.Context;
import android.os.Bundle;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.ui.info.InfoDialogFragment;

public final class InformationUtils {


    public final static int ERROR_LOADING_INFO = -1;
    public final static int SOL_RANGE_INFO = 0;
    public final static int WEATHER_INFO = 1;
    public final static int AIR_TEMP_INFO = 2;
    public final static int GROUND_TEMP_INFO = 3;
    public final static int SUNRISE_SUNSET_INFO = 4;
    public final static int ATMO_INFO = 5;

    public final static String INFO_INDEX_EXTRA = "info_index_extra";


    public static String getInformationText(Context context,  int infoIndex){

        switch (infoIndex){
            case SOL_RANGE_INFO:
                return context.getResources().getString(R.string.info_sol_text_details);
            case WEATHER_INFO:
                return context.getString(R.string.info_weather);
            case AIR_TEMP_INFO:
                return context.getString(R.string.info_air_temp);
            case GROUND_TEMP_INFO:
                return context.getString(R.string.info_ground_temp);
            case SUNRISE_SUNSET_INFO:
                return context.getString(R.string.info_sunrise_sunset);
            case ATMO_INFO:
                return context.getString(R.string.info_atmo);
            default:
                return context.getResources().getString(R.string.info_error_text);
        }
    }



    public static InfoDialogFragment getInfoFragment(int infoIndex){
        InfoDialogFragment infoDialogFragment = new InfoDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INFO_INDEX_EXTRA, infoIndex);
        infoDialogFragment.setArguments(bundle);
        return infoDialogFragment;
    }


}
