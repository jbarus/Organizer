package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameET;
    private TextView eventTimeTV;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private LocalTime time;
    private LocalDate date = LocalDate.now();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        initDatePicker();
        dateButton.setText(date.format(formatter));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));

    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventTimeTV = findViewById(R.id.eventTimeTV);
        dateButton = findViewById(R.id.datePickerButton);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d)
            {
                String year = Integer.toString(y);
                String month;
                String day;
                if((m+1)<10){
                     month ="0" + Integer.toString(m+1);
                }else{
                    month =Integer.toString(m+1);
                }
                if(d<10){
                    day ="0" + Integer.toString(d);
                }else{
                    day =Integer.toString(d);
                }
                date = LocalDate.parse(year+"-"+month+"-"+day,dateFormat);

                dateButton.setText(date.format(formatter));
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void saveEventAction(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, date, time);
        Event.eventsList.add(newEvent);
        sqLiteManager.addNoteToDatabase(newEvent);
        finish();
    }


}