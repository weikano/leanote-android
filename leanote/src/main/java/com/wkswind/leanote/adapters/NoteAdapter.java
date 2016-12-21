package com.wkswind.leanote.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wkswind.leanote.database.Note;

import java.util.List;

/**
 * Created by Administrator on 2016-12-21.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private Context context;
    private List<Note> datas;
    public NoteAdapter(Context context, List<Note> datas){
        this.context = context;
        this.datas = datas;
    }
    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NoteViewHolder.create(context, parent, viewType);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bind(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
