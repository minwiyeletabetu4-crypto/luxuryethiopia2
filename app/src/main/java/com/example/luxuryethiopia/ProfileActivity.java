package com.example.luxuryethiopia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SwitchMaterial themeSwitch = findViewById(R.id.themeSwitch);
        TextView tvThemeLabel = findViewById(R.id.tvThemeLabel);
        Button btnLogout = findViewById(R.id.btnLogout);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        // Set initial switch state based on current theme
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        themeSwitch.setChecked(currentMode == AppCompatDelegate.MODE_NIGHT_YES);
        tvThemeLabel.setText(currentMode == AppCompatDelegate.MODE_NIGHT_YES ? "Current Mode: Dark" : "Current Mode: Light");

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                tvThemeLabel.setText("Current Mode: Dark");
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                tvThemeLabel.setText("Current Mode: Light");
            }
        });

        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        bottomNavigation.setSelectedItemId(R.id.nav_profile);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_booking || id == R.id.nav_explore) {
                Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                return true;
            }
            return true;
        });
    }
}
