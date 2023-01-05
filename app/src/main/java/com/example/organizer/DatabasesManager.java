package com.example.organizer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabasesManager extends AppCompatActivity {

    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser firebaseUser;

    public static boolean wasLoaded= false;

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ISO_LOCAL_TIME;


    public static void getDataFromFirebase(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://organizer-ccfd6-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Event newEvent = new Event(dataSnapshot.getKey().toString(),getDateFromString(dataSnapshot.child("Date").getValue().toString()),getTimeFromString(dataSnapshot.child("Time").getValue().toString()),dataSnapshot.child("Flag").getValue().toString());
                    boolean exist = false;
                    for ( Event event : Event.eventsList){
                        if(newEvent.getName() == event.getName()){
                            exist = true;
                        }
                    }
                    if(!exist){
                        Event.eventsList.add(newEvent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void sendDataToDatabase(Event newEvent){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Date",getStringFromDate(newEvent.getDate()));
        hashMap.put("Time",getStringFromTime(newEvent.getTime()));
        hashMap.put("Flag",newEvent.getFlag());
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://organizer-ccfd6-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        myRef.child("Users").child(firebaseUser.getUid()).child(newEvent.getName()).setValue(hashMap);
    }



    private static LocalDate getDateFromString(String string)
    {
        try
        {
            LocalDate localDate;
            return localDate = LocalDate.parse(string, dateFormat);
        }
        catch (NullPointerException e)
        {
            return null;
        }
    }
    private static LocalTime getTimeFromString(String string)
    {
        try
        {
            LocalTime localTime;
            return localTime = LocalTime.parse(string, timeFormat);
        }
        catch (NullPointerException e)
        {
            return null;
        }
    }
    private static String getStringFromDate(LocalDate date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private static String getStringFromTime(LocalTime time)
    {
        if(time == null)
            return null;
        return timeFormat.format(time);
    }
}
