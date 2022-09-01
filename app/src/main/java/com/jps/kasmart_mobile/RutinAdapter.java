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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class RutinAdapter extends RecyclerView.Adapter<RutinAdapter.RutinViewHolder> {
    private Context mContext;
    private ArrayList<RutinItem> mRutinList;
    private boolean expandable;

    public RutinAdapter(Context context, ArrayList<RutinItem> rutinList) {
        this.mContext = context;
        this.mRutinList = rutinList;
        this.expandable = false;
    }

    @NonNull
    @Override
    public RutinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.rutin_card, parent, false);
        RutinAdapter.RutinViewHolder rutinViewHolder = new RutinAdapter.RutinViewHolder(v);
        return rutinViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RutinViewHolder holder, int position) {
        RutinItem currentItem = mRutinList.get(position);

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

        boolean isExpandable = mRutinList.get(position).ismExpandable();
        holder.expandableLayout.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return mRutinList.size();
    }

    public class RutinViewHolder extends RecyclerView.ViewHolder {
        public SwipeRefreshLayout swipeRefreshLayout;
        public TextView mId, mTanggalKegiatan, mKegiatan, mJenis, mLokasi, mDetail, mSasaran, mRealisasi, mCreatedBy;
        public LinearLayout linearLayout;
        public RelativeLayout expandableLayout;

        public RutinViewHolder(View itemView) {
            super(itemView);

            mTanggalKegiatan = itemView.findViewById(R.id.tanggal_rutin);
            mKegiatan = itemView.findViewById(R.id.nama_kegiatan_rutin);
            mJenis = itemView.findViewById(R.id.tipe_kegiatan_rutin);
            mLokasi = itemView.findViewById(R.id.isi_lokasi_rutin);
            mDetail = itemView.findViewById(R.id.isi_detail_rutin);
            mSasaran = itemView.findViewById(R.id.sasaran_rutin);
            mRealisasi = itemView.findViewById(R.id.realisasi_kegiatan_rutin);
            mCreatedBy = itemView.findViewById(R.id.pendamping_rutin);

            linearLayout = itemView.findViewById(R.id.header_rutin);
            expandableLayout = itemView.findViewById(R.id.extended_rutin);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RutinItem rutinItem = mRutinList.get(getBindingAdapterPosition());
                    rutinItem.setmExpandable(!rutinItem.ismExpandable());
                    notifyDataSetChanged();
                }
            });

            itemView.findViewById(R.id.button_edit_rutin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Edit");
                }
            });
            itemView.findViewById(R.id.button_delete_rutin).setOnClickListener(new View.OnClickListener() {
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


