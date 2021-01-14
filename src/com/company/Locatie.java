package com.company;

public class Locatie {
    private int x;
    private int y;
    private int value;
    private boolean visible;

    public Locatie(int x, int y, int value, boolean status) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.visible=status;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public boolean isFree()
    {
        return value == 0;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
