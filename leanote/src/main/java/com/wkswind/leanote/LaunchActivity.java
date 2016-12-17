package com.wkswind.leanote;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.wkswind.leanote.account.AccountUtils;
import com.wkswind.leanote.base.BaseActivity;
import com.wkswind.leanote.database.Note;
import com.wkswind.leanote.utils.RetrofitUtils;
import com.wkswind.leanote.utils.UtilsTest;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LaunchActivity extends BaseActivity {
    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fm = getSupportFragmentManager();
        checkAccount();
//        UtilsTest.gson();
    }

    private void checkAccount() {
        AccountUtils.hasAccount(this).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Toast.makeText(LaunchActivity.this, "已经登录", Toast.LENGTH_SHORT).show();
                } else {
//                    fm.beginTransaction().add(R.id.content, new UnLoginFragment(), UnLoginFragment.class.getSimpleName()).commit();

//                    Toast.makeText(LaunchActivity.this, "没有登录", Toast.LENGTH_SHORT).show();
                }
                fm.beginTransaction().add(R.id.content, new NoteListFragment(), NoteListFragment.class.getSimpleName()).commit();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lauch, menu);
        Drawable icon = menu.findItem(R.id.nav_me).getIcon().mutate();
        DrawableCompat.setTint(icon, ContextCompat.getColor(this, android.R.color.white));
        menu.findItem(R.id.nav_me).setIcon(icon);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_me) {
            AccountUtils.hasAccount(this).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        Toast.makeText(LaunchActivity.this, "已登录，进入个人信息", Toast.LENGTH_SHORT).show();
                    } else {
                        AccountUtils.enterLogin(LaunchActivity.this);
//                        Toast.makeText(LaunchActivity.this, "跳转到登录界面", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
