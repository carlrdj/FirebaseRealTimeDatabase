package com.rdj.carl.instagramfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rdj.carl.instagramfirebase.adapter.ArtistsAdapterRecyclerView;
import com.rdj.carl.instagramfirebase.model.Artist;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseApplication firebaseApplication;
    /******************************/
    private static final String ARTISTS_NODE = "Artists";
    private DatabaseReference databaseReference;
    /******************************/
    private Button bAddArtist;
    private EditText etName;
    private EditText etGenere;
    private Boolean ok;
    private ArrayList<Artist> artists = new ArrayList<>();
    private RecyclerView rvArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        etName = (EditText) findViewById(R.id.etName);
        etGenere = (EditText) findViewById(R.id.etGenere);
        firebaseApplication = (FirebaseApplication) getApplicationContext();

        /******************************/
        //Enable the persistent of data without internet
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //Database
        //databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = firebaseApplication.getDatabaseReference();
        /******************************/
        bAddArtist = (Button) findViewById(R.id.bAddArtist);
        bAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createArtist();
            }
        });



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvArtist = (RecyclerView) findViewById(R.id.rvArtist);
        rvArtist.setLayoutManager(linearLayoutManager);
        /******************************/
        databaseReference.child(ARTISTS_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(MainActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                artists.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Artist artist = snapshot.getValue(Artist.class);
                        artists.add(new Artist(artist.getId_artist(), artist.getName(), artist.getGenere()));
                        updateRecyclerView();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /******************************/
        ArtistsAdapterRecyclerView artistsAdapterRecyclerView = new ArtistsAdapterRecyclerView(artists, R.layout.cardview_artist, this);
        rvArtist.setAdapter(artistsAdapterRecyclerView);

    }

    private void updateRecyclerView() {
        ArtistsAdapterRecyclerView artistsAdapterRecyclerView = new ArtistsAdapterRecyclerView(artists, R.layout.cardview_artist, this);
        rvArtist.setAdapter(artistsAdapterRecyclerView);
    }

    /*
        private ArrayList<Artist> builtArtist() {
            ArrayList<Artist> artists = new ArrayList<>();
            artists.add(new Artist("asd", "asd", "asd"));
            artists.add(new Artist("asd", "asd", "asd"));
            artists.add(new Artist("asd", "asd", "asd"));
            artists.add(new Artist("asd", "asd", "asd"));
            Toast.makeText(this, artists.toString(), Toast.LENGTH_SHORT).show();
            return artists;
        }
    */
    public void createArtist(){
        String name = etName.getText().toString();
        String genere = etGenere.getText().toString();

        ok = true;

        if (genere.length()<1){
            ok = false;
            Toast.makeText(this, "Ingrese género musical", Toast.LENGTH_SHORT).show();
        }
        if (name.length()<1){
            ok = false;
            Toast.makeText(this, "Ingrese nombre de artista", Toast.LENGTH_SHORT).show();
        }
        if (ok){
            /******************************/
            Artist artist = new Artist(databaseReference.push().getKey(), name, genere);
            //databaseReference.child("Artist")
            databaseReference.child(ARTISTS_NODE).child(artist.getId_artist()).setValue(artist);
            /******************************/
        }
    }
}
