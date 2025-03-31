package com.xth.fortune;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 历史记录适配器
 */
public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    private static final String TAG = "HistoryAdapter";
    private static final int TYPE_SECTION = 0;
    private static final int TYPE_ITEM = 1;
    
    private List<HistoryItem> historyItems;
    private Context context;
    private OnHistoryItemClickListener listener;
    
    public HistoryAdapter(Context context, List<HistoryItem> historyItems) {
        this.context = context;
        this.historyItems = historyItems;
        
        Log.d(TAG, "创建HistoryAdapter, 项目数量: " + (historyItems != null ? historyItems.size() : 0));
    }
    
    public void setOnHistoryItemClickListener(OnHistoryItemClickListener listener) {
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_SECTION) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_history_section, parent, false);
            return new SectionViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
            return new ItemViewHolder(view);
        }
    }
    
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoryItem item = historyItems.get(position);
        
        if (holder instanceof SectionViewHolder) {
            // 设置分组标题
            SectionViewHolder sectionHolder = (SectionViewHolder) holder;
            sectionHolder.sectionTitle.setText(item.getTitle());
        } else {
            // 设置历史记录项
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.titleTextView.setText(item.getTitle());
            itemHolder.contentTextView.setText(item.getContent());
            itemHolder.timeTextView.setText(item.getTime());
            
            // 设置签等显示
            setFortuneLevel(itemHolder.levelIndicator, item);
            
            // 设置点击事件
            itemHolder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            });
        }
    }
    
    private void setFortuneLevel(TextView levelView, HistoryItem item) {
        // 计算平均运势等级
        float avgLevel = (item.getLoveLevel() + item.getWealthLevel() + 
                         item.getCareerLevel() + item.getHealthLevel()) / 4.0f;
        
        String levelText;
        int backgroundResId;
        
        if (avgLevel >= 4.5f) {
            levelText = "上上签";
            backgroundResId = R.drawable.bg_level_top;
        } else if (avgLevel >= 3.5f) {
            levelText = "上签";
            backgroundResId = R.drawable.bg_level_top;
        } else if (avgLevel >= 2.5f) {
            levelText = "中上签";
            backgroundResId = R.drawable.bg_level_middle;
        } else if (avgLevel >= 1.5f) {
            levelText = "中签";
            backgroundResId = R.drawable.bg_level_middle;
        } else if (avgLevel >= 0.5f) {
            levelText = "中下签";
            backgroundResId = R.drawable.bg_level_low;
        } else {
            levelText = "下签";
            backgroundResId = R.drawable.bg_level_low;
        }
        
        levelView.setText(levelText);
        levelView.setBackgroundResource(backgroundResId);
    }
    
    @Override
    public int getItemCount() {
        return historyItems != null ? historyItems.size() : 0;
    }
    
    @Override
    public int getItemViewType(int position) {
        return historyItems.get(position).getType();
    }
    
    /**
     * 更新数据
     */
    public void updateData(List<HistoryItem> newItems) {
        Log.d(TAG, "更新历史数据, 新项目数量: " + (newItems != null ? newItems.size() : 0));
        this.historyItems = newItems;
        notifyDataSetChanged();
    }
    
    /**
     * 分组标题的ViewHolder
     */
    static class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView sectionTitle;
        
        SectionViewHolder(View itemView) {
            super(itemView);
            sectionTitle = itemView.findViewById(R.id.section_title);
        }
    }
    
    /**
     * 历史记录项的ViewHolder
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        TextView timeTextView;
        TextView levelIndicator;
        
        ItemViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.history_title);
            contentTextView = itemView.findViewById(R.id.history_content);
            timeTextView = itemView.findViewById(R.id.history_time);
            levelIndicator = itemView.findViewById(R.id.history_level);
        }
    }
    
    /**
     * 历史记录项点击监听接口
     */
    public interface OnHistoryItemClickListener {
        void onItemClick(HistoryItem item);
    }
} 