package utils;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener {
    private final Array<Body> bodiesToDestroy = new Array<>();

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

        // Bird colliding with wall
        if ("this".equals(fa.getUserData()) && "userdata".equals(fb.getUserData())) {
            System.out.println("Bird hit Wall (A -> B)");
            markForDestruction(fb.getBody());
        } else if ("userdata".equals(fa.getUserData()) && "this".equals(fb.getUserData())) {
            System.out.println("Bird hit Wall (B -> A)");
            markForDestruction(fa.getBody());
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {}

    private void markForDestruction(Body body) {
        if (!bodiesToDestroy.contains(body, true)) {
            bodiesToDestroy.add(body);

            // Mark body as to-be-destroyed but defer deactivation
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
