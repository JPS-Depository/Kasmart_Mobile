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
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class PendampingFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<PendampingItem> mPendampingList;
    RequestQueue mRequestQueue;
    PendampingAdapter pendampingAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String role;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendamping,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("List Pendamping");

        mPendampingList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.getContext());
        recyclerView = view.findViewById(R.id.pendamping_card);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh_pendamping);

        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        user = sessionManager.getUserDetail();
        role = user.get(sessionManager.ROLE);

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
        switch(user.get(sessionManager.ROLE)){
            case "Guest":
            case "Super User":
                menu.clear();
                break;
        }
    }

    public void ParseJSON() {
        String url = "http://192.168.100.12/kasmart_mobile/get_pendamping.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        int id = data.getInt("id");
                        String nama = data.getString("fullname");
                        String telepon =data.getString("nohp");
                        String alamatTugas = data.getString("alamat_tugas")+
                                " "+ data.getString("KelurahanTugas")+
                                " "+ data.getString("KecamatanTugas")+
                                " "+ data.getString("KabupatenTugas");
                        String alamatPribadi = data.getString("alamat_pribadi")+
                                " "+ data.getString("KelurahanPribadi")+
                                " "+ data.getString("KecamatanPribadi")+
                                " "+ data.getString("KabupatenPribadi");
                        String bidang = data.getString("bidang");
                        String jabatan = data.getString("jabatan");
                        String noReg = data.getString("noreg");
                        String email = data.getString("email");
                        String ktp = data.getString("no_ktp");
                        String bpjs = data.getString("no_bpjs");
                        String noRek = data.getString("no_rek");
                        String npwp = data.getString("npwp");
                        String statusPernikahan = data.getString("status");
                        String jenisKelamin = data.getString("jenis_kelamin");
                        String agama = data.getString("agama");
                        String imgURL = "http://192.168.100.12/kasmart_mobile/image/pendamping/foto_pendamping_id_"+id+".jpg";
                        mPendampingList.add(new PendampingItem(id, nama, telepon, alamatTugas, alamatPribadi, bidang,
                                jabatan, noReg, email, ktp, bpjs, noRek, npwp, statusPernikahan, jenisKelamin, agama, imgURL));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    pendampingAdapter = new PendampingAdapter(getContext(), mPendampingList, swipeRefreshLayout);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(pendampingAdapter);
                    pendampingAdapter.notifyDataSetChanged();
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
