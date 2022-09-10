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
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class USPAdapter extends RecyclerView.Adapter<USPAdapter.USPViewHolder> {
    private Context mContext;
    private ArrayList<USPItem> mUSPList;
    private boolean expandable;
    public SwipeRefreshLayout swipeRefreshLayout;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String role;
    LinearLayout linearLayout;


    public USPAdapter(Context context, ArrayList<USPItem> USPList, SwipeRefreshLayout swipeRefreshLayout) {
        this.mContext = context;
        this.mUSPList = USPList;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.expandable = false;
    }

    @NonNull
    @Override
    public USPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.usp_uek_card, parent, false);
        USPViewHolder USPViewHolder = new USPViewHolder(v);
        sessionManager = new SessionManager(mContext);
        sessionManager.checkLogin();
        user = sessionManager.getUserDetail();
        role = user.get(sessionManager.ROLE);

        linearLayout = v.findViewById(R.id.button_edit_delete_uek);

        switch(role){
            case "Guest":
            case "Super User":
                linearLayout.setVisibility(v.GONE);
                break;
        }
        return USPViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull USPViewHolder holder, int position) {
        USPItem currentItem = mUSPList.get(position);

        int id = currentItem.getId();
        String namaDaerah = currentItem.getNamaDaerah();
        int modal = currentItem.getModal();
        int perguliranOrang = currentItem.getPerguliranOrang();
        int perguliranDana = currentItem.getPerguliranDana();
        int sisaPiutangOrang = currentItem.getSisaPiutangOrang();
        int sisaPiutangDana = currentItem.getSisaPiutangDana();
        int totalDanaUsp = currentItem.getTotalDanaUsp();
        int totalDanaDud = currentItem.getTotalDanaDud();
        int kasUed = currentItem.getKasUed();
        int inventaris = currentItem.getInventaris();
        int pengapusanCadangan = currentItem.getPenghapusanCadangan();
        int sitaanJaminan = currentItem.getSitaanJaminan();
        int totalAset = currentItem.getTotalAset();
        int tunggakanOrang = currentItem.getTunggakanOrang();
        int tunggakanDana = currentItem.getTunggakanDana();
        int pengembalianOrang = currentItem.getPengembalianOrang();
        int pengembalianDana = currentItem.getPengembalianDana();
        int dagangOrang = currentItem.getDagangOrang();
        int dagangDana = currentItem.getDagangDana();
        int pertanianOrang = currentItem.getPertanianOrang();
        int pertanianDana = currentItem.getPertanianDana();
        int perkebunanOrang = currentItem.getPerkebunanOrang();
        int perkebunanDana = currentItem.getPerkebunanDana();
        int perikananOrang = currentItem.getPerikananOrang();
        int perikananDana = currentItem.getPerikananDana();
        int peternakanOrang = currentItem.getPeternakanOrang();
        int peternakanDana = currentItem.getPeternakanDana();
        int industriOrang = currentItem.getIndustriOrang();
        int industriDana = currentItem.getIndustriDana();
        int jasaOrang = currentItem.getJasaOrang();
        int jasaDana = currentItem.getJasaDana();
        int totalPeminjamanJenisUsaha = currentItem.getTotalPeminjamanJenisUsaha();
        int shu = currentItem.getShu();
        int penyaluranOrang = currentItem.getPenyaluranOrang();
        int penyaluranDana = currentItem.getPenyaluranDana();
        int pemanfaatOrang = currentItem.getPemanfaatOrang();
        int pemanfaatDana = currentItem.getPemanfaatDana();
        int cashOpname = currentItem.getCashOpname();
        int pades = currentItem.getPades();
        int pengembalianUsaha = currentItem.getPengembalianUsaha();
        int labaBersih = currentItem.getLabaBersih();
        double pengembalianPersentase = currentItem.getPengembalianPersentase();
        String status = currentItem.getStatus();

        holder.mNamaDaerah.setText(namaDaerah);
        holder.mModal.setText(String.valueOf(modal));
        holder.mPerguliranOrang.setText(String.valueOf(perguliranOrang));
        holder.mPerguliranDana.setText(String.valueOf(perguliranDana));
        holder.mSisaPiutangOrang.setText(String.valueOf(sisaPiutangOrang));
        holder.mSisaPiutangDana.setText(String.valueOf(sisaPiutangDana));
        holder.mTotalDanaUsp.setText(String.valueOf(totalDanaUsp));
        holder.mTotalDanaDud.setText(String.valueOf(totalDanaDud));
        holder.mKasUed.setText(String.valueOf(kasUed));
        holder.mInventaris.setText(String.valueOf(inventaris));
        holder.mPengapusanCadangan.setText(String.valueOf(pengapusanCadangan));
        holder.mSitaanJaminan.setText(String.valueOf(sitaanJaminan));
        holder.mTotalAset.setText(String.valueOf(totalAset));
        holder.mTunggakanOrang.setText(String.valueOf(tunggakanOrang));
        holder.mTunggakanDana.setText(String.valueOf(tunggakanDana));
        holder.mPengembalianOrang.setText(String.valueOf(pengembalianOrang));
        holder.mPengembalianDana.setText(String.valueOf(pengembalianDana));
        holder.mDagangOrang.setText(String.valueOf(dagangOrang));
        holder.mDagangDana.setText(String.valueOf(dagangDana));
        holder.mPertanianOrang.setText(String.valueOf(pertanianOrang));
        holder.mPertanianDana.setText(String.valueOf(pertanianDana));
        holder.mPerkebunanOrang.setText(String.valueOf(perkebunanOrang));
        holder.mPerkebunanDana.setText(String.valueOf(perkebunanDana));
        holder.mPerikananOrang.setText(String.valueOf(perikananOrang));
        holder.mPerikananDana.setText(String.valueOf(perikananDana));
        holder.mPeternakanOrang.setText(String.valueOf(peternakanOrang));
        holder.mPeternakanDana.setText(String.valueOf(peternakanDana));
        holder.mIndustriOrang.setText(String.valueOf(industriOrang));
        holder.mIndustriDana.setText(String.valueOf(industriDana));
        holder.mJasaOrang.setText(String.valueOf(jasaOrang));
        holder.mJasaDana.setText(String.valueOf(jasaDana));
        holder.mTotalPeminjamanJenisUsaha.setText(String.valueOf(totalPeminjamanJenisUsaha));
        holder.mShu.setText(String.valueOf(shu));
        holder.mPenyaluranOrang.setText(String.valueOf(penyaluranOrang));
        holder.mPenyaluranDana.setText(String.valueOf(penyaluranDana));
        holder.mPemanfaatOrang.setText(String.valueOf(pemanfaatOrang));
        holder.mPemanfaatDana.setText(String.valueOf(pemanfaatDana));
        holder.mCashOpname.setText(String.valueOf(cashOpname));
        holder.mPades.setText(String.valueOf(pades));
        holder.mPengembalianUsaha.setText(String.valueOf(pengembalianUsaha));
        holder.mLabaBersih.setText(String.valueOf(labaBersih));
        holder.mPengembalianPersentase.setText(String.valueOf(pengembalianPersentase));
        holder.mStatus.setText(status);

        boolean isExpandable = mUSPList.get(position).ismExpandable();
        holder.expandableLayout.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return mUSPList.size();
    }

    public class USPViewHolder extends RecyclerView.ViewHolder {
        public TextView mNamaDaerah,mModal,mPerguliranOrang,mPerguliranDana,mSisaPiutangOrang
                ,mSisaPiutangDana,mTotalDanaUsp,mTotalDanaDud,mKasUed,mInventaris,mPengapusanCadangan
                ,mSitaanJaminan,mTotalAset,mTunggakanOrang,mTunggakanDana,mPengembalianOrang
                ,mPengembalianDana,mDagangOrang,mDagangDana,mPertanianOrang,mPertanianDana
                ,mPerkebunanOrang,mPerkebunanDana,mPerikananOrang,mPerikananDana,mPeternakanOrang
                ,mPeternakanDana,mIndustriOrang,mIndustriDana,mJasaOrang,mJasaDana,mTotalPeminjamanJenisUsaha
                ,mShu,mPenyaluranOrang,mPenyaluranDana,mPemanfaatOrang,mPemanfaatDana,mCashOpname,mPades
                ,mPengembalianUsaha,mLabaBersih,mPengembalianPersentase,mStatus,mCreateAt,mCreateBy;
        public LinearLayout linearLayout;
        public RelativeLayout expandableLayout;
        public USPViewHolder(View itemView) {
            super(itemView);

            mNamaDaerah = itemView.findViewById(R.id.nama_daerah_uek);
            mModal = itemView.findViewById(R.id.modal_uek);
            mPerguliranOrang =itemView.findViewById(R.id.perguliran_orang_uek);
            mPerguliranDana = itemView.findViewById(R.id.perguliran_dana_uek);
            mSisaPiutangOrang = itemView.findViewById(R.id.sisa_piutang_orang_uek);
            mSisaPiutangDana = itemView.findViewById(R.id.sisa_piutang_dana_uek);
            mTotalDanaUsp = itemView.findViewById(R.id.Total_dana_rek_DUD_uek);
            mTotalDanaDud = itemView.findViewById(R.id.Total_dana_rek_UED_uek);
            mKasUed = itemView.findViewById(R.id.kas_UED_uek);
            mInventaris = itemView.findViewById(R.id.inventaris_uek);
            mPengapusanCadangan = itemView.findViewById(R.id.penghapusan_cadangan_uek);
            mSitaanJaminan = itemView.findViewById(R.id.sitaan_jaminan_uek);
            mTotalAset = itemView.findViewById(R.id.total_aset_uek);
            mTunggakanOrang = itemView.findViewById(R.id.tunggakan_orang_uek);
            mTunggakanDana = itemView.findViewById(R.id.tunggakan_dana_uek);
            mPengembalianOrang =itemView.findViewById(R.id.pengembalian_orang_uek);
            mPengembalianDana = itemView.findViewById(R.id.pengembalian_dana_uek);
            mDagangOrang = itemView.findViewById(R.id.dagang_orang_uek);
            mDagangDana =itemView.findViewById(R.id.dagang_dana_uek);
            mPertanianOrang = itemView.findViewById(R.id.pertanian_orang_uek);
            mPertanianDana = itemView.findViewById(R.id.pertanian_dana_uek);
            mPerkebunanOrang = itemView.findViewById(R.id.perkebunan_orang_uek);
            mPerkebunanDana = itemView.findViewById(R.id.perkebunan_dana_uek);
            mPerikananOrang = itemView.findViewById(R.id.perikanan_orang_uek);
            mPerikananDana = itemView.findViewById(R.id.perikanan_dana_uek);
            mPeternakanOrang = itemView.findViewById(R.id.peternakan_orang_uek);
            mPeternakanDana = itemView.findViewById(R.id.peternakan_dana_uek);
            mIndustriOrang = itemView.findViewById(R.id.industri_orang_uek);
            mIndustriDana = itemView.findViewById(R.id.industri_dana_uek);
            mJasaOrang = itemView.findViewById(R.id.jasa_orang_uek);
            mJasaDana = itemView.findViewById(R.id.jasa_dana_uek);
            mTotalPeminjamanJenisUsaha = itemView.findViewById(R.id.total_pinjaman_jenis_usaha_uek);
            mShu = itemView.findViewById(R.id.SHU_RL_uek);
            mPenyaluranOrang = itemView.findViewById(R.id.penyaluran_kurang_mampu_orang_uek);
            mPenyaluranDana = itemView.findViewById(R.id.penyaluran_kurang_mampu_dana_uek);
            mPemanfaatOrang = itemView.findViewById(R.id.pemanfaat_lunas_orang_uek);
            mPemanfaatDana = itemView.findViewById(R.id.pemanfaat_lunas_dana_uek);
            mCashOpname = itemView.findViewById(R.id.cash_opname_uek);
            mPades = itemView.findViewById(R.id.pades_uek);
            mPengembalianUsaha = itemView.findViewById(R.id.cadangan_pengembangan_usaha_uek);
            mLabaBersih = itemView.findViewById(R.id.laba_bersih_uek);
            mPengembalianPersentase = itemView.findViewById(R.id.persentase_pengembalian_uek);
            mStatus = itemView.findViewById(R.id.status_uek);

            linearLayout = itemView.findViewById(R.id.header_uek);
            expandableLayout = itemView.findViewById(R.id.extended_uek);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    USPItem USPItem = mUSPList.get(getBindingAdapterPosition());
                    USPItem.setmExpandable(!USPItem.ismExpandable());
                    notifyDataSetChanged();
                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new USPFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    fragmentTransaction.commit();
                    notifyDataSetChanged();

                }
            });

            itemView.findViewById(R.id.button_edit_uek).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Edit");
                }
            });
            itemView.findViewById(R.id.button_delete_uek).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Delete");
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager confirmManager = activity.getSupportFragmentManager();
                    DialogFragment dialog = new ConfirmationDelete();
                    dialog.show(confirmManager,"Alert");
                }
            });
        }
    }
}


