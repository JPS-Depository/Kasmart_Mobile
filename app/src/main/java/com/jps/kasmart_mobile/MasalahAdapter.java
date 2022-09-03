package com.jps.kasmart_mobile;

import android.content.Context;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MasalahAdapter extends RecyclerView.Adapter<MasalahAdapter.MasalahViewHolder> {
    private Context mContext;
    private ArrayList<MasalahItem> mMasalahList;
    private boolean expandable;
    public SwipeRefreshLayout swipeRefreshLayout;

    public MasalahAdapter(Context context, ArrayList<MasalahItem> masalahList,SwipeRefreshLayout swipeRefreshLayout) {
        this.mContext = context;
        this.mMasalahList = masalahList;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.expandable = false;
    }

    @NonNull
    @Override
    public MasalahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.masalah_card, parent, false);
        MasalahAdapter.MasalahViewHolder masalahViewHolder = new MasalahAdapter.MasalahViewHolder(v);
        return masalahViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MasalahViewHolder holder, int position) {
        MasalahItem currentItem = mMasalahList.get(position);

        int id = currentItem.getId();
        String masalah = currentItem.getMasalah();
        String pembinaan = currentItem.getPembinaan();
        String createdBy = currentItem.getCreatedBy();

        holder.mMasalah.setText(masalah);
        holder.mPembinaan.setText(pembinaan);
        holder.mCreatedBy.setText(createdBy);

        boolean isExpandable = mMasalahList.get(position).ismExpandable();
        holder.expandableLayout.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return mMasalahList.size();
    }

    public class MasalahViewHolder extends RecyclerView.ViewHolder {
        public TextView mMasalah, mPembinaan, mCreatedBy;
        public LinearLayout linearLayout;
        public RelativeLayout expandableLayout;

        public MasalahViewHolder(View itemView) {
            super(itemView);

            mMasalah = itemView.findViewById(R.id.isi_text_masalah_masalah);
            mPembinaan = itemView.findViewById(R.id.isi_text_pembinaan_masalah);
            mCreatedBy = itemView.findViewById(R.id.createby_masalah);

            linearLayout = itemView.findViewById(R.id.header_masalah);
            expandableLayout = itemView.findViewById(R.id.extended_masalah);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MasalahItem masalahItem = mMasalahList.get(getBindingAdapterPosition());
                    masalahItem.setmExpandable(!masalahItem.ismExpandable());
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
                    Fragment fragment = new MasalahFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    fragmentTransaction.commit();
                    notifyDataSetChanged();

                }
            });

            itemView.findViewById(R.id.button_edit_masalah).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Edit");
                }
            });
            itemView.findViewById(R.id.button_delete_masalah).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Delete");
                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager confirmManager = activity.getSupportFragmentManager();
                    DialogFragment dialog = new ConfirmationDelete();
                    Bundle bundle = new Bundle();
                    final int id = mMasalahList.get(getAbsoluteAdapterPosition()).getId();
                    final String menu = "masalah";
                    bundle.putInt("id",id);
                    bundle.putString("menu",menu);
                    dialog.setArguments(bundle);
                    dialog.show(confirmManager,"Alert");
                    notifyDataSetChanged();
                }
            });
        }

    }
}


