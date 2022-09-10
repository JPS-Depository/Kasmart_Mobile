package com.jps.kasmart_mobile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DesaAdapter extends RecyclerView.Adapter<DesaAdapter.DesaViewHolder> {
    private Context mContext;
    private ArrayList<DesaItem> mDesaList;
    public SwipeRefreshLayout swipeRefreshLayout;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String role;
    Button editButton, deleteButton;
    String imageURL;

    public DesaAdapter(Context context, ArrayList<DesaItem> desaList, SwipeRefreshLayout swipeRefreshLayout) {
        this.mContext = context;
        this.mDesaList = desaList;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @NonNull
    @Override
    public DesaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.desa_card, parent, false);
        DesaViewHolder desaViewHolder = new DesaViewHolder(v);

        sessionManager = new SessionManager(mContext);
        sessionManager.checkLogin();
        user = sessionManager.getUserDetail();
        role = user.get(sessionManager.ROLE);

        editButton = v.findViewById(R.id.button_edit_desa);
        deleteButton = v.findViewById(R.id.button_delete_desa);

        switch(role){
            case "Guest":

                editButton.setVisibility(v.GONE);
                deleteButton.setVisibility(v.GONE);
                break;
        }

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
        imageURL = currentItem.getURL();
        Log.d("url",imageURL);
        Picasso.get().load(imageURL).placeholder(R.drawable.ic_baseline_image_24).fit().centerInside().networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.gambarDesa);


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
        public TextView mNamaDesa,mNamaKades,mkK, mDusun, mRW, mRT, mSuku, mNomor, mCreated_at, mUpdated_at;
        public ImageView gambarDesa;
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
            gambarDesa = itemView.findViewById(R.id.gambar_desa);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new DesaFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    fragmentTransaction.commit();
                    notifyDataSetChanged();
                }
            });
            itemView.findViewById(R.id.button_edit_desa).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new DesaEditFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    Bundle bundle = new Bundle();

                    final int id = mDesaList.get(getAbsoluteAdapterPosition()).getId();
                    final String namaDesa = mDesaList.get(getAbsoluteAdapterPosition()).getNamaDesa();
                    final String namaKades = mDesaList.get(getAbsoluteAdapterPosition()).getNamaKades();
                    final int telepon = mDesaList.get(getAbsoluteAdapterPosition()).getNomor();
                    final int kk = mDesaList.get(getAbsoluteAdapterPosition()).getkK();
                    final int dusun = mDesaList.get(getAbsoluteAdapterPosition()).getDusun();
                    final int rw = mDesaList.get(getAbsoluteAdapterPosition()).getRW();
                    final int rt = mDesaList.get(getAbsoluteAdapterPosition()).getRT();
                    final int suku = mDesaList.get(getAbsoluteAdapterPosition()).getSuku();

                    bundle.putInt("id",id);
                    bundle.putString("namaDesa",namaDesa);
                    bundle.putString("namaKades",namaKades);
                    bundle.putInt("telepon",telepon);
                    bundle.putInt("kk",kk);
                    bundle.putInt("dusun",dusun);
                    bundle.putInt("rw",rw);
                    bundle.putInt("rt",rt);
                    bundle.putInt("suku",suku);

                    fragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack(null).commit();
                    notifyDataSetChanged();
                }
            });
            itemView.findViewById(R.id.button_delete_desa).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Delete");
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager confirmManager = activity.getSupportFragmentManager();
                    DialogFragment dialog = new ConfirmationDelete();
                    Bundle bundle = new Bundle();
                    final int id = mDesaList.get(getAbsoluteAdapterPosition()).getId();
                    final String menu = "desa";
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


