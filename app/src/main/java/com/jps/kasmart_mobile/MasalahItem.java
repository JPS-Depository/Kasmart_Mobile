package com.jps.kasmart_mobile;

public class MasalahItem {
    private int mId;
    private String mMasalah, mPembinaan, mCreatedBy;
    private boolean mExpandable;

    public boolean ismExpandable() {
        return mExpandable;
    }

    public void setmExpandable(boolean mExpandable) {
        this.mExpandable = mExpandable;
    }

    public MasalahItem(int id, String masalah, String pembinaan, String createdBy){
        mId = id;
        mMasalah = masalah;
        mPembinaan = pembinaan;
        mCreatedBy = createdBy;
        this.mExpandable = false;
    }

    public int getId(){
        return mId;
    }
    public String getMasalah(){ return mMasalah;}
    public String getPembinaan(){ return mPembinaan;}
    public String getCreatedBy(){ return mCreatedBy;}
}
