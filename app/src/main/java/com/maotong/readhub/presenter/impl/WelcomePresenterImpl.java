package com.maotong.readhub.presenter.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;

import com.maotong.readhub.R;
import com.maotong.readhub.presenter.IWelcomePresenter;
import com.maotong.readhub.ui.iView.IWelcome;
import com.maotong.readhub.utils.SharePreferenceUtil;

import java.util.Random;

public class WelcomePresenterImpl implements IWelcomePresenter {

    private IWelcome mIWelcome;
    private Context mContext;
    private SharedPreferences sharedPreferences;

    public WelcomePresenterImpl(IWelcome iWelcome, Context context) {
        if (iWelcome == null)
            throw new IllegalArgumentException("iWelcome must not be null");
        mIWelcome = iWelcome;
        mContext = context;
        sharedPreferences = context.getSharedPreferences(SharePreferenceUtil.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void getBackground() {
        int vibrant = getRandomPrimaryColor();
        int muted = ContextCompat.getColor(mContext, R.color.colorAccent);
        sharedPreferences.edit()
                .putInt(SharePreferenceUtil.VIBRANT, vibrant)
                .putInt(SharePreferenceUtil.MUTED, muted)
                .apply();

        mIWelcome.hasGetBackground();
    }

    private int getRandomPrimaryColor() {
        int[] primaryInt = new int[]{
                R.color.colorBlueGreyPrimary,
                R.color.colorBluePrimary,
                R.color.colorBrownPrimary,
                R.color.colorCyanPrimary,
                R.color.colorDeepOrangePrimary,
                R.color.colorDeepPurplePrimary,
                R.color.colorGreenPrimary,
                R.color.colorIndigoPrimary,
                R.color.colorLightGreenPrimary,
                R.color.colorLimePrimary,
                R.color.colorRedPrimary,
                R.color.colorPinkPrimary,
                R.color.colorPrimary
        };
        return ContextCompat.getColor(mContext, primaryInt[new Random().nextInt(13)]);
    }
}
