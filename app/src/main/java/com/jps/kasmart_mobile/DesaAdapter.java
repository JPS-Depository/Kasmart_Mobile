package com.jps.kasmart_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        return new DesaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DesaViewHolder holder, int position) {
        DesaItem currentItem = mDesaList.get(position);

        String namaDesa = currentItem.getNamaDesa();
        String namaKades = currentItem.getNamaKades();
        int KK = currentItem.getKK();
        int dusun = currentItem.getDusun();
        int rw = currentItem.getRW();
        int rt = currentItem.getRT();
        int suku = currentItem.getSuku();
        String createdAt = currentItem.getCreatedAt();
        String updatedAt = currentItem.getUpdatedAt();

        holder.mNamaDesa.setText(namaDesa);
        holder.mNamaKades.setText(namaKades);
        holder.mKK.setText(KK);
        holder.mDusun.setText(dusun);
        holder.mRW.setText(rw);
        holder.mRT.setText(rt);
        holder.mSuku.setText(suku);
        holder.mCreated_at.setText(createdAt);
        holder.mUpdated_at.setText(updatedAt);
        //String imageUrl = currentItem.getImageUrl();
        //String first = currentItem.getFirst();

        //holder.mTextViewGuest.setText(first);
        //Picasso.get().load(imageUrl).fit().centerInside().into(holder.mGuestImage);
    }

    @Override
    public int getItemCount() {
        return mDesaList.size();
    }

    public class DesaViewHolder extends RecyclerView.ViewHolder {
        public TextView mNamaDesa,mNamaKades,mKK, mDusun, mRW, mRT, mSuku, mCreated_at, mUpdated_at;
        public DesaViewHolder(View itemView) {
            super(itemView);
            mNamaDesa = itemView.findViewById(R.id.nama_desa);
            mNamaKades = itemView.findViewById(R.id.nama_kades);
            mKK = itemView.findViewById(R.id.jumlah_kk);
            mDusun = itemView.findViewById(R.id.jumlah_dusun);
            mRW = itemView.findViewById(R.id.jumlah_rw);
            mRT = itemView.findViewById(R.id.jumlah_rt);
            mSuku = itemView.findViewById(R.id.jumlah_suku);
            mCreated_at=itemView.findViewById(R.id.tanggal_dibuat);
            mUpdated_at=itemView.findViewById(R.id.tanggal_diupdate);
        }
    }
}


