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
        Bugtags.start("1b1f86c7b80aaf78f076860ccebb5807", this, Bugtags.BTGInvocationEventBubble, options);
        MobclickAgent.setCatchUncaughtExceptions(false);
        readHubApplication = this;
    }

    public static Context getContext(){
        return readHubApplication;
    }
}
