package com.jps.kasmart_mobile;

import android.database.StaleDataException;

public class VisumItem {
    private int mId;
    private String mKegiatan, mTanggalVisum, mTanggalKegiatan, mKabupaten, mKecamatan, mKelDes,
            mHasilKegiatan,mImage;


    public VisumItem(int id,String kegiatan, String tanggalVisum, String tanggalKegiatan,
                     String kabupaten, String kecamatan, String kelDes,
                     String hasilKegiatan, String image){
        mId = id;
        mKegiatan = kegiatan;
        mTanggalVisum = tanggalVisum;
        mTanggalKegiatan = tanggalKegiatan;
        mKabupaten = kabupaten;
        mKecamatan = kecamatan;
        mKelDes = kelDes;
        mHasilKegiatan = hasilKegiatan;
        mImage = image;
    }

    public int getId(){
        return mId;
    }
    public String getKegiatan(){return mKegiatan;}
    public String getTanggalVisum(){return  mTanggalVisum;}
    public String getTanggalKegiatan(){return  mTanggalKegiatan;}
    public String getKabupaten(){return mKabupaten;}
    public String getKecamatan(){return mKecamatan;}
    public String getKelDes(){return mKelDes;}
    public String getHasilKegiatan(){return  mHasilKegiatan;}
    public String getImage(){return mImage;}
}
