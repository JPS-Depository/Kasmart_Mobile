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

public class MasalahFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<MasalahItem> mMasalahList;
    RequestQueue mRequestQueue;
    MasalahAdapter masalahAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String role;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_masalah_pendamping, container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Masalah Pendampingan");

        mMasalahList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.getContext());
        recyclerView = view.findViewById(R.id.masalah_card);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh_masalah);
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        user = sessionManager.getUserDetail();
        role = user.get(sessionManager.ROLE);

        ParseJSON();
        return view;
    }

    public void ParseJSON(){
        String url = "http://192.168.100.12/kasmart_mobile/get_masalah.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        int id = data.getInt("id");
                        String masalah = data.getString("masalah");
                        String pembinaan = data.getString("pembinaan");
                        String createdBy = data.getString("created_by");
                        mMasalahList.add(new MasalahItem(id, masalah, pembinaan, createdBy));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    masalahAdapter = new MasalahAdapter(getContext(), mMasalahList, swipeRefreshLayout);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(masalahAdapter);
                    masalahAdapter.notifyDataSetChanged();
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
                menu.clear();
                break;
        }
    }
}
