package com.curtisgetz.marsexplorer.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.ui.settings.SettingsActivity;

@SuppressLint("Registered")
public class MarsBaseActivity extends AppCompatActivity {


    //have all activities extend this class so they all inflate the settings menu.
    //Some fragments will inflate their own menus in addition
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

