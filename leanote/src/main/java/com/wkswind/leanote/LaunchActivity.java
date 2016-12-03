package com.wkswind.leanote;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

import java.util.Arrays;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LaunchActivity extends BaseActivity {
    Observer<Account[]> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        observer = new Observer<Account[]>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Account[] value) {
                if(value == null || value.length == 0){

                }else {

                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        checkAccount();
    }

    private void checkAccount() {
        AccountUtils.getAccount(this).subscribe(observer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lauch,menu);
        Drawable icon = menu.findItem(R.id.nav_me).getIcon().mutate();
        if(icon != null){
            DrawableCompat.setTint(icon,  ContextCompat.getColor(this, android.R.color.white));
            menu.findItem(R.id.nav_me).setIcon(icon);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.nav_me) {
            AccountUtils.hasAccount(this).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if(aBoolean){
                        Toast.makeText(LaunchActivity.this, "已登录，进入个人信息", Toast.LENGTH_SHORT).show();
                    }else {
                        AccountUtils.enterLogin(LaunchActivity.this);
//                        Toast.makeText(LaunchActivity.this, "跳转到登录界面", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
