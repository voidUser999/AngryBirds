package com.angrybirds;

public class HelmetPig extends Pigs {

    private int helmetDefense;

    public HelmetPig(int hp, int size, int scoreValue, Coordinates position, int helmetDefense) {
        super(hp, size, scoreValue, position);
        this.helmetDefense = helmetDefense;
    }

    @Override
    public void takeDamage(int damage) {
        int effectiveDamage = damage - helmetDefense;
        if (effectiveDamage < 0) effectiveDamage = 0;

        hp -= effectiveDamage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }

        helmetDefense -= damage / 2;
        if (helmetDefense < 0) helmetDefense = 0;
    }

    @Override
    public void destroy() {
        isDestroyed = true;
        System.out.println("Helmet Pig destroyed!");
    }

    public int getHelmetDefense() {
        return helmetDefense;
    }

    public void setHelmetDefense(int helmetDefense) {
        this.helmetDefense = helmetDefense;
    }
}
