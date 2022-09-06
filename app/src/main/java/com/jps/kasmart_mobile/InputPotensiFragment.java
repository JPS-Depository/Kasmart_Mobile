package com.jps.kasmart_mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class InputPotensiFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    int id;
    String  jenis, nama, sumberDayaAlam, sumberDayaManusia, asetDesa, budayaDesa;
    EditText inputSDA, inputSDM, inputAsetDesa, inputBudayaDesa;
    Button edit;
    TextView displayJenis, displayNama;
    Spinner spinnerKeldes;
    PotensiAdapter potensiAdapter;
    ArrayList<String> keldesList = new ArrayList<>();
    ArrayAdapter<String> keldesAdapter;
    RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.potensi_input_new, container,false);


        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();


        inputSDA = (EditText)  view.findViewById(R.id.input_sda_potensi);
        inputSDM = (EditText) view.findViewById(R.id.input_sdm_potensi);
        inputAsetDesa = (EditText) view.findViewById(R.id.input_aset_desa_potensi);
        inputBudayaDesa = (EditText) view.findViewById(R.id.input_budaya_potensi);
        displayJenis = (TextView) view.findViewById(R.id.tipe_daerah_potensi);
        displayNama = (TextView) view.findViewById(R.id.nama_daerah_potensi);
        spinnerKeldes = (Spinner) view.findViewById(R.id.spinner_keldes);

        edit = (Button) view.findViewById(R.id.button_input_edit_potensi);

        requestQueue = Volley.newRequestQueue(getContext());
        String url="http://192.168.100.12/kasmart_mobile/get_daerah_dropdown.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        String jenis = data.getString("jenis");
                        String namaKeldes;
                        switch (jenis){
                            case "Kelurahan":
                            case "Desa":
                                id = data.getInt("id");
                                namaKeldes = data.optString("nama");
                                keldesList.add(namaKeldes);
                                break;
                        }
                        keldesAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,keldesList);
                        keldesAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        spinnerKeldes.setAdapter(keldesAdapter);
                    }
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
        requestQueue.add(request);
        spinnerKeldes.setOnItemSelectedListener(this);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storedCreatedBy = user.get(sessionManager.NAME);
                String storedSDA = inputSDA.getText().toString().trim();
                String storedSDM = inputSDM.getText().toString().trim();
                String storedAset = inputAsetDesa.getText().toString().trim();
                String storedBudaya = inputBudayaDesa.getText().toString().trim();
                String storedKeldes = spinnerKeldes.getSelectedItem().toString();

                insertItem(storedSDA,storedKeldes,storedSDM,storedAset,storedBudaya,storedCreatedBy);
                Toast.makeText(getActivity(),"Data telah di ubah",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    private void insertItem(String storedSDA,String storedKeldes, String storedSDM, String storedAset, String storedBudaya, String storedCreatedBy){
        String url = "http://192.168.100.12/kasmart_mobile/input_potensi.php";
        requestQueue = Volley.newRequestQueue(this.getContext());
        Log.d("Url",url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response+ " "+url);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response",""+error);
            }
        }){
            protected HashMap<String,String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("keldes",storedKeldes);
                map.put("sda",storedSDA);
                map.put("sdm",storedSDM);
                map.put("aset",storedAset);
                map.put("budaya",storedBudaya);
                map.put("createdby",storedCreatedBy);
                return map;
            }
        };
        requestQueue.add(stringRequest);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new PotensiFragment();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

