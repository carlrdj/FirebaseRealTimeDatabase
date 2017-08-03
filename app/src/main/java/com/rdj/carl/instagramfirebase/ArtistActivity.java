package com.rdj.carl.instagramfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.rdj.carl.instagramfirebase.model.Artist;

public class ArtistActivity extends AppCompatActivity {
    private static final String ARTISTS_NODE = "Artists";
    private EditText etName;
    private EditText etGenere;
    private Button bSaveArtist;
    private Boolean ok;
    /************************************/
    private FirebaseApplication firebaseApplication;
    private DatabaseReference databaseReference;
    /************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        firebaseApplication = (FirebaseApplication) getApplicationContext();
        databaseReference = firebaseApplication.getDatabaseReference();

        etName = (EditText) findViewById(R.id.etName);
        etGenere = (EditText) findViewById(R.id.etGenere);
        etName.setText(getIntent().getExtras().getString("NAME"));
        etGenere.setText(getIntent().getExtras().getString("GENERE"));
        bSaveArtist = (Button) findViewById(R.id.bSaveArtist);

        bSaveArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveArtist(getIntent().getExtras().getString("ID_ARTIST"));
            }
        });
    }

    private void saveArtist(String Id_artist) {
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
            Artist artist = new Artist(Id_artist, name, genere);
            //Toast.makeText(firebaseApplication, Id_artist, Toast.LENGTH_SHORT).show();
            databaseReference.child(ARTISTS_NODE).child(Id_artist).setValue(artist);
            finish();
            /******************************/
        }
    }
}
