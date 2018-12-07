package cn.mauth.account.core.bean;

import java.io.Serializable;

public class Pageable implements Serializable{
    private static final long serialVersionUID = 1L;

    private int pageNum;
    private int pageSize;
    private String orderBy;

    private Pageable() {
    }

    private Pageable(int pageNum, int pageSize, String orderBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    private Pageable(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public static Pageable of(int pageNum, int pageSize, String orderBy) {
        return new Pageable(pageNum,pageSize,orderBy);
    }

    public static Pageable of(int pageNum, int pageSize) {
        return new Pageable(pageNum,pageSize);
    }

    public static Pageable of() {
        return new Pageable();
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
