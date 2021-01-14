package com.company;
public class Wumpus extends Characters {

    private boolean life = true;
    public Wumpus(int x, int y, int value)
    {
        super(x,y,"Wumpus",value);
    }

    public void killWumpus()
    {
        life = false;
    }

    public boolean isLife() {
        return life;
    }

    @Override
    public void setBullets(int i) {

    }

    @Override
    public int getBullets() {
        return 0;
    }

    public void setLife(boolean life) {
        this.life = life;
    }
}
