package com.maotong.readhub.presenter.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.maotong.readhub.api.readhubnews.ReadHubRequest;
import com.maotong.readhub.bean.readhub.news.DataList;
import com.maotong.readhub.bean.readhub.news.ReadHubNews;
import com.maotong.readhub.config.Config;
import com.maotong.readhub.presenter.IReadHubPresenter;
import com.maotong.readhub.ui.iView.IReadHubNewsFragment;
import com.maotong.readhub.utils.CacheUtil;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ReadHubNewsPresenterImpl extends BasePresenterImpl implements IReadHubPresenter {

    private IReadHubNewsFragment mReadHubFragment;
    private CacheUtil mCacheUtil;

    public ReadHubNewsPresenterImpl(IReadHubNewsFragment readHubFragment, Context context) {
        if (readHubFragment==null)
            throw new IllegalArgumentException("readhubNewsFragment must not be null");
        this.mReadHubFragment = readHubFragment;
        mCacheUtil = CacheUtil.get(context);
    }

    @Override
    public void getReadHub() {
        mReadHubFragment.showProgressDialog();

        //getNews();

        Observable.create(new ObservableOnSubscribe<Document>(){

            @Override
            public void subscribe(@NonNull ObservableEmitter<Document> emitter) throws Exception {
                try {
                    Document doc = Jsoup.connect("https://readhub.me/news").get();
                    emitter.onNext(doc);
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Document>() {
                    @Override
                    public void accept(Document document) throws Exception {
                        Element html = document.getElementById("data");
                        Elements data1 = html.select("div[data-state]");
                        String data3 = data1.get(0).attr("data-state");
                        ReadHubNews readHub = new Gson().fromJson(data3, ReadHubNews.class);
                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.updateList(readHub);
                        mCacheUtil.put(Config.READHUB_NEWS + 0, data3);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.showError(throwable.getMessage());
                    }
                });

    }


    @Override
    public void getReadHubFromCache(String offset) {
        if (mCacheUtil.getAsJSONObject(Config.READHUB_NEWS + offset) != null) {

            if (offset.equals("")){
                ReadHubNews readHub = new Gson().fromJson(mCacheUtil.getAsJSONObject(Config.READHUB_NEWS + offset).toString(), ReadHubNews.class);
                DataList dataList = new DataList();
                dataList.setData(readHub.getTimeline().getItems().getAll().getData());
                mReadHubFragment.updateListFromCache(dataList);
            } else {
                DataList readHub = new Gson().fromJson(mCacheUtil.getAsJSONObject(Config.READHUB_NEWS + offset).toString(), DataList.class);
                mReadHubFragment.updateListFromCache(readHub);

            }

        }
    }

    @Override
    public void getMoreReadHubData(final String offset) {
        ReadHubRequest.getReadHubApi().getMoreData(offset+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataList>() {
                    @Override
                    public void accept(DataList dataList) throws Exception {
                        Logger.e(dataList.toString());
                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.moreList(dataList);
                        mCacheUtil.put(Config.READHUB_NEWS + offset, new Gson().toJson(dataList));
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());
                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.showError(throwable.getMessage());
                    }
                });
    }

    @Override
    public void getReadHubFromCache(int offset) {

    }

    @Override
    public void getMoreReadHubData(int offset) {

    }

}
