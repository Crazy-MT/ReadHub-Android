package com.maotong.readhub.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.viewpager.PullToRefreshViewPager;
import com.maotong.readhub.R;
import com.maotong.readhub.bean.readhub.hot.Datum;
import com.maotong.readhub.bean.readhub.hot.ReadHub;
import com.maotong.readhub.bean.readhub.hot.newdata.DataList;
import com.maotong.readhub.config.Config;
import com.maotong.readhub.presenter.IReadHubActivityPresenter;
import com.maotong.readhub.presenter.impl.ReadHubActivityPresenterImpl;
import com.maotong.readhub.ui.fragment.ReadHubVPFragment;
import com.maotong.readhub.ui.iView.IReadHub;
import com.maotong.readhub.utils.CacheUtil;
import com.maotong.readhub.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadHubActivity extends BaseActivity implements IReadHub , PullToRefreshBase.OnRefreshListener2<ViewPager> {

    public static final String POSITION = "position";
    @BindView(R.id.ctl_main)
    RelativeLayout ctlMain;

    @BindView(R.id.viewpager)
    PullToRefreshViewPager mRefreshViewPager;

    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;

    private ViewPager mPager;
    private int mStartPosition = 0;

    private List<Datum> readHubHotItems;

    private IReadHubActivityPresenter mHubPresenter;

    private SharedPreferences mSharedPreferences;

    private FragmentAdapter mAdapter;

    private int mLastPosition = 0;
    private CacheUtil mCacheUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_hub);
        mCacheUtil = CacheUtil.get(this);
        mHubPresenter = new ReadHubActivityPresenterImpl(this, this);
        ButterKnife.bind(this);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        mSharedPreferences = getSharedPreferences(SharePreferenceUtil.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        boolean isKitKat = Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
        if (isKitKat)
            ctlMain.setFitsSystemWindows(false);
        setToolBar(null, toolbar, true, true, null);

        getSwipeBackLayout().setEnableGesture(false);
        String json = mCacheUtil.getAsString(Config.READHUB);
        Datum[] dataList = new Gson().fromJson(json, Datum[].class);
        readHubHotItems = new ArrayList<>();
        for (Datum data : dataList) {
            readHubHotItems.add(data);
        }
        mStartPosition = getIntent().getIntExtra(POSITION,0);
        mHubPresenter.loadView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHubPresenter.unSubscribe();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void loadViewPager() {
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), readHubHotItems, readHubHotItems.size());
        mPager = mRefreshViewPager.getRefreshableView();

        mPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        mPager.setOffscreenPageLimit(6);
        mPager.setAdapter(mAdapter);
        //viewPager.setPageTransformer(true, new ScalePageTransformer());
        mRefreshViewPager.setOnRefreshListener(this);
        mPager.setCurrentItem(mStartPosition);

        mTabLayout.setupWithViewPager(mPager,true);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void completeRefresh() {
        mRefreshViewPager.onRefreshComplete();
    }

    @Override
    public void showError(String errorMessage) {
        new AlertDialog.Builder(this).setMessage(getString(R.string.common_loading_error)).setCancelable(true).show();
    }

    @Override
    public void updateList(ReadHub readHub) {
        readHubHotItems.clear();
        readHubHotItems.addAll(readHub.getTopic().getData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void moreList(DataList readHubHot) {
        readHubHotItems.addAll(readHubHot.getData());
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), readHubHotItems, readHubHotItems.size());
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(mLastPosition);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ViewPager> refreshView) {
        mHubPresenter.refresh();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ViewPager> refreshView) {
        mLastPosition = mPager.getCurrentItem();
        mHubPresenter.loadMore(readHubHotItems.get(readHubHotItems.size() - 1).getOrder());
    }


    private static class FragmentAdapter extends FragmentStatePagerAdapter {
        List<Fragment> mFragments;
        List<Datum> mReadHubLists;
        int mCount;


        FragmentAdapter(FragmentManager supportFragmentManager, List<Datum> albumLists, int count) {
            super(supportFragmentManager);
            mFragments = new ArrayList<>();
            mCount = count;
            mReadHubLists = albumLists;

            for (int i = 0; i < mReadHubLists.size(); i++) {
                Datum album = mReadHubLists.get(i);
                mFragments.add(ReadHubVPFragment.newInstance(album));
            }
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            mFragments.clear();
            for (int i = 0; i < mReadHubLists.size(); i++) {
                Datum album = mReadHubLists.get(i);
                mFragments.add(ReadHubVPFragment.newInstance(album));
            }
        }
    }
}
