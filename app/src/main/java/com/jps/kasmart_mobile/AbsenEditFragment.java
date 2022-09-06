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

public class AbsenEditFragment extends Fragment {
    int id;
    String  kegiatan, lokasi, keterangan, tanggalAbsen;
    EditText inputTanggal, inputKeterangan;
    Button edit;
    TextView displayKegiatan, displayLokasi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.absen_input, container,false);
        Bundle bundle = this.getArguments();

        id = bundle.getInt("id");
        kegiatan = bundle.getString("kegiatan");
        lokasi = bundle.getString("lokasi");
        keterangan = bundle.getString("keterangan");
        tanggalAbsen = bundle.getString("tanggalAbsen");

        displayKegiatan = (TextView) view.findViewById(R.id.nama_kegiatan_absen);
        displayLokasi = (TextView)view.findViewById(R.id.isi_lokasi_absen);

        inputTanggal = (EditText) view.findViewById(R.id.input_tanggal_absen);
        inputKeterangan = (EditText) view.findViewById(R.id.input_isi_keterangan_absensi);

        displayKegiatan.setText(kegiatan);
        displayLokasi.setText(lokasi);
        inputTanggal.setText(tanggalAbsen);
        inputKeterangan.setText(kegiatan);

        edit = (Button) view.findViewById(R.id.button_input_edit_absen);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storedTanggal = inputTanggal.getText().toString().trim();
                String storedKeterangan = inputKeterangan.getText().toString().trim();
                insertItem(id,storedTanggal,storedKeterangan);

                Toast.makeText(getActivity(),"Data telah di ubah",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void insertItem(int id,String storedTanggal, String storedKeterangan){
        String url = "http://192.168.100.12/kasmart_mobile/edit_absen.php";
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
                map.put("keterangan",storedKeterangan);
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

