package com.jps.kasmart_mobile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DesaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DesaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ArrayList<DesaItem> mDesaList;
    private RecyclerView recyclerView;
    private RequestQueue mRequestQueue;

    public DesaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DesaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DesaFragment newInstance(String param1, String param2) {
        DesaFragment fragment = new DesaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_desa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDesaList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.getContext());
        parseJSON();
        recyclerView = view.findViewById(R.id.desa_card);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void parseJSON() {
        String url = "http://192.168.100.12/kasmart_mobile/get_desa.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        int id = data.getInt("id");
                        String namaDesa = data.getString("nama");
                        String namaKades = data.getString("nama_kades");
                        int kK = data.getInt("jumlah_kk");
                        int dusun = data.getInt("jumlah_dusun");
                        int rt = data.getInt("jumlah_rt");
                        int rw = data.getInt("jumlah_rw");
                        int suku = data.getInt("jumlah_suku");
                        String createAt = data.getString("created_at");
                        String updateAt = data.getString("updated_at");

                        mDesaList.add(new DesaItem(id,namaDesa,namaKades,kK,dusun,rt,rw,suku,createAt,updateAt));
                    }
                    DesaAdapter desaAdapter = new DesaAdapter(getContext(), mDesaList);
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
}