package com.wkswind.leanote.base;

import android.support.v4.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2016-12-3.
 */

public class BaseFragment extends Fragment {
    private CompositeDisposable ds = new CompositeDisposable();
    protected void addDisposable(Disposable d){
        ds.add(d);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ds.dispose();
    }
}
