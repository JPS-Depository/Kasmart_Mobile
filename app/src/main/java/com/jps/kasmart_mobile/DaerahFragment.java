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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DaerahFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<DaerahItem> mDaerahList;
    RequestQueue mRequestQueue;
    DaerahAdapter daerahAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daerah,container,false);
        mDaerahList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.getContext());
        recyclerView = view.findViewById(R.id.daerah_card);
        ParseJSON();

        return view;
    }

    public void ParseJSON() {
        String url = "http://192.168.100.12/kasmart_mobile/get_daerah.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        int id = data.getInt("id");
                        String jenis = data.getString("jenis");
                        String kode = data.getString("kode");
                        String nama = data.getString("nama");
                        String createAt = data.getString("created_at");
                        String updateAt = data.getString("updated_at");

                        mDaerahList.add(new DaerahItem(id,jenis,kode,nama,createAt,updateAt));
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    daerahAdapter = new DaerahAdapter(getContext(), mDaerahList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(daerahAdapter);
                    daerahAdapter.notifyDataSetChanged();
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

