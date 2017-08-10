package com.maotong.readhub;

import android.app.Application;
import android.content.Context;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;


public class ReadHubApplication extends Application {

    public static ReadHubApplication readHubApplication = null;

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
    }

    public static Context getContext(){
        return readHubApplication;
    }
}
