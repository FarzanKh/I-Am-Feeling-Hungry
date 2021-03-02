package com.team6.iamfeelinghungry;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DatabaseActivity extends AppCompatActivity {

    private static final String TAG = "DatabaseActivity";
    private TextView textView;
    private DatabaseReference mDatabase;
    private String idUser = "100";
    private String idRestaurant = "300";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        textView = findViewById(R.id.textView);
        mDatabase = FirebaseDatabase.getInstance("https://hungryapp-d791e-default-rtdb.firebaseio.com/").getReference();
    }

    public void basicReadWrite(){
        // [START write_message]
        // Write a message to the database

        // [START read_message]
        // Read from the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);

                // todo: refine this:
                textView.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // [END read_message]

        mDatabase.setValue("Hello, World!");
        // [END write_message]
    }

    public void createNewUser(View view){
        writeNewUser(idUser,"random@gmail.com");
        mDatabase.child("users").child(idUser).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    // todo: fix constraints on textView
                    String value = String.valueOf(task.getResult().getValue());
                    textView.setText(value);
                    Log.w(TAG, value);
                }
            }
        });
    }

    public void updateWithRestaurant(View view){
        writeNewRestaurant(idUser,idRestaurant);
        final String[] json = new String[1];
        mDatabase.child("users").child(idUser).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String value = String.valueOf(task.getResult().getValue());
                    textView.setText(value);
                    Log.w(TAG, value);
                }
            }
        });
        textView.setText(json[0]);
    }

    public void deleteCreatedRestaurant(View view){

    }


    public void writeNewUser(String userId, String email){
        User user = new User(email);
        mDatabase.child("users").child(userId).setValue(user);
    }

    public void writeNewRestaurant(String userId, String restaurantId){
        // get new restaurant entry key
        String key = mDatabase.child("users").child(userId).child("restaurants").push().getKey();
        Map<String,Object> childUpdates= new HashMap<>();
        childUpdates.put("/users/" + userId + "/restaurants/" + key,restaurantId);
        mDatabase.updateChildren(childUpdates);
    }

    public void deleteRestaurant(String userId, String entryId){
        // figure out how to use removeValue
        Map<String,Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + userId + "/restaurants/" + entryId, null);
        mDatabase.updateChildren(childUpdates);
    }


    /*
    strategy:
    1) fetch changes from main and make a new branch
    2) figure out how to connect authentication to database
    3) add simple functionality for a logged in user
        - make simple buttons to add and delete a restaurant for a logged in user
        - make sure correct listeners are applied
    4) add ability to get all restaurants for a user and display
        - have recycle view and adapter and populate by calling database
        - add listening functionality to update whenever new one is added or deleted
     */

    // todo: fix constraints on textView
    // todo: figure out how to delete restaurant
    // todo: connect auth to creating users in database
    // todo: just get title, category, address for dashboard page
    // todo: add child event listener for listening to changes on restaurants for user
    // todo: add a completion callback to notify successfully added restaurant to list
    // todo: figure out how to populate the database, update, etc.
    // todo: add to the dashboard activity
}