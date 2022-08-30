package com.jps.kasmart_mobile;

public class BumdesItem {
    private int mId;
    private String mNamaDaerah;
    private String mUnitUsaha;
    private String mJenisUsaha;
    private String mSumberDana;
    private String mTahunPenyertaanModal;
    private String mStatus;
    private boolean mExpandable;
    private int mPenyertaanModal,mPiutangUsaha, mPiutangGajiKaryawan, mTotalDanaRekening, mInventaris, mPengalihanAset, mPenghapusanPiutang, mKasHarian,
    mBarangDagang, mTotalKekayaan, mCashOpname, mShu, mPades, mPengembalianUsaha, mBungaBankUsp, mBiayaPromosi, mBiayaRapat, mTunjanganKinerja,
    mLabaBulanLalu, mlabaBulanIni, mLabaTotal;

    public boolean ismExpandable() {
        return mExpandable;
    }

    public void setmExpandable(boolean mExpandable) {
        this.mExpandable = mExpandable;
    }

    public BumdesItem(int id, String namaDaerah, String unitUsaha, String jenisUsaha,
                      int penyertaanModal, String sumberDana, String tahunPenyertaanModal, int piutangUsaha,
                      int piutangGajiKaryawan, int totalDanaRekening, int inventaris, int pengalihanAset,
                      int penghapusanPiutang, int kasHarian, int barangDagang, int totalKekayaan,
                      int cashOpname, int shu, int pades, int pengembalianUsaha, int bungaBankUsp,
                      int biayaPromosi, int biayaRapat, int tunjanganKinerja, int labaBulanLalu,
                      int labaBulanIni, int labaTotal, String status){

        mId = id; mNamaDaerah = namaDaerah; mUnitUsaha = unitUsaha; mJenisUsaha = jenisUsaha; mPenyertaanModal = penyertaanModal;
        mSumberDana =sumberDana; mTahunPenyertaanModal=tahunPenyertaanModal; mPiutangUsaha = piutangUsaha; mPiutangGajiKaryawan = piutangGajiKaryawan;
        mTotalDanaRekening = totalDanaRekening; mInventaris = inventaris; mPengalihanAset = pengalihanAset; mPenghapusanPiutang = penghapusanPiutang;
        mKasHarian = kasHarian; mBarangDagang = barangDagang; mTotalKekayaan = totalKekayaan; mCashOpname = cashOpname; mShu = shu;
        mPades = pades; mPengembalianUsaha = pengembalianUsaha; mBungaBankUsp = bungaBankUsp; mBiayaPromosi = biayaPromosi;
        mBiayaRapat = biayaRapat; mTunjanganKinerja = tunjanganKinerja; mLabaBulanLalu = labaBulanLalu; mlabaBulanIni = labaBulanIni;
        mLabaTotal = labaTotal; mStatus = status;
        this.mExpandable = false;
    }

    public int getId(){
        return mId;
    }
    public String getNamaDaerah(){return mNamaDaerah;}
    public String getUnitUsaha(){return mUnitUsaha;}
    public String getJenisUsaha(){return mJenisUsaha;}
    public int getPenyertaanModal(){return mPenyertaanModal;}
    public String getSumberDana(){return mSumberDana;}
    public String getTahunPenyertaanModal(){return mTahunPenyertaanModal;}
    public int getPiutangUsaha(){return mPiutangUsaha;}
    public int getPiutangGajiKaryawan(){return mPiutangGajiKaryawan;}
    public int getTotalDanaRekening(){return mTotalDanaRekening;}
    public int getInventaris(){return mInventaris;}
    public int getPengalihanAset(){return mPengalihanAset;}
    public int getPenghapusanPiutang(){return mPenghapusanPiutang;}
    public int getKasHarian(){return mKasHarian;}
    public int getBarangDagang(){return mBarangDagang;}
    public int getTotalKekayaan(){return mTotalKekayaan;}
    public int getCashOpname(){return mCashOpname;}
    public int getShu(){return mShu;}
    public int getPades(){return mPades;}
    public int getPengembalianUsaha(){return mPengembalianUsaha;}
    public int getBungaBankUsp(){return mBungaBankUsp;}
    public int getBiayaPromosi(){return mBiayaPromosi;}
    public int getBiayaRapat(){return mBiayaRapat;}
    public int getTunjanganKinerja(){return mTunjanganKinerja;}
    public int getLabaBulanLalu(){return mLabaBulanLalu;}
    public int getLabaBulanIni(){return mlabaBulanIni;}
    public int getLabaTotal(){return mLabaTotal;}
    public String getStatus(){return mStatus;}

}
