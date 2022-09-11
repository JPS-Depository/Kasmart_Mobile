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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class InputBeritaFragment extends Fragment {
    String createby;
    EditText inputBerita, inputTanggal, inputJudul;
    Button edit, inputGambar;
    TextView displayCreate, namaFile;
    BeritaAdapter beritaAdapter;
    Bitmap photo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.berita_input_new, container,false);
        Bundle bundle = this.getArguments();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Input Berita");

        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();

        displayCreate = (TextView) view.findViewById(R.id.penulis);
        namaFile = (TextView)view.findViewById(R.id.file_gambar);

        inputJudul = (EditText)view.findViewById(R.id.judul_berita);
        inputBerita = (EditText) view.findViewById(R.id.input_berita);
        inputTanggal = (EditText) view.findViewById(R.id.input_tanggal_berita);

        displayCreate = (TextView) view.findViewById(R.id.penulis);
        displayCreate.setText(user.get(sessionManager.NAME));

        edit = (Button) view.findViewById(R.id.button_input_edit_berita);
        inputGambar = (Button) view.findViewById(R.id.button_input_gambar_berita);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storedPenulis = displayCreate.getText().toString();
                String storedJudul = inputJudul.getText().toString();
                String storedTanggal = inputTanggal.getText().toString().trim();
                String storedBerita = inputBerita.getText().toString();

                insertItem(storedPenulis,storedJudul,storedTanggal,storedBerita);
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

    private void insertItem(String storedPenulis, String storedJudul, String storedTanggal, String storedBerita ){
        Log.d("Masuk","masuk");
        String url = "http://192.168.100.12/kasmart_mobile/input_berita.php";
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
                map.put("penulis",storedPenulis);
                map.put("judul",storedJudul);
                map.put("tanggal",storedTanggal);
                map.put("berita",storedBerita);
                map.put("img",image);
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

    private String getStringImage(Bitmap photo) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG,100,ba);
        byte[] imageByte = ba.toByteArray();
        String encode = android.util.Base64.encodeToString(imageByte, android.util.Base64.DEFAULT);
        return encode;
    }
}
