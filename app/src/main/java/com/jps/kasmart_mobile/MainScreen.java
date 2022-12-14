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
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;

public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    SessionManager sessionManager;
    int menuClicked = 0;
    String fullname,role,email,id;

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
        fullname = user.get(sessionManager.NAME);
        role = user.get(sessionManager.ROLE);
        email = user.get(sessionManager.EMAIL);
        id = user.get(sessionManager.ID);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        TextView nav_name = (TextView) headerView.findViewById(R.id.nama_pengguna);
        TextView nav_role = (TextView) headerView.findViewById(R.id.role_pengguna);
        TextView nav_email = (TextView) headerView.findViewById(R.id.email_pengguna);
        ImageView nav_picture = (ImageView) headerView.findViewById(R.id.nav_picture);
        String imageUrl = "http://192.168.100.12/kasmart_mobile/image/user/foto_user_id_"+id+".jpg";
        Picasso.get().load(imageUrl).fit().networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).centerInside().into(nav_picture);

        nav_name.setText(fullname);
        nav_role.setText(role);
        nav_email.setText(email);

        navigationView.setNavigationItemSelectedListener(this);

        switch(role){
            case "User":
                navigationView.getMenu().findItem(R.id.group_dataEkonomi).setVisible(false);
                break;
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null)
        {
            replaceFragment(new BeritaFragment());
            navigationView.setCheckedItem(R.id.nav_berita);
            menuClicked=0;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_berita:
                replaceFragment(new BeritaFragment());
                menuClicked=0;
                break;
            case R.id.nav_Bulanan_bumdes:
                replaceFragment(new BumdesFragment());
                getSupportActionBar().setTitle("Pendamping Data BUMDESA");
                menuClicked=1;
                break;
            case R.id.nav_usp_uek:
                replaceFragment(new USPFragment());
                getSupportActionBar().setTitle("Data USP dan UEK");
                menuClicked=2;
                break;
            case R.id.nav_shu:
                replaceFragment(new SHUFragment());
                getSupportActionBar().setTitle("Data SHU Khusus UEK");
                menuClicked=3;
                break;
            case R.id.nav_masalah_pendamping:
                replaceFragment(new MasalahFragment());
                menuClicked=4;
                break;
            case R.id.nav_pendamping:
                replaceFragment(new PendampingFragment());
                menuClicked=5;
                break;
            case R.id.nav_daerah:
                replaceFragment(new DaerahFragment());
                menuClicked=6;
                break;
            case R.id.nav_desa:
                replaceFragment(new DesaFragment());
                menuClicked=7;
                break;
            case R.id.nav_potensi:
                replaceFragment(new PotensiFragment());
                menuClicked=8;
                break;
            case R.id.nav_kegiatan_harian:
                replaceFragment(new RutinFragment());
                menuClicked=9;
                break;
            case R.id.nav_wajib_bermasa:
                replaceFragment(new WajibFragment());
                menuClicked=10;
                break;
            case R.id.nav_absen:
                replaceFragment(new AbsensiFragment());
                menuClicked=11;
                break;
            case R.id.nav_visum:
                replaceFragment(new VisumFragment());;
                menuClicked=12;
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
        fragmentTransaction.addToBackStack(null).commit();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_option:
                switch(menuClicked){
                    case 0:
                        replaceFragment(new InputBeritaFragment());
                        break;
                    case 4:
                        replaceFragment(new InputMasalahFragment());
                        getSupportActionBar().setTitle("Input Masalah Pendampingan");
                        break;
                    case 5:
                        replaceFragment(new PendampingFragment());
                        getSupportActionBar().setTitle("List Pendamping");
                        break;
                    case 7:
                        replaceFragment(new InputDesaFragment());
                        getSupportActionBar().setTitle("Input Data Desa / Kelurahan");
                        break;
                    case 8:
                        replaceFragment(new InputPotensiFragment());
                        getSupportActionBar().setTitle("Input Potensi Daerah");
                        break;
                    case 9:
                    case 10:
                        replaceFragment(new InputKegiatanFragment());
                        getSupportActionBar().setTitle("Input Kegiatan");
                        break;
                    case 11:
                        replaceFragment(new InputAbsenFragment());
                        getSupportActionBar().setTitle("Absensi Kegiatan");
                        break;
                    case 12:
                        replaceFragment(new InputVisumFragment());
                        getSupportActionBar().setTitle("Input Visum Kegiatan");
                        break;
                    case 13:
                        sessionManager.logout();
                        break;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}