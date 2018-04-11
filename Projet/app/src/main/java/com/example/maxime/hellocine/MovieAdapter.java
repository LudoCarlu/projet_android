package com.example.maxime.hellocine;
import android.content.Context;
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
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private Context mContext = null;
    private ArrayList<Films> data = null;

    // Pour faire passer le détail dans l'autre activité
    private CustomItemClickListener listener;


    public MovieAdapter(Context mContext, ArrayList<Films> data, CustomItemClickListener listener) {

        this.data = data;
        this.listener = listener;
        this.mContext = mContext;

    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_movie_element,parent,false);
        /**  On récupere notre vue pour afficher un ELEMENT du JSON (sa structure se trouve dans res/layout/rv_movie_element */
        final MovieHolder mv = new MovieHolder(view);

        /** On définit le custom listener qui sera utiliser pour afficher les détails au clic sur le film
         * Ce listener est une interface que l'on définit dans l'appel au constructeur du movie adapter (dans MovieActivity)
         * */

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, mv.getPosition());
            }
        });

        return mv;
    }

    @Override
    /** Applique une donnée a une vue */
    public void onBindViewHolder(MovieHolder holder, int position) {

        holder.bind(data.get(position).getTitre(), data.get(position).getImgUrl());
    }

    @Override
    public int getItemCount() {

        if(data != null) {
            return data.size();
        }
        else {
            return 0;
        }
    }


    public void setNewData (ArrayList<Films> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private ImageView img;

        public MovieHolder(final View itemView) {

            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.rv_movie_element_name);
            this.img = (ImageView) itemView.findViewById(R.id.rv_movie_element_img);

        }

        public void bind (String name, String imgUrl) {

            this.name.setText(name);
            // Glide library to download and load images
            Glide.with(img.getContext()).load("https://image.tmdb.org/t/p/w500"+imgUrl).into(img);

        }

    }









}

