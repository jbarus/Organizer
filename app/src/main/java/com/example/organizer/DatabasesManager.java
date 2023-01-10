package com.example.organizer;

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
                    Event newEvent = new Event(dataSnapshot.getKey().toString(),getDateFromString(dataSnapshot.child("Date").getValue().toString()),getTimeFromString(dataSnapshot.child("StartTime").getValue().toString()),getTimeFromString(dataSnapshot.child("EndTime").getValue().toString()),dataSnapshot.child("Flag").getValue().toString(),dataSnapshot.child("Note").getValue().toString());
                   newEvent.setNotificationID(Integer.parseInt(dataSnapshot.child("NotificationID").getValue().toString()));
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
        hashMap.put("StartTime",getStringFromTime(newEvent.getStartTime()));
        hashMap.put("EndTime",getStringFromTime(newEvent.getEndTime()));
        hashMap.put("Flag",newEvent.getFlag());
        hashMap.put("Note",newEvent.getNotes());
        hashMap.put("NotificationID",Integer.toString(newEvent.getNotificationID()));
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://organizer-ccfd6-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        myRef.child("Users").child(firebaseUser.getUid()).child(newEvent.getName()).setValue(hashMap);
    }

    public static void updateDataInDatabase(Event updatedEvent, String oldName){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://organizer-ccfd6-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(firebaseUser.getUid());
        databaseReference.child(oldName).removeValue();
        sendDataToDatabase(updatedEvent);

    }



    public static LocalDate getDateFromString(String string)
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
    public static LocalTime getTimeFromString(String string)
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
    public static String getStringFromDate(LocalDate date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    public static String getStringFromTime(LocalTime time)
    {
        if(time == null)
            return null;
        return timeFormat.format(time);
    }
}
