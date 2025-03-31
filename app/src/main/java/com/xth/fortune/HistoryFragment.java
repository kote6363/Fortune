package com.xth.fortune;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private static final String TAG = "HistoryFragment";
    private RecyclerView recyclerView;
    private View emptyStateView;
    private Button startFortuneButton;
    private Button clearHistoryButton;
    private HistoryAdapter adapter;
    private List<HistoryItem> historyItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        
        // 初始化视图
        initViews(view);
        
        // 加载历史数据
        loadHistoryData();
        
        return view;
    }
    
    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.history_list);
        emptyStateView = view.findViewById(R.id.empty_state);
        startFortuneButton = view.findViewById(R.id.start_fortune_button);
        clearHistoryButton = view.findViewById(R.id.clear_history_button);
        
        // 设置RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new HistoryAdapter(requireContext(), historyItems);
        recyclerView.setAdapter(adapter);
        
        // 设置历史记录点击事件
        adapter.setOnHistoryItemClickListener(item -> {
            if (item.getType() == HistoryItem.TYPE_ITEM) {
                Log.d(TAG, "点击查看历史记录: " + item.getTitle());
                
                // 创建结果对象用于传递
                FortuneResult result = new FortuneResult();
                result.setNumber(item.getNumber());
                result.setTitle(item.getTitle());
                result.setContent(item.getContent());
                result.setExplanation(item.getExplanation());
                result.setLoveLevel(item.getLoveLevel());
                result.setWealthLevel(item.getWealthLevel());
                result.setCareerLevel(item.getCareerLevel());
                result.setHealthLevel(item.getHealthLevel());
                
                // 跳转到结果页面，传递完整的签文信息
                Intent intent = new Intent(requireActivity(), ResultActivity.class);
                intent.putExtra("fortune_result", result);
                intent.putExtra("from_history", true); // 标记来自历史页面
                startActivity(intent);
            }
        });
        
        // 添加开始抽签按钮点击事件
        startFortuneButton.setOnClickListener(v -> {
            // 切换到首页Fragment
            if (getActivity() instanceof MainActivity) {
                // 使用ViewPager2切换到首页（索引0）
                ((MainActivity) getActivity()).getViewPager().setCurrentItem(0, true);
            }
        });
        
        // 添加清空历史记录按钮点击事件
        clearHistoryButton.setOnClickListener(v -> {
            // 显示确认对话框
            new AlertDialog.Builder(requireContext())
                    .setTitle("确认操作")
                    .setMessage("确定要清空所有历史记录吗？此操作不可恢复。")
                    .setPositiveButton("确定", (dialog, which) -> {
                        clearAllHistory();
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
    }
    
    private void clearAllHistory() {
        FortuneDbHelper dbHelper = new FortuneDbHelper(requireContext());
        int count = dbHelper.clearAllHistory();
        
        if (count > 0) {
            Toast.makeText(requireContext(), "已清空 " + count + " 条历史记录", Toast.LENGTH_SHORT).show();
            // 清空列表数据
            historyItems.clear();
            adapter.updateData(historyItems);
            // 显示空状态
            showEmptyState();
            // 隐藏清空按钮
            clearHistoryButton.setVisibility(View.GONE);
        } else {
            Toast.makeText(requireContext(), "没有历史记录可清空", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void loadHistoryData() {
        // 尝试从数据库加载历史记录
        try {
            FortuneDbHelper dbHelper = new FortuneDbHelper(requireContext());
            historyItems = dbHelper.getGroupedHistory();
            
            if (historyItems.isEmpty()) {
                showEmptyState();
                // 没有历史记录时隐藏清空按钮
                if (clearHistoryButton != null) {
                    clearHistoryButton.setVisibility(View.GONE);
                }
            } else {
                hideEmptyState();
                // 有历史记录时显示清空按钮
                if (clearHistoryButton != null) {
                    clearHistoryButton.setVisibility(View.VISIBLE);
                }
                adapter.updateData(historyItems);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to load history data", e);
            showEmptyState();
            // 发生错误时也隐藏清空按钮
            if (clearHistoryButton != null) {
                clearHistoryButton.setVisibility(View.GONE);
            }
        }
    }

    private void showEmptyState() {
        if (recyclerView != null && emptyStateView != null) {
            recyclerView.setVisibility(View.GONE);
            emptyStateView.setVisibility(View.VISIBLE);
        }
    }

    private void hideEmptyState() {
        if (recyclerView != null && emptyStateView != null) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyStateView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 每次显示时重新加载数据
        loadHistoryData();
    }
} 