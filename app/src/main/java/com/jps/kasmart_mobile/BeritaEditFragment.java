package com.jps.kasmart_mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BeritaEditFragment extends Fragment {
    int id;
    String  judul,berita,tanggal,createby;
    EditText inputBerita, inputTanggal;
    Button edit;
    TextView displayJudul, displayCreate;
    BeritaAdapter beritaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.berita_edit, container,false);
        Bundle bundle = this.getArguments();

        id = bundle.getInt("id");
        berita = bundle.getString("isiberita");
        tanggal = bundle.getString("tanggalberita");
        judul = bundle.getString("judul");
        createby = bundle.getString("createdby",createby);

        inputBerita = (EditText) view.findViewById(R.id.input_berita);
        inputTanggal = (EditText) view.findViewById(R.id.input_tanggal_berita);

        displayCreate = (TextView) view.findViewById(R.id.penulis);
        displayJudul = (TextView) view.findViewById(R.id.berita_detail_judul);

        displayCreate.setText(createby);
        displayJudul.setText(judul);
        inputBerita.setText(berita);
        inputTanggal.setText(tanggal);

        edit = (Button) view.findViewById(R.id.button_input_edit_berita);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storedTanggal = inputTanggal.getText().toString().trim();
                String storedBerita = inputBerita.getText().toString();
                insertItem(id,storedTanggal,storedBerita);
                Toast.makeText(getActivity(),"Data telah di ubah",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void insertItem(int id,String storedTanggal, String storedBerita ){
        Log.d("Masuk","masuk");
        String url = "http://192.168.100.12/kasmart_mobile/edit_berita.php";
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
                map.put("id",String.valueOf(id));
                map.put("tanggal",storedTanggal);
                map.put("berita",storedBerita);
                return map;
            }
        };
        requestQueue.add(stringRequest);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new BeritaFragment();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }
}
