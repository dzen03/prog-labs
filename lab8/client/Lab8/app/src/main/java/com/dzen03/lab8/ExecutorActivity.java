package com.dzen03.lab8;

import static com.dzen03.lab8.MainActivity.*;
import static com.dzen03.run.DataConversion.byteArrToData;
import static com.dzen03.run.DataConversion.dataToArrays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.dzen03.core.Command;
import com.dzen03.core.CommandFactory;
import com.dzen03.core.CommandType;
import com.dzen03.core.ShutdownException;
import com.dzen03.run.CommandClass;
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
import java.util.List;

public class ExecutorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executor);

        TextView textView = findViewById(R.id.command);

        Intent intent = getIntent();
        String commandString = intent.getStringExtra("Command");
        String IP = getIP();
        String login = getLogin();
        String password = getPassword();
        String argument = "";
        if (intent.hasExtra("Argument"))
            argument = intent.getStringExtra("Argument");
        textView.setText(String.format("Command: %s\nWith args: %s", commandString, argument));

        String[] data = null;
        if (intent.hasExtra("Data"))
            data = intent.getStringArrayExtra("Data");


        CommandType commandType = CommandType.pick(commandString);
        Command command = new CommandFactory().createCommand(commandType);

        String[] args = null;
        if (data == null)
             args = command.preExecute(new String[]{commandString, argument});
        else
            args = data;

        CommandClass commandClass = new CommandClass(command, args, login, password);


        AsyncExecutor executor = new AsyncExecutor();
        executor.execute(IP, commandClass, commandType);



    }

    class AsyncExecutor extends AsyncTask<Object, String, Object[]>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Snackbar.make(findViewById(R.id.response), "Wait, executing command",
                    Snackbar.LENGTH_LONG).show();
        }

        @Override
        protected Object[] doInBackground(Object... objects) {
            String IP = (String) objects[0];
            CommandClass commandClass = (CommandClass) objects[1];
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
            List<Byte> value = new ArrayList<>();
            for (int i = 0; i < count; ++i)
            {
                value.addAll(bytes.get(i));
            }

            byte[] result = new byte[value.size()];
            for (int i = 0; i < value.size(); ++i)
                result[i] = value.get(i);


            String output = new String(result);

            clientSocket.close();

            return new Object[]{output, objects[2], IP};
        }

        @Override
        protected void onPostExecute(Object[] objects) {
            super.onPostExecute(objects);
            String output = (String) objects[0];
            CommandType commandType = (CommandType) objects[1];

            TextView response = findViewById(R.id.response);
            response.setMovementMethod(new ScrollingMovementMethod());

            if (commandType == CommandType.SHOW || commandType == CommandType.FILTER_STARTS_WITH_NAME
                    || commandType == CommandType.MIN_BY_METERS_ABOVE_SEA_LEVEL)
            {
                Intent intent1 = new Intent(getApplicationContext(), ShowActivity.class);
                intent1.putExtra("Data", output);
                startActivity(intent1);
            }

            response.setText(output);
        }
    }
}