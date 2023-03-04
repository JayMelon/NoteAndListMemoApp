package com.example.noteandlistmemoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavbarImageButtons();
    }

    //Method that initilizes the navButtons
    void initNavbarImageButtons() {
        ImageButton listButton = (ImageButton) findViewById(R.id.noteList_ImageButton);
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_ImageButton);
        boolean ifMainActivity = this.getClass().getSimpleName().equals("MainActivity");
        //If this is mainActivity return true
        if(ifMainActivity){
            listButton.setBackgroundColor(Color.MAGENTA);
            settingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchSettings(v);
                }
            });
        }else{
            settingsButton.setBackgroundColor(Color.MAGENTA);
            listButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchList(v);
                }
            });
        }
    }

    //Method that Launches Settings Activity
    private void launchSettings(View v) {
        Intent i = new Intent(this, NotesSettingsActivity.class);
        i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    //Method that launches Contact List
    private void launchList(View v) {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}