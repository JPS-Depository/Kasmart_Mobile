package com.jps.kasmart_mobile;

public class BeritaItem {
    private int mId;
    private String mJudul;
    private String mIsiBerita;
    private String mTanggalBerita;
    private String mCreated_by;
    private String mImage;

    public BeritaItem(int id,String judul, String isiBerita, String tanggalBerita, String createBy, String image){
        mId = id;
        mJudul = judul;
        mIsiBerita = isiBerita;
        mTanggalBerita = tanggalBerita;
        mCreated_by = createBy;
        mImage = image;
    }

    public int getId(){
        return mId;
    }
    public String getJudul(){ return mJudul;}
    public String getIsiBerita(){ return mIsiBerita;}
    public String getTanggalBerita(){ return mTanggalBerita;}
    public String getCreatedBy(){return mCreated_by;}
    public String getImage(){return mImage;}
}
