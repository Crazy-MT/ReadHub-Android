package com.maotong.readhub.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maotong.readhub.BuildConfig;
import com.maotong.readhub.R;
import com.maotong.readhub.bean.UpdateItem;
import com.maotong.readhub.config.Config;
import com.maotong.readhub.event.StatusBarEvent;
import com.maotong.readhub.presenter.IMainPresenter;
import com.maotong.readhub.presenter.impl.MainPresenterImpl;
import com.maotong.readhub.ui.iView.IMain;
import com.maotong.readhub.utils.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;


public class MainActivity extends BaseActivity
        implements IMain, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ctl_main)
    CoordinatorLayout ctlMain;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;

    private IMainPresenter IMainPresenter;
    private Subscription rxSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setSupportActionBar(toolbar);
        IMainPresenter = new MainPresenterImpl(this);
        boolean isKitKat = Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
        if (isKitKat)
            ctlMain.setFitsSystemWindows(false);
        setToolBar(null, toolbar, true, true, null);

        getSwipeBackLayout().setEnableGesture(false);

        mNavigationView.setOnNavigationItemSelectedListener(this);
        mNavigationView.setSelectedItemId(R.id.navigation_hot);
        initMenu();
        IMainPresenter.checkUpdate();

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
        IMainPresenter.unSubscribe();
    }

    private void initMenu() {
    }



    @Override
    public void showUpdate(final UpdateItem updateItem) {
        if (updateItem.getVersionCode() > BuildConfig.VERSION_CODE)
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getString(R.string.update_title))
                    .setMessage(String.format(getString(R.string.update_description), updateItem.getVersionName(), updateItem.getReleaseNote()))
                    .setPositiveButton(getString(R.string.update_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(updateItem.getDownloadUrl())));
                        }
                    })
                    .setNegativeButton(R.string.common_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
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
