package com.example.learningspanish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 300;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
                // Set the content of the activity to use the activity_main.xml layout file

                setContentView(R.layout.activity_main);

                // Find the view pager that will allow the user to swipe between fragments
                ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
                // Create an adapter that knows which fragment should be shown on each page
                CategoryAdapter adapter = new CategoryAdapter(getSupportFragmentManager());
                // Set the adapter onto the view pager
                viewPager.setAdapter(adapter);
            }

        }