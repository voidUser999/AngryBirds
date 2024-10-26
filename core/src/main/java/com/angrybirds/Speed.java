package com.angrybirds;

import java.util.ArrayList;

public class Speed extends Birds {

    private int maxSpeed;

    public Speed(ArrayList<Coordinates> velocity, boolean state, int damage, int hp, int bonus, int maxSpeed) {
        super(velocity, state, damage, hp, bonus);
        this.maxSpeed = maxSpeed;
    }

    public void speedify() {
    }

    public int accelerationRate() {
        return 10;
    }

    @Override
    public void whenLaunched(ArrayList<Coordinates> path) {
    }

    @Override
    public void onHit(int impactDamage, int impactHp) {
    }

    @Override
    public boolean hasStopped(ArrayList<Coordinates> currentVelocity) {
        return false;
    }


}

