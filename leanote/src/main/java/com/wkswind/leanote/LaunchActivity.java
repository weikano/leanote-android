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

import java.util.Arrays;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkAccount();
    }

    private void checkAccount() {
        AccountManager am = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] account = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            account = am.getAccountsByTypeForPackage(BuildConfig.ACCOUNT_TYPE, getPackageName());
        }else {
            account = am.getAccountsByType(BuildConfig.ACCOUNT_TYPE);
        }
        if(account == null || account.length == 0) {

        }
//        am.getAccountsByTypeForPackage()
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
        return super.onOptionsItemSelected(item);
    }
}
