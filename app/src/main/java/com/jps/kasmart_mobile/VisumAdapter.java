package com.jps.kasmart_mobile;

import android.content.Context;
import android.media.Image;
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

public class VisumAdapter extends RecyclerView.Adapter<VisumAdapter.VisumViewHolder> {
    private Context mContext;
    private ArrayList<VisumItem> mVisumList;
    public SwipeRefreshLayout swipeRefreshLayout;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String role, imgUrl;
    Button deleteButton, editButton;
    public ImageView displayImage;

    public VisumAdapter(Context context, ArrayList<VisumItem> visumList, SwipeRefreshLayout swipeRefreshLayout) {
        this.mContext = context;
        this.mVisumList = visumList;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @NonNull
    @Override
    public VisumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.visum_card, parent, false);
        VisumAdapter.VisumViewHolder visumViewHolder = new VisumAdapter.VisumViewHolder(v);

        sessionManager = new SessionManager(mContext);
        sessionManager.checkLogin();
        user = sessionManager.getUserDetail();
        role = user.get(sessionManager.ROLE);

        deleteButton = v.findViewById(R.id.button_delete_visum);
        editButton = v.findViewById(R.id.button_edit_visum);

        switch(role){
            case "Guest":
                deleteButton.setVisibility(v.GONE);
                editButton.setVisibility(v.GONE);
                break;
        }

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

        imgUrl = currentItem.getImage();
        Picasso.get().load(imgUrl).placeholder(R.drawable.ic_baseline_image_24).fit().centerInside().networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(displayImage);

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
            displayImage = itemView.findViewById(R.id.gambar_kegiatan);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new VisumFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    fragmentTransaction.commit();
                    notifyDataSetChanged();
                }
            });

            itemView.findViewById(R.id.button_edit_visum).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new VisumEditFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    Bundle bundle = new Bundle();

                    final int id = mVisumList.get(getAbsoluteAdapterPosition()).getId();
                    final String kegiatan = mVisumList.get(getAbsoluteAdapterPosition()).getKegiatan();
                    final String tanggalVisum = mVisumList.get(getAbsoluteAdapterPosition()).getTanggalVisum();
                    final String tanggalKegiatan = mVisumList.get(getAbsoluteAdapterPosition()).getTanggalKegiatan();
                    final String kabupaten = mVisumList.get(getAbsoluteAdapterPosition()).getKabupaten();
                    final String kecamatan = mVisumList.get(getAbsoluteAdapterPosition()).getKecamatan();
                    final String keldes = mVisumList.get(getAbsoluteAdapterPosition()).getKelDes();
                    final String hasilKegiatan = mVisumList.get(getAbsoluteAdapterPosition()).getHasilKegiatan();
                    final String image = mVisumList.get(getAbsoluteAdapterPosition()).getImage();

                    bundle.putInt("id",id);
                    bundle.putString("kegiatan",kegiatan);
                    bundle.putString("tanggalvisum",tanggalVisum);
                    bundle.putString("tanggalkegiatan",tanggalKegiatan);
                    bundle.putString("kabupaten",kabupaten);
                    bundle.putString("kecamatan",kecamatan);
                    bundle.putString("keldes",keldes);
                    bundle.putString("hasilkegiatan",hasilKegiatan);
                    bundle.putString("img",image);
                    fragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack(null).commit();
                    notifyDataSetChanged();
                }
            });
            itemView.findViewById(R.id.button_delete_visum).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Delete");
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager confirmManager = activity.getSupportFragmentManager();
                    DialogFragment dialog = new ConfirmationDelete();
                    Bundle bundle = new Bundle();
                    final int id = mVisumList.get(getAbsoluteAdapterPosition()).getId();
                    final String menu = "visum";
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


