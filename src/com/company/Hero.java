package com.company;

public class Hero extends Characters {

    private boolean life = true;
    private int bullets=1;
    public Hero(int x, int y)
    {
        super(x,y,"Hero",1);
    }

    @Override
    public void setLife(boolean b) {
        life=b;
    }

    @Override
    public boolean isLife() {
        return life;
    }

    public int getBullets()
    {
        return bullets;
    }

    public void setBullets( int bullets)
    {
        this.bullets=bullets;
    }
}
