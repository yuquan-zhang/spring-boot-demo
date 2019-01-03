package com.zhang.yong.demothree.vo;

import com.github.pagehelper.Page;

import java.io.Serializable;

public class PageObject<T> implements Serializable {
    private T data;
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private String error;

    public PageObject(T data, int draw, long recordsTotal, long recordsFiltered, String error) {
        this.data = data;
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public PageObject setData(T data) {
        this.data = data;
        return this;
    }

    public int getDraw() {
        return draw;
    }

    public PageObject setDraw(int draw) {
        this.draw = draw;
        return this;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public PageObject setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
        return this;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public PageObject setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
        return this;
    }

    public String getError() {
        return error;
    }

    public PageObject setError(String error) {
        this.error = error;
        return this;
    }

    public static PageObject page(Page page, int draw) {
        return new PageObject<>(page.getResult(),draw,page.getTotal(),page.getTotal(),null);
    }

}
