package com.example.maxime.td0;

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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public RecyclerView rv=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button b=findViewById(R.id.bouton1);
        final TextView text1=findViewById(R.id.text1);

        GetBiersServices.startActionBiers(this);
        IntentFilter intentFilter=new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);

        this.rv = findViewById(R.id.rv_biere);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        BiersAdapter ba = new BiersAdapter(getBiersFromFile());
        rv.setAdapter(ba);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1.setText("Test");
                Intent intent1=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent1); //  PAssage a une autre activité
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu main) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, main);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(MainActivity.this,"Toastt ",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                AlertDialog.Builder builder =new AlertDialog.Builder(this);
                builder.setMessage("Alter dialog")
                        .setTitle("Bonjour");
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public JSONArray getBiersFromFile()  {
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8"));
            }catch (IOException e){
                e.printStackTrace();
                return new JSONArray();
            }catch (JSONException e){
                e.printStackTrace();
                return new JSONArray();
            }
    }

    public static final String BIERS_UPDATE="com.octip.cours.inf4042_11.BIERS_UPDATE";
    public class BierUpdate extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
            Log.d("TAG",getIntent().getAction());
            Toast.makeText(MainActivity.this,"Telechargement terminé",Toast.LENGTH_SHORT).show();
            BiersAdapter ba = (BiersAdapter) rv.getAdapter();
            ba.setNewBiere(getBiersFromFile());
        }
    }
}
