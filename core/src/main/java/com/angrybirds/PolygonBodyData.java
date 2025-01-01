package com.angrybirds;

import java.io.Serializable;

public class PolygonBodyData implements Serializable {
    private static final long serialVersionUID = 1L;

    private float[] vertices;
    private String textureName;
    private float x, y, angle, velocityX, velocityY;

    public PolygonBodyData(float[] vertices, String textureName, float x, float y, float angle, float velocityX, float velocityY) {
        this.vertices = vertices;
        this.textureName = textureName;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public float[] getVertices() {
        return vertices;
    }

    public String getTextureName() {
        return textureName;
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
}
