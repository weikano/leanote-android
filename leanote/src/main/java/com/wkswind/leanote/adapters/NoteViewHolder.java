package com.wkswind.leanote.adapters;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wkswind.leanote.R;
import com.wkswind.leanote.base.BaseViewHolder;
import com.wkswind.leanote.database.Note;

import java.util.Calendar;
import java.util.Locale;

class NoteViewHolder extends BaseViewHolder<Note> {
    private TextView title, date;
    private NoteViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        date = (TextView) itemView.findViewById(R.id.date);
    }

    @Override
    public void bind(Note note) {
        title.setText(note.getTitle());
        date.setText(DateUtils.getRelativeTimeSpanString(date.getContext(), note.getUpdatedTime()));
    }


    public static NoteViewHolder create(Context context, ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

}
