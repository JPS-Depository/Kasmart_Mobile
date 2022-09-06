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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

public class InputKegiatanFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    int radioId, radioJenisId;
    RadioGroup statusGroup, jenisGroup;
    RadioButton statusRadioButton, jenisRadioButton;
    String tipe, kegiatan, tanggalkegiatan, sasaran, detail, lokasi, kabupaten,kecamatan,keldes;
    EditText inputKegiatan,inputTanggal,inputSasaran, inputDetail, inputLokasi;
    Button edit;
    Spinner spinnerKabupaten, spinnerKecamatan, spinnerKeldes;
    ArrayList<String> kabupatenList = new ArrayList<>();
    ArrayList<String> kecamatanList = new ArrayList<>();
    ArrayList<String> keldesList = new ArrayList<>();
    ArrayAdapter<String> kabupatenAdapter;
    ArrayAdapter<String> kecamatanAdapter;
    ArrayAdapter<String> keldesAdapter;
    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rutin_input_new, container,false);

        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        inputKegiatan = (EditText)view.findViewById(R.id.input_nama_kegiatan_rutin);
        inputTanggal = (EditText)view.findViewById(R.id.input_new_tanggal);
        inputSasaran = (EditText) view.findViewById(R.id.input_new_sasaran);
        inputDetail = (EditText) view.findViewById(R.id.input_detail_new_kegiatan);
        inputLokasi = (EditText) view.findViewById(R.id.input_lokasi_new_kegiatan);

        jenisGroup = (RadioGroup) view.findViewById(R.id.jenis_radio_kegiatan);
        statusGroup = (RadioGroup) view.findViewById(R.id.status_radio_rutin);

        requestQueue = Volley.newRequestQueue(getContext());
        spinnerKabupaten = (Spinner) view.findViewById(R.id.spinner_kabupaten);
        spinnerKecamatan = (Spinner) view.findViewById(R.id.spinner_kecamatan);
        spinnerKeldes = (Spinner) view.findViewById(R.id.spinner_keldes);

        String url = "http://192.168.100.12/kasmart_mobile/get_daerah_dropdown.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        String jenis = data.getString("jenis");
                        String namaKeldes;
                        int id;
                        switch (jenis){
                            case "Kabupaten":
                                id = data.getInt("id");
                                String namaKabupaten = data.optString("nama");
                                kabupatenList.add(namaKabupaten);
                                break;
                            case "Kecamatan":
                                id = data.getInt("id");
                                String namaKecamatan = data.optString("nama");
                                kecamatanList.add(namaKecamatan);
                                break;
                            case "Kelurahan":
                            case "Desa":
                                id = data.getInt("id");
                                namaKeldes = data.optString("nama");
                                keldesList.add(namaKeldes);
                                break;
                        }
                        kabupatenAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,kabupatenList);
                        kabupatenAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        spinnerKabupaten.setAdapter(kabupatenAdapter);
                        kecamatanAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,kecamatanList);
                        kecamatanAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        spinnerKecamatan.setAdapter(kecamatanAdapter);
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
        spinnerKabupaten.setOnItemSelectedListener(this);
        spinnerKecamatan.setOnItemSelectedListener(this);
        spinnerKeldes.setOnItemSelectedListener(this);


        edit = (Button) view.findViewById(R.id.button_input_new_kegiatan);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storedKegiatan = inputKegiatan.getText().toString().trim();
                String storedJenis = jenisRadioButton.getText().toString().trim();
                String storedTanggal = inputTanggal.getText().toString().trim();
                String storedSasaran = inputSasaran.getText().toString().trim();
                String storedDetil = inputDetail.getText().toString().trim();
                String storedLokasi = inputLokasi.getText().toString().trim();
                String storedKabupaten = spinnerKabupaten.getSelectedItem().toString();
                String storedKecamatan = spinnerKecamatan.getSelectedItem().toString();
                String storedKeldes = spinnerKeldes.getSelectedItem().toString();
                String storedCreatedBy = user.get(sessionManager.NAME);
                insertItem(storedKegiatan, storedTanggal,
                        storedSasaran,storedDetil,storedLokasi,radioId,
                        radioJenisId,storedKabupaten,storedKecamatan,storedKeldes,storedCreatedBy,storedJenis);

                Toast.makeText(getActivity(),"Data telah di ubah",Toast.LENGTH_SHORT).show();
            }
        });

        statusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int statusId = statusGroup.getCheckedRadioButtonId();
                statusRadioButton = (RadioButton) getView().findViewById(statusId);
                switch (String.valueOf(statusRadioButton.getText())){
                    case "Belum Terealisasi":
                        radioId = 0;
                        break;
                    case "Terealisasi":
                        radioId = 1;
                        break;
                    case "Dalam Proses":
                        radioId = 2;
                        break;
                }
            }
        });

        jenisGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int jenisId = jenisGroup.getCheckedRadioButtonId();
                jenisRadioButton = (RadioButton)getView().findViewById(jenisId);
            }
        });
        return view;
    }

    private void insertItem(String storedKegiatan, String storedTanggal,
                            String storedSasaran, String storedDetil, String storedLokasi,
                            int radioId, int radioJenisId, String storedKabupaten,
                            String storedKecamatan, String storedKeldes, String storedCreatedBy, String storedJenis){
        String url = "http://192.168.100.12/kasmart_mobile/input_kegiatan.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
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
                map.put("kegiatan",storedKegiatan);
                map.put("tanggal",storedTanggal);
                map.put("sasaran",storedSasaran);
                map.put("detil",storedDetil);
                map.put("lokasi",storedLokasi);
                map.put("status",String.valueOf(radioId));
                map.put("kabupaten", storedKabupaten);
                map.put("kecamatan",storedKecamatan);
                map.put("keldes",storedKeldes);
                map.put("createdby",storedCreatedBy);
                map.put("jenis",storedJenis);
                Log.d("data", String.valueOf(map));
                return map;
            }
        };
        requestQueue.add(stringRequest);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new RutinFragment();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}

