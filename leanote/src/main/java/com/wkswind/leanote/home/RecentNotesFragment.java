package com.wkswind.leanote.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.wkswind.leanote.adapters.NoteAdapter;
import com.wkswind.leanote.base.BaseFragment;
import com.wkswind.leanote.database.Note;
import com.wkswind.leanote.database.SqlHelper;
import com.wkswind.leanote.database.SqlUtils;
import com.wkswind.leanote.utils.ObserverImpl;
import com.wkswind.leanote.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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
    private SwipeRefreshLayout refresh;
    private NoteAdapter adapter;
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
        Observable.concat(database, network).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).flatMap(new Function<List<Note>, ObservableSource<DiffUtil.DiffResult>>() {
            @Override
            public ObservableSource<DiffUtil.DiffResult> apply(final List<Note> notes) throws Exception {
                return Observable.create(new ObservableOnSubscribe<DiffUtil.DiffResult>() {
                    @Override
                    public void subscribe(ObservableEmitter<DiffUtil.DiffResult> e) throws Exception {
                        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                            @Override
                            public int getOldListSize() {
                                return adapter.getItemCount();
                            }

                            @Override
                            public int getNewListSize() {
                                return notes.size();
                            }

                            @Override
                            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                                return adapter.getItemId(oldItemPosition) == notes.get(newItemPosition).getId();
                            }

                            @Override
                            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                                Note oldItem = adapter.getItem(oldItemPosition);
                                Note newItem = notes.get(newItemPosition);
                                return oldItem != null && newItem != null && oldItem.areContentsTheSame(newItem);
                            }
                        });
                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new ObserverImpl<DiffUtil.DiffResult>(){
            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                refresh.setRefreshing(true);
            }

            @Override
            public void onNext(DiffUtil.DiffResult value) {
                value.dispatchUpdatesTo(adapter);
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
