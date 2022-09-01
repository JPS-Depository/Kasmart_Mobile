package com.jps.kasmart_mobile;

public class RutinItem {
    private int mId;
    private String mTanggalKegiatan, mKegiatan, mJenis, mLokasi,
    mDetail, mSasaran, mRealisasi, mCreatedBy;
    private boolean mExpandable;
    /*photo*/

    public boolean ismExpandable() {
        return mExpandable;
    }

    public void setmExpandable(boolean mExpandable) {
        this.mExpandable = mExpandable;
    }

    public RutinItem(int id, String tanggalKegiatan, String kegiatan, String jenis,
                     String lokasi, String detail, String sasaran, String realisasi, String createdBy){
        mId = id;
        mTanggalKegiatan = tanggalKegiatan;
        mKegiatan = kegiatan;
        mJenis = jenis;
        mLokasi = lokasi;
        mDetail = detail;
        mSasaran = sasaran;
        mRealisasi = realisasi;
        mCreatedBy = createdBy;
        this.mExpandable = false;
    }

    public int getId(){
        return mId;
    }
    public String getTanggalKegiatan(){return mTanggalKegiatan;}
    public String getKegiatan(){return  mKegiatan;}
    public String getJenis(){return mJenis;}
    public String getLokasi(){return mLokasi;}
    public String getDetail(){return mDetail;}
    public String getSasaran(){return mSasaran;}
    public String getRealisasi(){return mRealisasi;}
    public String getCreatedBy(){return mCreatedBy;}
}
