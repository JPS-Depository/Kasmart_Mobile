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

public class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.AbsensiViewHolder> {
    private Context mContext;
    private ArrayList<AbsensiItem> mAbsensiList;
    private boolean expandable;

    public AbsensiAdapter(Context context, ArrayList<AbsensiItem> absensiList) {
        this.mContext = context;
        this.mAbsensiList = absensiList;
        this.expandable = false;
    }

    @NonNull
    @Override
    public AbsensiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.absen_card, parent, false);
        AbsensiAdapter.AbsensiViewHolder absensiViewHolder = new AbsensiAdapter.AbsensiViewHolder(v);
        return absensiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AbsensiViewHolder holder, int position) {
        AbsensiItem currentItem = mAbsensiList.get(position);

        int id = currentItem.getId();
        String kegiatan = currentItem.getKegiatan();
        String lokasi = currentItem.getLokasi();
        String keterangan = currentItem.getKeterangan();
        String tanggalAbsen = currentItem.getTanggalAbsen();

        holder.mKegiatan.setText(kegiatan);
        holder.mLokasi.setText(lokasi);
        holder.mKeterangan.setText(keterangan);
        holder.mTanggalAbsen.setText(tanggalAbsen);

        boolean isExpandable = mAbsensiList.get(position).ismExpandable();
        holder.expandableLayout.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return mAbsensiList.size();
    }

    public class AbsensiViewHolder extends RecyclerView.ViewHolder {
        public SwipeRefreshLayout swipeRefreshLayout;
        public TextView mKegiatan, mLokasi, mKeterangan, mTanggalAbsen;
        public LinearLayout linearLayout;
        public RelativeLayout expandableLayout;

        public AbsensiViewHolder(View itemView) {
            super(itemView);

            mKegiatan = itemView.findViewById(R.id.nama_kegiatan_absen);
            mLokasi = itemView.findViewById(R.id.isi_lokasi_absen);
            mKeterangan = itemView.findViewById(R.id.isi_keterangan_absensi);
            mTanggalAbsen = itemView.findViewById(R.id.tanggal_absen);

            linearLayout = itemView.findViewById(R.id.header_absen);
            expandableLayout = itemView.findViewById(R.id.extended_absen);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AbsensiItem absensiItem = mAbsensiList.get(getBindingAdapterPosition());
                    absensiItem.setmExpandable(!absensiItem.ismExpandable());
                    notifyDataSetChanged();
                }
            });

            itemView.findViewById(R.id.button_edit_absen).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Edit");
                }
            });
            itemView.findViewById(R.id.button_delete_absen).setOnClickListener(new View.OnClickListener() {
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


