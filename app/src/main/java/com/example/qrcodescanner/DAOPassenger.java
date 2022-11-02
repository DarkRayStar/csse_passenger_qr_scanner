package com.example.qrcodescanner;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOPassenger {

    private static DAOPassenger daoPassenger;
    private DatabaseReference databaseReference;

    private final String firebaseURL = "https://csse-inspector-qr-app-default-rtdb.firebaseio.com/";

    public DAOPassenger(String userID) {
        databaseReference = FirebaseDatabase.getInstance(firebaseURL).getReference().child(userID);
    }

    public static DAOPassenger getInstance(String userID) {
        return new DAOPassenger(userID);
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return databaseReference.updateChildren(hashMap);
    }

}
