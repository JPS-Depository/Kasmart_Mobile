package com.jps.kasmart_mobile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DesaAdapter extends RecyclerView.Adapter<DesaAdapter.DesaViewHolder> {
    private Context mContext;
    private ArrayList<DesaItem> mDesaList;

    public DesaAdapter(Context context, ArrayList<DesaItem> desaList) {
        this.mContext = context;
        this.mDesaList = desaList;
    }

    @NonNull
    @Override
    public DesaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.desa_card, parent, false);
        DesaViewHolder desaViewHolder = new DesaViewHolder(v);
        return desaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DesaViewHolder holder, int position) {
        DesaItem currentItem = mDesaList.get(position);

        String namaDesa = currentItem.getNamaDesa();
        String namaKades = currentItem.getNamaKades();
        int kK = currentItem.getkK();
        int dusun = currentItem.getDusun();
        int rw = currentItem.getRW();
        int rt = currentItem.getRT();
        int suku = currentItem.getSuku();
        String createdAt = currentItem.getCreatedAt();
        String updatedAt = currentItem.getUpdatedAt();
        holder.mNamaKades.setText(namaKades);
        holder.mNamaDesa.setText(namaDesa);
        holder.mkK.setText(String.valueOf(kK));
        holder.mDusun.setText(String.valueOf(dusun));
        holder.mRW.setText(String.valueOf(rw));
        holder.mRT.setText(String.valueOf(rt));
        holder.mSuku.setText(String.valueOf(suku));
        holder.mCreated_at.setText(createdAt);
        holder.mUpdated_at.setText(updatedAt);
    }

    @Override
    public int getItemCount() {
        return mDesaList.size();
    }

    public class DesaViewHolder extends RecyclerView.ViewHolder {
        public TextView mNamaDesa,mNamaKades,mkK, mDusun, mRW, mRT, mSuku, mCreated_at, mUpdated_at;
        public DesaViewHolder(View itemView) {
            super(itemView);
            mNamaDesa = itemView.findViewById(R.id.nama_desa);
            mNamaKades = itemView.findViewById(R.id.nama_kades);
            mkK = itemView.findViewById(R.id.jumlah_kk);
            mDusun = itemView.findViewById(R.id.jumlah_dusun);
            mRW = itemView.findViewById(R.id.jumlah_rw);
            mRT = itemView.findViewById(R.id.jumlah_rt);
            mSuku = itemView.findViewById(R.id.jumlah_suku);
            mCreated_at=itemView.findViewById(R.id.tanggal_dibuat);
            mUpdated_at=itemView.findViewById(R.id.tanggal_diupdate);
        }
    }
}


