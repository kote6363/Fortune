package com.xth.fortune;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 占卜历史数据库帮助类
 */
public class FortuneDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "fortune.db";
    private static final int DATABASE_VERSION = 1;

    // 占卜历史表
    public static final String TABLE_HISTORY = "history";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_EXPLANATION = "explanation";
    public static final String COLUMN_LOVE_LEVEL = "love_level";
    public static final String COLUMN_WEALTH_LEVEL = "wealth_level";
    public static final String COLUMN_CAREER_LEVEL = "career_level";
    public static final String COLUMN_HEALTH_LEVEL = "health_level";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_BOOKMARKED = "bookmarked";

    // 创建表的SQL语句
    private static final String SQL_CREATE_HISTORY =
            "CREATE TABLE " + TABLE_HISTORY + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NUMBER + " INTEGER," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_CONTENT + " TEXT," +
                    COLUMN_EXPLANATION + " TEXT," +
                    COLUMN_LOVE_LEVEL + " INTEGER," +
                    COLUMN_WEALTH_LEVEL + " INTEGER," +
                    COLUMN_CAREER_LEVEL + " INTEGER," +
                    COLUMN_HEALTH_LEVEL + " INTEGER," +
                    COLUMN_TIMESTAMP + " INTEGER," +
                    COLUMN_BOOKMARKED + " INTEGER DEFAULT 0" +
                    ")";

    public FortuneDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 更新数据库版本时的处理，目前简单删除重建
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    /**
     * 保存一条占卜结果到历史记录
     */
    public long saveFortuneResult(FortuneResult result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        android.util.Log.d("FortuneDbHelper", "正在保存占卜结果: " + result.getTitle());
        
        values.put(COLUMN_NUMBER, result.getNumber());
        values.put(COLUMN_TITLE, result.getTitle());
        values.put(COLUMN_CONTENT, result.getContent());
        values.put(COLUMN_EXPLANATION, result.getExplanation());
        values.put(COLUMN_LOVE_LEVEL, result.getLoveLevel());
        values.put(COLUMN_WEALTH_LEVEL, result.getWealthLevel());
        values.put(COLUMN_CAREER_LEVEL, result.getCareerLevel());
        values.put(COLUMN_HEALTH_LEVEL, result.getHealthLevel());
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        values.put(COLUMN_BOOKMARKED, 0); // 默认未收藏

        long id = db.insert(TABLE_HISTORY, null, values);
        
        if (id == -1) {
            android.util.Log.e("FortuneDbHelper", "保存占卜结果失败: 插入返回-1");
        } else {
            android.util.Log.d("FortuneDbHelper", "成功保存占卜结果, ID: " + id + ", 标题: " + result.getTitle());
        }
        
        db.close();
        return id;
    }

    /**
     * 将历史记录按日期分组返回
     */
    public List<HistoryItem> getGroupedHistory() {
        List<HistoryItem> groupedList = new ArrayList<>();
        
        // 获取今天、昨天、本周、上月的时间戳范围
        long now = System.currentTimeMillis();
        
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        long todayStart = today.getTimeInMillis();
        
        Calendar yesterday = (Calendar) today.clone();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        long yesterdayStart = yesterday.getTimeInMillis();
        
        Calendar weekStart = (Calendar) today.clone();
        weekStart.set(Calendar.DAY_OF_WEEK, weekStart.getFirstDayOfWeek());
        long thisWeekStart = weekStart.getTimeInMillis();
        
        Calendar monthStart = (Calendar) today.clone();
        monthStart.add(Calendar.MONTH, -1);
        long lastMonthStart = monthStart.getTimeInMillis();
        
        // 添加今天的数据
        List<HistoryItem> todayItems = getHistoryItems(todayStart, now);
        if (!todayItems.isEmpty()) {
            groupedList.add(new HistoryItem("今天", null, null, null, HistoryItem.TYPE_SECTION, false));
            groupedList.addAll(todayItems);
        }
        
        // 添加昨天的数据
        List<HistoryItem> yesterdayItems = getHistoryItems(yesterdayStart, todayStart - 1);
        if (!yesterdayItems.isEmpty()) {
            groupedList.add(new HistoryItem("昨天", null, null, null, HistoryItem.TYPE_SECTION, false));
            groupedList.addAll(yesterdayItems);
        }
        
        // 添加本周的数据 (除去今天和昨天)
        List<HistoryItem> thisWeekItems = getHistoryItems(thisWeekStart, yesterdayStart - 1);
        if (!thisWeekItems.isEmpty()) {
            groupedList.add(new HistoryItem("本周", null, null, null, HistoryItem.TYPE_SECTION, false));
            groupedList.addAll(thisWeekItems);
        }
        
        // 添加上个月的数据
        List<HistoryItem> lastMonthItems = getHistoryItems(lastMonthStart, thisWeekStart - 1);
        if (!lastMonthItems.isEmpty()) {
            groupedList.add(new HistoryItem("上个月", null, null, null, HistoryItem.TYPE_SECTION, false));
            groupedList.addAll(lastMonthItems);
        }
        
        // 打印有多少记录
        android.util.Log.d("FortuneDbHelper", "分组后总共有 " + groupedList.size() + " 条记录"
                + " (今天: " + todayItems.size() 
                + ", 昨天: " + yesterdayItems.size() 
                + ", 本周: " + thisWeekItems.size() 
                + ", 上月: " + lastMonthItems.size() + ")");
        
        // 如果没有任何记录，返回空列表
        return groupedList;
    }

    /**
     * 获取特定时间范围内的历史记录
     */
    private List<HistoryItem> getHistoryItems(long startTime, long endTime) {
        List<HistoryItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String selection = COLUMN_TIMESTAMP + " >= ? AND " + COLUMN_TIMESTAMP + " <= ?";
        String[] selectionArgs = {String.valueOf(startTime), String.valueOf(endTime)};
        String orderBy = COLUMN_TIMESTAMP + " DESC";
        
        Cursor cursor = db.query(
                TABLE_HISTORY,
                null,
                selection,
                selectionArgs,
                null,
                null,
                orderBy
        );
        
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT));
                String explanation = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPLANATION));
                long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP));
                int number = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NUMBER));
                int loveLevel = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LOVE_LEVEL));
                int wealthLevel = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WEALTH_LEVEL));
                int careerLevel = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CAREER_LEVEL));
                int healthLevel = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HEALTH_LEVEL));
                int bookmarked = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKMARKED));
                
                // 将签文内容截短
                String shortContent = content;
                if (content.length() > 60) {
                    shortContent = content.substring(0, 57) + "...";
                }
                
                // 格式化时间
                String timeText = formatTime(timestamp);
                
                // 创建完整的HistoryItem对象
                HistoryItem item = new HistoryItem(
                        title,
                        shortContent,
                        timeText,
                        explanation,
                        number,
                        loveLevel,
                        wealthLevel,
                        careerLevel,
                        healthLevel,
                        HistoryItem.TYPE_ITEM,
                        bookmarked == 1
                );
                
                items.add(item);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return items;
    }
    
    /**
     * 根据level值获取对应的签等文本
     */
    private String getLevelText(int level) {
        switch (level) {
            case 5:
                return "上上签";
            case 4:
                return "上签";
            case 3:
                return "中上签";
            case 2:
                return "中签";
            case 1:
                return "中下签";
            case 0:
                return "下签";
            default:
                return "中签";
        }
    }
    
    /**
     * 格式化时间显示
     */
    private String formatTime(long timestamp) {
        Date date = new Date(timestamp);
        Date now = new Date();
        Calendar calDate = Calendar.getInstance();
        Calendar calNow = Calendar.getInstance();
        
        calDate.setTime(date);
        calNow.setTime(now);
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        
        if (calDate.get(Calendar.YEAR) == calNow.get(Calendar.YEAR) &&
                calDate.get(Calendar.DAY_OF_YEAR) == calNow.get(Calendar.DAY_OF_YEAR)) {
            // 今天
            return "今天 " + timeFormat.format(date);
        } else if (calDate.get(Calendar.YEAR) == calNow.get(Calendar.YEAR) &&
                calDate.get(Calendar.DAY_OF_YEAR) == calNow.get(Calendar.DAY_OF_YEAR) - 1) {
            // 昨天
            return "昨天 " + timeFormat.format(date);
        } else if (calDate.get(Calendar.YEAR) == calNow.get(Calendar.YEAR) &&
                calDate.get(Calendar.WEEK_OF_YEAR) == calNow.get(Calendar.WEEK_OF_YEAR)) {
            // 本周
            String[] weekdays = {"", "周日", "周一", "周二", "周三", "周四", "周五", "周六"};
            int dayOfWeek = calDate.get(Calendar.DAY_OF_WEEK);
            return weekdays[dayOfWeek] + " " + timeFormat.format(date);
        } else {
            // 更早
            return dateFormat.format(date);
        }
    }
    
    /**
     * 更新记录的收藏状态
     */
    public boolean updateBookmarkStatus(String title, String time, boolean isBookmarked) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKMARKED, isBookmarked ? 1 : 0);
        
        // 查找最匹配的记录
        String timePattern = time.replace("今天 ", "").replace("昨天 ", "");
        if (timePattern.startsWith("周")) {
            timePattern = timePattern.substring(3); // 去掉周几部分，只保留时间
        }
        
        // 使用模糊匹配，因为时间显示格式和存储格式可能有差异
        String selection = COLUMN_TITLE + " = ? AND " + COLUMN_TIMESTAMP + " IN " +
                "(SELECT " + COLUMN_TIMESTAMP + " FROM " + TABLE_HISTORY + 
                " ORDER BY ABS(strftime('%H:%M', " + COLUMN_TIMESTAMP + "/1000, 'unixepoch', 'localtime') - ?) LIMIT 1)";
        String[] selectionArgs = {title, timePattern};
        
        int rowsUpdated = db.update(TABLE_HISTORY, values, selection, selectionArgs);
        db.close();
        
        return rowsUpdated > 0;
    }

    /**
     * 清空所有历史记录
     * @return 成功删除的记录数量
     */
    public int clearAllHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(TABLE_HISTORY, null, null);
        db.close();
        return count;
    }
} 