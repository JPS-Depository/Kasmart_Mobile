package com.jps.kasmart_mobile;

import android.annotation.SuppressLint;
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

public class PendampingAdapter extends RecyclerView.Adapter<PendampingAdapter.PendampingViewHolder> {
    private Context mContext;
    private ArrayList<PendampingItem> mPendampingList;
    private boolean expandable;

    public PendampingAdapter(Context context, ArrayList<PendampingItem> pendampingList) {
        this.mContext = context;
        this.mPendampingList = pendampingList;
        this.expandable = false;
    }

    @NonNull
    @Override
    public PendampingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.pendamping_card, parent, false);
        PendampingViewHolder pendampingViewHolder = new PendampingViewHolder(v);
        return pendampingViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PendampingViewHolder holder, int position) {
        PendampingItem currentItem = mPendampingList.get(position);

        int id=currentItem.getId();
        String nama = currentItem.getNama();
        String telepon = currentItem.getTelepon();
        String alamatTugas = currentItem.getAlamatTugas();
        String alamatPribadi = currentItem.getAlamatPribadi();
        String bidang = currentItem.getBidang();
        String jabatan = currentItem.getJabatan();
        String noReg = currentItem.getNoReg();
        String email = currentItem.getEmail();
        String ktp = currentItem.getKTP();
        String bpjs = currentItem.getBPJS();
        String noRek = currentItem.getNoRek();
        String npwp = currentItem.getNoNPWP();
        String statusPernikahan = currentItem.getStatusPernikahan();
        String jenisKelamin = currentItem.getJenisKelamin();
        String agama = currentItem.getAgama();

        holder.mNama.setText(nama);
        holder.mTelepon.setText(telepon);
        holder.mAlamatTugas.setText(alamatTugas);
        holder.mAlamatPribadi.setText(alamatPribadi);
        holder.mBidang.setText(bidang);
        holder.mJabatan.setText(jabatan);
        holder.mNoReg.setText(noReg);
        holder.mEmail.setText(email);
        holder.mKTP.setText(ktp);
        holder.mBPJS.setText(bpjs);
        holder.mNoRek.setText(noRek);
        holder.mNpwp.setText(npwp);
        holder.mStatusPernikahan.setText(statusPernikahan);
        holder.mJenisKelamin.setText(jenisKelamin);
        holder.mAgama.setText(agama);

        boolean isExpandable = mPendampingList.get(position).ismExpandable();
        holder.expandableLayout.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return mPendampingList.size();
    }

    public class PendampingViewHolder extends RecyclerView.ViewHolder {
        public SwipeRefreshLayout swipeRefreshLayout;
        public TextView mNama, mTelepon, mAlamatTugas, mAlamatPribadi, mBidang, mJabatan, mNoReg, mEmail, mKTP,
        mBPJS, mNoRek, mNpwp, mStatusPernikahan, mJenisKelamin, mAgama;
        public LinearLayout linearLayout;
        public RelativeLayout expandableLayout;
        public PendampingViewHolder(View itemView) {
            super(itemView);

            mNama = itemView.findViewById(R.id.nama_pendamping_pendamping);
            mTelepon = itemView.findViewById(R.id.nomor_pendamping);
            mAlamatTugas = itemView.findViewById(R.id.alamat_tugas_header_pendamping);
            mAlamatPribadi = itemView.findViewById(R.id.alamat_pribadi_header_pendamping);
            mBidang = itemView.findViewById(R.id.bidang_header_pendamping);
            mJabatan = itemView.findViewById(R.id.jabatan_header_pendamping);
            mNoReg = itemView.findViewById(R.id.no_reg_header_pendamping);
            mEmail = itemView.findViewById(R.id.email_pendamping);
            mKTP = itemView.findViewById(R.id.ktp_pendamping);
            mBPJS = itemView.findViewById(R.id.bpjs_pendamping);
            mNoRek = itemView.findViewById(R.id.nomor_rekening_pendamping);
            mNpwp = itemView.findViewById(R.id.npwp_pendamping);
            mStatusPernikahan = itemView.findViewById(R.id.status_pendamping);
            mJenisKelamin = itemView.findViewById(R.id.jenis_kelamin_pendamping);
            mAgama = itemView.findViewById(R.id.agama_pendamping);

            linearLayout = itemView.findViewById(R.id.header_pendamping);
            expandableLayout = itemView.findViewById(R.id.extended_pendamping);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PendampingItem pendampingItem = mPendampingList.get(getBindingAdapterPosition());
                    pendampingItem.setmExpandable(!pendampingItem.ismExpandable());
                    notifyDataSetChanged();
                }
            });

            itemView.findViewById(R.id.button_edit_pendamping).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Press","Pressed Edit");
                }
            });
            itemView.findViewById(R.id.button_delete_pendamping).setOnClickListener(new View.OnClickListener() {
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


