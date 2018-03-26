package com.example.maxime.hellocine;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.AlertDialogLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private JSONObject movies;
    private List<Films> films = null;

    public MovieAdapter(JSONObject movies){
        if (!(movies.equals(null))){
            this.movies = movies;

            films = new ArrayList<Films>();

            try {
                films.add(new Films(
                        movies.getString("Title"),
                        movies.getString("Year"),
                        movies.getString("Released"),
                        movies.getString("Runtime"),
                        movies.getString("Genre"),
                        movies.getString("Director"),
                        movies.getString("Writer"),
                        movies.getString("Actors"),
                        movies.getString("Plot"),
                        movies.getString("Language"),
                        movies.getString("Country"),
                        movies.getString("Awards"),
                        movies.getString("Poster"),
                        movies.getString("imdbID"),
                        movies.getString("Type"),
                        movies.getString("DVD"),
                        movies.getString("BoxOffice"),
                        movies.getString("Production")
                )
            );
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else{
            this.movies = new JSONObject();
        }
    }


    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_movie_element,parent,false);
        /**  On récupere notre vue pour afficher un ELEMENT du JSON (sa structure se trouve dans res/layout/rv_movie_element */
        return new MovieHolder(view);
    }

    @Override
    /** Applique une donnée a une vue */
    public void onBindViewHolder(MovieHolder holder, int position) {

        //try {
            if(!this.films.isEmpty()) {
                Films f = films.get(position);
                holder.name.setText(f.getTitle());
                holder.description.setText(f.getPlot());

                new DownLoadImageTask(holder.img).execute(f.getPoster());
           }
            //holder.name.setText(movies.getString("Title")); /** Pour chaque element on set notre TextView en récupérant l'attribut Title du JSON*/
            /** c'est normalement ici qu'on itére avec la position  quand on scroll */
       // } catch (JSONException e) {
           // e.printStackTrace();
        //}


    }

    @Override
    public int getItemCount() {

        //if(!this.movies.equals(null)){ /** Compte le nombre d'élément de notre Recyclwer View */
        //return this.movies.length();
        //}else{
        //   return 0;
        //}
        if(!films.isEmpty()) {
            return films.size();
        }
        return 0;

    }

    public void setNewMovie(JSONObject tab){
        this.movies=tab;
        notifyDataSetChanged();
    }


    public class MovieHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private ImageView img;
        private TextView description;

        public MovieHolder(final View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.rv_movie_element_name);
            this.img = (ImageView) itemView.findViewById(R.id.rv_movie_element_img);
            this.description = (TextView) itemView.findViewById(R.id.rv_movie_element_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle(name.toString())
                            .setMessage(description.toString())
                            .show();
                }
            });
        }


    }

    /** Pour download l'image du film */
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap img = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();
                img = BitmapFactory.decodeStream(is);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return img;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }









}

