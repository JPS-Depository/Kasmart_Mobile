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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class InputAbsenFragment extends Fragment {
    int id;
    String  jenis, nama, sumberDayaAlam, sumberDayaManusia, asetDesa, budayaDesa;
    EditText inputAbsen;
    Button edit;
    TextView displayJenis;
    Spinner spinnerKegiatan;
    SpinnerKegiatan spin;
    PotensiAdapter potensiAdapter;
    ArrayList<SpinnerKegiatan> kegiatanList = new ArrayList<SpinnerKegiatan>();
    ArrayAdapter<SpinnerKegiatan> kegiatanAdapter;
    RequestQueue requestQueue;
    JSONArray jsonArray;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.absen_input_new, container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Input Absen");

        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();

        inputAbsen = (EditText)  view.findViewById(R.id.input_keterangan_absen);
        spinnerKegiatan = (Spinner) view.findViewById(R.id.spinner_kegiatan);

        displayJenis = (TextView) view.findViewById(R.id.jenis_kegiatan_input);

        edit = (Button) view.findViewById(R.id.button_input_absen);

        requestQueue = Volley.newRequestQueue(getContext());
        String url="http://192.168.100.12/kasmart_mobile/get_kegiatan_dropdown_absen.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonArray = response.getJSONArray("data");
                    for(int i=0;i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        String kegiatan = data.getString("kegiatan");
                        String jenis = data.getString("jenis");
                        int id = data.getInt("id");
                        kegiatanList.add(new SpinnerKegiatan(id,kegiatan,jenis));
                        kegiatanAdapter = new ArrayAdapter<SpinnerKegiatan>(getActivity(),android.R.layout.simple_spinner_item,kegiatanList);
                        kegiatanAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
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

        spinnerKegiatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin = (SpinnerKegiatan) parent.getItemAtPosition(position);
                displayJenis.setText(spin.jenis);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storedCreatedBy = user.get(sessionManager.NAME);
                int storedIdKegiatan = spin.id;
                String storedKegiatan = spinnerKegiatan.getSelectedItem().toString();
                String storedKeterangan = inputAbsen.getText().toString().trim();

                insertItem(storedCreatedBy,storedIdKegiatan,storedKegiatan,storedKeterangan);
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


    private void insertItem(String storedCreatedBy, int storedIdKegiatan, String storedKegiatan, String storedKeterangan){
        String url = "http://192.168.100.12/kasmart_mobile/input_absen.php";
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
                map.put("id",String.valueOf(storedIdKegiatan));
                map.put("keterangan",storedKeterangan);
                map.put("kegiatan",storedKegiatan);
                map.put("createdby",storedCreatedBy);
                return map;
            }
        };
        requestQueue.add(stringRequest);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new AbsensiFragment();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }
}

