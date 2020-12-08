package com.jvtc.catcampus_teacher.app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

public class MyApplication extends Application {
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

       // CrashReport.initCrashReport(getApplicationContext(), "ab82a4ef2b", true);
       // CrashReport.initCrashReport(getApplicationContext());
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
