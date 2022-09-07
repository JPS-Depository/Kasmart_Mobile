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

public class InputMasalahFragment extends Fragment {
    String  nama;
    EditText inputMasalah, inputPembinaan;
    Button edit;
    TextView namaDisplay;
    MasalahAdapter masalahAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.masalah_input_new, container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Input Masalah Pendampingan");
        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();

        nama = user.get(sessionManager.NAME);

        inputMasalah = (EditText) view.findViewById(R.id.input_text_masalah_masalah);
        inputPembinaan = (EditText) view.findViewById(R.id.input_text_pembinaan_masalah);

        namaDisplay = (TextView)view.findViewById(R.id.nama_masalah);

        namaDisplay.setText(nama);

        edit = (Button) view.findViewById(R.id.button_input_edit_masalah);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storedMasalah = inputMasalah.getText().toString().trim();
                String storedPembinaan = inputPembinaan.getText().toString();
                String storedCreated = nama;
                insertItem(storedMasalah,storedPembinaan, storedCreated);

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

    private void insertItem(String storedMasalah, String storedPembinaan, String storedCreated){
        Log.d("Masuk","masuk");
        String url = "http://192.168.100.12/kasmart_mobile/input_masalah.php";
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
                map.put("masalah",storedMasalah);
                map.put("pembinaan",storedPembinaan);
                map.put("createdby",storedCreated);
                return map;
            }
        };
        requestQueue.add(stringRequest);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new MasalahFragment();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }
}

