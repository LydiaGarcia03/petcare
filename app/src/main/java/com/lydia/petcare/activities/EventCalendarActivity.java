package com.lydia.petcare.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.lydia.petcare.R;
import com.lydia.petcare.models.Event;
import com.lydia.petcare.models.Mood;
import com.lydia.petcare.models.Pet;
import com.lydia.petcare.session.SessionManager;
import java.util.*;

public class EventCalendarActivity extends AppCompatActivity {
    private SessionManager session;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calendar);

        session = new SessionManager(this);
        TextView txtDate = findViewById(R.id.txtSelectedDate);
        Button btnPickDate = findViewById(R.id.btnPickDate);
        Spinner moodSpinner = findViewById(R.id.spinnerMood);
        Spinner petSpinner = findViewById(R.id.spinnerPet);
        EditText edtEventType = findViewById(R.id.edtEventType);
        Button btnSaveMood = findViewById(R.id.btnSaveMood);
        Button btnSaveEvent = findViewById(R.id.btnSaveEvent);
        ListView listView = findViewById(R.id.listEvents);

        List<Pet> pets = session.getList("pets", Pet.class);
        ArrayAdapter<String> petAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                pets.stream().map(Pet::getName).toArray(String[]::new));
        petSpinner.setAdapter(petAdapter);

        ArrayAdapter<String> moodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"Feliz", "Triste", "Agressivo", "Apatético"});
        moodSpinner.setAdapter(moodAdapter);

        btnPickDate.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                txtDate.setText("Data selecionada: " + selectedDate);
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnSaveMood.setOnClickListener(v -> {
            String petName = petSpinner.getSelectedItem().toString();
            String mood = moodSpinner.getSelectedItem().toString();
            Mood m = new Mood(selectedDate, mood, petName);
            List<Mood> moods = session.getList("moods", Mood.class);
            moods.add(m);
            session.saveList("moods", moods);
            Toast.makeText(this, "Humor salvo", Toast.LENGTH_SHORT).show();
        });

        btnSaveEvent.setOnClickListener(v -> {
            String petName = petSpinner.getSelectedItem().toString();
            String type = edtEventType.getText().toString();
            Event e = new Event(selectedDate, type, petName);
            List<Event> events = session.getList("events", Event.class);
            events.add(e);
            session.saveList("events", events);
            Toast.makeText(this, "Evento salvo", Toast.LENGTH_SHORT).show();
        });

        btnPickDate.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                txtDate.setText("Data selecionada: " + selectedDate);
                atualizarListaEventos(selectedDate); // <- CHAMAR AQUI
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnSaveMood.setOnClickListener(v -> {
            // ... salvar o humor ...
            atualizarListaEventos(selectedDate); // <- CHAMAR AQUI
        });

        btnSaveEvent.setOnClickListener(v -> {
            // ... salvar o evento ...
            atualizarListaEventos(selectedDate); // <- CHAMAR AQUI
        });
    }

    private void atualizarListaEventos(String dataSelecionada) {
        List<Event> eventos = session.getList("events", Event.class);
        List<Mood> humores = session.getList("moods", Mood.class);

        List<String> exibicao = new ArrayList<>();
        for (Event e : eventos) {
            if (e.getDate().equals(dataSelecionada)) {
                exibicao.add("Evento: " + e.getType() + " - Pet: " + e.getPetName());
            }
        }
        for (Mood m : humores) {
            if (m.getDate().equals(dataSelecionada)) {
                exibicao.add("Humor: " + m.getMood() + " - Pet: " + m.getPetName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exibicao);
        ListView listView = findViewById(R.id.listEvents);
        listView.setAdapter(adapter);
    }

}
