package com.xth.fortune;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;
import android.app.ActivityManager;

import androidx.multidex.MultiDex;

import com.kc.openset.config.OSETSDK;
import com.kc.openset.config.controller.OSETCustomController;
import com.kc.openset.listener.OSETInitListener;

import java.util.List;

public class App extends Application {
    
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Log.e("aaaaaa", "进程名:" + getMyProcessName());
            // 安卓9.0后不允许多进程使用同一个数据目录
            try {
                WebView.setDataDirectorySuffix(getMyProcessName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Implementation will be added when SDK is properly imported
        // setUserId传你们的用户Id，有就传没有就不传，用于根据用户反馈问题，查问题用
        OSETSDK.getInstance()
                .setUserId("aaa")
                .setCustomController(new OSETCustomController(){})
                .init(this, "F9CF3DF170B3C887", new OSETInitListener(){
                    @Override
                    public void onError(String s) {
                        Log.i("xthx","onError:"+s);
                    }

                    @Override
                    public void onSuccess() {
                        Log.i("xthx","onSuccess:");
                    }
                });
    }
    
    private String getMyProcessName() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        } else {
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
            if (runningApps == null) {
                return "";
            }
            for (ActivityManager.RunningAppProcessInfo processInfo : runningApps) {
                if (processInfo.pid == android.os.Process.myPid()) {
                    return processInfo.processName;
                }
            }
            return "";
        }
    }
} 