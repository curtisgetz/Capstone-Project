package com.curtisgetz.marsexplorer.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    //TODO  Use "DEMO_KEY" for limited use.  If you reach limits you can apply for a key at https://api.nasa.gov/index.html#apply-for-an-api-key
    private static final String NASA_API = Config.MY_API;
    //private static final String NASA_API = "DEMO_KEY";

    //remote config keys
    private final static String PHOTOS_BASE_REMOTE_CONFIG_KEY = "base_rover_photos_url";
    private final static String WEATHER_BASE_REMOTE_CONFIG_KEY = "base_mars_weather_url";
   // private static final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/";
   // private static final String WEATHER_BASE_URL= "https://api.maas2.jiinxt.com/";


    //query parameters.
    private final static String API_KEY = "api_key";
    private final static String PAGE = "page";
    private final static String SOL = "sol";


    //A mission manifest is available for each Rover at /manifests/rover_name.
    // This manifest will list details of the Rover's mission to help narrow down photo queries to the API.

    //manifest + rover
    //api.nasa.gov/mars-photos/api/v1/manifests/curiosity?
    private static final String MANIFEST_PATH = "manifests";

    // photos = base + 'rovers' + rover + 'photos' + ?
    private static final String ROVERS  = "rovers";
    private static final String  PHOTOS = "photos";


    //build URL for requesting rover photos by Sol number(as a String, validated before here)
    public static URL buildPhotosUrl(Context context,int roverIndex, String sol){
        //Get BASE Url from Firebase Remote Config
        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        String baseUrl = firebaseRemoteConfig.getString(PHOTOS_BASE_REMOTE_CONFIG_KEY);
        //get rover name from HelperUtils
        String rover = HelperUtils.getRoverNameByIndex(context, roverIndex);
        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendPath(ROVERS)
                .appendPath(rover)
                .appendPath(PHOTOS)
                .appendQueryParameter(SOL, sol)
                //TODO maybe add option to limit number of photos in a settings screen. If selected then set a static page number here. (1 or 2)
               // .appendQueryParameter(PAGE, String.valueOf(1))
                .appendQueryParameter(API_KEY, NASA_API)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());

        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    //Build URL for requesting rover manifest info
    public static URL buildManifestUrl(Context context, int roverIndex){
        //get rovername from HelperUtils
        String roverName = HelperUtils.getRoverNameByIndex(context,roverIndex);
        //get base url from remote config
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        String baseUrl = remoteConfig.getString(PHOTOS_BASE_REMOTE_CONFIG_KEY);
        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendPath(MANIFEST_PATH)
                .appendPath(roverName)
                .appendQueryParameter(API_KEY, NASA_API)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());

        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    //Build URL for requesting Weather data
    public static URL buildWeatherUrl(){
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        String baseUrl = remoteConfig.getString(WEATHER_BASE_REMOTE_CONFIG_KEY);
        //requesting latest weather just uses base url. Can add sol to url for specific requests
        Uri builtUrl = Uri.parse(baseUrl);
        URL url = null;
        try{
            url = new URL(builtUrl.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildWeatherUrl(int sol){
        //todo get weather within range (maybe can wait)
        URL url = buildWeatherUrl();
        return null;
    }

    //read input from http response
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        //todo change to longer read timeout if user wants all photos. Tie in with preferences.
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        //set connection timeouts
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(10000);
        InputStream inputStream = urlConnection.getInputStream();
        try (Scanner scanner = new Scanner(inputStream)) {
            //try scanning input
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }

    }

}
