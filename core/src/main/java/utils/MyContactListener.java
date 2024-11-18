package utils;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener {
    private final Array<Body> bodiesToDestroy = new Array<>();
    private float playerHealth = 500f; // Initialize player health

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

        // Bird (player) colliding with wall (box)
        if ("this".equals(fa.getUserData()) && "userdata".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody());
        } else if ("userdata".equals(fa.getUserData()) && "this".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody());
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {}

    private void handleCollision(Body playerBody, Body otherBody) {
        // Calculate speed of the player at the moment of collision
        float speed = playerBody.getLinearVelocity().len();
        float damage = speed * 10; // Example damage calculation (adjust as needed)

        // Apply damage to player health
        playerHealth -= damage;
        System.out.println("Player Health: " + playerHealth);

        // If health is below zero, player can no longer destroy objects
        if (playerHealth <= 0) {
            System.out.println("Player is too weak to destroy objects!");
            return;
        }

        // Mark the other body for destruction
        markForDestruction(otherBody);
    }

    private void markForDestruction(Body body) {
        if (!bodiesToDestroy.contains(body, true)) {
            bodiesToDestroy.add(body);
            body.setUserData("toBeDestroyed");
        }
    }

    public void processPendingDestructions(World world) {
        for (Body body : bodiesToDestroy) {
            if (!world.isLocked()) {
                world.destroyBody(body);
            }
        }
        bodiesToDestroy.clear();
    }

    // Getter and setter for player health if needed elsewhere
    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(float health) {
        this.playerHealth = health;
    }
}
