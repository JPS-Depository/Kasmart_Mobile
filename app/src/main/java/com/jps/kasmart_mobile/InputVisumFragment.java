package com.jps.kasmart_mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class InputVisumFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    int kegiatanId;
    String tanggalVisum, tanggalKegiatan, namaKabupaten, namaKecamatan,
            namaKeldes;
    EditText inputTanggal, inputHasil;
    Button edit;
    TextView kabupaten, kecamatan, keldes, displayTanggalKegiatan;
    Spinner spinnerKegiatan;
    ArrayList<String> kegiatanList = new ArrayList<>();
    ArrayAdapter<String> kegiatanAdapter;
    RequestQueue requestQueue;
    JSONArray jsonArray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.visum_input_new, container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Input Visum Kegiatan");

        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        inputTanggal = (EditText)view.findViewById(R.id.input_tanggal_visum_visum);
        inputHasil = (EditText)view.findViewById(R.id.input_hasil_kegiatan_visum);
        displayTanggalKegiatan = (TextView)view.findViewById(R.id.tanggal_kegiatan_visum);
        kecamatan = (TextView)view.findViewById(R.id.kecamatan_kegiatan_visum);
        kabupaten = (TextView)view.findViewById(R.id.kabupaten_kegiatan_visum);
        keldes = (TextView)view.findViewById(R.id.kelurahan_desa_visum);

        inputTanggal.setText(date);

        requestQueue = Volley.newRequestQueue(getContext());
        spinnerKegiatan = (Spinner) view.findViewById(R.id.spinner_kegiatan);

        String url = "http://192.168.100.12/kasmart_mobile/get_kegiatan_dropdown.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonArray = response.getJSONArray("data");
                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        int id = data.getInt("id");
                        String kegiatan = data.getString("kegiatan");
                        kegiatanList.add(kegiatan);
                        kegiatanAdapter =new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,kegiatanList);
                        spinnerKegiatan.setAdapter(kegiatanAdapter);
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
        spinnerKegiatan.setOnItemSelectedListener(this);

        edit = (Button) view.findViewById(R.id.button_input_new_visum);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int storedKegiatanID = kegiatanId;
                String storedTanggalVisum = inputTanggal.getText().toString().trim();
                String storedHasilKegiatan = inputHasil.getText().toString().trim();
                String storedPendamping = user.get(sessionManager.NAME);
                //image
                insertItem(storedKegiatanID,storedTanggalVisum,storedHasilKegiatan,storedPendamping);

                Toast.makeText(getActivity(),"Data telah di ubah",Toast.LENGTH_SHORT).show();
            }
        });
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

    private void insertItem(int storedKegiatanID, String storedTanggalVisum,
                            String storedHasilKegiatan, String storedPendamping){
        String url = "http://192.168.100.12/kasmart_mobile/input_new_visum.php";
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
                map.put("kegiatanID",String.valueOf(storedKegiatanID));
                map.put("tanggal",storedTanggalVisum);
                map.put("hasil",storedHasilKegiatan);
                map.put("pendamping",storedPendamping);
                return map;
            }
        };
        requestQueue.add(stringRequest);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new VisumFragment();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedKegiatan = parent.getSelectedItem().toString();
        try {
            for(int i=0;i< jsonArray.length();i++){
                JSONObject data = jsonArray.getJSONObject(i);
                if(data.getString("kegiatan")==selectedKegiatan){
                    displayTanggalKegiatan.setText(data.getString("tanggal_kegiatan"));
                    kabupaten.setText(data.getString("Kabupaten"));
                    kecamatan.setText(data.getString("Kecamatan"));
                    keldes.setText(data.getString("Kelurahan"));
                    kegiatanId = data.getInt("id");
                    Log.d("log", String.valueOf(data.getInt("id")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}

