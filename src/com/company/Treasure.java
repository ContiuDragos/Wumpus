package com.company;

public class Treasure extends Characters {

    public Treasure(int x, int y, int value)
    {
        super(x,y,"Treasure",value);
    }

    @Override
    public void setLife(boolean b) {

    }

    @Override
    public boolean isLife() {
        return true;
    }

    @Override
    public void setBullets(int i) {

    }

    @Override
    public int getBullets() {
        return 0;
    }
}
