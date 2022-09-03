package com.jps.kasmart_mobile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;


public class ConfirmationDelete extends DialogFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        Bundle bundle = this.getArguments();

        return new AlertDialog.Builder(getActivity())
                .setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin akan membuang Item ini?")
                .setCancelable(true)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final int id = bundle.getInt("id");
                        String menu = bundle.getString("menu");
                        deleteItem(id, menu);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
    }

    private void deleteItem(final int id, String menu) {
        String url = "http://192.168.100.12/kasmart_mobile/delete_"+ menu + ".php";
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
            protected HashMap<String,String> getParams() throws AuthFailureError{
                HashMap<String,String> map = new HashMap<>();
                map.put("id",String.valueOf(id));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);
        reloadFragment(menu);
    }

    private void reloadFragment(String menu) {
        switch(menu){
            case "berita":
                replaceFragment(new BeritaFragment());
                break;
            case "masalah":
                replaceFragment(new MasalahFragment());
                break;
            case "pendamping":
                replaceFragment(new PendampingFragment());
                break;
            case "daerah":
                replaceFragment(new DaerahFragment());
                break;
            case "desa":
                replaceFragment(new DesaFragment());
                break;
            case "potensi":
                replaceFragment(new PotensiFragment());
                break;
            case "rutin":
                replaceFragment(new RutinFragment());
                break;
            case "wajib":
                replaceFragment(new WajibFragment());
                break;
            case "absensi":
                replaceFragment(new AbsensiFragment());
                break;
            case "visum":
                replaceFragment(new VisumFragment());
                break;
        }
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }
}

