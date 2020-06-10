package com.example.learningspanish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDetails extends AppCompatActivity {
    public static final String EXTRA_RESOURCE_ID="image";
    public static final String EXTRA_SPANISH_TEXT = "spanish";
    public static final String EXTRA_ENGLISH_TEXT= "english";
    public static final String EXTRA_DESCRIPTION_TEXT = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        TextView spTextView = findViewById(R.id.spanish_text_id);
        spTextView.setText(getIntent().getStringExtra(EXTRA_SPANISH_TEXT));

        TextView enTextView = findViewById(R.id.english_text_id);
        enTextView.setText(getIntent().getStringExtra(EXTRA_ENGLISH_TEXT));

        TextView desTextView = findViewById(R.id.description_text_id);
        desTextView.setText(getIntent().getStringExtra(EXTRA_DESCRIPTION_TEXT));

        Intent intent = getIntent();

        ImageView imageView = findViewById(R.id.image_detail_id);
        imageView.setImageResource(intent.getIntExtra(EXTRA_RESOURCE_ID,0));

    }
}