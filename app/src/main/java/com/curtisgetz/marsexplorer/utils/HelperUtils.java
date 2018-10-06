package com.curtisgetz.marsexplorer.utils;

import android.content.Context;
import android.icu.text.IDNA;
import android.support.constraint.ConstraintLayout;
import android.util.Log;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.rover_explore.RoverExploreCategory;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public final class HelperUtils {

    private final static String TAG = HelperUtils.class.getSimpleName();

    public final static String PHOTO_PAGER_URL_EXTRA = "photo_url_extra";

    public final static String DEFAULT_SOL_NUMBER = "200";

    //EXPLORE INDICES
    public final static int MARS_EXPLORE_INDEX = 0;
    public final static int CURIOSITY_ROVER_INDEX = 1;
    public final static int OPPORTUNITY_ROVER_INDEX = 2;
    public final static int SPIRIT_ROVER_INDEX = 3;

    public final static int[] ROVER_INDICES = {CURIOSITY_ROVER_INDEX, OPPORTUNITY_ROVER_INDEX, SPIRIT_ROVER_INDEX};

    //MARS EXPLORE CATEGORY INDICES
    public final static int MARS_WEATHER_CAT_INDEX = 100;
    public final static int MARS_FACTS_CAT_INDEX = 101;
    public final static int MARS_FAVORITES_CAT_INDEX = 102;

    public final static int[] MARS_EXPLORE_CATEGORIES =
            {MARS_WEATHER_CAT_INDEX, MARS_FACTS_CAT_INDEX, MARS_FAVORITES_CAT_INDEX};

    //ROVER CATEGORY INDICES
    public final static int ROVER_PICTURES_CAT_INDEX = 0;
    public final static int ROVER_INFO_CAT_INDEX = 1;
    public final static int ROVER_SCIENCE_CAT_INDEX = 2;
    public final static int ROVER_TWEETS_CAT_INDEX = 3;

    //Set available categories for each rover
    private final static int[] CURIOSITY_CATEGORIES = {0,1,2};
    private final static int[] OPPORTUNITY_CATEGORIES = {0,1,2};
    private final static int[] SPIRIT_CATEGORIES = {0,1,2};

    //Sol start for each rover
    public final static int CURIOSITY_SOL_START = 0;
    public final static int OPPORTUNITY_SOL_START = 1;
    public final static int SPIRIT_SOL_START = 1;
    //all rovers have at least 2000 sols. use if any errors getting true max sol
    public final static int DEFAULT_MAX_SOL = 2000;

    //CAMERA INDICES
    public final static int CAM_FHAZ_INDEX = 0;
    public final static int CAM_RHAZ_INDEX = 1;
    public final static int CAM_MAST_INDEX = 2;
    public final static int CAM_CHEMCAM_INDEX = 3;
    public final static int CAM_MAHLI_INDEX = 4;
    public final static int CAM_MARDI_INDEX = 5;
    public final static int CAM_NAVCAM_INDEX = 6;
    public final static int CAM_PANCAM_INDEX = 7;
    public final static int CAM_MINITES_INDEX = 8;

    public final static int[] All_CAMERA_INDICES = {CAM_FHAZ_INDEX, CAM_RHAZ_INDEX,
            CAM_MAST_INDEX, CAM_CHEMCAM_INDEX, CAM_MAHLI_INDEX, CAM_MARDI_INDEX, CAM_NAVCAM_INDEX,
            CAM_PANCAM_INDEX, CAM_MINITES_INDEX};

    public final static int[] CURIOSITY_CAMERA_INDICES = {CAM_FHAZ_INDEX, CAM_RHAZ_INDEX,
            CAM_MAST_INDEX, CAM_CHEMCAM_INDEX, CAM_MAHLI_INDEX, CAM_MARDI_INDEX, CAM_NAVCAM_INDEX};
    public final static int[] OPPORTUNITY_CAMREA_INDICES = {CAM_FHAZ_INDEX, CAM_RHAZ_INDEX,
            CAM_NAVCAM_INDEX, CAM_PANCAM_INDEX, CAM_MINITES_INDEX};
    public final static int[] SPIRIT_CAMERA_INDICES = {CAM_FHAZ_INDEX, CAM_RHAZ_INDEX,
            CAM_NAVCAM_INDEX, CAM_PANCAM_INDEX, CAM_MINITES_INDEX};


    //WEATHER DETAILS INDICES
    public final static int WEATHER_STATUS_INDEX = 0;
    public final static int WEATHER_SOL_INDEX = 1;
    public final static int WEATHER_SEASON_INDEX = 2;
    public final static int WEATHER_MIN_TEMP_INDEX = 3;
    public final static int WEATHER_MAX_TEMP_INDEX = 4;
    public final static int WEATHER_ATMO_INDEX = 5;
    public final static int WEATHER_SUNSET_INDEX = 6;
    public final static int WEATHER_SUNRISE_INDEX = 7;
    public final static int WEATHER_MIN_GRND_TMP_INDEX = 9;
    public final static int WEATHER_MAX_GRND_TMP_INDEX = 10;
    public final static int WEATHER_UNIT_OF_MEASURE_INDEX = 11;
    public final static int WEATHER_TZ_DATA_INDEX = 12;



    public static String getCameraNameByIndex(Context context, int camIndex){
        switch (camIndex){
            case CAM_FHAZ_INDEX:
                return context.getResources().getString(R.string.FHAZ);
            case CAM_RHAZ_INDEX:
                return context.getResources().getString(R.string.RHAZ);
            case CAM_MAST_INDEX:
                return context.getResources().getString(R.string.MAST);
            case CAM_CHEMCAM_INDEX:
                return context.getResources().getString(R.string.CHEMCAM);
            case CAM_MAHLI_INDEX:
                return context.getResources().getString(R.string.MAHLI);
            case CAM_MARDI_INDEX:
                return context.getResources().getString(R.string.MARDI);
            case CAM_NAVCAM_INDEX:
                return context.getResources().getString(R.string.NAVCAM);
            case CAM_PANCAM_INDEX:
                return context.getResources().getString(R.string.PANCAM);
            case CAM_MINITES_INDEX:
                return context.getResources().getString(R.string.MINITES);
            default:
                return context.getResources().getString(R.string.unknown);
        }
    }

    public static String getRoverNameByIndex(Context context, int roverIndex){

        switch (roverIndex){
            case CURIOSITY_ROVER_INDEX:
                return context.getResources().getString(R.string.curiosity_rover);
            case OPPORTUNITY_ROVER_INDEX:
                return context.getResources().getString(R.string.opportunity_rover);
            case SPIRIT_ROVER_INDEX:
                return context.getResources().getString(R.string.spirit_rover);
            default:
                return null;
        }
    }


    public static List<RoverExploreCategory> getExploreCategories(Context context, int exploreIndex){
        return setupCategories(context, MARS_EXPLORE_CATEGORIES, MARS_EXPLORE_INDEX);


    }

    public static List<RoverExploreCategory> getRoverCategories(Context context, int exploreIndex){
        int[] categories;
        switch (exploreIndex){
            case MARS_EXPLORE_INDEX:
                categories = MARS_EXPLORE_CATEGORIES;
                break;
            case CURIOSITY_ROVER_INDEX:
                categories = CURIOSITY_CATEGORIES;
                break;
            case OPPORTUNITY_ROVER_INDEX:
                categories = OPPORTUNITY_CATEGORIES;
                break;
            case SPIRIT_ROVER_INDEX:
                categories = SPIRIT_CATEGORIES;
                break;
            default:
                return null;
        }

        return setupCategories(context, categories, exploreIndex);
    }


    private static List<RoverExploreCategory> setupCategories(Context context, int[] categories, int exploreIndex){
        List<RoverExploreCategory> exploreCategoriesList = new ArrayList<>();


        for (int category : categories) {
            String title = getCategoryTitle(context, category);
            int imageResId = getCategoryImgResId(context, exploreIndex, category);
            exploreCategoriesList.add(new RoverExploreCategory(title, imageResId, category));
        }
        return exploreCategoriesList;
    }


    private static String getCategoryTitle(Context context, int categoryIndex){
        String title  = "";
        switch (categoryIndex){
            case MARS_WEATHER_CAT_INDEX:
                title = context.getString(R.string.mars_weather_category_title);
                break;
            case MARS_FACTS_CAT_INDEX:
                title = context.getString(R.string.mars_facts_category_title);
                break;
            case MARS_FAVORITES_CAT_INDEX:
                title = context.getString(R.string.mars_user_favorites);
                break;
            case ROVER_PICTURES_CAT_INDEX:
                title = context.getString(R.string.rover_picture_category_title);
                break;
            case ROVER_INFO_CAT_INDEX:
                title = context.getString(R.string.rover_information_category_title);
                break;
            case ROVER_SCIENCE_CAT_INDEX:
                title = context.getString(R.string.rover_science_category_title);
        }
        return title;
    }



    private static int getCategoryImgResId(Context context, int exploreIndex, int categoryIndex){
        // Images will have the format = CATEGORY_INDEX
        //ex. Photo category for Curiosity will be 'photos_1'
        String exploreIndexString = "1";//String.valueOf(exploreIndex);
        String resourcePrefix;
        //todo update images
        switch (categoryIndex){
            case MARS_WEATHER_CAT_INDEX:
                resourcePrefix = "photos_";
//                resourcePrefix = context.getString(R.string.mars_weather_res_prefix);
                break;
            case MARS_FACTS_CAT_INDEX:
                resourcePrefix = "photos_";
//                resourcePrefix = context.getString(R.string.mars_facts_res_prefix);
                break;
            case MARS_FAVORITES_CAT_INDEX:
                resourcePrefix = "photos_";
//                resourcePrefix = context.getString(R.string.mars_favorites_res_prefix);
            case ROVER_PICTURES_CAT_INDEX:
                resourcePrefix = context.getString(R.string.photos_category_res_prefix);
                break;
            case ROVER_INFO_CAT_INDEX:
                resourcePrefix = context.getString(R.string.photos_category_res_prefix);
                //resourcePrefix = context.getString(R.string.info_category_res_prefix);
                break;
            case ROVER_SCIENCE_CAT_INDEX:
                resourcePrefix = context.getString(R.string.photos_category_res_prefix);
                //resourcePrefix = context.getString(R.string.science_category_res_prefix);
                break;
            default:
                resourcePrefix = context.getString(R.string.photos_category_res_prefix);
//                resourcePrefix = context.getString(R.string.science_category_res_prefix);
        }
        //todo remove this line once all images are added
        resourcePrefix = context.getString(R.string.photos_category_res_prefix);
        String resName = resourcePrefix + exploreIndexString;
        Log.d(TAG, resName );

        return context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
    }


    private static List<RoverExploreCategory> setupOpportunityCategories(){
        return null;
    }

    private static List<RoverExploreCategory> setupSpiritCategories(){
        return null;
    }

    private static String getMainExploreOptionTitle(Context context, int exploreIndex){
        String mainExploreTitle = "";
        String roverString = context.getString(R.string.rover_string);
        switch (exploreIndex){
            case MARS_EXPLORE_INDEX:
                mainExploreTitle = context.getString(R.string.explore_mars);
                break;
            case CURIOSITY_ROVER_INDEX:
                mainExploreTitle = context.getString(R.string.curiosity_rover);
                mainExploreTitle = mainExploreTitle + " " + roverString;
                break;
            case OPPORTUNITY_ROVER_INDEX:
                mainExploreTitle = context.getString(R.string.opportunity_rover);
                mainExploreTitle = mainExploreTitle + " " + roverString;
                break;
            case SPIRIT_ROVER_INDEX:
                mainExploreTitle = context.getString(R.string.spirit_rover);
                mainExploreTitle = mainExploreTitle + " " + roverString;
                break;
            default:
                break;
        }
        return mainExploreTitle;
    }


    public static String getWeatherLabel(Context context, int weatherIndex){
        switch (weatherIndex){
            case WEATHER_MIN_TEMP_INDEX:
                return context.getString(R.string.weather_min_air_temp);
            case WEATHER_MAX_TEMP_INDEX:
                return context.getString(R.string.weather_max_air_temp);
            case WEATHER_ATMO_INDEX:
                return context.getString(R.string.weather_atmo);
            case WEATHER_SUNSET_INDEX:
                return context.getString(R.string.weather_sunset);
            case WEATHER_SUNRISE_INDEX:
                return context.getString(R.string.weather_sunrise);
            case WEATHER_MIN_GRND_TMP_INDEX:
                return context.getString(R.string.weather_min_ground_temp);
            case WEATHER_MAX_GRND_TMP_INDEX:
                return context.getString(R.string.weather_max_ground_temp);
            default:
                return context.getString(R.string.unknown);

        }
    }

    public static int getWeatherInfoIndex(int weatherIndex){
        switch (weatherIndex){
            case WEATHER_MIN_TEMP_INDEX:
            case WEATHER_MAX_TEMP_INDEX:
                return InformationUtils.AIR_TEMP_INFO;
            case WEATHER_MIN_GRND_TMP_INDEX:
            case WEATHER_MAX_GRND_TMP_INDEX:
                return InformationUtils.GROUND_TEMP_INFO;
            case WEATHER_ATMO_INDEX:
                return InformationUtils.ATMO_INFO;
            case WEATHER_SUNRISE_INDEX:
            case WEATHER_SUNSET_INDEX:
                return InformationUtils.SUNRISE_SUNSET_INFO;
            default:
                return InformationUtils.ERROR_LOADING_INFO;
        }
    }



    public static String capitalizeFirstLetter(String text){
        return text.substring(0,1).toUpperCase() + text.substring(1).toLowerCase();

    }




    // Setup Main Explore Options Below
    //todo change photos

    public static List<MainExploreType> getAllExploreTypes(Context context){
        List<MainExploreType> mainExploreTypes = new ArrayList<>();
        mainExploreTypes.add(createMarsExploreType(context));
        mainExploreTypes.add(createCuriosityExploreType(context));
        mainExploreTypes.add(createOpportunityExploreType(context));
        mainExploreTypes.add(createSpiritExploreType(context));
        return mainExploreTypes;
    }

    private static MainExploreType createMarsExploreType(Context context){
        return new MainExploreType(MARS_EXPLORE_INDEX,
                getMainExploreOptionTitle(context, MARS_EXPLORE_INDEX), R.drawable.explore_main);
    }

    private static   MainExploreType createCuriosityExploreType(Context context){
        return new MainExploreType(CURIOSITY_ROVER_INDEX,
                getMainExploreOptionTitle(context, CURIOSITY_ROVER_INDEX), R.drawable.photos_1);
    }

    private static MainExploreType createOpportunityExploreType(Context context){
        return new MainExploreType(OPPORTUNITY_ROVER_INDEX,
                getMainExploreOptionTitle(context, OPPORTUNITY_ROVER_INDEX), R.drawable.photos_1);
    }

    private static MainExploreType createSpiritExploreType(Context context){
        return new MainExploreType(SPIRIT_ROVER_INDEX,
                getMainExploreOptionTitle(context, SPIRIT_ROVER_INDEX), R.drawable.curiosity_main);
    }



}
