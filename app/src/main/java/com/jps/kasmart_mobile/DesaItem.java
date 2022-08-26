package com.jps.kasmart_mobile;

public class DesaItem {
    private int mId;
    private String mNamaDesa;
    private String mNamaKades;
    private int mKK;
    private int mDusun;
    private int mRT;
    private int mRW;
    private int mSuku;
    private String mCreated_at;
    private String mUpdated_at;

    public DesaItem(int id, String namaDesa, String namaKades,int kK,int dusun, int rt,
                    int rw,int suku,String createAt,String updateAt){
        mId = id;
        mNamaDesa = namaDesa;
        mNamaKades = namaKades;
        mKK = kK;
        mDusun = dusun;
        mRW = rw;
        mRT = rt;
        mSuku = suku;
        mCreated_at = createAt;
        mUpdated_at = updateAt;
    }

    public int getId(){
        return mId;
    }
    public String getNamaDesa(){
        return mNamaDesa;
    }
    public String getNamaKades(){
        return mNamaKades;
    }
    public int getKK(){
        return mKK;
    }
    public int getDusun(){
        return mDusun;
    }
    public int getRW(){
        return mRW;
    }
    public int getRT(){
        return mRT;
    }
    public int getSuku(){ return mSuku;}
    public String getCreatedAt(){ return mCreated_at;}
    public String getUpdatedAt(){ return mUpdated_at;}
}
