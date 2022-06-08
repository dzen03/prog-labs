package com.dzen03.core;

import java.sql.*;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;

import static com.dzen03.run.Main.LOGGER;

public class Database
{
    static final String DB_LOGIN = "fxkxyadckkablm";
    static final String DB_PASS = "3af55521e5b3aca1e58e14f79194eb28433f5051bebdd3813c8d87e3036e483c";
    static final String DB_URL = "jdbc:postgresql://ec2-63-32-248-14.eu-west-1.compute.amazonaws.com:5432/d4lvqpo809udnf";

    static Connection conn = null;
    static Statement statement = null;

    public Database() throws Exception
    {
        Class.forName("org.postgresql.Driver");
        connect();
    }

    public static void connect() throws SQLException
    {
        String url = DB_URL;
        LOGGER.log(Level.INFO, "before connection " + url);
        Properties props = new Properties();
        props.setProperty("user", DB_LOGIN);
        props.setProperty("password", DB_PASS);
        props.setProperty("ssl", "false");
        conn = DriverManager.getConnection(url, props);
        statement = conn.createStatement();

        LOGGER.log(Level.INFO, String.format("connected to DB at url: %s", url));
    }

    public static void refresh(Vector<City> dataToSync) throws SQLException
    {
        dataToSync.clear();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM cities");

        while (resultSet.next())
        {
            String[] packedCity = new String[12];

            for (int i = 0; i < packedCity.length; ++i)
            {
                packedCity[i] = resultSet.getString(i + 1);
            }

            dataToSync.add(new City(packedCity));
        }
    }


    public static void update(long id, City newCity, int user_id) throws SQLException
    {
        statement.executeUpdate(String.format("UPDATE cities SET(city_id, area, car_code, name, meters_above_sea, " +
                        "population, x_coord, y_coord, government, governor, standard_of_living, user_id) = (%s) WHERE city_id = %d and user_id = %d",
                newCity.dumpForDB(), id, user_id));
    }

    public static int auth(String login, String pass) throws AuthException, SQLException
    {
        ResultSet resultSet = statement.executeQuery("SELECT user_id, pass_hash  FROM users WHERE login = '" + login + "'");
        boolean isEmpty = true;
        while (resultSet.next())
        {
            isEmpty = false;
            if (resultSet.getString(2).equals(pass))
                return resultSet.getInt(1);
        }
        if (isEmpty) //register
        {
            statement.execute("INSERT INTO users (login, pass_hash) Values ('" + login + "', '" + pass + "')");
            return auth(login, pass);
        }
        throw new AuthException("password incorrect");
    }

    public static void add(City city) throws SQLException
    {
        statement.executeUpdate(String.format("INSERT INTO cities (city_id, area, car_code, name, meters_above_sea, " +
                        "population, x_coord, y_coord, government, governor, standard_of_living, user_id) VALUES (%s)",
                city.dumpForDB()));
    }

    public static void remove(long index, int user_id) throws SQLException
    {
        statement.executeUpdate("DELETE FROM cities WHERE (city_id, user_id)=(" + index + ", " + user_id + ")");
    }

    public static void clear(int user_id) throws SQLException
    {
        statement.executeUpdate("DELETE FROM cities WHERE user_id=" + user_id);
    }

    public static void remove_first(int user_id) throws SQLException
    {
        statement.executeUpdate("DELETE FROM cities WHERE city_id=(SELECT MIN(city_id) FROM cities WHERE user_id=" + user_id + ")");
    }

    public static void remove_lower(City city, int user_id) throws SQLException
    {
        statement.executeUpdate("DELETE FROM cities WHERE city_id IN (SELECT city_id FROM cities WHERE user_id=" + user_id + " AND population < " + city.getPopulation());
    }

    public static void exit() throws SQLException
    {
        conn.close();
        statement.close();
    }
}
