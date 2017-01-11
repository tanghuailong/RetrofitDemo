package com.vstar.sacredsun_android.entity;

import java.util.List;

/**
 * Created by tanghuailong on 2017/1/11.
 */
@Deprecated
public class HttpResultTest<T> {
    //用来模仿resultCode和resultMessage
    private int count;
    private int start;
    private int total;
    private String title;

    //用来模仿Data
    private List<T> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<T> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<T> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "HttpResultTest{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", title='" + title + '\'' +
                ", subjects=" + subjects +
                '}';
    }
}
