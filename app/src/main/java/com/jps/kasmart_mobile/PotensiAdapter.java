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

public class PotensiAdapter extends RecyclerView.Adapter<PotensiAdapter.PotensiViewHolder> {
    private Context mContext;
    private ArrayList<PotensiItem> mPotensiList;

    public PotensiAdapter(Context context, ArrayList<PotensiItem> potensiList) {
        this.mContext = context;
        this.mPotensiList = potensiList;
    }

    @NonNull
    @Override
    public PotensiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.potensi_card, parent, false);
        PotensiAdapter.PotensiViewHolder potensiViewHolder = new PotensiAdapter.PotensiViewHolder(v);
        return potensiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PotensiViewHolder holder, int position) {
        PotensiItem currentItem = mPotensiList.get(position);

        int id = currentItem.getId();
        String jenis = currentItem.getJenis();
        String nama = currentItem.getNama();
        String sumberDayaAlam = currentItem.getSumberDayaAlam();
        String sumberDayaManusia = currentItem.getSumberDayaManusia();
        String asetDesa = currentItem.getAsetDesa();
        String budayaDesa = currentItem.getBudayaDesa();

        holder.mJenis.setText(jenis);
        holder.mNama.setText(nama);
        holder.mSumberDayaAlam.setText(sumberDayaAlam);
        holder.mSumberDayaManusia.setText(sumberDayaManusia);
        holder.mAsetDesa.setText(asetDesa);
        holder.mBudayaDesa.setText(budayaDesa);
    }

    @Override
    public int getItemCount() {
        return mPotensiList.size();
    }

    public class PotensiViewHolder extends RecyclerView.ViewHolder {
        public SwipeRefreshLayout swipeRefreshLayout;
        public TextView mJenis, mNama, mSumberDayaAlam, mSumberDayaManusia, mAsetDesa, mBudayaDesa;

        public PotensiViewHolder(View itemView) {
            super(itemView);

            mJenis = itemView.findViewById(R.id.tipe_daerah_potensi);
            mNama = itemView.findViewById(R.id.nama_daerah_potensi);
            mSumberDayaAlam = itemView.findViewById(R.id.sumber_daya_alam_potensi);
            mSumberDayaManusia = itemView.findViewById(R.id.sumber_daya_manusia_potensi);
            mAsetDesa = itemView.findViewById(R.id.aset_desa_potensi);
            mBudayaDesa = itemView.findViewById(R.id.budaya_dan_kesenian_potensi);

            itemView.findViewById(R.id.button_edit_potensi).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Edit");
                }
            });
            itemView.findViewById(R.id.button_delete_potensi).setOnClickListener(new View.OnClickListener() {
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


