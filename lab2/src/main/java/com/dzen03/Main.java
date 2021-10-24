package com.dzen03;

import java.lang.*;
import java.util.Random;

import com.dzen03.Pokemons.*;
import ru.ifmo.se.pokemon.*;
//import
//import ru.ifmo.se.pokemon.*;


public class Main{

    private static Pokemon getRandomPokemon(Pokemon[] arr)
    {
        Random generator = new Random();
        int pickedNumber = generator.nextInt(arr.length);
        Pokemon pickedPokemon = arr[pickedNumber];
        while (pickedPokemon == null)
        {
            pickedNumber = generator.nextInt(arr.length);
            pickedPokemon = arr[pickedNumber];
        }
        arr[pickedNumber] = null;
        return pickedPokemon;
    }

    public static void main (String[] args) {

        Battle b = new Battle();

        Zangoose zangoose = new Zangoose("Alpha", 1);
        Snubbull snubbull = new Snubbull("Bravo", 1);
        Granbull granbull = new Granbull("Charlie", 2);
        Rhyhorn rhyhorn = new Rhyhorn("Delta", 1);
        Rhydon rhydon = new Rhydon("Echo", 2);
        Rhyperior rhyperior = new Rhyperior("Foxtrot", 3);

        Pokemon[] a = {zangoose, snubbull, granbull, rhyhorn, rhydon, rhyperior};
        Pokemon[] ally = {getRandomPokemon(a), getRandomPokemon(a), getRandomPokemon(a)},
                  foe = {getRandomPokemon(a), getRandomPokemon(a), getRandomPokemon(a)};

        System.out.println("Team ally:");
        for (Pokemon pokemon : ally)
        {
            System.out.println(pokemon);
            b.addAlly(pokemon);
        }
        System.out.println("\nTeam foe:");
        for (Pokemon pokemon : foe)
        {
            System.out.println(pokemon);
            b.addFoe(pokemon);
        }
        System.out.println();

        b.go();
    }


}

//  ID 31184567