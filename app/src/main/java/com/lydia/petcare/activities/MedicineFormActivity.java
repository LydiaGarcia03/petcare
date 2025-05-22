package com.lydia.petcare.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.lydia.petcare.R;
import com.lydia.petcare.models.Medicine;
import com.lydia.petcare.models.Pet;
import com.lydia.petcare.receivers.NotificationReceiver;
import com.lydia.petcare.session.SessionManager;
import java.util.Calendar;
import java.util.List;

public class MedicineFormActivity extends AppCompatActivity {
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_form);

        session = new SessionManager(this);

        EditText edtName = findViewById(R.id.edtMedicineName);
        TextView txtTime = findViewById(R.id.txtTime);
        Spinner petSpinner = findViewById(R.id.spinnerPet);
        Button btnSave = findViewById(R.id.btnSaveMedicine);

        List<Pet> pets = session.getList("pets", Pet.class);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                pets.stream().map(Pet::getName).toArray(String[]::new));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petSpinner.setAdapter(spinnerAdapter);

        txtTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            new TimePickerDialog(this, (view, hourOfDay, minute1) ->
                    txtTime.setText(String.format("%02d:%02d", hourOfDay, minute1)), hour, minute, true).show();
        });

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String time = txtTime.getText().toString();
            String petName = petSpinner.getSelectedItem().toString();

            Medicine medicine = new Medicine(name, time, petName);
            List<Medicine> list = session.getList("medicines", Medicine.class);
            list.add(medicine);
            session.saveList("medicines", list);

            // Agendar notificação
            String[] timeParts = time.split(":");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
            calendar.set(Calendar.SECOND, 0);

            Intent intent = new Intent(this, NotificationReceiver.class);
            intent.putExtra("title", "Hora do remédio para " + petName);
            intent.putExtra("message", name);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            finish();
        });
    }
}

