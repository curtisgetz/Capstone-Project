package com.curtisgetz.marsexplorer.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.curtisgetz.marsexplorer.utils.Config.MY_API;

public final class NetworkUtils {


    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/";


    //query parameters.
    private final static String API_KEY = "api_key";
    //    final static String EARTH_DATE = "earth_date";
//    final static String CAMERA = "camera";
    private final static String PAGE = "page";
    private final static String SOL = "sol";


    //Mission Manifest parameters (for future use)
    //A mission manifest is available for each Rover at /manifests/rover_name.
    // This manifest will list details of the Rover's mission to help narrow down photo queries to the API.
    // The information in the manifest includes:

    //manifest + rover
    //api.nasa.gov/mars-photos/api/v1/manifests/curiosity?

    private static final String MANIFEST_URL = "manifests";

    // photos - base + 'rovers' + rover + 'photos' + ?

    private static final String ROVERS  = "rovers";
    private static final String  PHOTOS = "photos";


    //add int sol maybe
    public static URL buildPhotosUrl(Context context,int roverIndex, String sol){
        String rover = IndexUtils.getRoverNameByIndex(context, roverIndex);
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(ROVERS)
                .appendPath(rover)
                .appendPath(PHOTOS)
                .appendQueryParameter(SOL, sol)
                .appendQueryParameter(PAGE, String.valueOf(1))
                .appendQueryParameter(API_KEY, MY_API)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());

        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url );
        return url;
    }


    public static URL buildManifestUrl(Context context, int roverIndex){
        String roverName = IndexUtils.getRoverNameByIndex(context,roverIndex);
        Log.d(TAG, roverName);
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(MANIFEST_URL)
                .appendPath(roverName)
                .appendQueryParameter(API_KEY, MY_API)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());

        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        Log.v(TAG, "Built MANIFEST URI " + url );
        return url;

    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }else{
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }

    }


}
