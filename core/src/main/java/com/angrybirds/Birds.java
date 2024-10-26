package com.angrybirds;

import java.util.ArrayList;

public abstract class Birds {

    private ArrayList<Coordinates> velocity;
    private boolean state;
    private int damage;
    private int hp;
    private int bonus;

    public Birds(ArrayList<Coordinates> velocity, boolean state, int damage, int hp, int bonus) {

        this.velocity = velocity;
        this.state = state;
        this.damage = damage;
        this.hp = hp;
        this.bonus = bonus;
    }

    public abstract void whenLaunched(ArrayList<Coordinates> path);
    public abstract void onHit(int impactDamage, int impactHp);
    public abstract boolean hasStopped(ArrayList<Coordinates> currentVelocity);

    public int getBonus() {
        return bonus;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public boolean getState() {
        return state;
    }

    public ArrayList<Coordinates> getVelocity() {
        return velocity;
    }


    // Setter Methods
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setVelocity(ArrayList<Coordinates> velocity) {
        this.velocity = velocity;
    }

}

