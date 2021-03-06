package com.example.maxime.hellocine;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.support.v4.content.LocalBroadcastManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */


/**


C'est la classe qui nous permet d'aller chercher notre fichier JSON !


 */
public class GetFilmsService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET_FILM = "com.example.maxime.hellocine.action.GETFILM";
    private String APIKEY = "3ada60c13fe3eb7e9b92c41df28a40b4";
    private String APIURL = "";

    public GetFilmsService() {
        super("GetFilmsService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFilm(Context context) {
        Intent intent = new Intent(context, GetFilmsService.class); /**  Lorsqu'on lance le service il déclenche l'action HandleIntent*/
        intent.setAction(ACTION_GET_FILM);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            handleActionFilms(); /**  Maintenant On lance notre fonction pour aller chercher notre JSON */
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MovieActivity.FILMS_UPDATE));

        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */

    private void handleActionFilms() {
        Log.d("tag","Thread service:"+ Thread.currentThread().getName());

        JSONParser parser = new JSONParser();
        //String url = "http://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1&apikey=6da432bf";
        //String url = "http://www.omdbapi.com/?t=Batman&apikey=6da432bf";
        //https://api.themoviedb.org/3/discover/movie?api_key=3ada60c13fe3eb7e9b92c41df28a40b4&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+APIKEY+"&language=fr-FR&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
        String result = parser.getJsonFromUrl(url);

        /**Sauvegarde dans le cache de l'appli */
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File(getCacheDir(),"films.json")));
            writer.write(result);

        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if ( writer != null)
                    writer.close( );
            }
            catch ( IOException e)
            {
            }
        }


    }

    private void copyInputStreamToFile(InputStream in, File file){
        try{
            OutputStream out=new FileOutputStream(file);
            byte[] buf= new byte[1024];
            int len;
            while ((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
