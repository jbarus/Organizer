package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class EventCreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,  Dialog_window.Dialog_windowListener, CheckDialog.checkDialoglistenet {
    private EditText eventNameET;
    private DatePickerDialog datePickerDialog;
    private Button startingTimeButton;
    private Button endingTimeButton;
    private Button dateButton;
    private TextView flagtextview;
    private Spinner spinner_flag;
    private TextView debug;
    Switch reSwitch;
    private LocalTime time = LocalTime.now();
    private LocalTime startTime = LocalTime.now();
    private LocalTime endTime = LocalTime.now();
    private LocalDate date = LocalDate.now();
    private String flag;
    int repetitionnumber;
    int hour, minute;
    boolean isyesclicked=false;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ISO_LOCAL_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
        initWidgets();
        startTime = LocalTime.now();
        endTime = LocalTime.now();
        initDatePicker();
        dateButton.setText(date.format(dateFormatter));
        startingTimeButton.setText(startTime.format(timeFormatter));
        endingTimeButton.setText(endTime.format(timeFormatter));
        spinner_flag=findViewById(R.id.spinner_flag);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Flagi, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_flag.setAdapter(adapter);
        spinner_flag.setOnItemSelectedListener(this);
        debug=findViewById(R.id.debugtextview);
        reSwitch=findViewById(R.id.switch1);
        reSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked==true)
                {

                    openDialog();
                }


            }
        });
    }

    private void initWidgets() {
        flagtextview =findViewById(R.id.flagtextview);
        eventNameET = findViewById(R.id.eventNameET);
        startingTimeButton = findViewById(R.id.startingTimeButton);
        endingTimeButton = findViewById(R.id.startingTimeButton);
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


    public void popStartTimePicker(View view)
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
                startTime = LocalTime.parse(hour+ ":" + minute,timeFormat);
                startingTimeButton.setText(startTime.format(timeFormatter));
            }
        };


        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener, startTime.getHour(), startTime.getMinute(), true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void popEndTimePicker(View view)
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
                endTime = LocalTime.parse(hour+ ":" + minute,timeFormat);
                endingTimeButton.setText(startTime.format(timeFormatter));
            }
        };


        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener, startTime.getHour(), startTime.getMinute(), true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void saveEventAction(View view) {


          String repetitionnoumbertostring;
          String itostring;

        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, date, startTime,endTime,flag);
        if(!newEvent.isColliding()) {
            if (reSwitch.isChecked() == true) {
                for (int i = 0; i < repetitionnumber; i++) {
                    repetitionnoumbertostring = Integer.toString(repetitionnumber);
                    itostring = Integer.toString(i + 1);

                    newEvent.setName(eventName + " powtórzenie " + itostring + " z " + repetitionnoumbertostring);
                    if (i != 0) {
                        newEvent.setDate(date.plusDays(7));
                    }


                    DatabasesManager.sendDataToDatabase(newEvent);
                    finish();
                }

            } else {

                DatabasesManager.sendDataToDatabase(newEvent);
                finish();
            }

        }
        else
        {
            openChechkDialog();
            if(isyesclicked)
            {
                if (reSwitch.isChecked() == true) {
                    for (int i = 0; i < repetitionnumber; i++) {
                        repetitionnoumbertostring = Integer.toString(repetitionnumber);
                        itostring = Integer.toString(i + 1);

                        newEvent.setName(eventName + " powtórzenie " + itostring + " z " + repetitionnoumbertostring);
                        if (i != 0) {
                            newEvent.setDate(date.plusDays(7));
                        }


                        DatabasesManager.sendDataToDatabase(newEvent);
                        finish();
                    }

                } else {

                    DatabasesManager.sendDataToDatabase(newEvent);
                    finish();
                }

            }
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();

        flag=spinner_flag.getSelectedItem().toString();
        flagtextview.setText(flag);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void openDialog()
    {
        Dialog_window dialog_window= new Dialog_window();
        dialog_window.show(getSupportFragmentManager(),"dialog window");
    }

    @Override
    public void applyText(String repetition) {

        repetitionnumber= Integer.parseInt(repetition);


    }
    public void openChechkDialog()
    {
        CheckDialog checkDialog =new CheckDialog();
        checkDialog.show(getSupportFragmentManager(),"check dialog");
    }

    @Override
    public void onYesclciked() {
        isyesclicked=true;
    }
}