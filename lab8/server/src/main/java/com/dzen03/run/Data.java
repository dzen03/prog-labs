package com.dzen03.run;

import com.dzen03.core.City;
import com.dzen03.core.Database;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;

public class Data
{
    private static final Vector<City> citiesVector = new Vector<>();
    public static java.time.ZonedDateTime creationDate = java.time.ZonedDateTime.now();

    public static void add(City city) throws Exception
    {
        Database.add(city);

        Database.refresh(citiesVector);
    }

    public static void remove(long index, int user_id) throws SQLException
    {
        Database.remove(index, user_id);

        Database.refresh(citiesVector);
    }

    public static Vector<City> getCitiesVector()
    {
        return citiesVector;
    }

    public static void shuffle()
    {
        Collections.shuffle(citiesVector);
    }

    public static void update(long id, City city, int user_id) throws SQLException
    {
        Database.update(id, city, user_id);

        Database.refresh(citiesVector);
    }

    public static void clear(int user_id) throws SQLException
    {
        Database.clear(user_id);

        Database.refresh(citiesVector);
    }

    public static void sort()
    {
        Collections.sort(citiesVector);
    }

    public static void refresh() throws SQLException
    {
        Database.refresh(citiesVector);
    }

    public static void remove_first(int user_id) throws SQLException
    {
        Database.remove_first(user_id);

        Database.refresh(citiesVector);
    }

    public static void remove_lower(City city, int user_id) throws SQLException
    {
        Database.remove_lower(city, user_id);
    }
}
