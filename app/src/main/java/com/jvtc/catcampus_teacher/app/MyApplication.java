package com.jvtc.catcampus_teacher.app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.jvtc.catcampus_teacher.R;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

public class MyApplication extends Application {
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.upgradeCheckPeriod = 60 * 1000;
        //避免影响APP启动速度
        Beta.initDelay = 1 * 1000;
        Beta.smallIconId = R.mipmap.logo_jiu_black_mini;
        Bugly.init(getApplicationContext(), "ab82a4ef2b", false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        Beta.installTinker();
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

}
