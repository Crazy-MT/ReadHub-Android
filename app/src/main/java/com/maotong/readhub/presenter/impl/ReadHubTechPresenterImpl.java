package com.maotong.readhub.presenter.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.maotong.readhub.api.readhubtech.ReadHubRequest;
import com.maotong.readhub.bean.readhub.tech.Datum;
import com.maotong.readhub.bean.readhub.tech.ReadHubTech;
import com.maotong.readhub.bean.readhub.tech.newdata.DataList;
import com.maotong.readhub.config.Config;
import com.maotong.readhub.presenter.IReadHubPresenter;
import com.maotong.readhub.ui.iView.IReadHubTechFragment;
import com.maotong.readhub.utils.CacheUtil;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ReadHubTechPresenterImpl extends BasePresenterImpl implements IReadHubPresenter {

    private IReadHubTechFragment mReadHubFragment;
    private CacheUtil mCacheUtil;

    public ReadHubTechPresenterImpl(IReadHubTechFragment readHubFragment, Context context) {
        if (readHubFragment==null)
            throw new IllegalArgumentException("readhubTechFragment must not be null");
        this.mReadHubFragment = readHubFragment;
        mCacheUtil = CacheUtil.get(context);
    }

    @Override
    public void getReadHub() {
        mReadHubFragment.showProgressDialog();

        //getNews();

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
                        ReadHubTech readHub = new Gson().fromJson(data3,ReadHubTech.class);
                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.updateList(readHub);
                        mCacheUtil.put(Config.READHUB_TECH + 0, data3);
                    }
                });

        addSubscription(s);


    }

    @Override
    public void getReadHubFromCache(int offset) {

    }

    @Override
    public void getMoreReadHubData(int offset) {

    }


    @Override
    public void getReadHubFromCache(String offset) {
        if (mCacheUtil.getAsJSONObject(Config.READHUB_TECH + offset) != null) {

            if (offset.equals("")){
                ReadHubTech readHub = new Gson().fromJson(mCacheUtil.getAsJSONObject(Config.READHUB_TECH + offset).toString(), ReadHubTech.class);
                DataList dataList = new DataList();
                List<com.maotong.readhub.bean.readhub.tech.newdata.Datum> techDataList = new ArrayList<>();
                List<Datum> techDataListTemp =readHub.getTimeline().getItems().getTech().getData();
                for (Datum datum : techDataListTemp){
                    com.maotong.readhub.bean.readhub.tech.newdata.Datum datumTemp = new com.maotong.readhub.bean.readhub.tech.newdata.Datum(datum.getId(),datum.getSiteName(),datum.getAuthorName(),datum.getUrl(),datum.getSummary(),datum.getTitle(),datum.getPublishDate());
                    techDataList.add(datumTemp);
                }

                dataList.setData(techDataList);
                mReadHubFragment.updateListFromCache(dataList);
            } else {
                DataList readHub = new Gson().fromJson(mCacheUtil.getAsJSONObject(Config.READHUB_TECH + offset).toString(), DataList.class);
                mReadHubFragment.updateListFromCache(readHub);

            }

        }
    }

    @Override
    public void getMoreReadHubData(final String offset) {
        Logger.e(offset);
        Subscription s = ReadHubRequest.getReadHubApi().getMoreData(offset+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataList>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage() + "   " + offset);
                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(DataList readHubHot) {
                        mReadHubFragment.hidProgressDialog();
                        mReadHubFragment.moreList(readHubHot);
                        mCacheUtil.put(Config.READHUB_TECH + offset, new Gson().toJson(readHubHot));
                    }
                });
        addSubscription(s);
    }

}
