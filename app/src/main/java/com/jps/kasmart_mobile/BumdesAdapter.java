package com.jps.kasmart_mobile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class BumdesAdapter extends RecyclerView.Adapter<BumdesAdapter.BumdesViewHolder> {
    private Context mContext;
    private ArrayList<BumdesItem> mBumdesList;
    private boolean expandable;
    public SwipeRefreshLayout swipeRefreshLayout;

    public BumdesAdapter(Context context, ArrayList<BumdesItem> bumdesList, SwipeRefreshLayout swiperefreshlayout) {
        this.mContext = context;
        this.mBumdesList = bumdesList;
        this.swipeRefreshLayout = swiperefreshlayout;
        this.expandable = false;
    }

    @NonNull
    @Override
    public BumdesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.bumdes_card, parent, false);
        BumdesViewHolder bumdesViewHolder = new BumdesViewHolder(v);
        return bumdesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BumdesViewHolder holder, int position) {
        BumdesItem currentItem = mBumdesList.get(position);

        String namaDaerah = currentItem.getNamaDaerah();
        String unitUsaha = currentItem.getUnitUsaha();
        String jenisUsaha = currentItem.getJenisUsaha();
        int penyertaanModal = currentItem.getPenyertaanModal();
        String sumberDana = currentItem.getSumberDana();
        String tahunPenyertaanModal = currentItem.getTahunPenyertaanModal();
        int piutangUsaha = currentItem.getPiutangUsaha();
        int piutangGajiKaryawan = currentItem.getPiutangGajiKaryawan();
        int totalDanaRekening = currentItem.getTotalDanaRekening();
        int inventaris = currentItem.getInventaris();
        int pengalihanAset = currentItem.getPengalihanAset();
        int penghapusanPiutang = currentItem.getPenghapusanPiutang();
        int kasHarian = currentItem.getKasHarian();
        int barangDagang = currentItem.getBarangDagang();
        int totalKekayaan = currentItem.getTotalKekayaan();
        int cashOpname = currentItem.getCashOpname();
        int shu = currentItem.getShu();
        int pades = currentItem.getPades();
        int pengembalianUsaha = currentItem.getPengembalianUsaha();
        int bungaBankUsp = currentItem.getBungaBankUsp();
        int biayaPromosi = currentItem.getBiayaPromosi();
        int biayaRapat = currentItem.getBiayaRapat();
        int tunjanganKinerja = currentItem.getTunjanganKinerja();
        int labaBulanLalu = currentItem.getLabaBulanLalu();
        int labaBulanIni = currentItem.getLabaBulanIni();
        int labaTotal = currentItem.getLabaTotal();
        String status = currentItem.getStatus();

        holder.mNamaDaerah.setText(namaDaerah);
        holder.mUnitUsaha.setText(unitUsaha);
        holder.mJenisUsaha.setText(jenisUsaha);
        holder.mPenyertaanModal.setText(String.valueOf(penyertaanModal));
        holder.mSumberDana.setText(sumberDana);
        holder.mTahunPenyertaanModal.setText(tahunPenyertaanModal);
        holder.mPiutangUsaha.setText(String.valueOf(piutangUsaha));
        holder.mPiutangGajiKaryawan.setText(String.valueOf(piutangGajiKaryawan));
        holder.mTotalDanaRekening.setText(String.valueOf(totalDanaRekening));
        holder.mInventaris.setText(String.valueOf(inventaris));
        holder.mPengalihanAset.setText(String.valueOf(pengalihanAset));
        holder.mPenghapusanPiutang.setText(String.valueOf(penghapusanPiutang));
        holder.mKasHarian.setText(String.valueOf(kasHarian));
        holder.mBarangDagang.setText(String.valueOf(barangDagang));
        holder.mTotalKekayaan.setText(String.valueOf(totalKekayaan));
        holder.mCashOpname.setText(String.valueOf(cashOpname));
        holder.mShu.setText(String.valueOf(shu));
        holder.mPades.setText(String.valueOf(pades));
        holder.mPengembalianUsaha.setText(String.valueOf(pengembalianUsaha));
        holder.mBungaBankUsp .setText(String.valueOf(bungaBankUsp));
        holder.mBiayaPromosi.setText(String.valueOf(biayaPromosi));
        holder.mBiayaRapat.setText(String.valueOf(biayaRapat));
        holder.mTunjanganKinerja .setText(String.valueOf(tunjanganKinerja));
        holder.mLabaBulanLalu.setText(String.valueOf(labaBulanLalu));
        holder.mLabaBulanIni.setText(String.valueOf(labaBulanIni));
        holder.mLabaTotal.setText(String.valueOf(labaTotal));
        holder.mStatus.setText(status);

        boolean isExpandable = mBumdesList.get(position).ismExpandable();
        holder.expandableLayout.setVisibility(isExpandable? View.VISIBLE:View.GONE);

        holder.itemView.findViewById(R.id.button_edit_bumdes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button Press","Pressed Edit");
            }
        });
        holder.itemView.findViewById(R.id.button_delete_bumdes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mBumdesList.get(position).getId();
                Log.d("Button Press","Pressed Delete");
                FragmentActivity activity = (FragmentActivity) (mContext);
                FragmentManager confirmManager = activity.getSupportFragmentManager();
                DialogFragment dialog = new ConfirmationDelete();
                dialog.show(confirmManager,"Alert");
                Log.d("Button Id", String.valueOf(id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBumdesList.size();
    }

    public class BumdesViewHolder extends RecyclerView.ViewHolder {
        public TextView mNamaDaerah, mUnitUsaha, mJenisUsaha, mSumberDana, mTahunPenyertaanModal, mStatus,
                mPenyertaanModal,mPiutangUsaha, mPiutangGajiKaryawan, mTotalDanaRekening, mInventaris, mPengalihanAset, mPenghapusanPiutang, mKasHarian,
                mBarangDagang, mTotalKekayaan, mCashOpname, mShu, mPades, mPengembalianUsaha, mBungaBankUsp, mBiayaPromosi, mBiayaRapat, mTunjanganKinerja,
                mLabaBulanLalu, mLabaBulanIni, mLabaTotal;
        public LinearLayout linearLayout;
        public RelativeLayout expandableLayout;
        public BumdesViewHolder(View itemView) {
            super(itemView);

            mNamaDaerah = itemView.findViewById(R.id.nama_daerah_bumdes);
            mUnitUsaha = itemView.findViewById(R.id.unit_usaha_bumdes);
            mJenisUsaha = itemView.findViewById(R.id.jenis_usaha_bumdes);
            mPenyertaanModal = itemView.findViewById(R.id.penyertaan_modal_bumdes);
            mSumberDana = itemView.findViewById(R.id.sumber_dana_bumdes);
            mTahunPenyertaanModal = itemView.findViewById(R.id.tahun_modal_bumdes);
            mPiutangUsaha = itemView.findViewById(R.id.sisa_piutang_usaha_bumdes);
            mPiutangGajiKaryawan = itemView.findViewById(R.id.piutang_gaji_karyawan);
            mTotalDanaRekening = itemView.findViewById(R.id.total_dana_rekening_bumdes);
            mInventaris = itemView.findViewById(R.id.inventaris_bumdes);
            mPengalihanAset = itemView.findViewById(R.id.pengalihan_aset_bumdes);
            mPenghapusanPiutang = itemView.findViewById(R.id.penghapusan_piutang_bumdes);
            mKasHarian = itemView.findViewById(R.id.kas_harian_bumdes);
            mBarangDagang = itemView.findViewById(R.id.persediaan_barang_bumdes);
            mTotalKekayaan = itemView.findViewById(R.id.total_kekayaan_bumdes);
            mCashOpname = itemView.findViewById(R.id.cash_opname_bumdes);
            mShu = itemView.findViewById(R.id.SHU_bumdes);
            mPades = itemView.findViewById(R.id.pades_bumdes);
            mPengembalianUsaha = itemView.findViewById(R.id.pengembalian_usaha_bumdes);
            mBungaBankUsp = itemView.findViewById(R.id.bunga_bank_bumdes);
            mBiayaPromosi = itemView.findViewById(R.id.biaya_promosi_bumdes);
            mBiayaRapat = itemView.findViewById(R.id.biaya_rapat_bumdes);
            mTunjanganKinerja = itemView.findViewById(R.id.tunjangan_kinerja_bumdes);
            mLabaBulanLalu = itemView.findViewById(R.id.laba_sd_bulan_lalu_bumdes);
            mLabaBulanIni = itemView.findViewById(R.id.laba_bulan_ini_bumdes);
            mLabaTotal = itemView.findViewById(R.id.laba_sd_bulan_ini_bumdes);
            mStatus = itemView.findViewById(R.id.status_bumdes);

            linearLayout = itemView.findViewById(R.id.header_bumdes);
            expandableLayout = itemView.findViewById(R.id.extended_bumdes);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);

                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new BumdesFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    fragmentTransaction.commit();
                    notifyDataSetChanged();

                }
            });

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BumdesItem bumdesItem = mBumdesList.get(getBindingAdapterPosition());
                    bumdesItem.setmExpandable(!bumdesItem.ismExpandable());
                    notifyDataSetChanged();
                }
            });
        }
    }
}


