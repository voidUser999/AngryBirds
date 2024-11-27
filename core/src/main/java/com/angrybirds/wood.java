package com.angrybirds;

public class wood extends Prop_EX {
    float hp;

    public wood() {
        this.hp = 2;
    }

    @Override
    public void interact() {

    }

    public float getHp() {
        return hp;
    }
    public void setHp(float hp) {
        this.hp = hp;
    }
}
