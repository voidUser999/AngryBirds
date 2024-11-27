package com.angrybirds;

public abstract class Prop_EX {
    private float hp;



    protected Prop_EX() {
        this.hp = 4;

    }


    public abstract void interact();


    public float getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }


}
