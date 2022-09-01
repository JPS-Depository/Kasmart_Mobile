package com.jps.kasmart_mobile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class VisumAdapter extends RecyclerView.Adapter<VisumAdapter.VisumViewHolder> {
    private Context mContext;
    private ArrayList<VisumItem> mVisumList;

    public VisumAdapter(Context context, ArrayList<VisumItem> visumList) {
        this.mContext = context;
        this.mVisumList = visumList;
    }

    @NonNull
    @Override
    public VisumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.visum_card, parent, false);
        VisumAdapter.VisumViewHolder visumViewHolder = new VisumAdapter.VisumViewHolder(v);
        return visumViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VisumViewHolder holder, int position) {
        VisumItem currentItem = mVisumList.get(position);

        int id = currentItem.getId();
        String kegiatan = currentItem.getKegiatan();
        String tanggalVisum = currentItem.getTanggalVisum();
        String tanggalKegiatan = currentItem.getTanggalKegiatan();
        String kabupaten = currentItem.getKabupaten();
        String kecamatan = currentItem.getKecamatan();
        String kelDes = currentItem.getKelDes();
        String hasilKegiatan = currentItem.getHasilKegiatan();

        holder.mKegiatan.setText(kegiatan);
        holder.mTanggalVisum.setText(tanggalVisum);
        holder.mTanggalKegiatan.setText(tanggalKegiatan);
        holder.mKabupaten.setText(kabupaten);
        holder.mKecamatan.setText(kecamatan);
        holder.mKelDes.setText(kelDes);
        holder.mHasilKegiatan.setText(hasilKegiatan);
    }

    @Override
    public int getItemCount() {
        return mVisumList.size();
    }

    public class VisumViewHolder extends RecyclerView.ViewHolder {
        public SwipeRefreshLayout swipeRefreshLayout;
        public TextView mId, mKegiatan, mTanggalVisum, mTanggalKegiatan, mKabupaten, mKecamatan, mKelDes, mHasilKegiatan;

        public VisumViewHolder(View itemView) {
            super(itemView);

            mKegiatan = itemView.findViewById(R.id.nama_kegiatan_visum);
            mTanggalVisum = itemView.findViewById(R.id.tanggal_visum_visum);
            mTanggalKegiatan = itemView.findViewById(R.id.tanggal_kegiatan_visum);
            mKabupaten = itemView.findViewById(R.id.kabupaten_kegiatan_visum);
            mKecamatan = itemView.findViewById(R.id.kecamatan_kegiatan_visum);
            mKelDes = itemView.findViewById(R.id.kelurahan_desa_visum);
            mHasilKegiatan = itemView.findViewById(R.id.hasil_kegiatan_visum);

            itemView.findViewById(R.id.button_edit_visum).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Edit");
                }
            });
            itemView.findViewById(R.id.button_delete_visum).setOnClickListener(new View.OnClickListener() {
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


