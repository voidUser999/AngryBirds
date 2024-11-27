package com.angrybirds;

public class enemy2 extends Prop{
    private  int hp;
    private  double damage;

    public enemy2() {
        this.hp = 5;
        this.damage = 2;

    }

    @Override
    public void interact() {

    }

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
