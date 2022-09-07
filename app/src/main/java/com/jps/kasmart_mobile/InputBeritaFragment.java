package com.jps.kasmart_mobile;

import android.os.Bundle;
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
    Button edit;
    TextView displayCreate;
    BeritaAdapter beritaAdapter;

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

        inputJudul = (EditText)view.findViewById(R.id.judul_berita);
        inputBerita = (EditText) view.findViewById(R.id.input_berita);
        inputTanggal = (EditText) view.findViewById(R.id.input_tanggal_berita);

        displayCreate = (TextView) view.findViewById(R.id.penulis);

        displayCreate.setText(user.get(sessionManager.NAME));

        edit = (Button) view.findViewById(R.id.button_input_edit_berita);

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
                map.put("penulis",storedPenulis);
                map.put("judul",storedJudul);
                map.put("tanggal",storedTanggal);
                map.put("berita",storedBerita);
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
}
