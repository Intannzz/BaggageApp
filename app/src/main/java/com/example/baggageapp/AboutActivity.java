package com.example.baggageapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class AboutActivity extends AppCompatActivity {

    private MaterialCardView cardGithub;
    private TextView tvGithubUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // 1. Setup Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        // 2. Initialize Views
        cardGithub = findViewById(R.id.cardGithub);
        tvGithubUrl = findViewById(R.id.tvGithubUrl);

        // 3. GitHub Link Click Listener
        if (cardGithub != null) {
            cardGithub.setOnClickListener(v -> {
                String url = tvGithubUrl.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            });
        }
    }

    // Handle Toolbar Back Arrow Click
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}