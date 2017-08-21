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

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ReadHubActivityPresenterImpl extends BasePresenterImpl implements IReadHubActivityPresenter {

    private IReadHub mReadHub;
    private CacheUtil mCacheUtil;

    public ReadHubActivityPresenterImpl(IReadHub readHub, Context context) {
        if (readHub == null)
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

        ReadHubRequest.getReadHubApi().getMoreData(offset + "")
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<DataList>() {
                    @Override
                    public void accept(DataList dataList) throws Exception {
                        mCacheUtil.put(Config.READHUB + offset, new Gson().toJson(dataList));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<DataList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscription(d);
                    }

                    @Override
                    public void onNext(@NonNull DataList dataList) {
                        mReadHub.completeRefresh();
                        mReadHub.moreList(dataList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mReadHub.completeRefresh();
                        mReadHub.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        /*ReadHubRequest.getReadHubApi().getMoreData(offset+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataList>() {

                    @Override
                    public void accept(DataList dataList) throws Exception {
                        mReadHub.completeRefresh();
                        mReadHub.moreList(dataList);
                        mCacheUtil.put(Config.READHUB + offset, new Gson().toJson(dataList));
                        Logger.e("accept");
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mReadHub.completeRefresh();
                        mReadHub.showError(throwable.getMessage());
                    }
                });*/
    }


    @Override
    public void refresh() {

        Disposable disposable = Observable.create(new ObservableOnSubscribe<ReadHub>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ReadHub> emitter) throws Exception {
                try {
                    Document doc = Jsoup.connect("https://readhub.me/").get();
                    Element html = doc.getElementById("data");
                    Elements data1 = html.select("div[data-state]");
                    String data3 = data1.get(0).attr("data-state");
                    mCacheUtil.put(Config.READHUB + 0, data3);
                    ReadHub readHub = new Gson().fromJson(data3, ReadHub.class);
                    emitter.onNext(readHub);
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReadHub>() {
                    @Override
                    public void accept(ReadHub data) throws Exception {
                        mReadHub.completeRefresh();
                        mReadHub.updateList(data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mReadHub.completeRefresh();
                        mReadHub.showError(throwable.getMessage());
                    }
                });
        addSubscription(disposable);

    }
}
