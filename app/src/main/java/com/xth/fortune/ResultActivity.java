package com.xth.fortune;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ResultActivity extends AppCompatActivity {

    private TextView resultTitleTextView;
    private TextView resultContentTextView;
    private TextView resultNumberTextView;
    private TextView explanationContentTextView;
    private TextView loveLevelTextView;
    private TextView wealthLevelTextView;
    private TextView careerLevelTextView;
    private TextView healthLevelTextView;
    
    private FortuneResult currentResult;
    private FortuneDbHelper dbHelper;
    
    private static final String TAG = "ResultActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 在setContentView前禁用动画
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_result);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        
        // 初始化视图
        initViews();
        
        // 检查是否从历史页面跳转
        boolean fromHistory = getIntent().getBooleanExtra("from_history", false);
        
        if (fromHistory) {
            currentResult = (FortuneResult) getIntent().getSerializableExtra("fortune_result");
            if (currentResult == null) {
                finish();
                return;
            }
        } else {
            int fortuneNumber = getIntent().getIntExtra("fortune_number", -1);
            if (fortuneNumber == -1) {
                fortuneNumber = new Random().nextInt(100) + 1;
            }
            currentResult = FortuneResultGenerator.getResult(fortuneNumber);
            
            // 在后台线程保存结果
            new Thread(() -> saveResultToHistory()).start();
        }
        
        // 显示结果
        displayResult();
        
        // 设置返回按钮
        setupNavigation();
    }
    
    private void initViews() {
        Log.d(TAG, "初始化ResultActivity视图");
        
        resultTitleTextView = findViewById(R.id.result_title);
        resultContentTextView = findViewById(R.id.result_content);
        resultNumberTextView = findViewById(R.id.result_number);
        explanationContentTextView = findViewById(R.id.explanation_content);
        
        loveLevelTextView = findViewById(R.id.love_level);
        wealthLevelTextView = findViewById(R.id.wealth_level);
        careerLevelTextView = findViewById(R.id.career_level);
        healthLevelTextView = findViewById(R.id.health_level);
        
        Log.d(TAG, "ResultActivity视图初始化完成");
    }
    
    private void displayResult() {
        // 设置签题
        resultTitleTextView.setText(currentResult.getTitle());
        
        // 设置签文
        resultContentTextView.setText(currentResult.getContent());
        
        // 设置签号
        resultNumberTextView.setText(String.valueOf(currentResult.getNumber()));
        
        // 设置解签
        explanationContentTextView.setText(currentResult.getExplanation());
        
        // 设置运势等级
        loveLevelTextView.setText(getFortuneLevelString(currentResult.getLoveLevel()));
        wealthLevelTextView.setText(getFortuneLevelString(currentResult.getWealthLevel()));
        careerLevelTextView.setText(getFortuneLevelString(currentResult.getCareerLevel()));
        healthLevelTextView.setText(getFortuneLevelString(currentResult.getHealthLevel()));
    }
    
    private String getFortuneLevelString(int level) {
        switch (level) {
            case 5:
                return getString(R.string.fortune_level_top_top);
            case 4:
                return getString(R.string.fortune_level_top);
            case 3:
                return getString(R.string.fortune_level_middle_top);
            case 2:
                return getString(R.string.fortune_level_middle);
            case 1:
                return getString(R.string.fortune_level_middle_down);
            case 0:
                return getString(R.string.fortune_level_down);
            default:
                return getString(R.string.fortune_level_middle);
        }
    }
    
    private void setupNavigation() {
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
    }
    
    private void saveResultToHistory() {
        if (dbHelper == null) {
            dbHelper = new FortuneDbHelper(this);
        }
        
        try {
            FortuneResult result = new FortuneResult();
            result.setNumber(currentResult.getNumber());
            result.setTitle(currentResult.getTitle());
            result.setContent(currentResult.getContent());
            result.setExplanation(currentResult.getExplanation());
            result.setLoveLevel(currentResult.getLoveLevel());
            result.setWealthLevel(currentResult.getWealthLevel());
            result.setCareerLevel(currentResult.getCareerLevel());
            result.setHealthLevel(currentResult.getHealthLevel());

            dbHelper.saveFortuneResult(result);
        } catch (Exception e) {
            // 忽略保存失败的情况
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
            dbHelper = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
        // 禁用退出动画
        overridePendingTransition(0, 0);
    }
} 