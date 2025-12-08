package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.CostModel;

public class AddCost extends AppCompatActivity {

    EditText etName, etAmount, etDesc;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost);

        etName = findViewById(R.id.cost_name_input); // Sesuaikan ID
        etAmount = findViewById(R.id.cost_amount_input);
        etDesc = findViewById(R.id.cost_desc_input);
        btnSave = findViewById(R.id.save_cost_btn);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String amountStr = etAmount.getText().toString();
            String desc = etDesc.getText().toString();

            if(amountStr.isEmpty()) return;

            CostModel cost = new CostModel();
            cost.costName = name; // Asumsi public atau buat Setter di CostModel
            cost.amount = Double.parseDouble(amountStr);
            cost.description = desc;

            // PERBAIKAN: getDbInstance
            AppDatabase.getDbInstance(getApplicationContext()).costDao().insertCost(cost);
            finish();
        });
    }
}