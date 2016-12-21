package com.wkswind.leanote.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wkswind.leanote.R;
import com.wkswind.leanote.account.AccountUtils;
import com.wkswind.leanote.base.BaseFragment;
import com.wkswind.leanote.database.Note;
import com.wkswind.leanote.database.SqlHelper;
import com.wkswind.leanote.database.SqlUtils;
import com.wkswind.leanote.utils.ObserverImpl;
import com.wkswind.leanote.utils.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2016-12-17.
 */

public class RecentNotesFragment extends BaseFragment {
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recent_nots, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.content);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(R.integer.span_count), OrientationHelper.VERTICAL));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Observable<List<Note>> network = RetrofitUtils.syncNote(AccountUtils.getAuthToken(getActivity()), 0).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).doOnNext(new Consumer<List<Note>>() {
            @Override
            public void accept(List<Note> notes) throws Exception {
                SqlUtils.addNotes(notes);
            }
        });
        Observable<List<Note>> database = SqlUtils.getNotes(20).subscribeOn(Schedulers.io());
        Observable.concat(database, network).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new ObserverImpl<List<Note>>(){
            @Override
            public void onNext(List<Note> value) {
                super.onNext(value);
            }
        });


    }
}
