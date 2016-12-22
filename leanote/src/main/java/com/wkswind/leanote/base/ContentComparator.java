package com.wkswind.leanote.base;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Administrator on 2016-12-21.
 */

public interface ContentComparator<T> {
    public boolean areContentsTheSame(@NonNull T item);
    public Comparator<T> getDefaultComparator();
}
