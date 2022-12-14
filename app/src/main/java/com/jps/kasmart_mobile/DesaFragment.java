package com.jps.kasmart_mobile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class DesaFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<DesaItem> mDesaList;
    RequestQueue mRequestQueue;
    DesaAdapter desaAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String role;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_desa,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profil Desa / Kelurahan");

        mDesaList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.getContext());
        recyclerView = view.findViewById(R.id.desa_card);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh_Desa);
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


    public void ParseJSON() {
        String url = "http://192.168.100.12/kasmart_mobile/get_desa.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        int id = data.getInt("id");
                        int daerah_id = data.getInt("id");
                        String namaDesa = data.getString("nama");
                        String namaKades = data.getString("nama_kades");
                        int kK = data.getInt("jumlah_kk");
                        int dusun = data.getInt("jumlah_dusun");
                        int rt = data.getInt("jumlah_rt");
                        int rw = data.getInt("jumlah_rw");
                        int suku = data.getInt("jumlah_suku");
                        int nomor = data.getInt("telp_kades");
                        String createAt = data.getString("created_at");
                        String updateAt = data.getString("updated_at");
                        String url = "http://192.168.100.12/kasmart_mobile/image/daerah/daerah_id_"+daerah_id+".jpg";

                        mDesaList.add(new DesaItem(id,namaDesa,namaKades,kK,dusun,rt,rw,suku,nomor,createAt,updateAt, url));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    desaAdapter = new DesaAdapter(getContext(), mDesaList, swipeRefreshLayout);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(desaAdapter);
                    desaAdapter.notifyDataSetChanged();
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
