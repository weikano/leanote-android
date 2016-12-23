package com.wkswind.leanote.home;

import android.os.Bundle;
import android.os.FileObserver;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.AndroidCharacter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wkswind.leanote.LeanoteApplication;
import com.wkswind.leanote.R;
import com.wkswind.leanote.account.AccountUtils;
import com.wkswind.leanote.adapters.NoteAdapter;
import com.wkswind.leanote.base.BaseAdapter;
import com.wkswind.leanote.base.BaseFragment;
import com.wkswind.leanote.database.Note;
import com.wkswind.leanote.database.SqlUtils;
import com.wkswind.leanote.utils.ObserverImpl;
import com.wkswind.leanote.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
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
    private SwipeRefreshLayout refresh;
    private NoteAdapter adapter;
    private Observable<List<Note>> network;
    private final static int PAGE_SIZE = 7;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notelist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.content);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(R.integer.span_count), OrientationHelper.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new NoteAdapter(getActivity(),new ArrayList<Note>());
        recyclerView.setAdapter(adapter);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                network.subscribe(new ObserverImpl<List<Note>>(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<Note> value) {
                        super.onNext(value);
                        DiffUtil.DiffResult result = adapter.diff(value);
                        adapter.setDatas(value);
                        result.dispatchUpdatesTo(adapter);
                    }
                });
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        network = RetrofitUtils.syncNote(AccountUtils.getAuthToken(getActivity()), PAGE_SIZE).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).doOnNext(new Consumer<List<Note>>() {
            @Override
            public void accept(List<Note> notes) throws Exception {
                LeanoteApplication.getSession().getNoteDao().deleteAll();;
                LeanoteApplication.getSession().getNoteDao().insertInTx(notes);
            }
        });
        Observable<List<Note>> database = SqlUtils.getNotes(PAGE_SIZE).subscribeOn(Schedulers.io());
        Observable.concat(database, network).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).observeOn(Schedulers.io()).map(new Function<List<Note>, List<Note>>() {
            @Override
            public List<Note> apply(List<Note> notes) throws Exception {
                Collections.sort(notes, new Note().getDefaultComparator());
                return notes;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new ObserverImpl<List<Note>>(){
            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                refresh.setRefreshing(true);
            }

            @Override
            public void onNext(final List<Note> notes) {
                super.onNext(notes);
                DiffUtil.DiffResult result = adapter.diff(notes);
                adapter.setDatas(notes);
                result.dispatchUpdatesTo(adapter);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                refresh.setRefreshing(false);
            }

            @Override
            public void onComplete() {
                super.onComplete();
                refresh.setRefreshing(false);
            }
        });

    }

}
