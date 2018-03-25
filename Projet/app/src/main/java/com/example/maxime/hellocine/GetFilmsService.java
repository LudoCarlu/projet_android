package com.example.maxime.hellocine;

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
        //URL url=null;
        /*try { /** On va récuperer notre fichier JSON avec l'api .. Attention au paramètre */
            /*url= new URL("http://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1&apikey=6da432bf");
            //url = new URL ("http://www.omdbapi.com/?i=tt1480055&apikey=6da432bf");
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(HttpURLConnection.HTTP_OK==conn.getResponseCode()){ /** On va sauvegarder notre JSON dans le cache de l'appli  dans un fichier .json*/
                /*copyInputStreamToFile(conn.getInputStream(),new File(getCacheDir(),"films.json"));
                Log.d("OK","Films downloaded");
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }*/

        JSONParser parser = new JSONParser();
        String url = "http://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1&apikey=6da432bf";
        String result = parser.getJsonFromUrl(url);

        /**Sauvegarde dans le cache de l'appli */
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File(getCacheDir(),"films.json")));
            writer.write(result);
            Log.i("JSONFiles : ",result);
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
