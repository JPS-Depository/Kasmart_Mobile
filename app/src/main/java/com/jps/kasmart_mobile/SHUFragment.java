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

public class SHUFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<SHUItem> mSHUList;
    RequestQueue mRequestQueue;
    SHUAdapter shuAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shu, container,false);
        mSHUList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.getContext());
        recyclerView = view.findViewById(R.id.shu_card);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh_shu);
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

    public void ParseJSON(){
        String url = "http://192.168.100.12/kasmart_mobile/get_SHU.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        int id = data.getInt("id");
                        String namaDaerah = data.getString("nama");
                        int pembagianShu = data.getInt("pembagian_shu");
                        int inventaris = data.getInt("inventaris");
                        int tambahanModal = data.getInt("tambahan_modal");
                        int pelatihanPengembangan = data.getInt("pelatihan_pengembangan");
                        int bantuanSosial = data.getInt("bantuan_sosial");
                        int hadiahPemanfaat = data.getInt("hadiah_pemanfaat");
                        int pad = data.getInt("pad");
                        int totalSisaUsaha = data.getInt("total_sisa_usaha");


                        mSHUList.add(new SHUItem(id,namaDaerah,pembagianShu,inventaris,tambahanModal,pelatihanPengembangan,
                        bantuanSosial,hadiahPemanfaat,pad,totalSisaUsaha));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    shuAdapter = new SHUAdapter(getContext(), mSHUList, swipeRefreshLayout);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(shuAdapter);
                    shuAdapter.notifyDataSetChanged();
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
