package com.angrybirds;

public class Prop {
  private  int hp;
  private  double damage;

    Prop() {
       this.hp = 4;
       this.damage = 0.8;

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
