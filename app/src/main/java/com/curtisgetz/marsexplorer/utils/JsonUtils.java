package com.curtisgetz.marsexplorer.utils;

import android.util.Log;

import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;

import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {

    private final static String TAG = JsonUtils.class.getSimpleName();

    public final static String JSON_DATE_FORMAT = "yyyy/MM/dd";
    // Add all for future use

    //JSON keys---------------------------------
    private final static String  NASA_PHOTOS = "photos";
    //next level
    // private final static String NASA_PHOTO_ID = "id";
    // private final static String NASA_PHOTO_SOL = "sol";
    private final static String NASA_PHOTO_CAMERA = "camera";
    private final static String NASA_IMAGE = "img_src";
    private final static String NASA_DATE = "earth_date";
    //private final String PAGE_NUMBER = "page";
    private final static String NASA_NAME = "name";

    //final String NASA_ROVER = "rover";
    //final String ROVER_ID = "id";
    private final static String MANIFEST_KEY = "photo_manifest";

    //MAN IFEST KEYS
    private final static String ROVER_LAND_DATE = "landing_date", ROVER_LAUNCH_DATE = "launch_date",
            ROVER_STATUS = "status", ROVER_MAX_SOL = "max_sol",
            ROVER_MAX_DATE = "max_date", ROVER_TOTAL_PHOTOS = "total_photos";


    //rover cameras
    private final static String ROVER_CAMERAS = "cameras",
            ROVER_FHAZ ="FHAZ",
            ROVER_RHAZ ="RHAZ",ROVER_MAST ="MAST",
            ROVER_CHEMCAM ="CHEMCAM",ROVER_MAHLI ="MAHLI",
            ROVER_MARDI ="MARDI",ROVER_NAVCAM ="NAVCAM",
            ROVER_PANCAM ="PANCAM", ROVER_MINITES ="MINITES";

//JSON keys---------------------------------



    public static RoverManifest getRoverManifest(int roverIndex, String url) throws JSONException {

        JSONObject mainObject = new JSONObject(url);

        if(!mainObject.has(MANIFEST_KEY)) return null;

        JSONObject manifestObject = mainObject.getJSONObject(MANIFEST_KEY);

        if(manifestObject.length() > 0){
            String name, launch, land, status, maxSol, maxDate, totalPhotos;

            name = manifestObject.getString(NASA_NAME);
            launch = manifestObject.getString(ROVER_LAUNCH_DATE);
            land = manifestObject.getString(ROVER_LAND_DATE);
            status = manifestObject.getString(ROVER_STATUS);
            maxSol = manifestObject.getString(ROVER_MAX_SOL);
            maxDate = manifestObject.getString(ROVER_MAX_DATE);
            totalPhotos = manifestObject.getString(ROVER_TOTAL_PHOTOS);
            Log.i(TAG, status);
            return new RoverManifest(roverIndex, name, launch, land, status, maxSol, maxDate, totalPhotos);
        }
        return null;
    }



}
