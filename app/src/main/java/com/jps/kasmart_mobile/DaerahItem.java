package com.jps.kasmart_mobile;

public class DaerahItem {
    private int mId;
    private String mJenis;
    private String mKode;
    private String mNama;
    private String mCreated_at;
    private String mUpdated_at;

    public DaerahItem(int id,String jenis, String kode, String nama, String createAt, String updateAt){
        mId = id;
        mJenis = jenis;
        mKode = kode;
        mNama = nama;
        mCreated_at = createAt;
        mUpdated_at = updateAt;
    }

    public int getId(){
        return mId;
    }
    public String getJenis(){ return mJenis;}
    public String getKode(){ return mKode;}
    public String getNama(){ return mNama;}
    public String getCreatedAt(){ return mCreated_at;}
    public String getUpdatedAt(){ return mUpdated_at;}
}
