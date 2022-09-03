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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class RutinFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<RutinItem> mRutinList;
    RequestQueue mRequestQueue;
    RutinAdapter rutinAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rutin, container,false);
        mRutinList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.getContext());
        recyclerView = view.findViewById(R.id.rutin_card);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh_rutin);
        ParseJSON();
        return view;
    }

    public void ParseJSON(){
        String url = "http://192.168.100.12/kasmart_mobile/get_rutin.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        int id = data.getInt("id");
                        String tanggalKegiatan = data.getString("tanggal_kegiatan");
                        String kegiatan = data.getString("kegiatan");
                        String jenis = data.getString("jenis");
                        String lokasi = data.getString("alamat_kegiatan")+" "+
                                data.getString("Kabupaten")+", "+
                                data.getString("Kecamatan")+", "+
                                data.getString("Kelurahan");
                        String detail = data.getString("detil_kegiatan");
                        String sasaran = data.getString("sasaran");
                        String realisasi = data.getString("status");
                        String createdBy = data.getString("fullname");

                        mRutinList.add(new RutinItem(id, tanggalKegiatan, kegiatan, jenis,
                                lokasi, detail, sasaran, realisasi, createdBy));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    rutinAdapter = new RutinAdapter(getContext(), mRutinList, swipeRefreshLayout);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(rutinAdapter);
                    rutinAdapter.notifyDataSetChanged();
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
