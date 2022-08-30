package com.jps.kasmart_mobile;

public class SHUItem {
    private int mId, mPembagianShu, mInventaris, mTambahanModal,
            mPelatihanPengembangan, mBantuanSosial, mHadiahPemanfaat,
            mPad, mTotalSisaUsaha;
    private String mNamaDaerah;
    private boolean mExpandable;

    public boolean ismExpandable() {
        return mExpandable;
    }

    public void setmExpandable(boolean mExpandable) {
        this.mExpandable = mExpandable;
    }

    public SHUItem(int id, String namaDaerah, int pembagianShu, int inventaris,
                   int tambahanModal, int pelatihanPengembangan, int bantuanSosial,
                   int hadiahPemanfaat, int pad, int totalSisaUsaha){
        mId = id;
        mNamaDaerah = namaDaerah;
        mPembagianShu = pembagianShu;
        mInventaris = inventaris;
        mTambahanModal = tambahanModal;
        mPelatihanPengembangan = pelatihanPengembangan;
        mBantuanSosial = bantuanSosial;
        mHadiahPemanfaat = hadiahPemanfaat;
        mPad = pad;
        mTotalSisaUsaha = totalSisaUsaha;
        this.mExpandable = false;
    }

    public int getId(){
        return mId;
    }
    public String getNamaDaerah(){ return mNamaDaerah;}
    public int getPembagianShu(){return mPembagianShu;}
    public int getInventaris(){return mInventaris;}
    public int getTambahanModal(){return mTambahanModal;}
    public int getPelatihanPengembangan(){return mPelatihanPengembangan;}
    public int getBantuanSosial(){return mBantuanSosial;}
    public int getHadiahPemanfaat(){return mHadiahPemanfaat;}
    public int getPad(){return mPad;}
    public int getTotalSisaUsaha(){return mTotalSisaUsaha;}
}
