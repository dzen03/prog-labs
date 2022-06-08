package com.dzen03.core;


import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Random;

public class City implements Comparable<City>, Serializable
{
    private long id;
    private String name;
    private Coordinates coordinates;
    private final ZonedDateTime creationDate;
    private float area;
    private int population;
    private int metersAboveSeaLevel;
    private int carCode;
    private Government government;
    private StandardOfLiving standardOfLiving;
    private Human governor;

    private int userId;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        if (name == null || name.equals(""))
            throw new IncorrectFieldException();
        this.name = name;
    }

    public Coordinates getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate()
    {
        return creationDate;
    }

    public float getArea()
    {
        return area;
    }

    public void setArea(float area)
    {
        if (area <= 0)
            throw new IncorrectFieldException();
        this.area = area;
    }

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        if (population <= 0)
            throw new IncorrectFieldException();
        this.population = population;
    }

    public int getMetersAboveSeaLevel()
    {
        return metersAboveSeaLevel;
    }

    public void setMetersAboveSeaLevel(int metersAboveSeaLevel)
    {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public int getCarCode()
    {
        return carCode;
    }

    public void setCarCode(int carCode)
    {
        if (carCode <= 0 || carCode > 1000)
            throw new IncorrectFieldException();
        this.carCode = carCode;
    }

    public Government getGovernment()
    {
        return government;
    }

    public void setGovernment(Government government)
    {
        this.government = government;
    }

    public StandardOfLiving getStandardOfLiving()
    {
        return standardOfLiving;
    }

    public void setStandardOfLiving(StandardOfLiving standardOfLiving)
    {
        if (standardOfLiving == null)
            throw new IncorrectFieldException();
        this.standardOfLiving = standardOfLiving;
    }

    public Human getGovernor()
    {
        return governor;
    }

    public void setGovernor(Human governor)
    {
        this.governor = governor;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }


    public City()
    {
        Random random = new Random();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        this.id = random.nextLong();
        this.creationDate = zonedDateTime;
    }

    public City(String[] data)
    {
        this();

        this.setId(Long.parseLong(data[0]));
        this.setArea(Float.parseFloat(data[1]));
        this.setCarCode(Integer.parseInt(data[2]));
        this.setName(data[3]);
        this.setMetersAboveSeaLevel(Integer.parseInt(data[4]));
        this.setPopulation(Integer.parseInt(data[5]));

        Coordinates coordinates = new Coordinates();
        coordinates.setX(Integer.parseInt(data[6]));
        coordinates.setY(Float.parseFloat(data[7]));
        this.setCoordinates(coordinates);


        if (data[8] == null)
        {
            this.setGovernment(null);
        }
        else
        {
            this.setGovernment(Government.pick(data[8]));
        }

        if (data[9] == null || data[9].equals(""))
            this.setGovernor(null);
        else
            this.setGovernor(new Human(data[9]));

        this.setStandardOfLiving(StandardOfLiving.pick(data[10]));

        this.setUserId(Integer.parseInt(data[11]));
        if (data.length == 13)
        {
            this.setUserId(Integer.parseInt(data[12]));
        }
    }

    public City(Object[] data)
    {

        this();
        this.setArea((Float) data[0]);
        this.setCarCode((Integer) data[1]);
        this.setName((String) data[2]);
        this.setMetersAboveSeaLevel((Integer) data[3]);
        this.setPopulation((Integer) data[4]);

        Coordinates coordinates = new Coordinates();
        coordinates.setX((Integer) data[5]);
        coordinates.setY((Float) data[6]);
        this.setCoordinates(coordinates);

        this.setGovernment(Government.pick((String) data[7]));

        if (data[8].equals(""))
        {
            this.setGovernor(null);
        }
        else
        {
            this.setGovernor(new Human((String) data[8]));
        }

        this.setStandardOfLiving(StandardOfLiving.pick((String) data[9]));

    }

    public String dump() // TODO decide: remove it or not
    {
        return String.format("%d,%f,%d,%s,%d,%d,%d,%f,%s,%s,%s,%d", getId(), getArea(), getCarCode(), getName(),
                getMetersAboveSeaLevel(), getPopulation(), getCoordinates().getX(), getCoordinates().getY(),
                (getGovernment() != null ? getGovernment() : ""), (getGovernor() != null ? getGovernor().getName() : ""), getStandardOfLiving(), getUserId());
    }

    public String dumpForDB()
    {
        return String.format("%d,%f,%d,'%s',%d,%d,%d,%f,%s,%s,'%s',%d", getId(), getArea(), getCarCode(), getName(),
                getMetersAboveSeaLevel(), getPopulation(), getCoordinates().getX(), getCoordinates().getY(),
                (getGovernment() != null ? "'" + getGovernment() + "'" : null), (getGovernor() != null ? "'" + getGovernor().getName() + "'" : null), getStandardOfLiving(), getUserId());
    }

    public String[] dumpAsArray()
    {
        return this.dump().split(",");
    }

    @Override
    public String toString()
    {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", area=" + area +
                ", population=" + population +
                ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                ", carCode=" + carCode +
                ", government=" + government +
                ", standardOfLiving=" + standardOfLiving +
                ", governor=" + governor +
                ", owned by user_id=" + userId +
                '}';
    }

    @Override
    public int compareTo(City other)
    {
        return this.getName().compareTo(other.getName());
    }


}
