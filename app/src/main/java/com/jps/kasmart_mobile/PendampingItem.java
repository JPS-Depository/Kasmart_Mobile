package com.jps.kasmart_mobile;

public class PendampingItem {
    private int mId;
    private String mNama, mTelepon, mAlamatTugas, mAlamatPribadi, mBidang, mJabatan, mNoReg, mEmail, mKTP, mBPJS, mNoRek, mNpwp, mTanggalLahir,
    mStatusPernikahan, mJenisKelamin, mAgama;
    private String mImageURL;
    private boolean mExpandable;

    public boolean ismExpandable() {
        return mExpandable;
    }

    public void setmExpandable(boolean mExpandable) {
        this.mExpandable = mExpandable;
    }

    public PendampingItem(int id, String nama, String telepon, String alamatTugas, String alamatPribadi,
            String bidang, String jabatan, String noReg, String email, String ktp, String bpjs, String noRek,
            String npwp, String statusPernikahan, String jenisKelamin, String agama, String imageURL){
        mId = id;
        mNama = nama;
        mTelepon = telepon;
        mAlamatTugas = alamatTugas;
        mAlamatPribadi = alamatPribadi;
        mBidang = bidang;
        mJabatan = jabatan;
        mNoReg = noReg;
        mEmail = email;
        mKTP = ktp;
        mBPJS = bpjs;
        mNoRek = noRek;
        mNpwp = npwp;
        mStatusPernikahan = statusPernikahan;
        mJenisKelamin = jenisKelamin;
        mAgama = agama;
        mImageURL = imageURL;
    }

    public int getId(){
        return mId;
    }
    public String getNama(){return mNama;}
    public String getTelepon(){return mTelepon;}
    public String getAlamatTugas(){return mAlamatTugas;}
    public String getAlamatPribadi(){return mAlamatPribadi;}
    public String getBidang(){return mBidang;}
    public String getJabatan(){return mJabatan;}
    public String getNoReg(){return mNoReg;}
    public String getEmail(){return mEmail;}
    public String getKTP(){return mKTP;}
    public String getBPJS(){return mBPJS;}
    public String getNoRek(){return mNoRek;}
    public String getNoNPWP(){return mNpwp;}
    public String getStatusPernikahan(){return mStatusPernikahan;}
    public String getJenisKelamin(){return mJenisKelamin;}
    public String getAgama(){return mAgama;}
    public String getImageUrl(){
        return mImageURL;
    }
}
