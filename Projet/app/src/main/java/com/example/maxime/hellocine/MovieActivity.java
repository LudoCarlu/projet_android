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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


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
        GetFilmsService.startActionFilm(this);
        IntentFilter intentFilter = new IntentFilter(FILMS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new FilmUpdate(),intentFilter);

        this.rv = findViewById(R.id.rv_film);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        MovieAdapter ma = new MovieAdapter(getFilmFromFile());
        rv.setAdapter(ma);

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
            InputStream is = new FileInputStream(getCacheDir() + "/" + "films.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer, "UTF-8"));
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
        public void onReceive(Context context, Intent intent){
            Toast.makeText(MovieActivity.this,"Telechargement des films terminé !",Toast.LENGTH_SHORT).show();
            MovieAdapter ma = (MovieAdapter) rv.getAdapter();
            ma.setNewMovie(getFilmFromFile());
        }
    }



}
