package com.example.baggageapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class CalculatorActivity extends AppCompatActivity {

    // Declare UI elements
    private RadioGroup rgTicketClass;
    private RadioButton rbEconomy, rbBusiness;
    private EditText etWeight, etRate;
    private MaterialCardView cardCalculate, cardClear;
    private TextView tvExcessWeight, tvExcessCharge, tvTotalPayable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        // Bind toolbar and set as action bar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bind UI components with XML IDs
        rgTicketClass = findViewById(R.id.rgTicketClass);
        rbEconomy = findViewById(R.id.rbEconomy);
        rbBusiness = findViewById(R.id.rbBusiness);
        etWeight = findViewById(R.id.etWeight);
        etRate = findViewById(R.id.etRate);
        cardCalculate = findViewById(R.id.cardCalculate);
        cardClear = findViewById(R.id.cardClear);
        tvExcessWeight = findViewById(R.id.tvExcessWeight);
        tvExcessCharge = findViewById(R.id.tvExcessCharge);
        tvTotalPayable = findViewById(R.id.tvTotalPayable);

        // 1. Calculate Card Click Logic
        cardCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateFee();
            }
        });

        // 2. Clear Card Click Logic
        cardClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void calculateFee() {
        String weightInput = etWeight.getText().toString().trim();
        String rateInput = etRate.getText().toString().trim();

        // Input Validation
        if (weightInput.isEmpty()) {
            etWeight.setError("Please enter baggage weight");
            etWeight.requestFocus();
            return;
        }

        if (rateInput.isEmpty()) {
            etRate.setError("Please enter rate per kg");
            etRate.requestFocus();
            return;
        }

        double baggageWeight = Double.parseDouble(weightInput);
        double ratePerKg = Double.parseDouble(rateInput);

        // Allowance limit: Economy = 20kg, Business = 40kg
        double allowance = rbEconomy.isChecked() ? 20.0 : 40.0;

        // Calculate excess baggage weight (never negative)
        double excessWeight = baggageWeight - allowance;
        if (excessWeight < 0) {
            excessWeight = 0.0;
        }

        // Calculate charges
        double excessCharge = excessWeight * ratePerKg;
        double totalPayable = excessCharge + (excessCharge * 0.08); // 8% Service Tax

        // Display results formatted to 2 decimal places
        tvExcessWeight.setText(String.format("Excess Baggage Weight: %.2f kg", excessWeight));
        tvExcessCharge.setText(String.format("Excess Baggage Charge: RM %.2f", excessCharge));
        tvTotalPayable.setText(String.format("Total Amount Payable (incl. 8%% tax): RM %.2f", totalPayable));
    }

    private void clearFields() {
        etWeight.setText("");
        etRate.setText("");
        rbEconomy.setChecked(true);
        tvExcessWeight.setText("Excess Baggage Weight: 0.00 kg");
        tvExcessCharge.setText("Excess Baggage Charge: RM 0.00");
        tvTotalPayable.setText("Total Amount Payable (incl. 8% tax): RM 0.00");
        etWeight.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            Intent intent = new Intent(CalculatorActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(CalculatorActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBody = "Check out the Excess Baggage Fee Calculator app on GitHub: https://github.com/yourusername/namaprojek";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Baggage Fee Calculator");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}