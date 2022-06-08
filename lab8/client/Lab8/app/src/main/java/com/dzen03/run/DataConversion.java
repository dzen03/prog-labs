package com.dzen03.run;


import static com.dzen03.lab8.MainActivity.*;

import java.util.ArrayList;
import java.util.List;

public class DataConversion {
    public static byte[][] dataToArrays(byte[] data)
    {
        int count = (int) Math.ceil(data.length * 1.0 / BUFFER_LENGTH) + 1;
        ArrayList<Byte>[] tmp = new ArrayList[count];

        for (int offset = 0, length = data.length, ind = 0; ind < count; offset += BUFFER_LENGTH - 2 * BYTENUMPACKET,
                length -= (BUFFER_LENGTH - 2 * BYTENUMPACKET), ind += 1)
        {
            tmp[ind] = new ArrayList<>(List.of(new Byte[]{
                    (byte) ((ind >> 24) & 0xff),
                    (byte) ((ind >> 16) & 0xff),
                    (byte) ((ind >> 8) & 0xff),
                    (byte) ((ind >> 0) & 0xff),

                    (byte) ((count >> 24) & 0xff),
                    (byte) ((count >> 16) & 0xff),
                    (byte) ((count >> 8) & 0xff),
                    (byte) ((count >> 0) & 0xff)
            }));

            for (int i = 0; i < Math.min(length, BUFFER_LENGTH); ++i)
            {
                tmp[ind].add(data[offset + i]);
            }
        }
        byte[][] res = new byte[tmp.length][];
        for (int i = 0; i < tmp.length; ++i)
        {
            res[i] = new byte[tmp[i].size()];
            for (int j = 0; j < tmp[i].size(); ++j)
                res[i][j] = tmp[i].get(j);
        }


        return res;
    }

    public static Object[] byteArrToData(byte[] in) // Tuple of int packet_num, int packet_cnt and ArrayList<Byte> value
    {
        byte[] tmp = in.clone();
        // NOTE: type cast not necessary for int
        int index = (0xff & in[0]) << 24 |
                (0xff & in[1]) << 16 |
                (0xff & in[2]) << 8 |
                (0xff & in[3]) << 0;
        // NOTE: type cast not necessary for int
        int cnt = (0xff & in[4]) << 24 |
                (0xff & in[5]) << 16 |
                (0xff & in[6]) << 8 |
                (0xff & in[7]) << 0;
        ArrayList<Byte> data = new ArrayList<>();
        for (int i = 8; i < in.length; ++i)
            data.add(in[i]);
        return new Object[]{index, cnt, data};
    }
}
