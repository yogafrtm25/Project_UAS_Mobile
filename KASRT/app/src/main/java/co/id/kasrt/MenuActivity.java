package co.id.kasrt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    LinearLayout tombolSatu;
    LinearLayout tombolDua;
    LinearLayout tombolTiga;
    LinearLayout tombolEmpat;
    LinearLayout tombolLima;
    LinearLayout tombolEnam;

    private ViewFlipper viewFlipper;
    private ImageButton optionButton, messageButton, profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Inisialisasi LinearLayout dengan findViewById setelah setContentView
        tombolSatu = findViewById(R.id.btnLaporanKas);
        tombolDua = findViewById(R.id.btnDaftarWarga);
        tombolTiga = findViewById(R.id.btnTambahDokument);
        tombolEmpat = findViewById(R.id.btnBayarKas);
        tombolLima = findViewById(R.id.btnSecurity);
        tombolEnam = findViewById(R.id.btnInformasi);

        // Toolbar Section
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        ImageView toolbarLogo = findViewById(R.id.toolbar_logo);

        // Announcement Section
        viewFlipper = findViewById(R.id.viewFlipper);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        // Bottom Navigation
        optionButton = findViewById(R.id.option_button);
        messageButton = findViewById(R.id.message_button);
        profileButton = findViewById(R.id.profile_button);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Set onClick listeners for the menu items
        tombolSatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LaporanKasActivity.class);
                startActivity(intent);
            }
        });

        tombolDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tombolTiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SecurityActivity.class);
                startActivity(intent);
            }
        });

        tombolEmpat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, BayarKasActivity.class);
                startActivity(intent);
            }
        });

        tombolLima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SecurityActivity.class);
                startActivity(intent);
            }
        });

        tombolEnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, InformasiActivity.class);
                startActivity(intent);
            }
        });

        // Set onClick listeners for the bottom navigation items
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}
