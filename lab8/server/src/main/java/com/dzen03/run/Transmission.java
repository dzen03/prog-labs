package com.dzen03.run;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Transmission
{
    public ConcurrentHashMap<Integer, ArrayList<Byte>> bytes = new ConcurrentHashMap<>();

    volatile int[] count = {Integer.MAX_VALUE};

    Transmission(Integer cnt, ArrayList<Byte> data)
    {
        bytes.put(cnt, data);
    }

    @Override
    public String toString()
    {
        return bytes.toString();
    }
}
