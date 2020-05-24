package com.example.moviesapi.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesapi.R;
import com.squareup.picasso.Picasso;

public class ShowInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        ImageView imageViewPicture = findViewById(R.id.imageViewPicture);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewRating = findViewById(R.id.textViewRating);
        TextView textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        TextView textViewOverview = findViewById(R.id.textViewOverview);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String title = bundle.getString("title");
            if (title != null) {
                textViewTitle.setText(title);
            }
            String rating = bundle.getString("rating");
            if (title != null) {
                String s = "Popularity: " + rating;
                textViewRating.setText(s);
            }
            String releaseDate = bundle.getString("releaseDate");
            if (title != null) {
                String r = "Release date: " + releaseDate;
                textViewReleaseDate.setText(r);
            }
            String overview = bundle.getString("overview");
            if (title != null) {
                textViewOverview.setText(overview);
            }
            String url = bundle.getString("url");
            if (url != null ) {
                String urlToImage = "https://image.tmdb.org/t/p/w500" + url;
                Picasso.get().load(urlToImage).into(imageViewPicture);
            }
        }
    }
}
