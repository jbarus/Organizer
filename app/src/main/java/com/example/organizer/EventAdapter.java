package com.example.organizer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder>
{
    private final ArrayList<Event> events;
    private final OnEventListener onEventListener;

    public EventAdapter(ArrayList<Event> events, OnEventListener onEventListener) {
        this.events = events;
        this.onEventListener = onEventListener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = 80;

        return new EventViewHolder(view, onEventListener, events);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        final Event event = events.get(position);
        if(event == null)
            holder.name.setText("");
        else
        {
            holder.name.setText(event.getName()+" "+ CalendarUtils.formattedTime(event.getStartTime())+" - "+CalendarUtils.formattedTime(event.getEndTime()));
        }



    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public interface OnEventListener{
        void onEventClick(Event event);
    }
}