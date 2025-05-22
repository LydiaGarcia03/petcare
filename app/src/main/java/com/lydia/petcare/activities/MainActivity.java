package com.lydia.petcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.lydia.petcare.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPets = findViewById(R.id.btnPets);
        Button btnMedicines = findViewById(R.id.btnMedicines);
        Button btnCalendar = findViewById(R.id.btnCalendar);
        Button btnProfile = findViewById(R.id.btnProfile);

        btnPets.setOnClickListener(v -> startActivity(new Intent(this, PetListActivity.class)));
        btnMedicines.setOnClickListener(v -> startActivity(new Intent(this, MedicineFormActivity.class)));
        btnCalendar.setOnClickListener(v -> startActivity(new Intent(this, EventCalendarActivity.class)));
        btnProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
    }
}
