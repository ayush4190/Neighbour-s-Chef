package com.example.myapplication;

public class Order {

    private int mId;
    private String mName;
    private int mQuantity;
    private double mTotal;
    private String mCategory;
    private String mDate;
    private String mAddress;
    private String mStatus;

    public Order() {
    }

    public Order(int mId, String mName, int mQuantity, double mTotal, String mCategory, String mDate, String mAddress, String mStatus) {
        this.mId = mId;
        this.mName = mName;
        this.mQuantity = mQuantity;
        this.mTotal = mTotal;
        this.mCategory = mCategory;
        this.mDate = mDate;
        this.mAddress = mAddress;
        this.mStatus = mStatus;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public double getmTotal() {
        return mTotal;
    }

    public void setmTotal(double mTotal) {
        this.mTotal = mTotal;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }
}
