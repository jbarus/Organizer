package com.example.organizer;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView name;
    private final ArrayList<Event> events;
    private final EventAdapter.OnEventListener onEventListener;

    public EventViewHolder(@NonNull View itemView, EventAdapter.OnEventListener onEventListener, ArrayList<Event> events) {
        super(itemView);
        name = itemView.findViewById(R.id.eventCellTV);
        this.events = events;
        this.onEventListener = onEventListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onEventListener.onEventClick();
    }
}
