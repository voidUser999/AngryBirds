package com.angrybirds;

import java.io.Serial;
import java.io.Serializable;

public class BodyData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private float x, y, angle, velocityX, velocityY;
    private String type;

    public BodyData(float x, float y, float angle, float velocityX, float velocityY, String type) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public String getType() {
        return type;
    }
}
