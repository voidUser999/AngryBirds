package com.angrybirds;

public abstract class Pigs {

    protected int hp;
    protected int size;
    protected boolean isDestroyed;
    protected int scoreValue;
    protected Coordinates position;

    public Pigs(int hp, int size, int scoreValue, Coordinates position) {
        this.hp = hp;
        this.size = size;
        this.isDestroyed = false;
        this.scoreValue = scoreValue;
        this.position = position;
    }

    public abstract void takeDamage(int damage);

    public abstract void destroy();

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public int getHp() {
        return hp;
    }

    public int getSize() {
        return size;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }
}


