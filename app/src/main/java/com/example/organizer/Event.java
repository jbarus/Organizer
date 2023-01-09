package com.example.organizer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {
    public static ArrayList<Event> eventsList = new ArrayList<>();
    public static Event selectedEvent;

    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList) {
            if (event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList) {
            int eventHour = event.startTime.getHour();
            int cellHour = time.getHour();
            if (event.getDate().equals(date) && eventHour == cellHour)
                events.add(event);
        }

        return events;
    }

    private String name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String flag;
    private String Notes;

    public Event(String name, LocalDate date, LocalTime startTime, LocalTime endTime, String flag, String Notes) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.flag = flag;
        this.Notes=Notes;
    }


    public boolean isColliding()
    {

        boolean temp=false;
        for(int i=0;i<eventsList.size();i++)
        {

           if(temp)
           {
               break;
           }
            if(this.date.isEqual(eventsList.get(i).date))
            {


                if(this.startTime.isAfter(eventsList.get(i).endTime) && this.endTime.isAfter(eventsList.get(i).endTime))
                {
                    temp=false;
                }
                else if(this.startTime.isBefore(eventsList.get(i).startTime) && this.endTime.isBefore(eventsList.get(i).startTime))
                {
                    temp=true;
                }
                else
                {
                        temp=false;
                }

//                if(this.endTime.isAfter(eventsList.get(i).startTime)&&this.endTime.isBefore(eventsList.get(i).endTime))
//                {
//                    temp=true;
//                }
//                else if(this.startTime.isAfter(eventsList.get(i).startTime)&&this.endTime.isBefore(eventsList.get(i).endTime))
//                {
//                    temp=true;
//                }
//                else if(this.startTime.isBefore(eventsList.get(i).endTime)&&this.endTime.isAfter(eventsList.get(i).endTime))
//                {
//                    temp=true;
//                }
            }
            else
            {
                temp=false;
            }
        }
        return temp;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() { return Notes; }

    public void setNotes(String Notes) {this.Notes = Notes; }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public LocalTime getEndTime() {return endTime;}

    public void setEndTime(LocalTime endTime) {this.endTime = endTime;}

}

