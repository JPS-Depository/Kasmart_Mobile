package com.jps.kasmart_mobile;

import android.content.Context;
import android.os.Bundle;
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

public class WajibAdapter extends RecyclerView.Adapter<WajibAdapter.WajibViewHolder> {
    private Context mContext;
    private ArrayList<WajibItem> mWajibList;
    private boolean expandable;
    public SwipeRefreshLayout swipeRefreshLayout;

    public WajibAdapter(Context context, ArrayList<WajibItem> wajibList, SwipeRefreshLayout swipeRefreshLayout) {
        this.mContext = context;
        this.mWajibList = wajibList;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.expandable = false;
    }

    @NonNull
    @Override
    public WajibViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.wajib_card, parent, false);
        WajibAdapter.WajibViewHolder wajibViewHolder = new WajibAdapter.WajibViewHolder(v);
        return wajibViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WajibViewHolder holder, int position) {
        WajibItem currentItem = mWajibList.get(position);

        int id = currentItem.getId();
        String tanggalKegiatan = currentItem.getTanggalKegiatan();
        String kegiatan = currentItem.getKegiatan();
        String jenis = currentItem.getJenis();
        String lokasi = currentItem.getLokasi();
        String detail = currentItem.getDetail();
        String sasaran = currentItem.getSasaran();
        String realisasi = currentItem.getRealisasi();
        String createdBy = currentItem.getCreatedBy();

        switch (realisasi){
            case "0":
                realisasi = "Belum Terealisasi";
                break;
            case "1":
                realisasi = "Selesai";
                break;
            case "2":
                realisasi = "Dalam Proses";
                break;
        }

        holder.mTanggalKegiatan.setText(tanggalKegiatan);
        holder.mKegiatan.setText(kegiatan);
        holder.mJenis.setText(jenis);
        holder.mLokasi.setText(lokasi);
        holder.mDetail.setText(detail);
        holder.mSasaran.setText(sasaran);
        holder.mRealisasi.setText(realisasi);
        holder.mCreatedBy.setText(createdBy);

        boolean isExpandable = mWajibList.get(position).ismExpandable();
        holder.expandableLayout.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return mWajibList.size();
    }

    public class WajibViewHolder extends RecyclerView.ViewHolder {
        public TextView mId, mTanggalKegiatan, mKegiatan, mJenis, mLokasi, mDetail, mSasaran, mRealisasi, mCreatedBy;
        public LinearLayout linearLayout;
        public RelativeLayout expandableLayout;

        public WajibViewHolder(View itemView) {
            super(itemView);

            mTanggalKegiatan = itemView.findViewById(R.id.tanggal_wajib);
            mKegiatan = itemView.findViewById(R.id.nama_kegiatan_wajib);
            mJenis = itemView.findViewById(R.id.tipe_kegiatan_wajib);
            mLokasi = itemView.findViewById(R.id.isi_lokasi_wajib);
            mDetail = itemView.findViewById(R.id.isi_detail_wajib);
            mSasaran = itemView.findViewById(R.id.sasaran_wajib);
            mRealisasi = itemView.findViewById(R.id.realisasi_kegiatan_wajib);
            mCreatedBy = itemView.findViewById(R.id.pendamping_wajib);

            linearLayout = itemView.findViewById(R.id.header_wajib);
            expandableLayout = itemView.findViewById(R.id.extended_wajib);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WajibItem wajibItem = mWajibList.get(getBindingAdapterPosition());
                    wajibItem.setmExpandable(!wajibItem.ismExpandable());
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
                    Fragment fragment = new WajibFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    fragmentTransaction.commit();
                    notifyDataSetChanged();

                }
            });

            itemView.findViewById(R.id.button_edit_wajib).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new WajibEditFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    Bundle bundle = new Bundle();

                    final int id = mWajibList.get(getAbsoluteAdapterPosition()).getId();
                    final String tipe = mWajibList.get(getAbsoluteAdapterPosition()).getJenis();
                    final String kegiatan = mWajibList.get(getAbsoluteAdapterPosition()).getKegiatan();
                    final String tanggalkegiatan = mWajibList.get(getAbsoluteAdapterPosition()).getTanggalKegiatan();
                    final String sasaran = mWajibList.get(getAbsoluteAdapterPosition()).getSasaran();
                    final String detail = mWajibList.get(getAbsoluteAdapterPosition()).getDetail();
                    final String lokasi = mWajibList.get(getAbsoluteAdapterPosition()).getLokasi();

                    bundle.putInt("id",id);
                    bundle.putString("tipe",tipe);
                    bundle.putString("kegiatan",kegiatan);
                    bundle.putString("tanggalkegiatan",tanggalkegiatan);
                    bundle.putString("sasaran",sasaran);
                    bundle.putString("detail",detail);
                    bundle.putString("lokasi",lokasi);
                    fragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack(null).commit();
                    notifyDataSetChanged();
                }
            });
            itemView.findViewById(R.id.button_delete_wajib).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Delete");
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager confirmManager = activity.getSupportFragmentManager();
                    DialogFragment dialog = new ConfirmationDelete();
                    Bundle bundle = new Bundle();
                    final int id = mWajibList.get(getAbsoluteAdapterPosition()).getId();
                    final String menu = "wajib";
                    bundle.putInt("id",id);
                    Log.d("id", String.valueOf(id));
                    bundle.putString("menu",menu);
                    dialog.setArguments(bundle);
                    dialog.show(confirmManager,"Alert");
                    notifyDataSetChanged();
                }
            });
        }

    }
}


