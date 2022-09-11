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
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DesaEditFragment extends Fragment {
    EditText inputKades,inputTelp,inputJumlahkk,inputJumlahDusun,inputJumlahSuku,inputJumlahRt,inputJumlahRw;
    int id, telepon, kk, dusun, rw, rt, suku;
    String namaDesa, namaKades;
    Button edit,inputGambar;
    TextView displayNama, namaFile;
    Bitmap photo;
    Uri uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.desa_input, container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Data");

        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();

        Bundle bundle = this.getArguments();

        id = bundle.getInt("id");
        namaDesa = bundle.getString("namaDesa");
        namaKades = bundle.getString("namaKades");
        telepon = bundle.getInt("telepon");
        kk = bundle.getInt("kk");
        dusun = bundle.getInt("dusun");
        rw = bundle.getInt("rw");
        rt = bundle.getInt("rt");
        suku = bundle.getInt("suku");

        displayNama = (TextView)view.findViewById(R.id.nama_desa);

        namaFile = (TextView)view.findViewById(R.id.file_gambar);

        inputKades = (EditText)view.findViewById(R.id.input_nama_kades_desa);
        inputTelp = (EditText)view.findViewById(R.id.input_telepon_kades);
        inputJumlahkk = (EditText) view.findViewById(R.id.input_jumlah_kk_desa);
        inputJumlahDusun = (EditText) view.findViewById(R.id.input_jumlah_dusun_desa);
        inputJumlahSuku = (EditText) view.findViewById(R.id.input_jumlah_suku_desa);
        inputJumlahRt = (EditText) view.findViewById(R.id.input_jumlah_rt_desa);
        inputJumlahRw = (EditText) view.findViewById(R.id.input_jumlah_rw_desa);
        inputGambar = (Button) view.findViewById(R.id.button_input_gambar);

        displayNama.setText(namaDesa);
        inputKades.setText(namaKades);
        inputTelp.setText(String.valueOf(telepon));
        inputJumlahkk.setText(String.valueOf(kk));
        inputJumlahDusun.setText(String.valueOf(dusun));
        inputJumlahSuku.setText(String.valueOf(suku));
        inputJumlahRt.setText(String.valueOf(rt));
        inputJumlahRw.setText(String.valueOf(rw));

        edit = (Button) view.findViewById(R.id.button_input_new_desa);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storedKades = inputKades.getText().toString().trim();
                String storedTelp = inputTelp.getText().toString().trim();
                String storedKK = inputJumlahkk.getText().toString().trim();
                String storedDusun = inputJumlahDusun.getText().toString().trim();
                String storedSuku = inputJumlahSuku.getText().toString().trim();
                String storedRT = inputJumlahRt.getText().toString().trim();
                String storedRW = inputJumlahRw.getText().toString().trim();

                insertItem(storedKades,storedTelp, storedKK, storedDusun, storedSuku,
                        storedRT,storedRW);
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
        uri = data.getData();
        try {
            photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri );

        } catch (IOException e) {
            e.printStackTrace();
        }
        namaFile.setText(getFileName(uri,getContext()));
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
                            String storedRW){
        String url = "http://192.168.100.12/kasmart_mobile/edit_desa.php";
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
                String image = getStringImage(photo);
                map.put("id",String.valueOf(id));
                map.put("kades",storedKades);
                map.put("telp",storedTelp);
                map.put("kk",storedKK);
                map.put("dusun",storedDusun);
                map.put("suku",storedSuku);
                map.put("rt",storedRT);
                map.put("rw",storedRW);
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
    private String getStringImage(Bitmap photo) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG,100,ba);
        byte[] imageByte = ba.toByteArray();
        String encode = android.util.Base64.encodeToString(imageByte, android.util.Base64.DEFAULT);
        return encode;
    }

}

