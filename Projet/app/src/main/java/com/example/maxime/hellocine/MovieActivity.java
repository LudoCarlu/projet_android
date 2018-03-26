package com.example.maxime.hellocine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
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


public class MovieActivity extends AppCompatActivity {


    public RecyclerView rv=null;

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
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)); /** On lui indique comment afficher nos éléments */
        MovieAdapter ma = new MovieAdapter(getFilmFromFile()); /** on va chercher notre fichier JSON et on le donne a MovieAdapter pour qu'il le parse */
        /**  Il va parser le JSON dans la fonction getFilmFromFile() qui va retourner notre tableau JSON, voir en dessous !
         * Logiquement il retourne un JSONArray mais comment c'est pas itérable je lui fait retourner UN UNIQUE OBJECT : JSONObject */

        rv.setAdapter(ma); /**  On donne le movie Adapter a notre Recycler view */

    }

    public boolean onCreateOptionsMenu(Menu menu) {
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


    public JSONObject getFilmFromFile()  {
        try {
            JSONParser jsonParser = new JSONParser();
            InputStream is = new FileInputStream(getCacheDir() + "/" + "films.json"); /**  va récup le JSON en cache */
            //byte[] buffer = new byte[is.available()];
            //is.read(buffer);
            //is.close();
            //Log.i("JSON Files",new String(buffer, "UTF-8"));
            /** Le parseInputStream retourne le String correspondant au JSON */
            return new JSONObject(jsonParser.parseInputStream(is));
            //new JSONObject(new String(buffer, "UTF-8")); /** Retourne l'objet JSON */

        }catch (IOException e){
            e.printStackTrace();
            return new JSONObject();

        }catch (JSONException e){
            e.printStackTrace();
            return new JSONObject();
        }
    }


    public static final String FILMS_UPDATE="com.octip.cours.inf4042_11.FILMS_UPDATE";
    public class FilmUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){ /** Receiver : lorsque le telechargement se finit (genre de Listener de notre service*/
            Toast.makeText(MovieActivity.this,"Telechargement des films terminé !",Toast.LENGTH_SHORT).show();
            MovieAdapter ma = (MovieAdapter) rv.getAdapter();
            ma.setNewMovie(getFilmFromFile()); /** S'il y va une modification de notre fichier, on update notre RecyclerView */
        }
    }



}
