package com.dzen03.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.dzen03.core.City;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class ShowActivity extends AppCompatActivity {

    TableLayout tableLayout;

    final String UP = "▲";
    final String DOWN = "▼";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        tableLayout = findViewById(R.id.table);

        Intent intent = getIntent();




        final String header = ",city_id,area,car_code,name,meters_above_sea,population,x_coord,y_coord," +
                "government,governor,standard_of_living,user_id";


        String input = getIntent().getStringExtra("Data").split("\r")[0].replace("'", "")
                        .replace("null", "");
        ArrayList<String[]> data = new ArrayList<>();

        ArrayList<TextView> row = new ArrayList<>();
        for (int i = 0; i < header.split(",").length; ++i)
        {
            EditText editText = new EditText(this);
            editText.setPadding(5,5,1,5);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            editText.setLayoutParams(params);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
//                    redraw(input, finalI, s.toString());
                    ArrayList<String> filter = new ArrayList<>();
                    for (TextView tv : row)
                    {
                        filter.add(tv.getText().toString());
                    }
                    redraw(input, filter);
                }
            });
            row.add(editText);
        }


        ArrayList<TextView> tvRow = new ArrayList<>();
        int ind = 0;
        for (String el : header.split(","))
        {
            TextView cell = new TextView(this);
            cell.setText(el);
            cell.setPadding(5,5,1,5);
            int finalInd = ind;
            if (ind != 0)
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int l = 0; l < tvRow.size(); ++l)
                        {
                            if (l == finalInd)
                                continue;
                            TextView el = tvRow.get(l);
                            el.setText(el.getText().toString().replace(UP, "").replace(DOWN, ""));
                        }
                        Boolean ascending = null;
                        String text = cell.getText().toString();
                        if (text.length() == 0 || !(UP + DOWN).contains(String.valueOf(text.charAt(text.length() - 1))))
                        {
                            cell.setText(text + DOWN);
                            ascending = false;
                        }
                        else if (text.endsWith("▲"))
                        {
                            cell.setText(text.subSequence(0, Math.max(0, text.length() - 1)));
                            ascending = null;
                        }
                        else
                        {
                            cell.setText(text.subSequence(0, Math.max(0, text.length() - 1)) + UP);
                            ascending = true;
                        }

                        redraw(input, finalInd - 1, ascending);
                    }
                });
            tvRow.add(cell);
            ++ind;
        }

        TableRow headerRow = new TableRow(this);
        for (TextView textView : tvRow)
        {
            headerRow.addView(textView);
        }

        TableRow tableRow0 = new TableRow(this);
        for (TextView textView : row)
        {
            tableRow0.addView(textView);
        }
        tableLayout.addView(tableRow0);
        tableLayout.addView(headerRow);

        redraw(input);


    }

    ArrayList<String> filter = new ArrayList<>();
    int index = 0;
    Boolean ascending = null;

    public void redraw(String input)
    {
        redraw(input, new ArrayList<>(), 1, null);
    }
    public void redraw(String input, int index, Boolean ascending)
    {
        this.index = index;
        this.ascending = ascending;
        redraw(input, filter, index, ascending);
    }
    public void redraw(String input, ArrayList<String> filter)
    {
        this.filter = filter;
        redraw(input, filter, index, ascending);
    }

    public void redraw(String input, ArrayList<String> filter, int index, Boolean ascending)
    {
        tableLayout.removeViews(2, Math.max(0, tableLayout.getChildCount() - 2));
        ArrayList<String> data = new ArrayList<>(Arrays.asList(input.split("\n")));
        data.sort(new Comparator<>() {
            @Override
            public int compare(String o1, String o2) {
                if (ascending == null)
                    return 0;
                City city1 = new City(o1.split(","));
                City city2 = new City(o2.split(","));
                String[] row1 = city1.dumpForDB().replace("null", "").replace("null", "").split(",");
                String[] row2 = city2.dumpForDB().replace("null", "").split(",");
                if (row1[index].startsWith("'"))
                    if (ascending)
                        return row1[index].compareTo(row2[index]);
                    else
                        return row2[index].compareTo(row1[index]);
                else
                    return (ascending ? 1 : -1) * (Double.compare(Double.parseDouble(row1[index]), Double.parseDouble((row2[index]))));
            }
        });
        int i = 1;
        for (String row : data) {
            ArrayList<TextView> tvRow = new ArrayList<>();
            if (i != 0)
            {
                TextView ind = new TextView(this);
                ind.setText(String.valueOf(i));
                ind.setPadding(5,5,1,5);
                ind.setTextColor(getColor(R.color.teal_200));
                int finalI = i;
                ind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(v, getString(R.string.edit_this, ((TextView) v).getText().toString()), Snackbar.LENGTH_LONG).setAction(R.string.yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View v1) {
                                Intent intent = new Intent(getApplicationContext(), CityInputActivity.class);
                                intent.putExtra("Data", data.get(finalI - 1));
                                intent.putExtra("Command", "update");
                                startActivity(intent);
                            }
                        }).show();
                    }
                });
                tvRow.add(ind);
            }
            for (String el : row.split(","))
            {
                TextView cell = new TextView(this);
                cell.setText(el);
                if (el.equals(""))
                    cell.setBackgroundColor(getColor(R.color.purple_200));
                cell.setPadding(5,5,1,5);
                tvRow.add(cell);
            }
            tvRow.get(tvRow.size() - 1).setTextColor(DrawActivity.getColorForId(
                    Integer.parseInt(tvRow.get(tvRow.size() - 1).getText().toString())));

            TableRow tableRow = new TableRow(this);
            for (TextView textView : tvRow)
            {
                tableRow.addView(textView);
            }
            boolean add = true;
            for (int k = 0; k < filter.size(); ++k)
            {
                add &= tvRow.get(k).getText().toString().contains(filter.get(k));
            }
            if (add || i == 0)
                tableLayout.addView(tableRow);
            ++i;
        }
    }
}