package com.maotong.readhub.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.maotong.readhub.R;
import com.maotong.readhub.bean.readhub.hot.Datum;
import com.maotong.readhub.bean.readhub.hot.ReadHub;
import com.maotong.readhub.bean.readhub.hot.newdata.DataList;
import com.maotong.readhub.presenter.IReadHubPresenter;
import com.maotong.readhub.presenter.impl.ReadHubPresenterImpl;
import com.maotong.readhub.ui.adapter.ReadHubAdapter;
import com.maotong.readhub.ui.iView.IReadHubFragment;
import com.maotong.readhub.ui.view.DividerItemDecoration;
import com.maotong.readhub.utils.NetWorkUtil;
import com.maotong.readhub.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReadHubHotFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IReadHubFragment {

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder mUnbinder;

    private List<Datum> readHubItems = new ArrayList<>();

    private ReadHubAdapter readHubAdapter;
    private IReadHubPresenter mReadHubPresenter;

    private int currentOffset;
    private int lastOffset;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean loading = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    public ReadHubHotFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        mReadHubPresenter = new ReadHubPresenterImpl(this, getActivity());
    }

    private void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        setSwipeRefreshLayoutColor(swipeRefreshLayout);
        swipeTarget.setLayoutManager(mLinearLayoutManager);
        swipeTarget.setHasFixedSize(true);
        swipeTarget.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        swipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //向下滚动
                {
                    visibleItemCount = mLinearLayoutManager.getChildCount();
                    totalItemCount = mLinearLayoutManager.getItemCount();
                    pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        onLoadMore();
                    }
                }
            }
        });

        readHubAdapter = new ReadHubAdapter((ArrayList<Datum>) readHubItems, getActivity());
        swipeTarget.setAdapter(readHubAdapter);
        mReadHubPresenter.getReadHubFromCache(0);
        if (SharePreferenceUtil.isRefreshOnlyWifi(getActivity())) {
            if (NetWorkUtil.isWifiConnected(getActivity())) {
                onRefresh();
            } else {
                Toast.makeText(getActivity(), R.string.toast_wifi_refresh_data, Toast.LENGTH_SHORT).show();
            }
        } else {
            onRefresh();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mReadHubPresenter.unSubscribe();
        mUnbinder.unbind();
    }

    @Override
    public void onRefresh() {
        currentOffset = 0;
        lastOffset = 0;
        readHubItems.clear();
        //2016-04-05修复Inconsistency detected. Invalid view holder adapter positionViewHolder
        readHubAdapter.notifyDataSetChanged();
        mReadHubPresenter.getReadHub();
    }

    public void onLoadMore() {
        lastOffset = currentOffset;

        if (currentOffset != -1){
            currentOffset = readHubItems.get(readHubItems.size() - 1).getOrder();
        }
        // TODO: 2017/8/5 判空
        mReadHubPresenter.getMoreReadHubData(readHubItems.get(readHubItems.size() - 1).getOrder());
    }

    @Override
    public void showProgressDialog() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidProgressDialog() {
        if (swipeRefreshLayout != null) {//不加可能会崩溃
            swipeRefreshLayout.setRefreshing(false);
            loading = false;
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showError(String error) {

        if (currentOffset != 0){
            if (lastOffset != currentOffset) {
                currentOffset = readHubItems.get(readHubItems.size() - 1).getOrder();
                lastOffset = currentOffset;
            } else {
                lastOffset = -1;
                currentOffset = -1;
            }
        }
        mReadHubPresenter.getReadHubFromCache(currentOffset);
        Snackbar.make(swipeTarget, getString(R.string.common_loading_error) + error, Snackbar.LENGTH_SHORT).setAction(getString(R.string.comon_retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReadHubPresenter.getReadHub();
            }
        }).show();
    }

    @Override
    public void updateList(ReadHub readHubHotItems) {


        lastOffset = currentOffset;
        currentOffset = readHubHotItems.getTopic().getData().get(readHubHotItems.getTopic().getData().size() - 1).getOrder();

        this.readHubItems.addAll(readHubHotItems.getTopic().getData());
        readHubAdapter.notifyDataSetChanged();
    }

    @Override
    public void moreList(DataList moreData) {
        lastOffset = currentOffset;
        currentOffset = moreData.getData().get(moreData.getData().size() - 1).getOrder();
        this.readHubItems.addAll(moreData.getData());
        readHubAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateListFromCache(DataList readHubItems) {
        this.readHubItems.addAll(readHubItems.getData());
        readHubAdapter.notifyDataSetChanged();
    }

    @Override
    public void show(String news) {

    }


}
