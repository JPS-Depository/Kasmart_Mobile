package com.jps.kasmart_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class BeritaDetailFragment extends Fragment{
    int id;
    String judul, isi_berita, tanggal_berita, mpenulis,role,url;
    TextView judulBerita, isiBerita, penulis, tanggalBerita;
    ImageView detailImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.berita_detail, container,false);
        Bundle bundle = this.getArguments();

        id = bundle.getInt("id");
        role = bundle.getString("role");
        judul = bundle.getString("judul");
        isi_berita = bundle.getString("isi berita");
        tanggal_berita = bundle.getString("tanggal_berita");
        mpenulis = bundle.getString("createdBy");
        url = "http://192.168.100.12/kasmart_mobile/image/berita/foto_berita_id_"+id+".jpg";
        judulBerita = (TextView) view.findViewById(R.id.berita_detail_judul);
        isiBerita = (TextView) view.findViewById(R.id.berita_detail_text);
        tanggalBerita = (TextView) view.findViewById(R.id.tanggal_berita_detail);
        penulis = (TextView) view.findViewById(R.id.penulis);
        detailImage = (ImageView) view.findViewById(R.id.berita_detail_gambar);

        Picasso.get().load(url).placeholder(R.drawable.ic_baseline_image_24).resize(1000,0).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(detailImage);

        judulBerita.setText(judul);
        isiBerita.setText(isi_berita);
        tanggalBerita.setText(String.valueOf(tanggal_berita));
        penulis.setText(mpenulis);

        isiBerita.setMovementMethod(new ScrollingMovementMethod());

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
        switch(role){
            case "Guest":
                menu.clear();
                break;
        }
    }

}
