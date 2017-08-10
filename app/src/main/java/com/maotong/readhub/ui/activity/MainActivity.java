package com.maotong.readhub.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maotong.readhub.R;
import com.maotong.readhub.config.Config;
import com.maotong.readhub.event.StatusBarEvent;
import com.maotong.readhub.utils.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;


public class MainActivity extends BaseActivity
        implements  BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ctl_main)
    CoordinatorLayout ctlMain;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;

    private Subscription rxSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setSupportActionBar(toolbar);
        boolean isKitKat = Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
        if (isKitKat)
            ctlMain.setFitsSystemWindows(false);
        setToolBar(null, toolbar, true, true, null);

        getSwipeBackLayout().setEnableGesture(false);

        mNavigationView.setOnNavigationItemSelectedListener(this);
        mNavigationView.setSelectedItemId(R.id.navigation_hot);
        initMenu();


        rxSubscription = RxBus.getDefault().toObservable(StatusBarEvent.class)
                .subscribe(new Action1<StatusBarEvent>() {
                    @Override
                    public void call(StatusBarEvent statusBarEvent) {
                        com.orhanobut.logger.Logger.i("rxbus", statusBarEvent);
                        finish();
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

        Config.TabFragment.onDestroy();
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }

    private void initMenu() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.replace, Config.TabFragment.from(item.getItemId()).fragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(item.getTitle());
        return true;
    }
}
