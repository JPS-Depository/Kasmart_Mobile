package com.jps.kasmart_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Berita");

        HashMap<String, String> user = sessionManager.getUserDetail();
        String name = user.get(sessionManager.NAME);
        String email = user.get(sessionManager.EMAIL);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        TextView nav_name = (TextView) headerView.findViewById(R.id.nama_pengguna);
        TextView nav_email = (TextView) headerView.findViewById(R.id.email_pengguna);

        nav_name.setText(name);
        nav_email.setText(email);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null)
        {
            replaceFragment(new BeritaFragment());
            navigationView.setCheckedItem(R.id.nav_berita);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_berita:
                replaceFragment(new BeritaFragment());
                getSupportActionBar().setTitle("Berita");
                break;
            case R.id.nav_Bulanan_bumdes:
                replaceFragment(new BumdesFragment());
                getSupportActionBar().setTitle("Pendamping Data BUMDESA");
                break;
            case R.id.nav_usp_uek:
                replaceFragment(new USPFragment());
                getSupportActionBar().setTitle("Data USP dan UEK");
                break;
            case R.id.nav_shu:
                replaceFragment(new SHUFragment());
                getSupportActionBar().setTitle("Data SHU Khusus UEK");
                break;
            case R.id.nav_masalah_pendamping:
                replaceFragment(new MasalahFragment());
                getSupportActionBar().setTitle("Masalah Pendampingan");
                break;
            case R.id.nav_pendamping:
                replaceFragment(new PendampingFragment());
                getSupportActionBar().setTitle("List Pendamping");
                break;
            case R.id.nav_daerah:
                replaceFragment(new DaerahFragment());
                getSupportActionBar().setTitle("Daftar Daerah");
                break;
            case R.id.nav_desa:
                replaceFragment(new DesaFragment());
                getSupportActionBar().setTitle("Daftar Desa / Kelurahan");
                break;
            case R.id.nav_potensi:
                replaceFragment(new PotensiFragment());
                getSupportActionBar().setTitle("Potensi Daerah");
                break;
            case R.id.nav_kegiatan_harian:
                replaceFragment(new RutinFragment());
                getSupportActionBar().setTitle("Kegiatan Rutin");
                break;
            case R.id.nav_wajib_bermasa:
                replaceFragment(new WajibFragment());
                getSupportActionBar().setTitle("Kegiatan Wajib");
                break;
            case R.id.nav_absen:
                replaceFragment(new AbsensiFragment());
                getSupportActionBar().setTitle("Absensi Kegiatan");
                break;
            case R.id.nav_visum:
                replaceFragment(new VisumFragment());
                getSupportActionBar().setTitle("Visum Kegiatan");
                break;
            case R.id.nav_logout:
                sessionManager.logout();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }
}