package com.wkswind.leanote;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wkswind.leanote.account.AccountUtils;
import com.wkswind.leanote.adapters.HomeTabAdapter;
import com.wkswind.leanote.base.BaseActivity;

import io.reactivex.functions.Consumer;

public class HomeActivity extends BaseActivity {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new HomeTabAdapter(getSupportFragmentManager()));
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) pager.getLayoutParams();


        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setElevation(getResources().getDimensionPixelOffset(R.dimen.material_component_elevation_bottom_navigation));
        final int margin = 2 * getResources().getDimensionPixelOffset(R.dimen.default_margin);
        pager.post(new Runnable() {
            @Override
            public void run() {
                pager.setPaddingRelative(0,toolbar.getHeight()+ margin,0, bottomNavigationView.getHeight()+margin);
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
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
                        Toast.makeText(HomeActivity.this, "已登录，进入个人信息", Toast.LENGTH_SHORT).show();
                    } else {
                        AccountUtils.enterLogin(HomeActivity.this);
//                        Toast.makeText(HomeActivity.this, "跳转到登录界面", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
