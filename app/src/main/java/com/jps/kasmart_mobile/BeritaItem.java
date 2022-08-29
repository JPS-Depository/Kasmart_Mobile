package com.jps.kasmart_mobile;

public class BeritaItem {
    private int mId;
    private String mJudul;
    private String mIsiBerita;
    private String mTanggalBerita;
    private String mCreated_by;
    /*insert image assignment here*/

    public BeritaItem(int id,String judul, String isiBerita, String tanggalBerita, String createBy /*, Image Gambar Berita */){
        mId = id;
        mJudul = judul;
        mIsiBerita = isiBerita;
        mTanggalBerita = tanggalBerita;
        mCreated_by = createBy;
    }

    public int getId(){
        return mId;
    }
    public String getJudul(){ return mJudul;}
    public String getIsiBerita(){ return mIsiBerita;}
    public String getTanggalBerita(){ return mTanggalBerita;}
    public String getCreatedBy(){return mCreated_by;}
    /* insert image getter here */
}
