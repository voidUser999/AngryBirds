package utils;

import com.angrybirds.Prop;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class MyContactListener implements ContactListener {
    private final Array<Body> bodiesToDestroy = new Array<>();
    // Initialize player health
    HashMap<Body, Prop> birdProp;

    public MyContactListener(HashMap<Body, Prop> birdProp) {
        this.birdProp = birdProp;
    }
    @Override
    public void beginContact(Contact contact){
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

        // Bird (player) colliding with wall (box)
        if ("thisRed0".equals(fa.getUserData()) && "userdata".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody());

        } else if ("userdata".equals(fa.getUserData()) && "thisRed0".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody());
        }
        else if ("thisRed1".equals(fa.getUserData()) && "userdata".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody());

        } else if ("userdata".equals(fa.getUserData()) && "thisRed1".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody());
        }
        else if ("thisRed2".equals(fa.getUserData()) && "userdata".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody());

        }
        else if ("userdata".equals(fa.getUserData()) && "thisRed2".equals(fb.getUserData())) {
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
        // Retrieve current health from the HashMap

        float playerHealth = birdProp.get(playerBody).getHp();
//        float speed = playerBody.getLinearVelocity().len();
//        float damage = speed * 10;
        float damage = (float) (1f*birdProp.get(playerBody).getDamage());

        // Subtract damage from player health
        playerHealth -= damage;
        System.out.println("Player Health: " + playerHealth);

        // Update the health in the HashMap
        birdProp.get(playerBody).setHp((int) playerHealth);

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


}
