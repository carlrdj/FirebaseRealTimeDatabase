package com.rdj.carl.instagramfirebase.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.rdj.carl.instagramfirebase.ArtistActivity;
import com.rdj.carl.instagramfirebase.FirebaseApplication;
import com.rdj.carl.instagramfirebase.R;
import com.rdj.carl.instagramfirebase.model.Artist;

import java.util.ArrayList;

/**
 * Created by SEVEN on 8/2/2017.
 */

public class ArtistsAdapterRecyclerView extends RecyclerView.Adapter<ArtistsAdapterRecyclerView.ArtistViewHolder> {
    private ArrayList<Artist> artists;
    private int resource;
    private Activity activity;
    private FirebaseApplication firebaseApplication;
    /********************************************/
    private static final String ARTISTS_NODE = "Artists";
    private DatabaseReference databaseReference;
    /********************************************/

    public ArtistsAdapterRecyclerView(ArrayList<Artist> artists, int resource, Activity activity) {
        this.artists = artists;
        this.resource = resource;
        this.activity = activity;
        /********************************************/
        firebaseApplication = (FirebaseApplication) activity.getApplicationContext();
        databaseReference = firebaseApplication.getDatabaseReference();
        /********************************************/
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        final Artist artist = artists.get(position);
        holder.tvId.setText(artist.getId_artist());
        holder.tvName.setText(artist.getName());
        holder.tvGenere.setText(artist.getGenere());
        holder.cvArtist.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                /********************************************/
                Toast.makeText(activity, "Touch prolongado", Toast.LENGTH_SHORT).show();
                databaseReference.child(ARTISTS_NODE).child(artist.getId_artist()).removeValue();
                /********************************************/
                return false;
            }
        });
        holder.cvArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ArtistActivity.class);
                intent.putExtra("ID_ARTIST", artist.getId_artist());
                intent.putExtra("NAME", artist.getName());
                intent.putExtra("GENERE", artist.getGenere());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder{
         private CardView cvArtist;
         private TextView tvId;
         private TextView tvName;
         private TextView tvGenere;
         public ArtistViewHolder(View itemView) {
             super(itemView);
             cvArtist = (CardView) itemView.findViewById(R.id.cvArtist);
             tvId = (TextView) itemView.findViewById(R.id.tvId);
             tvName = (TextView) itemView.findViewById(R.id.tvName);
             tvGenere = (TextView) itemView.findViewById(R.id.tvGenere);
         }
     }
}
