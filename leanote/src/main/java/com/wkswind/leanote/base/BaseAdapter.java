package com.wkswind.leanote.base;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T extends BaseEntity> extends RecyclerView.Adapter<VH> {
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

    public void setDatas(List<T> datas){
        this.datas = datas;
    }

    public DiffUtil.DiffResult diff(final List<T> newData) {
        return DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return getItemCount();
            }

            @Override
            public int getNewListSize() {
                return newData.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return getItemId(oldItemPosition) == newData.get(newItemPosition).getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                T oldItem = getItem(oldItemPosition);
                T newItem = (T) newData.get(newItemPosition);
                if(oldItem != null && newItem != null){
                    if(oldItem instanceof ContentComparator && newItem instanceof ContentComparator){
                        return ((ContentComparator) oldItem).areContentsTheSame(newItem);
                    }
                    return oldItem.equals(newItem);
                }
                return false;
            }
        });
    }
}
