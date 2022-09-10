package com.jps.kasmart_mobile;

public class WajibItem {
    private int mId;
    private String mTanggalKegiatan, mKegiatan, mJenis, mLokasi,
    mDetail, mSasaran, mRealisasi, mCreatedBy, mImg;
    private boolean mExpandable;

    public WajibItem(int id, String tanggalKegiatan, String kegiatan, String jenis,
                     String lokasi, String detail, String sasaran, String realisasi, String createdBy, String img){
        mId = id;
        mTanggalKegiatan = tanggalKegiatan;
        mKegiatan = kegiatan;
        mJenis = jenis;
        mLokasi = lokasi;
        mDetail = detail;
        mSasaran = sasaran;
        mRealisasi = realisasi;
        mCreatedBy = createdBy;
        mImg = img;
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
    public String getImg(){return mImg;}
}
