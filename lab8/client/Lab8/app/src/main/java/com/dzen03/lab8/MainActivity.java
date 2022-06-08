package com.dzen03.lab8;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.dzen03.core.Command;
import com.dzen03.core.CommandFactory;
import com.dzen03.core.CommandType;
import com.dzen03.run.ArgumentType;
import com.dzen03.run.ExecuteProcessor;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity
{
    public static final int PORT = 10000;

    public static final int BYTENUMPACKET = 4; // 2^(4*8) = 2^32 = int -- max count of packets, should be enough
    public static final int BUFFER_LENGTH = 65535 - 28 - 2 * BYTENUMPACKET; // max UDP packet size - ipv4 data - number of packet *2

    static ArrayList<ExecuteProcessor> works = new ArrayList<>(); // channel, address, data

    public static final ForkJoinPool pool = new ForkJoinPool();


    public static String path;
    public static final String MY_PREFS = "MY_PREFS";

    static SharedPreferences sharedPreferences;

    boolean haveIP()
    {
        return (sharedPreferences.contains("IP"));
    }
    static String getIP()
    {
        return sharedPreferences.getString("IP", "");
    }
    void saveIP(String IP)
    {
        sharedPreferences.edit().putString("IP", IP).apply();
    }


    boolean haveLogin()
    {
        return (sharedPreferences.contains("Login"));
    }
    static String getLogin()
    {
        return sharedPreferences.getString("Login", "");
    }
    void saveLogin(String login)
    {
        sharedPreferences.edit().putString("Login", login).apply();
    }


    boolean havePassword()
    {
        return (sharedPreferences.contains("Password"));
    }
    static String getPassword()
    {
        return sharedPreferences.getString("Password", "");
    }
    void savePassword(String password)
    {
        sharedPreferences.edit().putString("Password", getHash(password)).apply();
    }

    boolean haveLanguage()
    {
        return (sharedPreferences.contains("Language"));
    }
    static String getLanguage()
    {
        return sharedPreferences.getString("Language", "");
    }
    void saveLanguage(String language)
    {
        sharedPreferences.edit().putString("Language", language).apply();
    }

    public static String getHash(String input)
    {
        try
        {
            return org.apache.commons.codec.digest.DigestUtils.sha384Hex(input);
        }
        catch (Exception ex)
        {
            Log.e("ERROR", ex.getMessage());
        }
        return null;
    }

    public void setLocale(String lang)
    {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (getBaseContext().getResources().getConfiguration().locale.equals(locale))
            return;
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        Intent refresh = new Intent(this, MainActivity.class);
//        saveLanguage(lang);
        startActivity(refresh);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);  // TODO remove this

        sharedPreferences = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        super.onCreate(savedInstanceState);

        Intent intent0 = getIntent();
        saveLanguage(intent0.hasExtra("Language") ? intent0.getStringExtra("Language") : getLanguage());
        switch ((intent0.hasExtra("Language") ? intent0.getStringExtra("Language") : getLanguage()))
        {
            case "Русский":
                setLocale("ru");
                break;
            case "Slovenský":
                setLocale("sk");
                break;
            case "Shqipe":
                setLocale("sq");
                break;
            case "Canadian English":
                setLocale("en_CA");
                break;
        }
        setContentView(R.layout.activity_main);

        if ((!haveIP() || !haveLogin() || !havePassword() || !haveLanguage()) && !getIntent().hasExtra("IP"))
        {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            if (haveIP())
                intent.putExtra("IP", getIP());
            if (haveLogin())
                intent.putExtra("Login", getLogin());
            if (haveLanguage())
                intent.putExtra("Language", getLanguage());
            startActivity(intent);
            finish();
        }

        /*
        <item>Slovenský</item>
        <item>Shqipe</item>
        <item>Canadian English</item>
         */


        Spinner commands = findViewById(R.id.spinner);


        Button draw = findViewById(R.id.draw);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), DrawActivity.class);
                startActivity(intent1);
            }
        });

        Button run = findViewById(R.id.run);
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = commands.getSelectedItemPosition();
                String[] commandsArray = getResources().getStringArray(R.array.commands);

                CommandType commandType = CommandType.pick(commandsArray[position]);
                Command command = new CommandFactory().createCommand(commandType);


                Intent intent = null;
                if ((command.getArgs().length == 1 && command.getArgs()[0] == ArgumentType.CITY) ||
                        (command.getArgs().length == 2 && command.getArgs()[1] == ArgumentType.CITY)) {
                    intent = new Intent(getApplicationContext(), CityInputActivity.class);
                }
                else
                {
                    intent = new Intent(getApplicationContext(), ExecutorActivity.class);
                }
                EditText etArgument = findViewById(R.id.argument);

                if (command.getArgs().length >= 1 && (command.getArgs()[0] == ArgumentType.INT ||
                        command.getArgs()[0] == ArgumentType.STRING)) {
                    intent.putExtra("Argument", etArgument.getText().toString());
                }

                intent.putExtra("Command", commandsArray[position]);
                intent.putExtra("IP", getIP());
                intent.putExtra("Login", getLogin());
                intent.putExtra("Password", getPassword());

                startActivity(intent);
            }
        });


        commands.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                {
                    run.setVisibility(View.GONE);
                    return;
                }
                String[] commandsArray = getResources().getStringArray(R.array.commands);

                CommandType commandType = CommandType.pick(commandsArray[position]);
                Command command = new CommandFactory().createCommand(commandType);
//                Snackbar.make(findViewById(R.id.spinner), command.toString(), Snackbar.LENGTH_SHORT).show();

                EditText etArgument = findViewById(R.id.argument);

                if (command.getArgs().length >= 1)
                {
                    etArgument.setVisibility(View.VISIBLE);
                    if (command.getArgs()[0] == ArgumentType.INT)
                    {
                        etArgument.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
//                        etArgument.setHint("id");
                        etArgument.setHint(command.getArgs()[0].toString());
                    }
                    else if (command.getArgs()[0] == ArgumentType.STRING)
                    {
                        if (commandType == CommandType.EXECUTE_SCRIPT)
                            etArgument.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                        else
                            etArgument.setInputType(InputType.TYPE_CLASS_TEXT);
                        etArgument.setHint(command.getArgs()[0].toString());
                    }
                    else
                        etArgument.setVisibility(View.GONE);
                }
                else
                    etArgument.setVisibility(View.GONE);

                run.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Button settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                if (haveIP())
                    intent.putExtra("IP", getIP());
                if (haveLogin())
                    intent.putExtra("Login", getLogin());
                if (haveLanguage())
                    intent.putExtra("Language", getLanguage());

                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void onResume()
    {
        super.onResume();

        String IP = "";
        String Login = "";
        String Password = "";
        String Language = "";
        Intent intent1 = getIntent();
        if (intent1.hasExtra("IP"))
            IP = intent1.getStringExtra("IP");
        if (intent1.hasExtra("Login"))
            Login = intent1.getStringExtra("Login");
        if (intent1.hasExtra("Password"))
            Password = intent1.getStringExtra("Password");
        if (intent1.hasExtra("Language"))
            Language = intent1.getStringExtra("Language");

        if (!IP.equals(""))
            saveIP(IP);
        if (!Login.equals(""))
            saveLogin(Login);
        if (!Password.equals(""))
            savePassword(Password);
        if (!Language.equals(""))
            saveLanguage(Language);
    }
}