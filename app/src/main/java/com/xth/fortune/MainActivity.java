package com.xth.fortune;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private HistoryFragment historyFragment;
    private View navHome;
    private View navHistory;
    private ViewPager2 viewPager;
    private FragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 隐藏ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        
        // 设置沉浸式状态栏
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
        
        // 初始化Fragment
        homeFragment = new HomeFragment();
        historyFragment = new HistoryFragment();
        
        // 初始化ViewPager2
        setupViewPager();
        
        // 初始化底部导航栏
        setupNavigation();
    }
    
    private void setupViewPager() {
        viewPager = findViewById(R.id.view_pager);
        
        // 创建Fragment数组传递给Adapter
        Fragment[] fragments = {homeFragment, historyFragment};
        pagerAdapter = new FragmentPagerAdapter(this, fragments);
        viewPager.setAdapter(pagerAdapter);
        
        // 禁用ViewPager2的默认滑动动画
        viewPager.setUserInputEnabled(true);
        
        // 监听页面切换
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateNavigation(position == 0);
            }
        });
    }
    
    private void setupNavigation() {
        navHome = findViewById(R.id.nav_fortune);
        navHistory = findViewById(R.id.nav_history);
        
        navHome.setOnClickListener(v -> {
            viewPager.setCurrentItem(0, true);
        });
        navHistory.setOnClickListener(v -> {
            viewPager.setCurrentItem(1, true);
        });
    }
    
    private void updateNavigation(boolean isHome) {
        // 更新图标颜色
        ImageView homeIcon = navHome.findViewById(R.id.nav_fortune_icon);
        ImageView historyIcon = navHistory.findViewById(R.id.nav_history_icon);
        homeIcon.setColorFilter(isHome ? ContextCompat.getColor(this, R.color.primary) : ContextCompat.getColor(this, R.color.text_hint));
        historyIcon.setColorFilter(isHome ? ContextCompat.getColor(this, R.color.text_hint) : ContextCompat.getColor(this, R.color.primary));

        // 更新文字颜色
        TextView homeText = navHome.findViewById(R.id.nav_fortune_text);
        TextView historyText = navHistory.findViewById(R.id.nav_history_text);
        homeText.setTextColor(isHome ? ContextCompat.getColor(this, R.color.primary) : ContextCompat.getColor(this, R.color.text_hint));
        historyText.setTextColor(isHome ? ContextCompat.getColor(this, R.color.text_hint) : ContextCompat.getColor(this, R.color.primary));
    }
    
    /**
     * 获取ViewPager2实例，供Fragment使用
     * @return ViewPager2实例
     */
    public ViewPager2 getViewPager() {
        return viewPager;
    }
}