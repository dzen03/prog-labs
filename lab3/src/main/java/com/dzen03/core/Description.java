package com.dzen03.core;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
