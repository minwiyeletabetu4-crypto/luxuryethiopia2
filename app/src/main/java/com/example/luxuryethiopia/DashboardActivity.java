package com.example.luxuryethiopia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private FrameLayout cardLalibela, cardDanakil, cardSimien, cardLakeTana, cardAxum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Bind cards
        cardLalibela = findViewById(R.id.cardLalibela);
        cardDanakil = findViewById(R.id.cardDanakil);
        cardSimien = findViewById(R.id.cardSimien);
        cardLakeTana = findViewById(R.id.cardLakeTana);
        cardAxum = findViewById(R.id.cardAxum);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        // Card Click Listeners
        cardLalibela.setOnClickListener(v -> openDetailsScreen("LALIBELA", "$3,500"));
        cardDanakil.setOnClickListener(v -> openDetailsScreen("DANAKIL DEPRESSION", "$4,200"));
        cardSimien.setOnClickListener(v -> openDetailsScreen("SIMIEN MOUNTAINS", "$2,900"));
        cardLakeTana.setOnClickListener(v -> openDetailsScreen("LAKE TANA", "$1,800"));
        cardAxum.setOnClickListener(v -> openDetailsScreen("AXUM HISTORICAL SITE", "$2,500"));

        // Bottom Navigation Logic
        bottomNavigation.setSelectedItemId(R.id.nav_home);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profile) {
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_booking || id == R.id.nav_explore) {
                Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                return true;
            }
            return true;
        });
    }

    private void openDetailsScreen(String name, String price) {
        Intent intent = new Intent(DashboardActivity.this, DetailsActivity.class);
        intent.putExtra("DESTINATION_NAME", name);
        intent.putExtra("DESTINATION_PRICE", price);
        startActivity(intent);
    }
}
