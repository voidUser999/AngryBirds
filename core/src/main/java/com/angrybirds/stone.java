package com.angrybirds;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class stone extends Prop_EX {
    float hp;

    public stone() {
        this.hp = 4;
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
