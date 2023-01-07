package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventViewActivity extends AppCompatActivity {
    TextView titleTV,dateTV, startTimeTV, endTimeTV, flagTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        initWidgets();
        setContent();
    }

    private void initWidgets() {
        titleTV = findViewById(R.id.titleTV);
        dateTV = findViewById(R.id.dateTV);
        startTimeTV = findViewById(R.id.startTimeTV);
        endTimeTV = findViewById(R.id.endTimeTV);
        flagTV = findViewById(R.id.flagTV);
    }

    private void setContent() {
        titleTV.setText("Tytuł: " + Event.selectedEvent.getName());
        dateTV.setText("Data: " + DatabasesManager.getStringFromDate(Event.selectedEvent.getDate()));
        startTimeTV.setText("Czas rozpoczęcia: " + DatabasesManager.getStringFromTime(Event.selectedEvent.getStartTime()));
        endTimeTV.setText("Czas zakończenia: " + DatabasesManager.getStringFromTime(Event.selectedEvent.getEndTime()));
        flagTV.setText("Flaga: " + Event.selectedEvent.getFlag());
    }

    public void EditEvent(View view) {
        startActivity(new Intent(this, EventChangeActivity.class));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setContent();
    }
}