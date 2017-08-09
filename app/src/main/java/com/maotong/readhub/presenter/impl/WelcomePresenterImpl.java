package com.maotong.readhub.presenter.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.maotong.readhub.R;
import com.maotong.readhub.api.zhihu.ZhihuRequest;
import com.maotong.readhub.bean.image.ImageResponse;
import com.maotong.readhub.presenter.IWelcomePresenter;
import com.maotong.readhub.ui.iView.IWelcome;
import com.maotong.readhub.utils.SharePreferenceUtil;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class WelcomePresenterImpl implements IWelcomePresenter {

    private IWelcome mIWelcome;
    private Context mContext;
    private SharedPreferences sharedPreferences;

    public WelcomePresenterImpl(IWelcome iWelcome, Context context) {
        if (iWelcome==null)
            throw new IllegalArgumentException("iWelcome must not be null");
        mIWelcome = iWelcome;
        mContext = context;
        sharedPreferences = context.getSharedPreferences(SharePreferenceUtil.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void getBackground() {
        ZhihuRequest.getZhihuApi().getImage().subscribeOn(Schedulers.io())
                .map(new Func1<ImageResponse, Boolean>() {
                    @Override
                    public Boolean call(ImageResponse imageResponse) {
                        if (imageResponse.getData() != null && imageResponse.getData().getImages() != null) {
                            try {
                                Bitmap bitmap = BitmapFactory.decodeStream(new URL("http://wpstatic.zuimeia.com/" + imageResponse.getData().getImages().get(0).getImageUrl() + "?imageMogr/v2/auto-orient/thumbnail/480x320/quality/100").openConnection().getInputStream());
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File(mContext.getFilesDir().getPath() + "/bg.jpg")));
                                Palette palette = Palette.from(bitmap).generate();
                                int color = 0x000000;
                                int vibrant = palette.getVibrantColor(color);
                                int vibrantDark = palette.getDarkVibrantColor(color);
                                if (vibrant == 0)
                                    vibrant = vibrantDark;
                                if (vibrant == 0)
                                    vibrant = getRandomPrimaryColor();
                                int muted = palette.getMutedColor(color);
                                int mutedDark = palette.getDarkMutedColor(color);
                                if (muted == 0)
                                    muted = mutedDark;
                                if (muted == 0)
                                    muted = ContextCompat.getColor(mContext, R.color.colorAccent);
                                DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.DATE_FIELD);
                                sharedPreferences.edit()
                                        .putString(SharePreferenceUtil.IMAGE_DESCRIPTION, imageResponse.getData().getImages().get(0).getDescription())
                                        .putInt(SharePreferenceUtil.VIBRANT, vibrant)
                                        .putInt(SharePreferenceUtil.MUTED, muted)
                                        .putString(SharePreferenceUtil.IMAGE_GET_TIME, dateFormat.format(new Date()))
                                        .apply();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mIWelcome.hasGetBackground();
                    }

                    @Override
                    public void onNext(Boolean imageReponse) {
                        mIWelcome.hasGetBackground();
                    }
                });
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
        return ContextCompat.getColor(mContext, primaryInt[new Random().nextInt(14)]);
    }
}
