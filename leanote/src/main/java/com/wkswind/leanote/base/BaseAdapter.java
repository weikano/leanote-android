package com.wkswind.leanote.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016-12-21.
 */

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {
    private List<T> datas;
    protected Context context;

    public BaseAdapter(Context context, List<T> datas){
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public T getItem(int position){
        if(position > getItemCount() - 1){
            return null;
        }
        return datas.get(position);
    }
}
