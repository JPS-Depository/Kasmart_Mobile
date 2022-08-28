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

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder> {
    private Context mContext;
    private ArrayList<BeritaItem> mBeritaList;

    public BeritaAdapter(Context context, ArrayList<BeritaItem> beritaList) {
        this.mContext = context;
        this.mBeritaList = beritaList;
    }

    @NonNull
    @Override
    public BeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.berita_card, parent, false);// layout ganti bambang
        BeritaViewHolder beritaViewHolder = new BeritaViewHolder(v);
        return beritaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaViewHolder holder, int position) {
        BeritaItem currentItem = mBeritaList.get(position);

        String judul = currentItem.getJudul();
        String isi_berita = currentItem.getIsiBerita();
        String tanggal_berita = currentItem.getTanggalBerita();
        String createdAt = currentItem.getCreatedAt();
        String updatedAt = currentItem.getUpdatedAt();
        String createdBy = currentItem.getCreatedBy();
        /* current item untuk gambar*/

        holder.mJudul.setText(judul);
        holder.mIsiBerita.setText(isi_berita);
        holder.mTanggalBerita.setText(tanggal_berita);
        holder.mCreatedAt.setText(createdAt);
        holder.mUpdatedAt.setText(updatedAt);
        holder.mCreatedBy.setText(createdBy);
        /*holder for image*/
    }

    @Override
    public int getItemCount() {
        return mBeritaList.size();
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder {
        public SwipeRefreshLayout swipeRefreshLayout;
        public TextView mJudul, mIsiBerita,mTanggalBerita, mCreatedAt, mUpdatedAt, mCreatedBy;
        /*image declaration*/

        public BeritaViewHolder(View itemView) {
            super(itemView);
            // dibawah ini layout belum dibuat
            mJudul = itemView.findViewById(R.id);
            mIsiBerita = itemView.findViewById(R.id);
            mTanggalBerita = itemView.findViewById(R.id);
            mCreatedAt = itemView.findViewById(R.id);
            mUpdatedAt = itemView.findViewById(R.id);
            mCreatedBy = itemView.findViewById(R.id);
            /*item id for image*/

            /// button dibawah blom ada
            itemView.findViewById(R.id.button_edit_desa).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Edit");
                }
            });
            itemView.findViewById(R.id.button_delete_desa).setOnClickListener(new View.OnClickListener() {
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


