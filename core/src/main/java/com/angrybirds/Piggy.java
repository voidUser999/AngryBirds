package com.angrybirds;

public class Piggy extends Pigs {

    public Piggy(int hp, int size, int scoreValue, Coordinates position) {
        super(hp, size, scoreValue, position);
    }

    @Override
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }

    @Override
    public void destroy() {
        isDestroyed = true;
        System.out.println("Piggy destroyed!");
    }
}

