package com.company;

public class Hole extends Characters {

    public Hole(int x, int y, int value)
    {
        super(x,y,"Hole",value);
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
