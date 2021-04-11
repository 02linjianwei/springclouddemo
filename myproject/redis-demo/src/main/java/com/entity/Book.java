package com.entity;

import com.google.common.base.MoreObjects;

public class Book {
    Integer bookNo;
    String bookName;

    public Integer getBookNo() {
        return bookNo;
    }

    public void setBookNo(Integer bookNo) {
        this.bookNo = bookNo;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    @Override
    public String toString() {
        return this.toStringHelper().toString();
    }

    protected MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(this)
                .add("bookNo",this.getBookNo())
                .add("bookName",this.getBookName());
    }
}
