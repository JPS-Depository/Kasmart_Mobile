package com.jps.kasmart_mobile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Base64;
import java.util.HashMap;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BeritaEditFragment extends Fragment {
    int id;
    String  judul,berita,tanggal,createby,url;
    EditText inputBerita, inputTanggal;
    Button edit;
    TextView displayJudul, displayCreate;
    SessionManager sessionManager;
    HashMap<String, String> user;
    ImageView detailImage;
    Uri uri;
    Bitmap photo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.berita_edit, container,false);
        Bundle bundle = this.getArguments();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Berita");

        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        user = sessionManager.getUserDetail();

        id = bundle.getInt("id");
        berita = bundle.getString("isiberita");
        tanggal = bundle.getString("tanggalberita");
        judul = bundle.getString("judul");
        createby = bundle.getString("createdby",createby);
        url = "http://192.168.100.12/kasmart_mobile/image/berita/foto_berita_id_"+id+".jpg";

        detailImage = (ImageView) view.findViewById(R.id.berita_detail_gambar);
        Picasso.get().load(url).placeholder(R.drawable.ic_baseline_image_24).resize(1000,0).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(detailImage);

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

        detailImage.setOnClickListener(new View.OnClickListener() {
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
        Picasso.get().load(uri).placeholder(R.drawable.ic_baseline_image_24).resize(1000,1000).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(detailImage);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        switch(user.get(sessionManager.ROLE)){
            case "Guest":
                menu.clear();
                break;
        }
    }

    private void insertItem(int id,String storedTanggal, String storedBerita ){
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
                String image = getStringImage(photo);
                map.put("id",String.valueOf(id));
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
