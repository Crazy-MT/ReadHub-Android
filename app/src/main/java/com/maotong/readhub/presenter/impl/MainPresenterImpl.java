package com.maotong.readhub.presenter.impl;

import com.maotong.readhub.api.zhihu.ZhihuRequest;
import com.maotong.readhub.bean.UpdateItem;
import com.maotong.readhub.presenter.IMainPresenter;
import com.maotong.readhub.ui.iView.IMain;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainPresenterImpl extends BasePresenterImpl implements IMainPresenter {

    private IMain mIMain;

    public MainPresenterImpl(IMain main) {
        if (main == null)
            throw new IllegalArgumentException("main must not be null");
        mIMain = main;
    }

    @Override
    public void checkUpdate() {
        Subscription s = ZhihuRequest.getZhihuApi().getUpdateInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateItem>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(final UpdateItem updateItem) {
                        mIMain.showUpdate(updateItem);
                    }
                });
        addSubscription(s);
    }
}
