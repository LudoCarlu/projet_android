package com.example.maxime.td0;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.example.maxime.td0.R.layout.rv_bier_element;

/**
 * Created by maxime on 09/03/2018.
 */

public class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder> {

    private JSONArray biers;

    public BiersAdapter(JSONArray biers){
        if (!(biers.equals(null))){
            this.biers = biers;
        }else{
            this.biers = new JSONArray();
        }

    }

    @Override
    public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_bier_element,parent,false);

        return new BierHolder(view);
    }

    @Override
    public void onBindViewHolder(BierHolder holder, int position) {

        try {
            JSONObject obj= this.biers.getJSONObject(position);
            holder.name.setText(obj.getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        if(!this.biers.equals(null)){
            return this.biers.length();
        }else{
            return 0;
        }

    }

    public void setNewBiere(JSONArray tab){
        this.biers=tab;
        notifyDataSetChanged();
    }




    public class BierHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public BierHolder(View view) {
            super(view);
            this.name=view.findViewById(R.id.rv_bier_element_name);
        }
    }






}

