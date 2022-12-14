package com.jps.kasmart_mobile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class PotensiAdapter extends RecyclerView.Adapter<PotensiAdapter.PotensiViewHolder> {
    private Context mContext;
    private ArrayList<PotensiItem> mPotensiList;
    SwipeRefreshLayout swipeRefreshLayout;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String role;
    Button editButton, deleteButton;

    public PotensiAdapter(Context context, ArrayList<PotensiItem> potensiList, SwipeRefreshLayout swipeRefreshLayout) {
        this.mContext = context;
        this.mPotensiList = potensiList;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @NonNull
    @Override
    public PotensiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.potensi_card, parent, false);
        PotensiAdapter.PotensiViewHolder potensiViewHolder = new PotensiAdapter.PotensiViewHolder(v);

        sessionManager = new SessionManager(mContext);
        sessionManager.checkLogin();
        user = sessionManager.getUserDetail();
        role = user.get(sessionManager.ROLE);

        editButton = v.findViewById(R.id.button_edit_potensi);
        deleteButton = v.findViewById(R.id.button_delete_potensi);

        switch(role){
            case "Guest":
                deleteButton.setVisibility(v.GONE);
                editButton.setVisibility(v.GONE);
                break;
        }

        return potensiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PotensiViewHolder holder, int position) {
        PotensiItem currentItem = mPotensiList.get(position);

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
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new PotensiEditFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    Bundle bundle = new Bundle();
                    final int id = mPotensiList.get(getAbsoluteAdapterPosition()).getId();
                    final String jenis = mPotensiList.get(getAbsoluteAdapterPosition()).getJenis();
                    final String nama = mPotensiList.get(getAbsoluteAdapterPosition()).getNama();
                    final String sumberDayaAlam = mPotensiList.get(getAbsoluteAdapterPosition()).getSumberDayaAlam();
                    final String sumberDayaManusia = mPotensiList.get(getAbsoluteAdapterPosition()).getSumberDayaManusia();
                    final String asetDesa = mPotensiList.get(getAbsoluteAdapterPosition()).getAsetDesa();
                    final String budayaDesa = mPotensiList.get(getAbsoluteAdapterPosition()).getBudayaDesa();

                    bundle.putInt("id",id);
                    bundle.putString("jenis",jenis);
                    bundle.putString("nama",nama);
                    bundle.putString("sumberdayaalam",sumberDayaAlam);
                    bundle.putString("sumberdayamanusia",sumberDayaManusia);
                    bundle.putString("asetDesa",asetDesa);
                    bundle.putString("budayaDesa",budayaDesa);
                    fragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack(null).commit();
                    notifyDataSetChanged();
                }
            });
            itemView.findViewById(R.id.button_delete_potensi).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Delete");
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager confirmManager = activity.getSupportFragmentManager();
                    DialogFragment dialog = new ConfirmationDelete();
                    Bundle bundle = new Bundle();
                    final int id = mPotensiList.get(getAbsoluteAdapterPosition()).getId();
                    final String menu = "potensi";
                    bundle.putInt("id",id);
                    bundle.putString("menu",menu);
                    dialog.setArguments(bundle);
                    dialog.show(confirmManager,"Alert");
                    notifyDataSetChanged();
                }
            });
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new PotensiFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    fragmentTransaction.commit();
                    notifyDataSetChanged();
                }
            });
        }

    }
}


