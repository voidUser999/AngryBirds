package com.angrybirds;
import java.util.ArrayList;

public class Red extends Birds {

    public Red(ArrayList<Coordinates> velocity, boolean state, int damage, int hp, int bonus) {
        super(velocity, state, damage, hp, bonus);
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

