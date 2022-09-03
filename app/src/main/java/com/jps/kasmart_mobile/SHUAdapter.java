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

public class SHUAdapter extends RecyclerView.Adapter<SHUAdapter.SHUViewHolder> {
    private Context mContext;
    private ArrayList<SHUItem> mSHUList;
    private boolean expandable;
    public SwipeRefreshLayout swipeRefreshLayout;

    public SHUAdapter(Context context, ArrayList<SHUItem> shuList, SwipeRefreshLayout swipeRefreshLayout) {
        this.mContext = context;
        this.mSHUList = shuList;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.expandable = false;
    }

    @NonNull
    @Override
    public SHUViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.shu_card, parent, false);
        SHUViewHolder shuViewHolder = new SHUViewHolder(v);
        return shuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SHUViewHolder holder, int position) {
        SHUItem currentItem = mSHUList.get(position);

        int id = currentItem.getId();
        String namaDaerah = currentItem.getNamaDaerah();
        int pembagianSHU = currentItem.getPembagianShu();
        int inventaris = currentItem.getInventaris();
        int tambahanModal = currentItem.getTambahanModal();
        int pelatihanPengembangan = currentItem.getPelatihanPengembangan();
        int bantuanSosial = currentItem.getBantuanSosial();
        int hadiahPemanfaat = currentItem.getHadiahPemanfaat();
        int pad = currentItem.getPad();
        int totalSisaUsaha = currentItem.getTotalSisaUsaha();

        holder.mNamaDaerah.setText(namaDaerah);
        holder.mPembagianShu.setText(String.valueOf(pembagianSHU));
        holder.mInventaris.setText(String.valueOf(inventaris));
        holder.mTambahanModal.setText(String.valueOf(tambahanModal));
        holder.mPelatihanPengembangan.setText(String.valueOf(pelatihanPengembangan));
        holder.mBantuanSosial.setText(String.valueOf(bantuanSosial));
        holder.mHadiahPemanfaat.setText(String.valueOf(hadiahPemanfaat));
        holder.mPad.setText(String.valueOf(pad));
        holder.mTotalSisaUsaha.setText(String.valueOf(totalSisaUsaha));

        boolean isExpandable = mSHUList.get(position).ismExpandable();
        holder.expandableLayout.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return mSHUList.size();
    }

    public class SHUViewHolder extends RecyclerView.ViewHolder {
        public TextView mNamaDaerah, mPembagianShu, mInventaris, mTambahanModal, mPelatihanPengembangan,
        mBantuanSosial, mHadiahPemanfaat, mPad, mTotalSisaUsaha;

        public LinearLayout linearLayout;
        public RelativeLayout expandableLayout;

        public SHUViewHolder(View itemView) {
            super(itemView);

            mNamaDaerah = itemView.findViewById(R.id.nama_daerah_shu);
            mPembagianShu = itemView.findViewById(R.id.pembagian_shu);
            mInventaris = itemView.findViewById(R.id.inventaris_shu);
            mTambahanModal = itemView.findViewById(R.id.tambahan_modal_shu);
            mPelatihanPengembangan = itemView.findViewById(R.id.pelatihan_pengembangan_shu);
            mBantuanSosial = itemView.findViewById(R.id.bansos_shu);
            mHadiahPemanfaat = itemView.findViewById(R.id.hadiah_pemanfaat_shu);
            mPad = itemView.findViewById(R.id.pad_shu);
            mTotalSisaUsaha = itemView.findViewById(R.id.total_sisa_hasil_usah_shu);

            linearLayout = itemView.findViewById(R.id.header_shu);
            expandableLayout = itemView.findViewById(R.id.extended_shu);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new SHUFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    fragmentTransaction.commit();
                    notifyDataSetChanged();

                }
            });

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SHUItem shuItem = mSHUList.get(getBindingAdapterPosition());
                    shuItem.setmExpandable(!shuItem.ismExpandable());
                    notifyDataSetChanged();
                }
            });

            itemView.findViewById(R.id.button_edit_shu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Edit");
                }
            });
            itemView.findViewById(R.id.button_delete_shu).setOnClickListener(new View.OnClickListener() {
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


