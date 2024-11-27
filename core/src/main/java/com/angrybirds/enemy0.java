package com.angrybirds;

public class enemy0 extends Prop {
    private  int hp;
    private  double damage;

    public enemy0() {
        this.hp = 1;
        this.damage = 1;

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
