package com.jps.kasmart_mobile;

public class USPItem {
    private int mId;
    private String mNamaDaerah,mStatus;
    private boolean mExpandable;
    private int mModal, mPerguliranOrang, mPerguliranDana, mSisaPiutangOrang, mSisaPiutangDana, mTotalDanaUsp,
    mTotalDanaDud, mKasUed, mInventaris, mPenghapusanCadangan, mSitaanJaminan, mTotalAset, mTunggakanOrang, mTunggakanDana,
    mPengembalianOrang, mPengembalianDana, mDagangOrang, mDagangDana, mPertanianOrang, mPertanianDana, mPerkebunanOrang,
    mPerkebunanDana, mPerikananOrang, mPerikananDana, mPeternakanOrang, mPeternakanDana, mIndustriOrang, mIndustriDana,
    mJasaOrang, mJasaDana, mTotalPeminjamanJenisUsaha, mShu, mPenyaluranOrang, mPenyaluranDana, mPemanfaatOrang, mPemanfaatDana,
    mCashOpname, mPades, mPengembalianUsaha, mLabaBersih;
    private double mPengembalianPersentase;

    public boolean ismExpandable() {
        return mExpandable;
    }

    public void setmExpandable(boolean mExpandable) {
        this.mExpandable = mExpandable;
    }

    public USPItem(
            int id,String namaDaerah, int modal, int perguliranOrang, int perguliranDana, int sisaPiutangOrang,
            int sisaPiutangDana, int totalDanaUsp, int totalDanaDud, int kasUed, int inventaris ,int penghapusanCadangan
            ,int sitaanJaminan,int totalAset,int tunggakanOrang,int tunggakanDana,int pengembalianOrang,double pengembalianPersentase
            ,int pengembalianDana,int dagangOrang,int dagangDana,int pertanianOrang,int pertanianDana,int perkebunanOrang
            ,int perkebunanDana,int perikananOrang,int perikananDana,int peternakanOrang,int peternakanDana
            ,int industriOrang,int industriDana,int jasaOrang,int jasaDana,int totalPeminjamanJenisUsaha,int shu,int penyaluranOrang
            ,int penyaluranDana,int pemanfaatOrang,int pemanfaatDana,int cashOpname,int pades,int pengembalianUsaha
            ,int labaBersih,String status){

        mId = id; mNamaDaerah = namaDaerah; mModal=modal; mPerguliranOrang=perguliranOrang;mPerguliranDana=perguliranDana;
        mSisaPiutangOrang=sisaPiutangOrang;mSisaPiutangDana=sisaPiutangDana;mTotalDanaUsp=totalDanaUsp;mTotalDanaDud=totalDanaDud;
        mKasUed=kasUed;mInventaris=inventaris;mPenghapusanCadangan=penghapusanCadangan;mSitaanJaminan=sitaanJaminan;mTotalAset=totalAset;
        mTunggakanOrang=tunggakanOrang;mTunggakanDana=tunggakanDana;mPengembalianDana=pengembalianDana;mPengembalianOrang=pengembalianOrang;
        mPengembalianPersentase=pengembalianPersentase;mDagangOrang=dagangOrang;mDagangDana=dagangDana;mPertanianOrang=pertanianOrang;
        mPertanianDana=pertanianDana;mPerkebunanOrang=perkebunanOrang;mPerkebunanDana=perkebunanDana;mPerikananOrang=perikananOrang;
        mPerikananDana=perikananDana;mPeternakanOrang=peternakanOrang;mPeternakanDana=peternakanDana;mIndustriOrang=industriOrang;
        mIndustriDana=industriDana;mJasaOrang=jasaOrang;mJasaDana=jasaDana;mTotalPeminjamanJenisUsaha=totalPeminjamanJenisUsaha;mShu=shu;
        mPenyaluranOrang=penyaluranOrang;mPenyaluranDana=penyaluranDana;mPemanfaatOrang=pemanfaatOrang;mPemanfaatDana=pemanfaatDana;
        mCashOpname=cashOpname;mPades=pades;mPengembalianUsaha=pengembalianUsaha;mLabaBersih=labaBersih;mStatus = status;
        this.mExpandable = false;
    }

    public int getId(){
        return mId;
    }
    public String getNamaDaerah(){return mNamaDaerah;}
    public int getModal(){return mModal;}
    public int getPerguliranOrang(){return mPerguliranOrang;}
    public int getPerguliranDana(){return mPerguliranDana;}
    public int getSisaPiutangOrang(){return mSisaPiutangOrang;}
    public int getSisaPiutangDana(){return mSisaPiutangDana;}
    public int getTotalDanaUsp(){return mTotalDanaUsp;}
    public int getTotalDanaDud(){return mTotalDanaDud;}
    public int getKasUed(){return mKasUed;}
    public int getInventaris(){return mInventaris;}
    public int getPenghapusanCadangan(){return mPenghapusanCadangan;}
    public int getSitaanJaminan(){return mSitaanJaminan;}
    public int getTotalAset(){return mTotalAset;}
    public int getTunggakanOrang(){return mTunggakanOrang;}
    public int getTunggakanDana(){return mTunggakanDana;}
    public int getPengembalianOrang(){return mPengembalianOrang;}
    public int getPengembalianDana(){return mPengembalianDana;}
    public double getPengembalianPersentase(){return mPengembalianPersentase;}
    public int getDagangOrang(){return mDagangOrang;}
    public int getDagangDana(){return mDagangDana;}
    public int getPertanianOrang(){return mPertanianOrang;}
    public int getPertanianDana(){return mPertanianDana;}
    public int getPerkebunanOrang(){return mPerkebunanOrang;}
    public int getPerkebunanDana(){return mPerkebunanDana;}
    public int getPerikananOrang(){return mPerikananOrang;}
    public int getPerikananDana(){return mPerikananDana;}
    public int getPeternakanOrang(){return mPeternakanOrang;}
    public int getPeternakanDana(){return mPeternakanDana;}
    public int getIndustriOrang(){return mIndustriOrang;}
    public int getIndustriDana(){return mIndustriDana;}
    public int getJasaOrang(){return mJasaOrang;}
    public int getJasaDana(){return mJasaDana;}
    public int getTotalPeminjamanJenisUsaha(){return mTotalPeminjamanJenisUsaha;}
    public int getShu(){return mShu;}
    public int getPenyaluranOrang(){return mPenyaluranOrang;}
    public int getPenyaluranDana(){return mPenyaluranDana;}
    public int getPemanfaatOrang(){return mPemanfaatOrang;}
    public int getPemanfaatDana(){return mPemanfaatDana;}
    public int getCashOpname(){return mCashOpname;}
    public int getPades(){return mPades;}
    public int getPengembalianUsaha(){return mPengembalianUsaha;}
    public int getLabaBersih(){return mLabaBersih;}
    public String getStatus(){return mStatus;}

}
