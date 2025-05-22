package com.lydia.petcare.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.lydia.petcare.R;
import com.lydia.petcare.models.Pet;
import com.lydia.petcare.session.SessionManager;
import java.util.List;

public class PetFormActivity extends AppCompatActivity {
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_form);

        session = new SessionManager(this);

        EditText edtName = findViewById(R.id.edtPetName);
        EditText edtSpecies = findViewById(R.id.edtPetSpecies);
        EditText edtBreed = findViewById(R.id.edtPetBreed);
        Button btnSave = findViewById(R.id.btnSavePet);

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String species = edtSpecies.getText().toString();
            String breed = edtBreed.getText().toString();

            Pet pet = new Pet(name, species, breed);
            List<Pet> pets = session.getList("pets", Pet.class);
            pets.add(pet);
            session.saveList("pets", pets);
            finish();
        });
    }
}

