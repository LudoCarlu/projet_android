package com.example.maxime.hellocine;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class SerieActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    System.out.println("home");
                    SerieActivity.this.finish();
                    return true;

                case R.id.navigation_movie:
                    Intent intent2 = new Intent(SerieActivity.this, MovieActivity.class);
                    SerieActivity.this.finish();
                    startActivity(intent2);
                    return true;

                case R.id.navigation_serie:
                    Toast.makeText(SerieActivity.this, R.string.already_here, Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_serie);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
                new AlertDialog.Builder(this).setMessage(R.string.aboutus_text).show();
                return true;
/*            case R.id.item2:
                Toast.makeText(MainActivity.this, "Toast : You press pause", Toast.LENGTH_SHORT).show();
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
