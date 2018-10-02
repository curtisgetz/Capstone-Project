package com.curtisgetz.marsexplorer.utils;

import android.content.Context;
import android.util.Log;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MainExploreType;
import com.curtisgetz.marsexplorer.data.rover_explore.RoverExploreCategory;

import java.util.ArrayList;
import java.util.List;

public final class IndexUtils {

    private final static String TAG = IndexUtils.class.getSimpleName();

    public final static String DEFAULT_SOL_NUMBER = "200";

    //EXPLORE INDICES
    public final static int MARS_EXPLORE_INDEX = 0;
    public final static int CURIOSITY_ROVER_INDEX = 1;
    public final static int OPPORTUNITY_ROVER_INDEX = 2;
    public final static int SPIRIT_ROVER_INDEX = 3;

    public final static int[] ROVER_INDICES = {CURIOSITY_ROVER_INDEX, OPPORTUNITY_ROVER_INDEX, SPIRIT_ROVER_INDEX};

    //ROVER CATEGORY INDICES
    public final static int ROVER_PICTURES_CAT_INDEX = 0;
    private final static int ROVER_INFO_CAT_INDEX = 1;
    private final static int ROVER_SCIENCE_CAT_INDEX = 2;

    //Set available categories for each rover
    private final static int[] CURIOSITY_CATEGORIES = {0,1,2};
    private final static int[] OPPORTUNITY_CATEGORIES = {0,1,2};
    private final static int[] SPIRIT_CATEGORIES = {0,1,2};

    //Sol start for each rover
    public final static int CURIOSITY_SOL_START = 0;
    public final static int OPPORTUNITY_SOL_START = 1;
    public final static int SPIRIT_SOL_START = 1;
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


    public static List<RoverExploreCategory> getExploreCategories(int exploreIndex){
        return null;
    }

    public static List<RoverExploreCategory> getRoverCategories(Context context, int roverIndex){
        int[] categories;
        switch (roverIndex){
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

        return setupCategories(context, categories, roverIndex);
    }


    private static List<RoverExploreCategory> setupCategories(Context context, int[] categories, int roverIndex){
        List<RoverExploreCategory> exploreCategoriesList = new ArrayList<>();


        for (int category : categories) {
            String title = getCategoryTitle(context, category);
            int imageResId = getCategoryImgResId(context, roverIndex, category);
            exploreCategoriesList.add(new RoverExploreCategory(title, imageResId, category));
        }
        return exploreCategoriesList;
    }


    private static String getCategoryTitle(Context context, int categoryIndex){
        String title  = "";
        switch (categoryIndex){
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
