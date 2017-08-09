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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.maotong.readhub.R;
import com.maotong.readhub.bean.readhub.tech.Datum;
import com.maotong.readhub.bean.readhub.tech.ReadHubTech;
import com.maotong.readhub.bean.readhub.tech.newdata.DataList;
import com.maotong.readhub.presenter.IReadHubPresenter;
import com.maotong.readhub.presenter.impl.ReadHubTechPresenterImpl;
import com.maotong.readhub.ui.adapter.ReadHubTechAdapter;
import com.maotong.readhub.ui.iView.IReadHubTechFragment;
import com.maotong.readhub.ui.view.DividerItemDecoration;
import com.maotong.readhub.utils.NetWorkUtil;
import com.maotong.readhub.utils.SharePreferenceUtil;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReadHubTechFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IReadHubTechFragment {

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.readhub)
    EditText et;

    private Unbinder mUnbinder;

    private List<Datum> readHubItems = new ArrayList<>();

    private ReadHubTechAdapter readHubAdapter;
    private IReadHubPresenter mReadHubPresenter;

    private String currentOffset;
    private String lastOffset;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean loading = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    public ReadHubTechFragment() {
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
        mReadHubPresenter = new ReadHubTechPresenterImpl(this, getActivity());
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

        readHubAdapter = new ReadHubTechAdapter((ArrayList<Datum>) readHubItems, getActivity());
        swipeTarget.setAdapter(readHubAdapter);
        mReadHubPresenter.getReadHubFromCache("");
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
        currentOffset = "";
        lastOffset = "";
        readHubItems.clear();
        //2016-04-05修复Inconsistency detected. Invalid view holder adapter positionViewHolder
        readHubAdapter.notifyDataSetChanged();
        mReadHubPresenter.getReadHub();
    }

    public void onLoadMore() {
        lastOffset = currentOffset;

        if (!currentOffset.equals("error")){
            currentOffset = getTime(readHubItems.get(readHubItems.size() - 1).getPublishDate());
        } 
        mReadHubPresenter.getMoreReadHubData(currentOffset);
    }

    private String getTime(String tzTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = df.parse(tzTime);
            Logger.e(date.getTime()+"");
            return date.getTime()+"";

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
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

        if (!currentOffset.equals("")){
            if (!lastOffset.equals(currentOffset) ){
                currentOffset = getTime(readHubItems.get(readHubItems.size() - 1).getPublishDate());
                lastOffset = currentOffset;
            } else {
                lastOffset = "error";
                currentOffset = "error";
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
    public void updateList(ReadHubTech readHubHotItems) {


        lastOffset = currentOffset;
        List<Datum> datum = readHubHotItems.getTimeline().getItems().getTech().getData();
        currentOffset = getTime(datum.get(datum.size() - 1).getPublishDate());

        this.readHubItems.addAll(datum);
        readHubAdapter.notifyDataSetChanged();
    }

    @Override
    public void moreList(DataList moreData) {
        lastOffset = currentOffset;
        currentOffset = getTime(moreData.getData().get(moreData.getData().size() - 1).getPublishDate());

        List<com.maotong.readhub.bean.readhub.tech.newdata.Datum> datumTemp = moreData.getData();
        for (com.maotong.readhub.bean.readhub.tech.newdata.Datum datum:datumTemp){
            Datum data = new Datum(datum.getId(),datum.getSiteName(),datum.getAuthorName(),datum.getUrl(),datum.getSummary(),datum.getTitle(),datum.getPublishDate(),"");
            this.readHubItems.add(data);
        }
        readHubAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateListFromCache(DataList readHubItems) {

        List<com.maotong.readhub.bean.readhub.tech.newdata.Datum> datumTemp = readHubItems.getData();
        for (com.maotong.readhub.bean.readhub.tech.newdata.Datum datum:datumTemp){
            Datum data = new Datum(datum.getId(),datum.getSiteName(),datum.getAuthorName(),datum.getUrl(),datum.getSummary(),datum.getTitle(),datum.getPublishDate(),"");
            this.readHubItems.add(data);
        }

        readHubAdapter.notifyDataSetChanged();
    }

    @Override
    public void show(String news) {
        et.setText(news);
    }


}
