package com.dzen03.core;

import java.io.Serializable;

public class Coordinates implements Serializable
{
    private int x;
    private float y;

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
