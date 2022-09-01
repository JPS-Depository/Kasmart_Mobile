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

public class VisumFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<VisumItem> mVisumList;
    RequestQueue mRequestQueue;
    VisumAdapter visumAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visum, container,false);
        mVisumList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.getContext());
        recyclerView = view.findViewById(R.id.visum_card);
        ParseJSON();
        return view;
    }

    public void ParseJSON(){
        String url = "http://192.168.100.12/kasmart_mobile/get_visum.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        int id = data.getInt("id");
                        String kegiatan = data.getString("kegiatan");
                        String tanggalVisum = data.getString("tanggal");
                        String tanggalKegiatan = data.getString("tanggal_kegiatan");
                        String kabupaten = data.getString("Kabupaten");
                        String kecamatan = data.getString("Kecamatan");
                        String kelDes = data.getString("Kelurahan");
                        String hasilKegiatan = data.getString("hasil_kegiatan");

                        mVisumList.add(new VisumItem(id, kegiatan, tanggalVisum, tanggalKegiatan,
                                kabupaten, kecamatan, kelDes, hasilKegiatan));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    visumAdapter = new VisumAdapter(getContext(), mVisumList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(visumAdapter);
                    visumAdapter.notifyDataSetChanged();
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
