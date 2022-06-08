package com.dzen03.lab8;

import static com.dzen03.lab8.MainActivity.BUFFER_LENGTH;
import static com.dzen03.lab8.MainActivity.PORT;
import static com.dzen03.lab8.MainActivity.getIP;
import static com.dzen03.lab8.MainActivity.getLogin;
import static com.dzen03.lab8.MainActivity.getPassword;
import static com.dzen03.run.DataConversion.byteArrToData;
import static com.dzen03.run.DataConversion.dataToArrays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidplot.xy.BubbleFormatter;
import com.androidplot.xy.BubbleSeries;
import com.androidplot.xy.CustomPanZoom;
import com.androidplot.xy.XYPlot;
import com.dzen03.core.City;
import com.dzen03.core.Command;
import com.dzen03.core.CommandFactory;
import com.dzen03.core.CommandType;
import com.dzen03.core.Coordinates;
import com.dzen03.run.CommandClass;
import com.dzen03.run.Data;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class DrawActivity extends AppCompatActivity {

    public static final double ACCURACY = 0.05;

    private static ArrayList<City> cities = null;

    private static Context context;

    private static XYPlot plot;

    private boolean running;
    private boolean pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        context = getApplicationContext();

        plot = findViewById(R.id.plot);
        CustomPanZoom.attach(plot);

        running = true;
        Thread updating = new Thread()
        {
            @Override
            public void run() {
                while (running)
                {
                    try
                    {
                        if (!pause)
                        {
                            AsyncExecutor executor = new AsyncExecutor();
                            executor.execute();
                        }

                        Thread.sleep(10000);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };

        updating.start();

    }
    class AsyncExecutor extends AsyncTask<Object, String, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pause = true;
        }

        @Override
        protected String doInBackground(Object... objects) {
            String IP = getIP();
            Command command = new CommandFactory().createCommand(CommandType.SHOW);
            CommandClass commandClass = new CommandClass(command, new String[]{}, getLogin(), getPassword());
            DatagramSocket clientSocket = null;
            InetSocketAddress serverAddr = null;
            try {
                serverAddr = new InetSocketAddress(InetAddress.getByName(IP), PORT);
                clientSocket = new DatagramSocket();
            } catch (UnknownHostException | SocketException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = null;
            byte[] outByte = null;
            try {
                out = new ObjectOutputStream(bos);
                out.writeObject(commandClass);
                outByte = bos.toByteArray();
                out.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[][] data = dataToArrays(outByte);

            for (byte[] i : data)
            {
                DatagramPacket outPacket = new DatagramPacket(i, i.length, serverAddr);

                try {
                    clientSocket.send(outPacket); // TODO send it in new thread!!!(or async)
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            HashMap<Integer, ArrayList<Byte>> bytes = new HashMap<>();

            int count = Integer.MAX_VALUE;
            for (int i = 0; i < count; ++i)
            {
                byte[] inByte = new byte[BUFFER_LENGTH];
                DatagramPacket inPacket = new DatagramPacket(inByte, inByte.length);
                try {
                    clientSocket.receive(inPacket);  // TODO receive in new thread!!!(or async)
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Object[] tmp = byteArrToData(inByte);
                int ind = (int) tmp[0];
                count = (int) tmp[1];
                ArrayList<Byte> dt = (ArrayList<Byte>) tmp[2];

                bytes.put(ind, dt);
            }
            ArrayList<Byte> value = new ArrayList<>();
            for (int i = 0; i < count; ++i)
            {
                value.addAll(bytes.get(i));
            }

            byte[] result = new byte[value.size()];
            for (int i = 0; i < value.size(); ++i)
                result[i] = value.get(i);


            String output = new String(result);

            clientSocket.close();

            return output;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

//            ArrayList<Number> x = new ArrayList<>(), y = new ArrayList<>(), z = new ArrayList<>();
            HashMap<Integer, ArrayList<Number>[]> dictionary = new HashMap<>();

            ArrayList<City> data = new ArrayList<>();

            for (String row : str.replace("'", "").split("\r")[0].split("\n"))
            {
                City city = new City(row.split(","));
                data.add(city);
                if (dictionary.containsKey(city.getUserId()))
                {
                    dictionary.get(city.getUserId())[0].add(city.getCoordinates().getX());
                    dictionary.get(city.getUserId())[1].add(city.getCoordinates().getY());
                    dictionary.get(city.getUserId())[2].add(city.getPopulation());
                }
                else
                {
                    ArrayList<Number>[] tmp = new ArrayList[]{new ArrayList<>(), new ArrayList<>(), new ArrayList<>()};

                    tmp[0].add(city.getCoordinates().getX());
                    tmp[1].add(city.getCoordinates().getY());
                    tmp[2].add(city.getPopulation());

                    dictionary.put(city.getUserId(), tmp);
                }
            }

            plot.clear();

            for (int key : dictionary.keySet())
            {
                ArrayList<Number>[] el = dictionary.get(key);

                BubbleSeries series = new BubbleSeries(el[0], el[1], el[2], String.format("user_id=%d", key));
                plot.addSeries(series, new BubbleFormatter(getColorForId(key), getColorForId(key)));
            }

            plot.redraw();

            cities = data;

            pause = false;
        }
    }

    public static int getColorForId(int id)
    {
        return Color.rgb((float) Math.abs(Math.sin(id)), (float) Math.abs(Math.cos(id)),
                (float) Math.abs(Math.sin(id) * Math.cos(id)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        running = false;
    }

    public static class AsyncPick extends AsyncTask<Double, Void, City>
    {


        @Override
        protected City doInBackground(Double... numbers)
        {
            double x = numbers[0];
            double y = numbers[1];
            double x_dist = numbers[2];
            double y_dist = numbers[3];

            ArrayList<City> data = cities;
//            ArrayList<City> picked = new ArrayList<>();
            TreeMap<Double, City> picked = new TreeMap<>();
            for (City city : data)
            {
                Coordinates coordinates = city.getCoordinates();
                if (coordinates.getX() < x + x_dist * ACCURACY && coordinates.getX() > x - x_dist * ACCURACY &&
                    coordinates.getY() < y + y_dist * ACCURACY && coordinates.getY() > y - y_dist * ACCURACY)
                {
                    picked.put(Math.hypot(coordinates.getX() - x, coordinates.getY() - y), city);
                }
            }

            if (picked.isEmpty())
                return null;
            else
                return picked.firstEntry().getValue();

        }


        @Override
        protected void onPostExecute(City city) {
            super.onPostExecute(city);
            if (city == null)
                return;
            Snackbar snackbar = Snackbar.make(plot, city.toString(), Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setMaxLines(20);
            snackbar.setAction(R.string.edit, new View.OnClickListener() {
                @Override
                public void onClick(View v1) {
                    Intent intent = new Intent(context, CityInputActivity.class);
                    intent.putExtra("Data", city.dumpForDB());
                    intent.putExtra("Command", "update");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }).show();
        }
    }

}