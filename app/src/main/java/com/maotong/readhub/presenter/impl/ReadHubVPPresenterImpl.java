package com.maotong.readhub.presenter.impl;

import android.content.Context;

import com.maotong.readhub.presenter.IReadHubVPPresenter;
import com.maotong.readhub.ui.iView.IReadHubVPFragment;
import com.maotong.readhub.utils.CacheUtil;


public class ReadHubVPPresenterImpl extends BasePresenterImpl implements IReadHubVPPresenter{

    private IReadHubVPFragment mReadHubFragment;
    private CacheUtil mCacheUtil;

    public ReadHubVPPresenterImpl(IReadHubVPFragment readHubFragment, Context context) {
        if (readHubFragment==null)
            throw new IllegalArgumentException("readhub must not be null");
        this.mReadHubFragment = readHubFragment;
        mCacheUtil = CacheUtil.get(context);
    }
}
