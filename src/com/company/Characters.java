package com.company;

public abstract class Characters {
    Locatie locatie;
    private String nume;

    public Characters(int x, int y, String nume, int value)
    {
        this.locatie = new Locatie(x,y,value,false);
        this.nume=nume;
    }

    public Locatie getLocatie()
    {
        return this.locatie;
    }

    public void setLocatie(int x, int y)
    {
        this.locatie.setX(x);
        this.locatie.setY(y);
    }

    public Locatie getLocation()
    {
        return this.locatie;
    }

    public boolean isSet()
    {
        if(locatie.isFree())
            return false;
        return true;
    }
     public void setValue(int value)
     {
         locatie.setValue(value);
     }

    public abstract void setLife(boolean b);

    public abstract boolean isLife();

    public abstract void setBullets(int i);

    public abstract int getBullets();
}
