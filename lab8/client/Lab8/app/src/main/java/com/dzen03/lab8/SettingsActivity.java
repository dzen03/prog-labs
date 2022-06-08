package com.dzen03.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        String IP = "";
        String Login = "";
        String Language = "";
        final Intent intent = getIntent();
        if (intent.hasExtra("IP"))
            IP = intent.getStringExtra("IP");
        if (intent.hasExtra("Login"))
            Login = intent.getStringExtra("Login");
        if (intent.hasExtra("Language"))
            Language = intent.getStringExtra("Language");


        final EditText etIP = findViewById(R.id.ip);
        etIP.setText(IP);
        final EditText etLogin = findViewById(R.id.login);
        etLogin.setText(Login);
        final EditText etPassword = findViewById(R.id.password);
        final Spinner spLanguage = findViewById(R.id.language);

        ArrayList<String> langs = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.languages)));

        spLanguage.setSelection(langs.indexOf(Language));

        Button save = findViewById(R.id.save);

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String ip = etIP.getText().toString();
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();
                if (!ip.equals("") && !login.equals("") && !password.equals(""))
                {
                    save.setEnabled(true);
                }
            }
        });

        spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ip = etIP.getText().toString();
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();
                String language = spLanguage.getSelectedItem().toString();
                if (!ip.equals("") && !login.equals("") && !password.equals("") && !language.equals(getString(R.string.choose_language)))
                {
                    save.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                String ip = etIP.getText().toString();
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();
                String language = spLanguage.getSelectedItem().toString();
                intent1.putExtra("IP", ip);
                intent1.putExtra("Login", login);
                intent1.putExtra("Password", password);
                intent1.putExtra("Language", language);
                if (ip.equals("") || login.equals("") || password.equals("") || language.equals(getString(R.string.choose_language)))
                {

                    Snackbar.make(findViewById(R.id.save), R.string.settingsIncorrect,
                            Snackbar.LENGTH_LONG).show();
                }
                else
                    startActivity(intent1);
            }
        });
    }
}