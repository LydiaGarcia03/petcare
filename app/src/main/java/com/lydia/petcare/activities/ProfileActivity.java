package com.lydia.petcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.lydia.petcare.R;
import com.lydia.petcare.session.SessionManager;

public class ProfileActivity extends AppCompatActivity {
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new SessionManager(this);

        TextView txtEmail = findViewById(R.id.txtEmail);
        Button btnLogout = findViewById(R.id.btnLogout);

        String loggedUser = session.getString("loggedUser");
        txtEmail.setText("Usuário logado: " + (loggedUser != null ? loggedUser : "Nenhum"));

        btnLogout.setOnClickListener(v -> {
            session.clear();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}

