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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class AbsensiFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<AbsensiItem> mAbsensiList;
    RequestQueue mRequestQueue;
    AbsensiAdapter absensiAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_absen, container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Daftar Absensi Kegiatan");

        mAbsensiList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.getContext());
        recyclerView = view.findViewById(R.id.absen_card);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh_absen);
        ParseJSON();
        return view;
    }

    public void ParseJSON(){
        String url = "http://192.168.100.12/kasmart_mobile/get_absensi.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        int id = data.getInt("id");
                        String kegiatan = data.getString("kegiatan");
                        String lokasi = data.getString("jeniskabupaten")+ " "+
                                data.getString("Kabupaten")+", "+
                                data.getString("jeniskecamatan")+" "+
                                data.getString("Kecamatan")+", "+
                                data.getString("jeniskelurahan")+" "+
                                data.getString("Kelurahan");
                        String keterangan = data.getString("keterangan");
                        String tanggalAbsen = data.getString("tanggal_absensi");
                        mAbsensiList.add(new AbsensiItem(id, kegiatan, lokasi, keterangan, tanggalAbsen));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    absensiAdapter = new AbsensiAdapter(getContext(), mAbsensiList, swipeRefreshLayout);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(absensiAdapter);
                    absensiAdapter.notifyDataSetChanged();
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
