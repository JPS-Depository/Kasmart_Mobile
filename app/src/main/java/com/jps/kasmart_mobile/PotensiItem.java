package com.jps.kasmart_mobile;

public class PotensiItem {
    private int mId;
    private String mJenis, mNama, mSumberDayaAlam, mSumberDayaManusia, mAsetDesa, mBudayaDesa;

    public PotensiItem(int id, String jenis, String nama, String sumberDayaAlam,
                       String sumberDayaManusia, String asetDesa, String budayaDesa){
        mId = id;
        mJenis = jenis;
        mNama = nama;
        mSumberDayaAlam = sumberDayaAlam;
        mSumberDayaManusia = sumberDayaManusia;
        mAsetDesa = asetDesa;
        mBudayaDesa = budayaDesa;
    }

    public int getId(){
        return mId;
    }
    public String getJenis(){ return mJenis;}
    public String getNama(){ return mNama;}
    public String getSumberDayaAlam(){ return mSumberDayaAlam;}
    public String getSumberDayaManusia(){ return mSumberDayaManusia;}
    public String getAsetDesa(){return mAsetDesa;}
    public String getBudayaDesa(){return mBudayaDesa;}
}
