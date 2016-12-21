package com.wkswind.leanote.database;

import android.content.Context;

import com.wkswind.leanote.LeanoteApplication;

import org.greenrobot.greendao.query.DeleteQuery;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;

public class SqlUtils {
    

    public static Observable<List<Note>> getNotes(final int size){
        return Observable.defer(new Callable<ObservableSource<List<Note>>>() {
            @Override
            public ObservableSource<List<Note>> call() throws Exception {
                return Observable.create(new ObservableOnSubscribe<List<Note>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<Note>> e) throws Exception {
                        e.onNext(LeanoteApplication.getSession().getNoteDao().queryBuilder().orderDesc(NoteDao.Properties.UpdatedTime).limit(size).list());
                        e.onComplete();
                    }
                });
            }
        });
    }

    public static Observable<Boolean> deleteBoteById(final Context context ,final long id){
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                LeanoteApplication.getSession().getNoteDao().deleteByKey(id);
                e.onNext(true);
                e.onComplete();
            }
        });
    }

    public static Observable<Boolean> deleteBoteByNotebook(final Context context ,final long notebookId){
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                DeleteQuery<Note> delete = LeanoteApplication.getSession().getNoteDao().queryBuilder().where(NoteDao.Properties.NotebookId.eq(notebookId)).buildDelete();
                delete.executeDeleteWithoutDetachingEntities();
                e.onNext(true);
                e.onComplete();
            }
        });
    }

    public static Observable<Boolean> deleteAll(final Context context){
        return Observable.defer(new Callable<ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> call() throws Exception {
                return Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                        LeanoteApplication.getSession().getNoteDao().deleteAll();
                        e.onNext(true);
                        e.onComplete();
                    }
                });
            }
        });
    }

    public static Observable<Boolean> addNote( final Note note){
        return Observable.defer(new Callable<ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> call() throws Exception {
                return Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                        e.onNext(LeanoteApplication.getSession().getNoteDao().insert(note) != -1);
                        e.onComplete();
                    }
                });
            }
        });
    }

    public static Observable<Boolean> addNotes( final List<Note> notes){
        return Observable.defer(new Callable<ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> call() throws Exception {
                return Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                        LeanoteApplication.getSession().getNoteDao().insertInTx(notes);
                        e.onNext(true);
                        e.onComplete();
                    }
                });
            }
        });
    }

    

}
