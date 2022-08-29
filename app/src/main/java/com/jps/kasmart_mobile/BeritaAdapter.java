package com.jps.kasmart_mobile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder> {
    private Context mContext;
    private ArrayList<BeritaItem> mBeritaList;
    String judul, isi_berita, tanggal_berita, createdAt, updatedAt, createdBy;
    BeritaItem currentItem;
    Bundle bundle;

    public BeritaAdapter(Context context, ArrayList<BeritaItem> beritaList) {
        this.mContext = context;
        this.mBeritaList = beritaList;
    }

    @NonNull
    @Override
    public BeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.berita_card, parent, false);
        BeritaViewHolder beritaViewHolder = new BeritaViewHolder(v);
        return beritaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaViewHolder holder, int position) {
        currentItem = mBeritaList.get(position);

        judul = currentItem.getJudul();
        isi_berita = currentItem.getIsiBerita();
        tanggal_berita = currentItem.getTanggalBerita();
        createdBy = currentItem.getCreatedBy();
        /* current item untuk gambar*/

        holder.mJudul.setText(judul);
        holder.mIsiBerita.setText(isi_berita);
        /*holder for image*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem = mBeritaList.get(position);
                bundle = new Bundle();

                bundle.putString("judul",currentItem.getJudul());
                bundle.putString("isi berita", currentItem.getIsiBerita());
                bundle.putString("tanggal_berita", currentItem.getTanggalBerita());
                bundle.putString("createdBy", currentItem.getCreatedBy());


                FragmentActivity activity = (FragmentActivity) (mContext);
                FragmentManager detailManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = detailManager.beginTransaction();
                Fragment detail = new BeritaDetailFragment();
                detail.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container,detail);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeritaList.size();
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder {
        public SwipeRefreshLayout swipeRefreshLayout;
        public TextView mJudul, mIsiBerita;
        /*image declaration*/

        public BeritaViewHolder(View itemView) {
            super(itemView);
            mJudul = itemView.findViewById(R.id.judul_berita);
            mIsiBerita = itemView.findViewById(R.id.isi_berita);
            /*item id for image*/
        }
    }
}


