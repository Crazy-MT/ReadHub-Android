package com.maotong.readhub.presenter.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.maotong.readhub.api.readhubhot.ReadHubRequest;
import com.maotong.readhub.bean.readhub.hot.ReadHub;
import com.maotong.readhub.bean.readhub.hot.newdata.DataList;
import com.maotong.readhub.config.Config;
import com.maotong.readhub.presenter.IReadHubPresenter;
import com.maotong.readhub.ui.iView.IReadHubFragment;
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


public class ReadHubPresenterImpl extends BasePresenterImpl implements IReadHubPresenter {

    private IReadHubFragment mReadHubFragment;
    private CacheUtil mCacheUtil;

    public ReadHubPresenterImpl(IReadHubFragment readHubFragment, Context context) {
        if (readHubFragment==null)
            throw new IllegalArgumentException("readhubFragment must not be null");
        this.mReadHubFragment = readHubFragment;
        mCacheUtil = CacheUtil.get(context);
    }

    @Override
    public void getReadHub() {
        mReadHubFragment.showProgressDialog();

        getNews();

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

                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Document o) {
                        Element html = o.getElementById("data");
                        Elements data1 = html.select("div[data-state]");
                        String data3 =  data1.get(0).attr("data-state");
                        ReadHub readHub = new Gson().fromJson(data3,ReadHub.class);
                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.updateList(readHub);
                        mCacheUtil.put(Config.READHUB + 0, data3);
                    }
                });

        addSubscription(s);


    }

    private void getNews() {
        Subscription s = Observable.create(new Observable.OnSubscribe<Document>(){

            @Override
            public void call(Subscriber<? super Document> subscriber) {

                try {
                    Document doc = Jsoup.connect("https://readhub.me/tech").get();
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

                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Document o) {
                        Element html = o.getElementById("data");
                        Elements data1 = html.select("div[data-state]");
                        String data3 =  data1.get(0).attr("data-state");
//                        ReadHub readHub = new Gson().fromJson(data3,ReadHub.class);
                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.show(data3);
                    }
                });

        addSubscription(s);
    }


    @Override
    public void getReadHubFromCache(int offset) {
        if (mCacheUtil.getAsJSONObject(Config.READHUB + offset) != null) {

            if (offset == 0){
                ReadHub readHub = new Gson().fromJson(mCacheUtil.getAsJSONObject(Config.READHUB + offset).toString(), ReadHub.class);
                DataList dataList = new DataList();
                dataList.setData(readHub.getTopic().getData());
                mReadHubFragment.updateListFromCache(dataList);
            } else {
                DataList readHub = new Gson().fromJson(mCacheUtil.getAsJSONObject(Config.READHUB + offset).toString(), DataList.class);
                mReadHubFragment.updateListFromCache(readHub);

            }

        }
    }

    @Override
    public void getMoreReadHubData(final int offset) {
        Subscription s = ReadHubRequest.getReadHubApi().getMoreData(offset+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataList>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(DataList readHubHot) {
                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.moreList(readHubHot);
                        mCacheUtil.put(Config.READHUB + offset, new Gson().toJson(readHubHot));
                    }
                });
        addSubscription(s);
    }

    @Override
    public void getReadHubFromCache(String offset) {

    }

    @Override
    public void getMoreReadHubData(String offset) {

    }

}
