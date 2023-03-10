package com.example.organizer;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

//Adapters provide a binding from an app-specific data set to views that are displayed within a RecyclerView
public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener) {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    //Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (days.size() > 15) {
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        } else {
            layoutParams.height = (int) parent.getHeight();
        }

        return new CalendarViewHolder(view, onItemListener, days);
    }

    @Override
    //Called by RecyclerView to display the data at the specified position.
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        LocalDate date = days.get(position);

        holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
        if (date.equals(CalendarUtils.selectedDate)) {
            holder.parentView.setBackgroundColor(Color.LTGRAY);
        }

        if (date.getMonth().equals(CalendarUtils.selectedDate.getMonth())){
            holder.dayOfMonth.setTextColor(Color.BLACK);
        }
        else{
            holder.dayOfMonth.setTextColor(Color.LTGRAY);
        }

    }

    //Returns the total number of items in the data set held by the adapter.
    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, LocalDate date);
    }
}
