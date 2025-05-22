package com.lydia.petcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.lydia.petcare.R;
import com.lydia.petcare.models.User;
import com.lydia.petcare.session.SessionManager;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = new SessionManager(this);

        EditText edtEmail = findViewById(R.id.edtRegisterEmail);
        EditText edtPassword = findViewById(R.id.edtRegisterPassword);
        Button btnCreate = findViewById(R.id.btnCreateAccount);

        btnCreate.setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            List<User> users = session.getList("users", User.class);
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    Toast.makeText(this, "E-mail já cadastrado", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            users.add(new User(email, password));
            session.saveList("users", users);
            session.saveString("loggedUser", email);
            Toast.makeText(this, "Cadastro realizado", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
