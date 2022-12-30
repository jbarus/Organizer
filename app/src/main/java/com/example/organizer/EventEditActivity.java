package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameET;
    private DatePickerDialog datePickerDialog;
    private Button timeButton;
    private Button dateButton;

    private LocalTime time = LocalTime.now();
    private LocalDate date = LocalDate.now();
    int hour, minute;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ISO_LOCAL_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        initDatePicker();
        dateButton.setText(date.format(dateFormatter));
        timeButton.setText(time.format(timeFormatter));


    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        timeButton = findViewById(R.id.timeButton);
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

                dateButton.setText(date.format(dateFormatter));
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


    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                String hour;
                String minute;
                if(selectedHour<10){
                    hour = "0"+Integer.toString(selectedHour);
                }else{
                    hour = Integer.toString(selectedHour);
                }
                if(selectedMinute<10){
                    minute = "0"+Integer.toString(selectedMinute);
                }else{
                    minute = Integer.toString(selectedMinute);
                }
                time = LocalTime.parse(hour+ ":" + minute,timeFormat);
                timeButton.setText(time.format(timeFormatter));
            }
        };


        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener, time.getHour(), time.getMinute(), true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
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