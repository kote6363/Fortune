package com.xth.fortune;

/**
 * 历史记录项数据类
 */
public class HistoryItem {
    
    // 视图类型常量
    public static final int TYPE_SECTION = 0;  // 分组标题
    public static final int TYPE_ITEM = 1;     // 记录项

    private String title;       // 标题
    private String content;     // 内容
    private String time;        // 时间
    private String explanation; // 解释
    private int number;         // 签号
    private int loveLevel;      // 爱情运势
    private int wealthLevel;    // 财富运势
    private int careerLevel;    // 事业运势
    private int healthLevel;    // 健康运势
    private int type;           // 项目类型
    private boolean bookmarked; // 是否收藏

    /**
     * 构造函数
     */
    public HistoryItem(String title, String content, String time, String explanation, int type, boolean bookmarked) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.explanation = explanation;
        this.type = type;
        this.bookmarked = bookmarked;
    }

    /**
     * 完整的构造函数，包含所有字段
     */
    public HistoryItem(String title, String content, String time, String explanation, 
                      int number, int loveLevel, int wealthLevel, int careerLevel, int healthLevel, 
                      int type, boolean bookmarked) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.explanation = explanation;
        this.number = number;
        this.loveLevel = loveLevel;
        this.wealthLevel = wealthLevel;
        this.careerLevel = careerLevel;
        this.healthLevel = healthLevel;
        this.type = type;
        this.bookmarked = bookmarked;
    }

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLoveLevel() {
        return loveLevel;
    }

    public void setLoveLevel(int loveLevel) {
        this.loveLevel = loveLevel;
    }

    public int getWealthLevel() {
        return wealthLevel;
    }

    public void setWealthLevel(int wealthLevel) {
        this.wealthLevel = wealthLevel;
    }

    public int getCareerLevel() {
        return careerLevel;
    }

    public void setCareerLevel(int careerLevel) {
        this.careerLevel = careerLevel;
    }

    public int getHealthLevel() {
        return healthLevel;
    }

    public void setHealthLevel(int healthLevel) {
        this.healthLevel = healthLevel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    @Override
    public String toString() {
        return "HistoryItem{" +
                "title='" + title + '\'' +
                ", type=" + (type == TYPE_SECTION ? "SECTION" : "ITEM") +
                ", bookmarked=" + bookmarked +
                '}';
    }
} 