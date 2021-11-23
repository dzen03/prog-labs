package com.dzen03.core;

public class Description {
    private Thing text;

    public Description()
    {
        this.text = new Thing();
    }

    public Description(String text)
    {
        this.text = new Thing(text);
    }

    public Description(Thing text)
    {
        this.text = text;
    }

    @Override
    public String toString() {
        return text.toString();
    }
}
