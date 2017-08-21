package com.maotong.readhub.presenter.impl;

import com.maotong.readhub.presenter.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BasePresenterImpl implements BasePresenter {

    private CompositeDisposable mCompositeDisposable;

    protected void addSubscription(Disposable s) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(s);
    }

    @Override
    public void unSubscribe() {
        if (this.mCompositeDisposable != null) {
            this.mCompositeDisposable.clear();
        }
    }
}
