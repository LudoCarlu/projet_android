package com.example.maxime.hellocine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.widget.RecyclerView;


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

        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {

        try {
           // JSONObject obj= this.movies.getJSONObject(position);
            holder.name.setText(movies.getString("Title"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        if(!this.movies.equals(null)){
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
        public TextView name;

        public MovieHolder(View view) {
            super(view);
            this.name=view.findViewById(R.id.rv_movie_element_name);
        }
    }






}

