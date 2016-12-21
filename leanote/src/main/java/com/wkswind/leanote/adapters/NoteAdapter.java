package com.wkswind.leanote.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wkswind.leanote.base.BaseAdapter;
import com.wkswind.leanote.database.Note;

import java.util.List;

/**
 * Created by Administrator on 2016-12-21.
 */

public class NoteAdapter extends BaseAdapter<NoteViewHolder, Note> {

    public NoteAdapter(Context context, List<Note> datas){
        super(context, datas);
    }
    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NoteViewHolder.create(context, parent, viewType);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        if(position > getItemCount() - 1){
            return RecyclerView.NO_ID;
        }
        return getItem(position).getId();
    }
}
