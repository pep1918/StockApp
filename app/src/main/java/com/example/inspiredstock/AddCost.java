package com.example.inspiredstock;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Database.CostDao; // Buat DAO ini di folder Database jika belum
import com.example.inspiredstock.Models.CostModel;

public class AddCost extends AppCompatActivity {
    EditText name, amount, desc;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost);

        name = findViewById(R.id.cost_name_input);
        amount = findViewById(R.id.cost_amount_input);
        desc = findViewById(R.id.cost_desc_input);
        save = findViewById(R.id.save_cost_btn);

        save.setOnClickListener(v -> {
            AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
            // Perlu buat CostDao dulu di file terpisah atau gabung, disini asumsi pakai logic DAO umum
            CostModel cost = new CostModel(name.getText().toString(), amount.getText().toString(), desc.getText().toString());
            db.costDao().insertCost(cost); // Pastikan buat method ini di AppDatabase & CostDao
            Toast.makeText(this, "Cost Saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}