package com.example.project;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by User on 3/23/2018.
 */

public class DatabaseProvider {
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private String dbRefString;

    public DatabaseProvider(String ref){
        this.db = FirebaseDatabase.getInstance();
        this.dbRef = db.getReference(ref);
    }
}
