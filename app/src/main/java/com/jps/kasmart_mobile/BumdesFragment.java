package com.jps.kasmart_mobile;

import android.os.Bundle;
import android.view.LayoutInflater;
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

public class BumdesFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<BumdesItem> mBumdesList;
    RequestQueue mRequestQueue;
    BumdesAdapter bumdesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bumdes,container,false);
        mBumdesList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.getContext());
        recyclerView = view.findViewById(R.id.bumdes_card);
        ParseJSON();
        return view;
    }

    public void ParseJSON() {
        String url = "http://192.168.100.12/kasmart_mobile/get_bumdes.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        int id = data.getInt("id");
                        String namaDaerah = data.getString("nama");
                        String unitUsaha = data.getString("unit_usaha");
                        String jenisUsaha = data.getString("jenis_usaha");
                        int penyertaanModal = data.getInt("penyertaan_modal");
                        String sumberDana = data.getString("sumber_dana");
                        String tahunPenyertaanModal = data.getString("tahun_penyertaan_modal");
                        int piutangUsaha = data.getInt("piutang_usaha");
                        int piutangGajiKaryawan = data.getInt("piutang_gaji_karyawan");
                        int totalDanaRekening = data.getInt("total_dana_rekening");
                        int inventaris = data.getInt("inventaris");
                        int pengalihanAset = data.getInt("pengalihan_aset");
                        int penghapusanPiutang = data.getInt("penghapusan_piutang");
                        int kasHarian = data.getInt("kas_harian");
                        int barangDagang = data.getInt("barang_dagang");
                        int totalKekayaan = data.getInt("total_kekayaan");
                        int cashOpname = data.getInt("cash_opname");
                        int shu = data.getInt("shu");
                        int pades = data.getInt("pades");
                        int pengembalianUsaha = data.getInt("pengembalian_usaha");
                        int bungaBankUsp = data.getInt("bunga_bank_usp");
                        int biayaPromosi = data.getInt("biaya_promosi");
                        int biayaRapat = data.getInt("biaya_rapat");
                        int tunjanganKinerja = data.getInt("tunjangan_kinerja");
                        int labaBulanLalu = data.getInt("laba_bulan_lalu");
                        int labaBulanIni = data.getInt("laba_bulan_ini");
                        int labaTotal = data.getInt("laba_total");
                        String status = data.getString("status");

                        mBumdesList.add(new BumdesItem(id, namaDaerah, unitUsaha ,jenisUsaha, penyertaanModal, sumberDana,
                                tahunPenyertaanModal, piutangUsaha, piutangGajiKaryawan, totalDanaRekening, inventaris,
                                pengalihanAset, penghapusanPiutang, kasHarian, barangDagang, totalKekayaan,
                                cashOpname, shu, pades, pengembalianUsaha, bungaBankUsp, biayaPromosi, biayaRapat, tunjanganKinerja,
                                labaBulanLalu, labaBulanIni, labaTotal, status));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    bumdesAdapter = new BumdesAdapter(getContext(), mBumdesList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(bumdesAdapter);
                    bumdesAdapter.notifyDataSetChanged();
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
