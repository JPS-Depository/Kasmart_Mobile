package com.jps.kasmart_mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class InputDesaFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    EditText inputKades,inputTelp,inputJumlahkk,inputJumlahDusun,inputJumlahSuku,inputJumlahRt,inputJumlahRw;
    Button edit,inputGambar;
    Spinner spinnerKeldes;
    TextView namaFile;
    PotensiAdapter potensiAdapter;
    ArrayList<String> keldesList = new ArrayList<>();
    ArrayAdapter<String> keldesAdapter;
    RequestQueue requestQueue;
    Bitmap photo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.desa_input_new, container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Input Data Desa / Kelurahan");

        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();

        inputKades = (EditText)view.findViewById(R.id.input_nama_kades_desa);
        inputTelp = (EditText)view.findViewById(R.id.input_telepon_kades);
        inputJumlahkk = (EditText) view.findViewById(R.id.input_jumlah_kk_desa);
        inputJumlahDusun = (EditText) view.findViewById(R.id.input_jumlah_dusun_desa);
        inputJumlahSuku = (EditText) view.findViewById(R.id.input_jumlah_suku_desa);
        inputJumlahRt = (EditText) view.findViewById(R.id.input_jumlah_rt_desa);
        inputJumlahRw = (EditText) view.findViewById(R.id.input_jumlah_rw_desa);
        spinnerKeldes = (Spinner) view.findViewById(R.id.spinner_keldes);

        inputGambar = (Button) view.findViewById(R.id.button_input_gambar);

        namaFile = (TextView)view.findViewById(R.id.file_gambar);

        edit = (Button) view.findViewById(R.id.button_input_new_desa);

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
                String storedKades = inputKades.getText().toString().trim();
                String storedTelp = inputTelp.getText().toString().trim();
                String storedKK = inputJumlahkk.getText().toString().trim();
                String storedDusun = inputJumlahDusun.getText().toString().trim();
                String storedSuku = inputJumlahSuku.getText().toString().trim();
                String storedRT = inputJumlahRt.getText().toString().trim();
                String storedRW = inputJumlahRw.getText().toString().trim();
                String storedKeldes = spinnerKeldes.getSelectedItem().toString();

                insertItem(storedKades,storedTelp, storedKK, storedDusun, storedSuku,
                        storedRT,storedRW,storedKeldes,storedCreatedBy);
                Toast.makeText(getActivity(),"Data telah di ubah",Toast.LENGTH_SHORT).show();
            }
        });

        inputGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(getActivity())
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        namaFile.setText(getFileName(uri,getContext()));
        try {
            photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("Range")
    String getFileName(Uri uri, Context context){
        String res = null;
        if(uri.getScheme().equals("content")){
            Cursor cursor = context.getContentResolver().query(uri,null, null,null,null);
            try{
                if(cursor != null && cursor.moveToFirst()){
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
            finally {
                cursor.close();
            }
            if(res == null){
                res = uri.getPath();
                int cutt = res.lastIndexOf('/');
                if(cutt != -1){
                    res = res.substring(cutt+1);
                }
            }
        }else{
            res = "Gambar dari Kamera";
        }
        return res;
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

    private void insertItem(String storedKades, String storedTelp, String storedKK,
                            String storedDusun, String storedSuku, String storedRT,
                            String storedRW, String storedKeldes, String storedCreatedBy){
        String url = "http://192.168.100.12/kasmart_mobile/input_desa.php";
        requestQueue = Volley.newRequestQueue(this.getContext());
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
                String image = getStringImage(photo);
                map.put("kades",storedKades);
                map.put("telp",storedTelp);
                map.put("kk",storedKK);
                map.put("dusun",storedDusun);
                map.put("suku",storedSuku);
                map.put("rt",storedRT);
                map.put("rw",storedRW);
                map.put("keldes",storedKeldes);
                map.put("createdby",storedCreatedBy);
                map.put("img",image);
                return map;
            }
        };
        requestQueue.add(stringRequest);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new DesaFragment();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String getStringImage(Bitmap photo) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG,100,ba);
        byte[] imageByte = ba.toByteArray();
        String encode = android.util.Base64.encodeToString(imageByte, android.util.Base64.DEFAULT);
        return encode;
    }
}

