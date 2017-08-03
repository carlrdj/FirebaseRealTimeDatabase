package com.rdj.carl.instagramfirebase;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by SEVEN on 8/2/2017.
 */

public class FirebaseApplication extends Application {

    private DatabaseReference databaseReference;
    @Override
    public void onCreate() {
        super.onCreate();
        //Enable the persistent of data without internet
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public DatabaseReference getDatabaseReference(){
        //Database
        databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference;
    }
}
