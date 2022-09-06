package com.jps.kasmart_mobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class USPFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<USPItem> mUSPList;
    RequestQueue mRequestQueue;
    USPAdapter USPAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usp_uek,container,false);

        mUSPList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.getContext());
        recyclerView = view.findViewById(R.id.usp_uek_card);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh_usp_uek);
        ParseJSON();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    public void ParseJSON() {
        String url = "http://192.168.100.12/kasmart_mobile/get_USP.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        int id = data.getInt("id");
                        String namaDaerah = data.getString("nama");
                        int modal = data.getInt("modal");
                        int perguliranOrang = data.getInt("perguliran_orang");
                        int perguliranDana = data.getInt("perguliran_dana");
                        int sisaPiutangOrang = data.getInt("sisa_piutang_orang");
                        int sisaPiutangDana = data.getInt("sisa_piutang_dana");
                        int totalDanaUsp = data.getInt("total_dana_usp");
                        int totalDanaDud = data.getInt("total_dana_dud");
                        int kasUed =data.getInt("kas_ued");
                        int inventaris = data.getInt("inventaris");
                        int penghapusanCadangan = data.getInt("penghapusan_cadangan");
                        int sitaanJaminan = data.getInt("sitaan_jaminan");
                        int totalAset = data.getInt("total_aset");
                        int tunggakanOrang = data.getInt("tunggakan_orang");
                        int tunggakanDana = data.getInt("tunggakan_dana");
                        int pengembalianOrang = data.getInt("pengembalian_orang");
                        int pengembalianDana = data.getInt("pengembalian_dana");
                        int dagangOrang = data.getInt("dagang_orang");
                        int dagangDana = data.getInt("dagang_dana");
                        int pertanianOrang = data.getInt("pertanian_orang");
                        int pertanianDana = data.getInt("pertanian_dana");
                        int perkebunanOrang = data.getInt("perkebunan_orang");
                        int perkebunanDana = data.getInt("perkebunan_dana");
                        int perikananOrang = data.getInt("perikanan_orang");
                        int perikananDana = data.getInt("perikanan_dana");
                        int peternakanOrang = data.getInt("peternakan_orang");
                        int peternakanDana = data.getInt("peternakan_dana");
                        int industriOrang = data.getInt("industri_orang");
                        int industriDana = data.getInt("industri_dana");
                        int jasaOrang = data.getInt("jasa_orang");
                        int jasaDana = data.getInt("jasa_dana");
                        int totalPeminjamanJenisUsaha = data.getInt("total_pinjaman_jenis_usaha");
                        int shu = data.getInt("shu");
                        int penyaluranOrang = data.getInt("penyaluran_orang");
                        int penyaluranDana = data.getInt("penyaluran_dana");
                        int pemanfaatOrang = data.getInt("pemanfaat_orang");
                        int pemanfaatDana = data.getInt("pemanfaat_dana");
                        int cashOpname = data.getInt("cash_opname");
                        int pades = data.getInt("pades");
                        int pengembalianUsaha = data.getInt("pengembalian_usaha");
                        int labaBersih = data.getInt("laba_bersih");
                        double pengembalianPersentase = data.getDouble("pengembalian_persentase");
                        String status = data.getString("status");

                        mUSPList.add(new USPItem(id,namaDaerah,modal,perguliranOrang,perguliranDana,sisaPiutangOrang,
                        sisaPiutangDana,totalDanaUsp,totalDanaDud,kasUed,inventaris,penghapusanCadangan,sitaanJaminan,
                        totalAset,tunggakanOrang,tunggakanDana,pengembalianOrang,pengembalianPersentase,pengembalianDana,
                        dagangOrang,dagangDana,pertanianOrang,pertanianDana,perkebunanOrang,perkebunanDana,perikananOrang,
                        perikananDana,peternakanOrang,peternakanDana,industriOrang,industriDana,jasaOrang,jasaDana,
                        totalPeminjamanJenisUsaha,shu,penyaluranOrang,penyaluranDana,pemanfaatOrang,pemanfaatDana,cashOpname,
                        pades,pengembalianUsaha,labaBersih,status));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    USPAdapter = new USPAdapter(getContext(), mUSPList,swipeRefreshLayout);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(USPAdapter);
                    USPAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
