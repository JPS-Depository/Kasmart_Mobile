package com.jps.kasmart_mobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
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

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class VisumEditFragment extends Fragment {
    int id;
    String  kegiatan, tanggalVisumS, tanggalKegiatanS, kabupaten, kecamatan, keldes, hasilKegiatanS, imgUrl;
    EditText tanggalVisum, hasilKegiatan;
    Button edit;
    TextView displayKegiatan, displayTanggalKegiatan, displayKabupaten,
    displayKecamatan, displayKeldes;
    VisumAdapter visumAdapter;
    ImageView displayGambar;
    Bitmap photo;
    Uri uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.visum_input, container,false);
        Bundle bundle = this.getArguments();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Daftar Visum");

        id = bundle.getInt("id");
        kegiatan = bundle.getString("kegiatan");
        tanggalVisumS = bundle.getString("tanggalvisum");
        tanggalKegiatanS = bundle.getString("tanggalkegiatan");
        kabupaten = bundle.getString("kabupaten");
        kecamatan = bundle.getString("kecamatan");
        keldes = bundle.getString("keldes");
        hasilKegiatanS = bundle.getString("hasilkegiatan");
        imgUrl = bundle.getString("img");

        displayGambar = (ImageView) view.findViewById(R.id.gambar_kegiatan);

        Picasso.get().load(imgUrl).fit().centerInside().placeholder(R.drawable.ic_baseline_image_24).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(displayGambar);

        displayKegiatan = (TextView) view.findViewById(R.id.nama_kegiatan_visum);
        displayTanggalKegiatan = (TextView)view.findViewById(R.id.tanggal_kegiatan_visum);
        displayKabupaten = (TextView)view.findViewById(R.id.kabupaten_kegiatan_visum);
        displayKecamatan = (TextView)view.findViewById(R.id.kecamatan_kegiatan_visum);
        displayKeldes = (TextView)view.findViewById(R.id.kelurahan_desa_visum);

        tanggalVisum = (EditText) view.findViewById(R.id.input_tanggal_visum_visum);
        hasilKegiatan = (EditText) view.findViewById(R.id.input_hasil_kegiatan_visum);

        displayKegiatan.setText(kegiatan);
        displayTanggalKegiatan.setText(tanggalKegiatanS);
        displayKabupaten.setText(kabupaten);
        displayKecamatan.setText(kecamatan);
        displayKeldes.setText(keldes);
        tanggalVisum.setText(tanggalVisumS);
        hasilKegiatan.setText(hasilKegiatanS);

        edit = (Button) view.findViewById(R.id.button_input_edit_visum);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storedTanggal = tanggalVisum.getText().toString().trim();
                String storedHasil = hasilKegiatan.getText().toString().trim();
                insertItem(id,storedTanggal,storedHasil);

                Toast.makeText(getActivity(),"Data telah di ubah",Toast.LENGTH_SHORT).show();
            }
        });
        displayGambar.setOnClickListener(new View.OnClickListener() {
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
        Picasso.get().load(uri).placeholder(R.drawable.ic_baseline_image_24).resize(1000,1000).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(displayGambar);
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

    private void insertItem(int id,String storedTanggal, String storedHasil){
        String url = "http://192.168.100.12/kasmart_mobile/edit_visum.php";
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
                map.put("hasil",storedHasil);
                map.put("img",image);
                return map;
            }
        };
        requestQueue.add(stringRequest);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new VisumFragment();
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

