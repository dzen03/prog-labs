package com.dzen03.core;

public class Coordinates
{
    private int x; //Максимальное значение поля: 992
    private float y; //Максимальное значение поля: 822

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        if (x > 992)
            throw new IncorrectFieldException();
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        if (y > 822)
            throw new IncorrectFieldException();
        this.y = y;
    }

    public Coordinates()
    {
        x = 0;
        y = 0;
    }

    @Override
    public String toString()
    {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
