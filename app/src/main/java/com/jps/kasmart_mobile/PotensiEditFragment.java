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

import org.w3c.dom.Text;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PotensiEditFragment extends Fragment {
    int id;
    String  jenis, nama, sumberDayaAlam, sumberDayaManusia, asetDesa, budayaDesa;
    EditText inputSDA, inputSDM, inputAsetDesa, inputBudayaDesa;
    Button edit;
    TextView displayJenis, displayNama;
    PotensiAdapter potensiAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.potensi_input, container,false);
        Bundle bundle = this.getArguments();

        id = bundle.getInt("id");
        jenis = bundle.getString("jenis");
        nama = bundle.getString("nama");
        sumberDayaAlam = bundle.getString("sumberdayaalam");
        sumberDayaManusia = bundle.getString("sumberdayamanusia");
        asetDesa = bundle.getString("asetDesa");
        budayaDesa = bundle.getString("budayaDesa");

        inputSDA = (EditText)  view.findViewById(R.id.input_sda_potensi);
        inputSDM = (EditText) view.findViewById(R.id.input_sdm_potensi);
        inputAsetDesa = (EditText) view.findViewById(R.id.input_aset_desa_potensi);
        inputBudayaDesa = (EditText) view.findViewById(R.id.input_budaya_potensi);
        displayJenis = (TextView) view.findViewById(R.id.tipe_daerah_potensi);
        displayNama = (TextView) view.findViewById(R.id.nama_daerah_potensi);

        displayNama.setText(nama);
        displayJenis.setText(jenis);
        inputSDA.setText(sumberDayaAlam);
        inputSDM.setText(sumberDayaManusia);
        inputAsetDesa.setText(asetDesa);
        inputBudayaDesa.setText(budayaDesa);

        edit = (Button) view.findViewById(R.id.button_input_edit_potensi);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storedSDA = inputSDA.getText().toString().trim();
                String storedSDM = inputSDM.getText().toString().trim();
                String storedAset = inputAsetDesa.getText().toString().trim();
                String storedBudaya = inputBudayaDesa.getText().toString().trim();
                insertItem(id,storedSDA,storedSDM,storedAset,storedBudaya);

                Toast.makeText(getActivity(),"Data telah di ubah",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void insertItem(int id, String storedSDA, String storedSDM, String storedAset, String storedBudaya){
        String url = "http://192.168.100.12/kasmart_mobile/edit_potensi.php";
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
                map.put("sda",storedSDA);
                map.put("sdm",storedSDM);
                map.put("aset",storedAset);
                map.put("budaya",storedBudaya);
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
}

