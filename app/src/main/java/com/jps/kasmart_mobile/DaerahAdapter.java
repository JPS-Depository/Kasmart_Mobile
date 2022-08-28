//package com.jps.kasmart_mobile;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.DialogFragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//public class DaerahAdapter extends RecyclerView.Adapter<DaerahAdapter.DaerahViewHolder> {
//    private Context mContext;
//    private ArrayList<DaerahItem> mDaerahList;
//
//    public DaerahAdapter(Context context, ArrayList<DaerahItem> daerahList) {
//        this.mContext = context;
//        this.mDaerahList = daerahList;
//    }
//
//    @NonNull
//    @Override
//    public DaerahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(mContext).inflate(R.layout.daerah_card, parent, false);// layout ganti bambang
//        DaerahViewHolder daerahViewHolder = new DaerahViewHolder(v);
//        return daerahViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DaerahViewHolder holder, int position) {
//        DaerahItem currentItem = mDaerahList.get(position);
//
//        String jenis = currentItem.getJenis();
//        String nama = currentItem.getNama();
//
//        holder.jenis.setText(jenis);
//        holder.nama.setText(nama);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDaerahList.size();
//    }
//
//    public class DaerahViewHolder extends RecyclerView.ViewHolder {
//        public SwipeRefreshLayout swipeRefreshLayout;
//        public TextView mJenis,mNama;
//        public DaerahViewHolder(View itemView) {
//            super(itemView);
//            mJenis = itemView.findViewById(R.id);//layout blom dibuat
//            mNama = itemView.findViewById(R.id);// layout blom dibuat
//
//            /// button dibawah blom ada
//            itemView.findViewById(R.id.button_edit_desa).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("Button Press","Pressed Edit");
//                }
//            });
//            itemView.findViewById(R.id.button_delete_desa).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("Button Press","Pressed Delete");
//                    FragmentActivity activity = (FragmentActivity) (mContext);
//                    FragmentManager confirmManager = activity.getSupportFragmentManager();
//                    DialogFragment dialog = new ConfirmationDelete();
//                    dialog.show(confirmManager,"Alert");
//                }
//            });
//        }
//    }
//}
//
//
