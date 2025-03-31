package com.xth.fortune;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.kc.openset.ad.listener.OSETSplashAdLoadListener;
import com.kc.openset.ad.splash.OSETSplash;
import com.kc.openset.ad.splash.OSETSplashAd;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private static final int SPLASH_DELAY = 2000; // 2 seconds delay
    
    private FrameLayout adContainer;
    private boolean hasJumpToMain = false;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private OSETSplashAdLoadListener osetSplashAdLoadListener = new OSETSplashAdLoadListener() {
        @Override
        public void onLoadSuccess(OSETSplashAd osetSplashAd) {
            Log.i("xthx","success");
        }

        @Override
        public void onLoadFail(String s, String s1) {
            Log.i("xthx","onLoadFail");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        adContainer = findViewById(R.id.ad_container);
        
        // Simulate ad loading and display
        Log.d(TAG, "正在加载广告...");
        
        // Find view and setup ad container as shown in the image
        OSETSplash.getInstance()
                .setContext(this)
                .setPosId("3A9C32C9CE8C0CC12D0A36AE4B812C99")
                .loadAd(osetSplashAdLoadListener);
        
        // In a real implementation, we would initialize the OSET SDK here
        // For now, we'll just delay and then jump to main activity
        delayJumpToMain();
    }


    private void delayJumpToMain() {
        handler.postDelayed(this::jumpToMain, SPLASH_DELAY);
    }

    private void jumpToMain() {
        if (!hasJumpToMain) {
            hasJumpToMain = true;
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
} 