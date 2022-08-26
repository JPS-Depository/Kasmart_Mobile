package com.jps.kasmart_mobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;

    private Button login;
    private EditText username, password;
    private ProgressBar loading;
    private static String URL_LOGIN="http://192.168.100.12/kasmart_mobile/login.php";

    private String name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);

        loading=(ProgressBar)findViewById(R.id.loading);

        login = (Button) findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storedUsername = username.getText().toString().trim();
                String storedPassword = password.getText().toString();
                checkInput(storedUsername,storedPassword);
            }
        });

    }

    public void checkInput(String storedUsername, String storedPassword) {
        if(storedUsername.equals("")||storedPassword.equals("")){
            Snackbar.make(login, "Kolom harus diisi", Snackbar.LENGTH_LONG)
                    .show();
        }else{
            login(storedUsername,storedPassword);
        }
    }

    private void login(String storedUsername, String storedPassword) {
        loading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("Message",response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("login");
                            if(success.equals("1")) {
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    name = object.getString("name").trim();
                                    email = object.getString("email").trim();
                                    loading.setVisibility(View.GONE);
                                    openScreen2();
                                }
                            }else{
                                Toast.makeText(MainActivity.this,
                                        "Password Salah",Toast.LENGTH_SHORT).show();

                                loading.setVisibility(View.GONE);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this,"Username Tidak ada",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> {
                    Toast.makeText(MainActivity.this, "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", storedUsername);
                params.put("password",storedPassword);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openScreen2() {
        sessionManager.createSession(name, email);
        Intent intent = new Intent(this, MainScreen.class);
        intent.putExtra("NAME", name);
        intent.putExtra("EMAIL",email);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        
    }
}
