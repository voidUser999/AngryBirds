package com.angrybirds;

public class CrownPig extends Pigs {

    private int crownArmor;

    public CrownPig(int hp, int size, int scoreValue, Coordinates position, int crownArmor) {
        super(hp, size, scoreValue, position);
        this.crownArmor = crownArmor;
    }

    @Override
    public void takeDamage(int damage) {
        int effectiveDamage = damage - crownArmor;
        if (effectiveDamage < 0) effectiveDamage = 0;

        hp -= effectiveDamage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }


        crownArmor -= damage / 4;
        if (crownArmor < 0) crownArmor = 0;
    }

    @Override
    public void destroy() {
        isDestroyed = true;
        System.out.println("Crown Pig destroyed!");
    }

    public int getCrownArmor() {
        return crownArmor;
    }

    public void setCrownArmor(int crownArmor) {
        this.crownArmor = crownArmor;
    }
}
