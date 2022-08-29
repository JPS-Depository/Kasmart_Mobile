package com.jps.kasmart_mobile;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
        int nomor = currentItem.getNomor();
        String createdAt = currentItem.getCreatedAt();
        String updatedAt = currentItem.getUpdatedAt();

        holder.mNamaKades.setText(namaKades);
        holder.mNamaDesa.setText(namaDesa);
        holder.mkK.setText(String.valueOf(kK));
        holder.mDusun.setText(String.valueOf(dusun));
        holder.mRW.setText(String.valueOf(rw));
        holder.mRT.setText(String.valueOf(rt));
        holder.mSuku.setText(String.valueOf(suku));
        holder.mNomor.setText(String.valueOf(nomor));
        holder.mCreated_at.setText(createdAt);
        holder.mUpdated_at.setText(updatedAt);
    }

    @Override
    public int getItemCount() {
        return mDesaList.size();
    }

    public class DesaViewHolder extends RecyclerView.ViewHolder {
        public SwipeRefreshLayout swipeRefreshLayout;
        public TextView mNamaDesa,mNamaKades,mkK, mDusun, mRW, mRT, mSuku, mNomor, mCreated_at, mUpdated_at;
        public DesaViewHolder(View itemView) {
            super(itemView);
            mNamaDesa = itemView.findViewById(R.id.nama_desa);
            mNamaKades = itemView.findViewById(R.id.nama_kades);
            mkK = itemView.findViewById(R.id.jumlah_kk);
            mDusun = itemView.findViewById(R.id.jumlah_dusun);
            mRW = itemView.findViewById(R.id.jumlah_rw);
            mRT = itemView.findViewById(R.id.jumlah_rt);
            mSuku = itemView.findViewById(R.id.jumlah_suku);
            mNomor = itemView.findViewById(R.id.nomor_kades);
            mCreated_at=itemView.findViewById(R.id.tanggal_dibuat);
            mUpdated_at=itemView.findViewById(R.id.tanggal_diupdate);

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


