package com.jps.kasmart_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class BeritaDetailFragment extends Fragment{
    String judul, isi_berita, tanggal_berita, mpenulis;
    TextView judulBerita, isiBerita, penulis, tanggalBerita;
    Button buttonDelete, buttonEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.berita_detail, container,false);
        Bundle bundle = this.getArguments();

        judul = bundle.getString("judul");
        isi_berita = bundle.getString("isi berita");
        tanggal_berita = bundle.getString("tanggal_berita");
        mpenulis = bundle.getString("createdBy");

        judulBerita = (TextView) view.findViewById(R.id.berita_detail_judul);
        isiBerita = (TextView) view.findViewById(R.id.berita_detail_text);
        tanggalBerita = (TextView) view.findViewById(R.id.tanggal_berita_detail);
        penulis = (TextView) view.findViewById(R.id.penulis);

        judulBerita.setText(judul);
        isiBerita.setText(isi_berita);
        tanggalBerita.setText(String.valueOf(tanggal_berita));
        penulis.setText(mpenulis);

        isiBerita.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }

}
