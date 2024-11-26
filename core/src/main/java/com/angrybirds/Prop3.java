package com.angrybirds;

public class Prop3 extends Prop {
    private  int hp;
    private  double damage;

    Prop3() {
//        super();
//        setHp(10);
//        setDamage(5);
        this.hp = 30;
        this.damage = 3;

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
