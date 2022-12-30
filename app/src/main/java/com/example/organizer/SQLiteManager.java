package com.example.organizer;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.jar.Attributes;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "EventDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Event";
    private static final String COUNTER = "Counter";

    private static final String NAME_FIELD = "name";
    private static final String DATE_FIELD = "date";
    private static final String TIME_FIELD = "time";

    @SuppressLint("SimpleDateFormat")
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ISO_LOCAL_TIME;

    public SQLiteManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(DATE_FIELD)
                .append(" TEXT, ")
                .append(TIME_FIELD)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addNoteToDatabase(Event event)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_FIELD, event.getName());
        contentValues.put(DATE_FIELD, getStringFromDate(event.getDate()));
        contentValues.put(TIME_FIELD, getStringFromTime(event.getTime()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public void populateNoteListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null))
        {
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {

                    String name = result.getString(1);
                    String stringDate = result.getString(2);
                    String stringTime = result.getString(3);
                    LocalDate date = getDateFromString(stringDate);
                    LocalTime time = getTimeFromString(stringTime);
                    Event event = new Event(name,date,time);
                    Event.eventsList.add(event);
                }
            }
        }
    }

    private LocalDate getDateFromString(String string)
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
    private LocalTime getTimeFromString(String string)
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
    private String getStringFromDate(LocalDate date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private String getStringFromTime(LocalTime time)
    {
        if(time == null)
            return null;
        return timeFormat.format(time);
    }
}
