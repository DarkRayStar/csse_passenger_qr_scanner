package com.example.qrcodescanner;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOPassenger {

    private static DatabaseReference databaseReference;
    private static FirebaseDatabase firebaseDatabase = null;
    private final static String firebaseURL = "https://csse-inspector-qr-app-default-rtdb.firebaseio.com/";

    private DAOPassenger(String userID) {
        databaseReference = DAOPassenger.getFirebaseInstance().getReference().child(userID);
    }

    public static DAOPassenger getInstance(String userID) {
        return new DAOPassenger(userID);
    }

    public static FirebaseDatabase getFirebaseInstance(){
        if(DAOPassenger.firebaseDatabase == null){
            firebaseDatabase = FirebaseDatabase.getInstance(firebaseURL);
        }
        return firebaseDatabase;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return databaseReference.updateChildren(hashMap);
    }

}
