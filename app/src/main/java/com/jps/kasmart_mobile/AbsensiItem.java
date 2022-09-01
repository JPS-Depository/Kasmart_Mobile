package com.jps.kasmart_mobile;

public class AbsensiItem {
    private int mId;
    private String mKegiatan, mLokasi, mKeterangan, mTanggalAbsen;
    private boolean mExpandable;

    public boolean ismExpandable() {
        return mExpandable;
    }

    public void setmExpandable(boolean mExpandable) {
        this.mExpandable = mExpandable;
    }

    public AbsensiItem(int id, String kegiatan, String lokasi, String keterangan, String tanggalAbsen){
        mId = id;
        mKegiatan = kegiatan;
        mLokasi = lokasi;
        mKeterangan = keterangan;
        mTanggalAbsen = tanggalAbsen;
        this.mExpandable = false;
    }

    public int getId(){
        return mId;
    }
    public String getKegiatan(){return  mKegiatan;}
    public String getLokasi(){return mLokasi;}
    public String getKeterangan(){return  mKeterangan;}
    public String getTanggalAbsen(){return mTanggalAbsen;}
}
