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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder> {
    private Context mContext;
    private ArrayList<BeritaItem> mBeritaList;
    public SwipeRefreshLayout swipeRefreshLayout;
    Bundle bundle;
    SessionManager sessionManager;
    HashMap<String, String> user;
    Button buttonDelete, buttonEdit;
    String role,imageURL;

    public BeritaAdapter(Context context, ArrayList<BeritaItem> beritaList, SwipeRefreshLayout swipeRefreshLayout) {
        this.mContext = context;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.mBeritaList = beritaList;
    }

    @NonNull
    @Override
    public BeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.berita_card, parent, false);
        BeritaViewHolder beritaViewHolder = new BeritaViewHolder(v);
        sessionManager = new SessionManager(mContext);
        sessionManager.checkLogin();
        user = sessionManager.getUserDetail();
        role = user.get(sessionManager.ROLE);

        buttonDelete = (Button) v.findViewById(R.id.button_delete_berita);
        buttonEdit = (Button) v.findViewById(R.id.button_edit_berita);

        switch(role){
            case "Guest":
                buttonDelete.setVisibility(v.GONE);
                buttonEdit.setVisibility(v.GONE);
                break;
        }

        return beritaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaViewHolder holder, int position) {
        String judul, isi_berita, tanggal_berita, createdAt, updatedAt, createdBy;
        BeritaItem currentItem = mBeritaList.get(position);

        judul = currentItem.getJudul();
        isi_berita = currentItem.getIsiBerita();

        imageURL = currentItem.getImage();
        holder.mJudul.setText(judul);
        holder.mIsiBerita.setText(isi_berita);
        Picasso.get().load(imageURL).placeholder(R.drawable.ic_baseline_image_24).fit().centerInside().networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.beritaPicture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString("role",role);
                bundle.putInt("id",currentItem.getId());
                bundle.putString("judul",currentItem.getJudul());
                bundle.putString("isi berita", currentItem.getIsiBerita());
                bundle.putString("tanggal_berita", currentItem.getTanggalBerita());
                bundle.putString("createdBy", currentItem.getCreatedBy());

                FragmentActivity activity = (FragmentActivity) (mContext);
                FragmentManager detailManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = detailManager.beginTransaction();
                Fragment detail = new BeritaDetailFragment();
                detail.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container,detail);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        holder.itemView.findViewById(R.id.button_edit_berita).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = (FragmentActivity) (mContext);
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new BeritaEditFragment();
                fragmentTransaction.replace(R.id.fragment_container,fragment);
                Bundle bundle = new Bundle();

                final int id = currentItem.getId();
                final String judul = currentItem.getJudul();
                final String isi_berita = currentItem.getIsiBerita();
                final String tanggal_berita = currentItem.getTanggalBerita();
                final String createdBy = currentItem.getCreatedBy();

                bundle.putInt("id",id);
                bundle.putString("judul",judul);
                bundle.putString("isiberita",isi_berita);
                bundle.putString("tanggalberita",tanggal_berita);
                bundle.putString("createdby",createdBy);
                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null).commit();
                notifyDataSetChanged();
            }
        });
        holder.itemView.findViewById(R.id.button_delete_berita).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = (FragmentActivity) (mContext);
                FragmentManager confirmManager = activity.getSupportFragmentManager();
                DialogFragment dialog = new ConfirmationDelete();
                Bundle bundle = new Bundle();
                final int id = mBeritaList.get(holder.getAbsoluteAdapterPosition()).getId();
                final String menu = "berita";
                bundle.putInt("id",id);
                bundle.putString("menu",menu);
                dialog.setArguments(bundle);
                dialog.show(confirmManager,"Alert");
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeritaList.size();
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder {
        public TextView mJudul, mIsiBerita;
        public ImageView beritaPicture;

        public BeritaViewHolder(View itemView) {
            super(itemView);
            mJudul = itemView.findViewById(R.id.judul_berita);
            mIsiBerita = itemView.findViewById(R.id.isi_berita);
            beritaPicture = itemView.findViewById(R.id.gambar_berita);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new BeritaFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    fragmentTransaction.commit();
                    notifyDataSetChanged();
                }
            });
        }
    }
}


