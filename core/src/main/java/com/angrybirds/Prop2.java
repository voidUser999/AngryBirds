package com.angrybirds;

public class Prop2 extends Prop {
    private  int hp;
    private  double damage;

    Prop2() {
        this.hp = 15;
        this.damage = 5;

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
