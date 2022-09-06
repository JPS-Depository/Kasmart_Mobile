package com.jps.kasmart_mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class RutinEditFragment extends Fragment {
    int id, radioId;
    RadioGroup statusGroup;
    RadioButton statusRadioButton;
    String tipe, kegiatan, tanggalkegiatan, sasaran, detail, lokasi;
    EditText inputSasaran, inputDetail;
    Button edit;
    TextView displayKegiatan,displayTipe, displayTanggal, displayLokasi;
    WajibAdapter wajibAdapter;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rutin_input, container,false);
        Bundle bundle = this.getArguments();

        id = bundle.getInt("id");
        tipe = bundle.getString("tipe");
        kegiatan = bundle.getString("kegiatan");
        tanggalkegiatan = bundle.getString("tanggalkegiatan");
        sasaran = bundle.getString("sasaran");
        detail = bundle.getString("detail");
        lokasi = bundle.getString("lokasi");

        inputDetail = (EditText) view.findViewById(R.id.input_detail_rutin);
        inputSasaran = (EditText) view.findViewById(R.id.input_sasaran_rutin);

        displayKegiatan = (TextView) view.findViewById(R.id.nama_kegiatan_rutin);
        displayTipe = (TextView) view.findViewById(R.id.tipe_kegiatan_rutin);
        displayTanggal = (TextView) view.findViewById(R.id.tanggal_rutin);
        displayLokasi = (TextView) view.findViewById(R.id.isi_lokasi_rutin);

        statusGroup = (RadioGroup) view.findViewById(R.id.status_radio_rutin);

        inputDetail.setText(detail);
        inputSasaran.setText(sasaran);
        displayKegiatan.setText(kegiatan);
        displayTipe.setText(tipe);
        displayTanggal.setText(tanggalkegiatan);
        displayLokasi.setText(lokasi);

        edit = (Button) view.findViewById(R.id.button_input_edit_rutin);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storedSasaran = inputSasaran.getText().toString().trim();
                String storedDetil = inputDetail.getText().toString().trim();
                insertItem(id,storedSasaran,storedDetil,radioId);

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
        return view;
    }

    private void insertItem(int id, String storedSasaran, String storedDetil, int radioId){
        String url = "http://192.168.100.12/kasmart_mobile/edit_rutin.php";
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
                map.put("sasaran",storedSasaran);
                map.put("detil",storedDetil);
                map.put("status",String.valueOf(radioId));
                return map;
            }
        };
        requestQueue.add(stringRequest);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new RutinFragment();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

}

