package com.angrybirds;

// Class to hold the data of the shape
public class ShapeData {
    private String shapeType;  // Polygon, Circle, etc.
    private float[] vertices;  // For polygons
    private float radius;      // For circles
    private float width, height;  // For boxes

    // Getters and Setters
    public String getShapeType() { return shapeType; }
    public void setShapeType(String shapeType) { this.shapeType = shapeType; }

    public float[] getVertices() { return vertices; }
    public void setVertices(float[] vertices) { this.vertices = vertices; }

    public float getRadius() { return radius; }
    public void setRadius(float radius) { this.radius = radius; }

    public float getWidth() { return width; }
    public void setWidth(float width) { this.width = width; }

    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }
}
