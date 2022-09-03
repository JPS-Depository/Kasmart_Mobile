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

public class DaerahAdapter extends RecyclerView.Adapter<DaerahAdapter.DaerahViewHolder> {
    private Context mContext;
    private ArrayList<DaerahItem> mDaerahList;
    public SwipeRefreshLayout swipeRefreshLayout;

    public DaerahAdapter(Context context, ArrayList<DaerahItem> daerahList,SwipeRefreshLayout swipeRefreshLayout) {
        this.mContext = context;
        this.mDaerahList = daerahList;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @NonNull
    @Override
    public DaerahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.daerah_card, parent, false);// layout ganti bambang
        DaerahViewHolder daerahViewHolder = new DaerahViewHolder(v);
        return daerahViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DaerahViewHolder holder, int position) {
        DaerahItem currentItem = mDaerahList.get(position);

        String jenis = currentItem.getJenis();
        String nama = currentItem.getNama();
        String kode = currentItem.getKode();

        holder.mJenis.setText(jenis);
        holder.mNama.setText(nama);
        holder.mKode.setText(kode);
    }

    @Override
    public int getItemCount() {
        return mDaerahList.size();
    }

    public class DaerahViewHolder extends RecyclerView.ViewHolder {
        public TextView mJenis,mNama, mKode;
        public DaerahViewHolder(View itemView) {
            super(itemView);
            mJenis = itemView.findViewById(R.id.jenisDaerah);
            mKode = itemView.findViewById(R.id.kodeDaerah);
            mNama = itemView.findViewById(R.id.nama_daerah);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new DaerahFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    fragmentTransaction.commit();
                    notifyDataSetChanged();

                }
            });

            itemView.findViewById(R.id.button_edit_daerah).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Edit");
                }
            });
            itemView.findViewById(R.id.button_delete_daerah).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Delete");
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager confirmManager = activity.getSupportFragmentManager();
                    DialogFragment dialog = new ConfirmationDelete();
                    Bundle bundle = new Bundle();
                    final int id = mDaerahList.get(getAbsoluteAdapterPosition()).getId();
                    final String menu = "daerah";
                    bundle.putInt("id",id);
                    Log.d("id", String.valueOf(id));
                    bundle.putString("menu",menu);
                    dialog.setArguments(bundle);
                    dialog.show(confirmManager,"Alert");
                    notifyDataSetChanged();
                }
            });
        }
    }
}


