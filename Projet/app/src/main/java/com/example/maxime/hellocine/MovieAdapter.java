package com.example.maxime.hellocine;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private JSONObject movies;

    public MovieAdapter(JSONObject movies){
        if (!(movies.equals(null))){
            this.movies = movies;
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
    public void onBindViewHolder(MovieHolder holder, int position) {

        try {

            //JSONArray jsonArray = movies.getJSONArray("Title");

            /*for(int i=0; i < jsonArray.length(); i++) {
                list.add(jsonArray.get(i).toString());
            }*/

            Log.i("JSON empty ?",movies.toString());

            holder.name.setText(movies.get("Title").toString());
            //holder.name.setText(movies.get(position).toString());
           // JSONObject obj= this.movies.getJSONObject(position);
            //holder.name.setText(movies.getString("Title")); /** Pour chaque element on set notre TextView en récupérant l'attribut Title du JSON*/
            /** c'est normalement ici qu'on itére avec la position  quand on scroll */
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        if(!this.movies.equals(null)){ /** Compte le nombre d'élément de notre Recyclwer View */
            return this.movies.length();
        }else{
            return 0;
        }

    }

    public void setNewMovie(JSONObject tab){
        this.movies=tab;
        notifyDataSetChanged();
    }




    public class MovieHolder extends RecyclerView.ViewHolder{
        private TextView name;

        public MovieHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.rv_movie_element_name);
        }
    }









}

