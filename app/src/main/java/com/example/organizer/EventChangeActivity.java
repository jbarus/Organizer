package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EventChangeActivity extends AppCompatActivity {
    EditText titleET, dateET, startTimeET, endTimeET, flagET, NotesET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_change);
        initWidgets();
        setContent();
    }

    private void initWidgets() {
        titleET = findViewById(R.id.titleET);
        dateET = findViewById(R.id.dateET);
        startTimeET = findViewById(R.id.startTimeET);
        endTimeET = findViewById(R.id.endTimeET);
        flagET = findViewById(R.id.flagET);
        NotesET = findViewById(R.id.NotesET);
    }

    private void setContent() {
        titleET.setText(Event.selectedEvent.getName());
        dateET.setText(DatabasesManager.getStringFromDate(Event.selectedEvent.getDate()));
        startTimeET.setText(DatabasesManager.getStringFromTime(Event.selectedEvent.getStartTime()));
        endTimeET.setText(DatabasesManager.getStringFromTime(Event.selectedEvent.getEndTime()));
        flagET.setText(Event.selectedEvent.getFlag());
        NotesET.setText(Event.selectedEvent.getNotes());
    }

    public void SaveEvent(View view) {
        String oldName = Event.selectedEvent.getName();
        Event.selectedEvent.setName(titleET.getText().toString());
        Event.selectedEvent.setDate(DatabasesManager.getDateFromString(dateET.getText().toString()));
        Event.selectedEvent.setStartTime(DatabasesManager.getTimeFromString(startTimeET.getText().toString()));
        Event.selectedEvent.setEndTime(DatabasesManager.getTimeFromString(endTimeET.getText().toString()));
        Event.selectedEvent.setFlag(flagET.getText().toString());
        Event.selectedEvent.setNotes(NotesET.getText().toString());
        DatabasesManager.updateDataInDatabase(Event.selectedEvent,oldName);
        finish();
    }
}