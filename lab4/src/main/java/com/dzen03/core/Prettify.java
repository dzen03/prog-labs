package com.dzen03.core;

import jdk.nashorn.internal.runtime.regexp.RegExp;

import java.io.PrintStream;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class Prettify {

    private static class TrimZeros{ // TODO static local class
        public static String trim(String input)
        {

            StringBuilder result = new StringBuilder(input);
            int startIndex = 0;
            while (result.charAt(startIndex) == ' ')
                ++startIndex;
            result.delete(0, startIndex);

            return result.toString();
        }
    }


    public static void PrettifyAndPrint(String input) {
//        System.out.println(input);
        input = input.replace("  ", " ");




//        result = ;

        String res = TrimZeros.trim(input);

//        res = result.substring(0, 2).toUpperCase(new Locale("RU")) + result.substring(2);
        PrintStream out = null;
        try {

            class UpFirstLetter // TODO inner class
            {
                private final String lower = "àáâãäå¸æçèéêëìíîïğñòóôõö÷øùúûüışÿ";
                private final String upper = "ÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÙÚÛÜİŞß";

                public String up(String str)
                {
                    String first = str.substring(0, 1);
                    int id = lower.indexOf(first);
                    if (id != -1)
                        first = String.valueOf(upper.charAt(id));
//                    System.out.println(first);


                    return first + str.substring(1);
                }
            }



            out = new PrintStream(System.out, true, "cp1251");
//            out.println(res);
            UpFirstLetter upFirstLetter = new UpFirstLetter();
            out.println(upFirstLetter.up(res));
        }
        catch (Exception ex){

            System.out.println(ex);
        }
    }
}
