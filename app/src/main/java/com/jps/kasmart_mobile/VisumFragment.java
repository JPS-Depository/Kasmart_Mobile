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

public class VisumFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<VisumItem> mVisumList;
    RequestQueue mRequestQueue;
    VisumAdapter visumAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String role;
    String baseURL = "http://192.168.100.12/kasmart_mobile/image/visum/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visum, container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Daftar Visum Realisasi Kegiatan");

        mVisumList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.getContext());
        recyclerView = view.findViewById(R.id.visum_card);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh_usp_visum);

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
        switch(role){
            case "Guest":
                menu.clear();
                break;
        }
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
                        String image = data.getString("image");
                        String imgUrl = baseURL+image;
                        mVisumList.add(new VisumItem(id, kegiatan, tanggalVisum, tanggalKegiatan,
                                kabupaten, kecamatan, kelDes, hasilKegiatan, imgUrl));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    visumAdapter = new VisumAdapter(getContext(), mVisumList, swipeRefreshLayout);
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
