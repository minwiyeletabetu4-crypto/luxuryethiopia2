package com.example.luxuryethiopia; // Ensure this matches your package location!

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvDetailsName, tvDetailsPrice;
    private Button btnBookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Bind the text views and action button
        tvDetailsName = findViewById(R.id.tvDetailsName);
        tvDetailsPrice = findViewById(R.id.tvDetailsPrice);
        btnBookNow = findViewById(R.id.btnBookNow);

        // Extract data payload sent from DashboardActivity via Intents
        String destinationName = getIntent().getStringExtra("DESTINATION_NAME");
        String destinationPrice = getIntent().getStringExtra("DESTINATION_PRICE");

        // Dynamically assign text values onto screen views
        if (destinationName != null && destinationPrice != null) {
            tvDetailsName.setText(destinationName);
            tvDetailsPrice.setText("Exclusive Package Rate: " + destinationPrice);
        }

        // Action button listener setup
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailsActivity.this, "Booking Request Sent to Concierge Desk", Toast.LENGTH_LONG).show();
            }
        });
    }
}