package com.lydia.petcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.lydia.petcare.R;
import com.lydia.petcare.models.Pet;
import com.lydia.petcare.session.SessionManager;
import java.util.List;

public class PetListActivity extends AppCompatActivity {
    private SessionManager session;
    private ListView petListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        session = new SessionManager(this);
        petListView = findViewById(R.id.petListView);

        List<Pet> pets = session.getList("pets", Pet.class);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                pets.stream().map(p -> p.getName() + " (" + p.getSpecies() + ")").toArray(String[]::new)
        );

        petListView.setAdapter(adapter);

        Button btnAddPet = findViewById(R.id.btnAddPet);
        btnAddPet.setOnClickListener(v -> startActivity(new Intent(this, PetFormActivity.class)));
    }
}
