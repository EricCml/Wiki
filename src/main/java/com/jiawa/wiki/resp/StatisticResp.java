package com.jiawa.wiki.resp;

import java.util.Date;

public class StatisticResp {
    private Date date;

    private int viewCount;

    private int voteCount;

    private int viewIncrease;

    private int voteIncrease;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getViewIncrease() {
        return viewIncrease;
    }

    public void setViewIncrease(int viewIncrease) {
        this.viewIncrease = viewIncrease;
    }

    public int getVoteIncrease() {
        return voteIncrease;
    }

    public void setVoteIncrease(int voteIncrease) {
        this.voteIncrease = voteIncrease;
    }

    @Override
    public String toString() {
        return "StatisticResp{" +
                "date=" + date +
                ", viewCount=" + viewCount +
                ", voteCount=" + voteCount +
                ", viewIncrease=" + viewIncrease +
                ", voteIncrease=" + voteIncrease +
                '}';
    }
}
