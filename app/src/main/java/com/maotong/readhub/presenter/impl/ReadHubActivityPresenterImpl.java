package com.maotong.readhub.presenter.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.maotong.readhub.api.readhubhot.ReadHubRequest;
import com.maotong.readhub.bean.readhub.hot.ReadHub;
import com.maotong.readhub.bean.readhub.hot.newdata.DataList;
import com.maotong.readhub.config.Config;
import com.maotong.readhub.presenter.IReadHubActivityPresenter;
import com.maotong.readhub.ui.iView.IReadHub;
import com.maotong.readhub.utils.CacheUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ReadHubActivityPresenterImpl extends BasePresenterImpl implements IReadHubActivityPresenter {

    private IReadHub mReadHub;
    private CacheUtil mCacheUtil;

    public ReadHubActivityPresenterImpl(IReadHub readHub, Context context) {
        if (readHub==null)
            throw new IllegalArgumentException("readhubActivity must not be null");
        this.mReadHub = readHub;
        mCacheUtil = CacheUtil.get(context);
    }



    @Override
    public void loadView() {
        mReadHub.loadViewPager();
    }

    @Override
    public void loadMore(final int offset) {
        Subscription s = ReadHubRequest.getReadHubApi().getMoreData(offset+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataList>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mReadHub.completeRefresh();
                        mReadHub.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(DataList readHubHot) {
                        mReadHub.completeRefresh();
                        mReadHub.moreList(readHubHot);
                        mCacheUtil.put(Config.READHUB + offset, new Gson().toJson(readHubHot));
                    }
                });
        addSubscription(s);
    }


    @Override
    public void refresh() {
        Subscription s = Observable.create(new Observable.OnSubscribe<Document>(){

            @Override
            public void call(Subscriber<? super Document> subscriber) {

                try {
                    Document doc = Jsoup.connect("https://readhub.me/").get();
                    subscriber.onNext(doc);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Document>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        mReadHub.completeRefresh();
                        mReadHub.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Document o) {
                        Element html = o.getElementById("data");
                        Elements data1 = html.select("div[data-state]");
                        String data3 =  data1.get(0).attr("data-state");
                        ReadHub readHub = new Gson().fromJson(data3,ReadHub.class);
                        mReadHub.completeRefresh();
                        mReadHub.updateList(readHub);
                        mCacheUtil.put(Config.READHUB + 0, data3);
                    }
                });

        addSubscription(s);
    }
}
