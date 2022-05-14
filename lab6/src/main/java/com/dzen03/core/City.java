package com.dzen03.core;


import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Random;

public class City implements Comparable<City>, Serializable
{
    private long id;
    private String name;
    private Coordinates coordinates;
    private java.time.ZonedDateTime creationDate;
    private float area;
    private int population;
    private int metersAboveSeaLevel;
    private int carCode;
    private Government government;
    private StandardOfLiving standardOfLiving;
    private Human governor;

    public long getId()
    {
        return id;
    }

    public void setId(long id){this.id = id;}

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
        this.setArea(Float.parseFloat(data[0]));
        this.setCarCode(Integer.parseInt(data[1]));
        this.setName(data[2]);
        this.setMetersAboveSeaLevel(Integer.parseInt(data[3]));
        this.setPopulation(Integer.parseInt(data[4]));

        Coordinates coordinates = new Coordinates();
        coordinates.setX(Integer.parseInt(data[5]));
        coordinates.setY(Float.parseFloat(data[6]));
        this.setCoordinates(coordinates);

        this.setGovernment(Government.pick(data[7]));

        if (data[8].equals(""))
            this.setGovernor(null);
        else
            this.setGovernor(new Human(data[8]));

        this.setStandardOfLiving(StandardOfLiving.pick(data[9]));
    }
     public String dump()
     {
         return String.format("%f,%d,%s,%d,%d,%d,%f,%s,%s,%s", getArea(), getCarCode(), getName(),
                 getMetersAboveSeaLevel(), getPopulation(), getCoordinates().getX(), getCoordinates().getY(),
                 (getGovernment() != null ? getGovernment() : ""), (getGovernor() != null ? getGovernor().getName() : ""), getStandardOfLiving());
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
                '}';
    }

//    @Override
//    public int compareTo(City other)
//    {
//        return Long.compare(this.getId(), other.getId());
//    }
    @Override
    public int compareTo(City other)
    {
        return this.getName().compareTo(other.getName());
    }


}
