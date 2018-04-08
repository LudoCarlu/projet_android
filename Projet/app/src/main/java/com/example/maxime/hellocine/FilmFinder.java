package com.example.maxime.hellocine;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludoviccarlu on 06/04/2018.
 */

public class FilmFinder {

    private static FilmFinder FILMFINDER = null;
    private ArrayList<Films> filmsList = null;

    private FilmFinder() {
        if(filmsList == null) {
            filmsList = new ArrayList<Films>();
            Log.i("Film Finder", "Création");

        }
    }

    public static FilmFinder getInstance() {

        if(FILMFINDER == null) {
            FILMFINDER = new FilmFinder();
        }
        return FILMFINDER;
    }

    public Films getFilmById(int id) {

        for(Films f : filmsList) {
            if (f.getId() == id) {
                return f;
            }
        }

        return null;
    }

    public void addFilm (Films f) {
        if(filmsList != null) {
            filmsList.add(f);
            Log.i("Film Finder", "Ajout film");
        }
    }

    public ArrayList<Films> getFilmsList() {
        return this.filmsList;
    }

    public void clearList() {
        filmsList.clear();
    }

}
