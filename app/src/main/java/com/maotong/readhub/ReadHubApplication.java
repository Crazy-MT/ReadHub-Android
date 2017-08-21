package com.maotong.readhub;

import android.app.Application;
import android.content.Context;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.maotong.readhub.utils.Events;
import com.maotong.readhub.utils.RxBus;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


public class ReadHubApplication extends Application {

    public static ReadHubApplication readHubApplication = null;

    private RxBus bus;
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());

        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingLocation(true).//是否获取位置
                trackingCrashLog(!BuildConfig.DEBUG).//是否收集crash
                trackingConsoleLog(true).//是否收集console log
                trackingUserSteps(true).//是否收集用户操作步骤
                build();
        Bugtags.start("50ff7c62b153b1900b4b82670c7b35b8", this, Bugtags.BTGInvocationEventNone, options);
        MobclickAgent.setCatchUncaughtExceptions(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        readHubApplication = this;
        bus = new RxBus();
    }

    public static Context getContext(){
        return readHubApplication;
    }

    public RxBus bus() {
        return bus;
    }
    public void sendAutoEvent() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        bus.send(new Events.AutoEvent());
                    }
                });
    }
}
