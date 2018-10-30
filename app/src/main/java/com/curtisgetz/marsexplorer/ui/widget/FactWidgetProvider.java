package com.curtisgetz.marsexplorer.ui.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MarsFact;
import com.curtisgetz.marsexplorer.ui.explore.MarsExploreActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.ExploreDetailActivity;
import com.curtisgetz.marsexplorer.ui.main.MainActivity;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.curtisgetz.marsexplorer.utils.RealtimeDatabaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class FactWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String fact) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fact_widget);
        //set textview with fact text
        views.setTextViewText(R.id.widget_fact_body, fact);

        //Intents to allow back navigation
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent marsExploreIntent = new Intent(context, MarsExploreActivity.class);
        Intent intent = new Intent(context, ExploreDetailActivity.class);
        intent.putExtra(context.getString(R.string.explore_index_extra_key), HelperUtils.MARS_FACTS_CAT_INDEX);
        //create pending intent with getActivities to allow back navigation
        PendingIntent pendingIntent = PendingIntent.getActivities(context,
                0,new Intent[] {mainIntent, marsExploreIntent, intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        //set click listener for intent
        views.setOnClickPendingIntent(R.id.widget_click_box, pendingIntent);

        //tell manager to update widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateAllWidgets(Context context, AppWidgetManager appWidgetManager,
                                        int[] appWidgetIds, String fact){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, fact);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //start service to get Fact
        context.startService(new Intent(context, UpdateService.class));
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    public static class UpdateService extends Service {
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            //get todays fact
            getTodaysFact();
            return START_STICKY;
        }

        private void getTodaysFact() {
            //get instance of Realtime Database
            FirebaseDatabase realtimeDatabase = RealtimeDatabaseUtils.getDatabase();
            //get reference to 'facts' node
            DatabaseReference factNode = realtimeDatabase.getReference("facts");
            //get current day
            String currentDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
            //get child for the current day
            DatabaseReference factReference = factNode.child(currentDay);
            //extract Fact data through Listener
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    setupFact(dataSnapshot.getValue(MarsFact.class));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            factReference.addValueEventListener(listener);
        }

        private void setupFact(MarsFact fact) {
            //get fact and update widgets with the shortDescription
            String factText =  fact.getShortDescription();
            ComponentName widget = new ComponentName(this, FactWidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds =manager.getAppWidgetIds(widget);
            updateAllWidgets(this, manager, appWidgetIds, factText);
        }


        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

}

