package com.example.maxime.hellocine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludoviccarlu on 06/04/2018.
 */

public class FilmFinder {

    private static FilmFinder FILMFINDER = null;
    private List<Films> filmsList = null;

    private FilmFinder() {}

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
        if(filmsList == null) {
            filmsList = new ArrayList<Films>();
            filmsList.add(f);
        }
        else {
            filmsList.add(f);
        }
    }

}
