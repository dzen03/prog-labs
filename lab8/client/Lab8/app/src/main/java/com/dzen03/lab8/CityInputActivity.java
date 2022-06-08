package com.dzen03.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dzen03.core.City;
import com.dzen03.core.Coordinates;
import com.dzen03.core.Government;
import com.dzen03.core.Human;
import com.dzen03.core.StandardOfLiving;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class CityInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_input);

        EditText city_id = findViewById(R.id.city_id);
        EditText area = findViewById(R.id.area);
        EditText car_code = findViewById(R.id.car_code);
        EditText name = findViewById(R.id.name);
        EditText meters_above_sea = findViewById(R.id.meters_above_sea);
        EditText population = findViewById(R.id.population);
        EditText x_coord = findViewById(R.id.x_coord);
        EditText y_coord = findViewById(R.id.y_coord);
        EditText government = findViewById(R.id.government);
        EditText governor = findViewById(R.id.governor);
        EditText standard_of_living = findViewById(R.id.standard_of_living);
        EditText user_id = findViewById(R.id.user_id);


        Intent intent = getIntent();
        if (intent.hasExtra("Data"))
        {
            String[] data = intent.getStringExtra("Data").replace("'", "")
                    .replace("null", "").split(",");
            city_id.setText(data[0]);
            area.setText(data[1]);
            car_code.setText(data[2]);
            name.setText(data[3]);
            meters_above_sea.setText(data[4]);
            population.setText(data[5]);
            x_coord.setText(data[6]);
            y_coord.setText(data[7]);
            government.setText(data[8]);
            governor.setText(data[9]);
            standard_of_living.setText(data[10]);
            user_id.setText(data[11]);
        }
        if (intent.hasExtra("Argument"))
        {
            city_id.setText(intent.getStringExtra("Argument"));
        }

        Button remove = findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ExecutorActivity.class);
                intent1.putExtra("Command", "remove_by_id");
                intent1.putExtra("Argument", city_id.getText().toString());

                startActivity(intent1);
            }
        });

        if (!city_id.getText().toString().equals(""))
        {
            remove.setVisibility(View.VISIBLE);
        }

        Button done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                City city = new City();
                try
                {
                    if (!String.valueOf(city_id.getText()).equals(""))
                        city.setId(Long.parseLong(String.valueOf(city_id.getText())));
                    city.setArea(Float.parseFloat(String.valueOf(area.getText())));
                    city.setCarCode(Integer.parseInt(String.valueOf(car_code.getText())));
                    city.setName(String.valueOf(name.getText()));
                    city.setMetersAboveSeaLevel(Integer.parseInt(String.valueOf(meters_above_sea.getText())));
                    city.setPopulation(Integer.parseInt(String.valueOf(population.getText())));
                    Coordinates coordinates = new Coordinates();
                    coordinates.setX(Integer.parseInt(String.valueOf(x_coord.getText())));
                    coordinates.setY(Float.parseFloat(String.valueOf(y_coord.getText())));
                    city.setCoordinates(coordinates);
                    city.setGovernment(Government.pick(String.valueOf(government.getText()).toUpperCase()));
                    city.setGovernor(new Human(String.valueOf(governor.getText())));
                    city.setStandardOfLiving(StandardOfLiving.pick(String.valueOf(standard_of_living.getText()).toUpperCase()));
                    if (!String.valueOf(user_id.getText()).equals(""))
                        city.setUserId(Integer.parseInt(String.valueOf(user_id.getText())));

                    Intent intent1 = new Intent(getApplicationContext(), ExecutorActivity.class);
                    intent1.putExtra("Command", intent.getStringExtra("Command"));
                    intent1.putExtra("Data", city.dumpForDB().replace("'", "").split(","));

                    startActivity(intent1);
                }
                catch (Exception ex)
                {
                    Snackbar.make(v, ex.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                }


            }
        });
    }
}