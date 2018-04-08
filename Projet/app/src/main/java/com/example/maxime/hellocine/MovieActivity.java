package com.example.maxime.hellocine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MovieActivity extends AppCompatActivity {

    public RecyclerView rv = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    System.out.println("home");
                    MovieActivity.this.finish();
                    return true;

                case R.id.navigation_movie:
                    Toast.makeText(MovieActivity.this, R.string.already_here, Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_serie:
                    Intent intent3 = new Intent(MovieActivity.this, SerieActivity.class);
                    MovieActivity.this.finish();
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_movie);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        GetFilmsService.startActionFilm(this); /** On lance le telechargement du JSON et sa sauvegarde */
        IntentFilter intentFilter = new IntentFilter(FILMS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new FilmUpdate(),intentFilter); /** On informe l'appli qu'on la téléchargé */

        this.rv = findViewById(R.id.rv_film); /** On récupere le recyclerview afin de lui setter du text plus tard */
        rv.setLayoutManager(new GridLayoutManager(this,2));

        /*
        MovieAdapter ma = new MovieAdapter(getFilmFromFile(), new CustomItemClickListener() {
            @Override
            // Pour définir l'action que l'on fait au moment du clic
            public void onItemClick(View v, int position) {
                Log.i("ON CLICK POSITION", String.valueOf(position));
                Films f = FilmFinder.getInstance().getFilmByPosition(position);
                Intent i = new Intent(v.getContext(), DetailsFilmActivity.class);
                i.putExtra("filmId", position);
                v.getContext().startActivity(i);
            }
        });*/

        /** Lancement extraction données du cache et remplissage du film finder */
        initData();

        MovieAdapter ma = new MovieAdapter(MovieActivity.this, FilmFinder.getInstance().getFilmsList(), new CustomItemClickListener() {
            @Override
            /** Action que l'on fait quand on click sur un item du recycler */
            public void onItemClick(View v, int position) {
                int id = FilmFinder.getInstance().getFilmsList().get(position).getId();
                Intent i = new Intent(v.getContext(), DetailsFilmActivity.class);
                i.putExtra("filmId", id);
                v.getContext().startActivity(i);

            }
        });

        rv.setAdapter(ma); /**  On donne le movie Adapter a notre Recycler view */

    }

    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.aboutus:
                new AlertDialog.Builder(MovieActivity.this).setMessage(R.string.aboutus_text).show();
                return true;
/*            case R.id.item2:
                Toast.makeText(MainActivity.this, "Toast : You press pause", Toast.LENGTH_SHORT).show();
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public JSONObject extractJSON()  {
        try {
            JSONParser jsonParser = new JSONParser();
            InputStream is = new FileInputStream(getCacheDir() + "/" + "films.json"); /** va récup le JSON en cache */

            /** Le parseInputStream retourne le String correspondant au JSON */
            return new JSONObject(jsonParser.parseInputStream(is));

        }catch (IOException e){
            e.printStackTrace();
            return new JSONObject();

        }catch (JSONException e){
            e.printStackTrace();
            return new JSONObject();
        }
    }

    /** initialise le film finder */

    public void initData () {

        /** Va chercher dans le cache l'extrait de l'API */
        JSONObject jsonObject = extractJSON();
        JSONArray result = null;
        FilmFinder finder  = FilmFinder.getInstance();

        if (!(jsonObject.equals(null)) && jsonObject.length() > 0) {

            if(finder.getFilmsList().isEmpty() ==  false) {
                finder.getFilmsList().clear();
            }

            try {
                result = jsonObject.getJSONArray("results");

                //Log.i("MActivity", "Results" + result.toString());

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jsonFilm = result.getJSONObject(i);
                    finder.addFilm(
                            new Films(
                                    jsonFilm.getInt("id"),
                                    jsonFilm.getString("title"),
                                    jsonFilm.getString("poster_path"),
                                    jsonFilm.getString("overview"),
                                    jsonFilm.getString("vote_average"),
                                    jsonFilm.getString("release_date")
                            )
                    );
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public static final String FILMS_UPDATE="com.octip.cours.inf4042_11.FILMS_UPDATE";
    public class FilmUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){ /** Receiver : lorsque le telechargement se finit (genre de Listener de notre service*/
            Toast.makeText(MovieActivity.this,"Telechargement des films terminé !",Toast.LENGTH_SHORT).show();
            MovieAdapter ma = (MovieAdapter) rv.getAdapter();
            //ma.setNewMovie(getFilmFromFile()); /** S'il y va une modification de notre fichier, on update notre RecyclerView */
            /** Si on veut modifier le contenu du recycler udpate le film finder */
            ma.setNewData(FilmFinder.getInstance().getFilmsList());
        }
    }



}
