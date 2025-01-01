package com.angrybirds;

public abstract class Prop {
    private int hp;
    private double damage;


    protected Prop() {
        this.hp = 24;
        this.damage = 5;
    }


    public abstract void interact();


    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
