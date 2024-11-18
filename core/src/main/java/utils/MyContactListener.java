package utils;

import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        // Null checks for fixtures and user data
        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;
        if (fa.getUserData().equals(fb.getUserData())) return;
        // Corrected string comparison using equals()
        if ("userdata".equals(fb.getUserData()) || "this".equals(fa.getUserData())) {
            System.out.println("A collision happened between bird and wall");
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
