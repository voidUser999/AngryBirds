package com.angrybirds;

public abstract class Prop {
    private int hp;
    private double damage;

    // Constructor to initialize default values
    protected Prop() {
        this.hp = 24;
        this.damage = 5;
    }

    // Abstract method for additional behavior (to be implemented by subclasses)
    public abstract void interact();

    // Getters and setters for common properties
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
