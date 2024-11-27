package com.angrybirds;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class glass extends Prop_EX {
    float hp;

    public glass() {
        this.hp = 1;
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
