package com.angrybirds;
import java.util.ArrayList;

public class Bomb extends Birds {

    private int explosionRadius;

    public Bomb(ArrayList<Coordinates> velocity, boolean state, int damage, int hp, int bonus, int explosionRadius) {
        super(velocity, state, damage, hp, bonus);
        this.explosionRadius = explosionRadius;
    }

    public void explode() {
    }

    @Override
    public void whenLaunched(ArrayList<Coordinates> path) {
    }

    @Override
    public void onHit(int impactDamage, int impactHp) {
        explode();
    }

    @Override
    public boolean hasStopped(ArrayList<Coordinates> currentVelocity) {
        return false;
    }


}

