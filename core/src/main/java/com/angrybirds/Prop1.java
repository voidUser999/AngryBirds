package com.angrybirds;

public class Prop1 extends Prop{
    private  int hp;
    private  double damage;

    Prop1() {
        this.hp = 20;
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
