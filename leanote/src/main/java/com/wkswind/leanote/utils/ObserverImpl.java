package com.wkswind.leanote.utils;

import android.widget.Toast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class ObserverImpl<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
        Timber.i("onSubscribe: onThread %1$s", Thread.currentThread().getName());
    }

    @Override
    public void onNext(T value) {
        Timber.i("onNext: onThread %1$s", Thread.currentThread().getName());
    }

    @Override
    public void onError(Throwable e) {
        Timber.i("onError: onThread %1$s", Thread.currentThread().getName());
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
        Timber.i("onComplete: onThread %1$s", Thread.currentThread().getName());
    }
}
