package com.dzen03.core;

import jdk.nashorn.internal.runtime.regexp.RegExp;

import java.io.PrintStream;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class Prettify {
    public static void PrettifyAndPrint(String input) {
//        System.out.println(input);
        input = input.replace("  ", " ");

        StringBuilder result = new StringBuilder(input);

        int startIndex = 0;
        while (result.charAt(startIndex) == ' ')
            ++startIndex;
        result.delete(0, startIndex);

//        result = ;

        String res = result.toString();

//        res = result.substring(0, 2).toUpperCase(new Locale("RU")) + result.substring(2);
        PrintStream out = null;
        try {
            out = new PrintStream(System.out, true, "cp1251");
            out.println(res);
        }
        catch (Exception ex){

            System.out.println(ex);
        }


    }
}
