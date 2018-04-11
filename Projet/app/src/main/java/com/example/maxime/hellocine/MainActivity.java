package com.example.maxime.hellocine;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            manager.cancel(1);
        }catch (NullPointerException e){
            System.out.print(e);
        }

        setContentView(R.layout.activity_main);

        final Button button_film = findViewById(R.id.button_film);
        final Button button_github = findViewById(R.id.button_github);

        //final Button button_serie = findViewById(R.id.button_serie);
        super.onCreate(savedInstanceState);


        button_film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,MovieActivity.class);
                startActivity(intent1);
            }

        });

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        button_github.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createNotification();
                }catch (NullPointerException e){
                    System.out.print(e);
                }

                String url = "https://github.com/LudoCarlu/projet_android";
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                startActivity(intent);
            }
        });

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
                new android.support.v7.app.AlertDialog.Builder(MainActivity.this).setMessage(R.string.aboutus_text).show();
                return true;
            case R.id.action_search:
                new android.support.v7.app.AlertDialog.Builder(MainActivity.this).setMessage(R.string.err_search).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private final void createNotification(){
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(this, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setTicker(getString(R.string.titre_notif))
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setContentTitle(getResources().getString(R.string.titre_notif))
                .setContentText(getResources().getString(R.string.desc_notif))
                .setContentIntent(pendingIntent);

        Notification mynotification= builder.build();
        mynotification.vibrate = new long[] {0,200,100,200,100,200};
        mNotification.notify(1, mynotification);

    }


}
