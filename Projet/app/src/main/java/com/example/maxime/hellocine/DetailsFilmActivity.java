package com.example.maxime.hellocine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailsFilmActivity extends AppCompatActivity {

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_film);
        Bundle b = getIntent().getExtras();
        this.id = b.getInt("filmId");

        Films f = FilmFinder.getInstance().getFilmById(id);

        TextView t_titre = findViewById(R.id.details_film_titre);
        t_titre.setText(f.getTitre());

        TextView t_desc = findViewById(R.id.details_film_description);
        t_desc.setText(f.getDesc());

        TextView t_released = findViewById(R.id.details_film_release_date);
        t_released.setText(f.getReleaseDate());

        ImageView t_imgUrl = findViewById(R.id.details_film_image);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500"+f.getImgUrl()).into(t_imgUrl);


    }
}
