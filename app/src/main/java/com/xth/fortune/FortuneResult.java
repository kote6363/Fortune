package com.xth.fortune;

import java.io.Serializable;

/**
 * 占卜结果数据类
 */
public class FortuneResult implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // 占卜编号
    private int number;
    // 占卜结果标题
    private String title;
    // 占卜结果正文
    private String content;
    // 解释
    private String explanation;
    // 各方面运势等级 (1-5)
    private int loveLevel;
    private int wealthLevel;
    private int careerLevel;
    private int healthLevel;

    // 默认构造函数
    public FortuneResult() {
    }

    // 获取/设置属性的方法
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

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

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
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

    @Override
    public String toString() {
        return "FortuneResult{" +
                "number=" + number +
                ", title='" + title + '\'' +
                ", content='" + (content != null ? content.substring(0, Math.min(content.length(), 30)) + "..." : null) + '\'' +
                ", loveLevel=" + loveLevel +
                ", wealthLevel=" + wealthLevel +
                ", careerLevel=" + careerLevel +
                ", healthLevel=" + healthLevel +
                '}';
    }
} 