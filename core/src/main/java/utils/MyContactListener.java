package utils;

import com.angrybirds.Prop;
import com.angrybirds.Prop_EX;
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
        //wood
        System.out.println(fa.getUserData());
        System.out.println(fb.getUserData());
        if ("thisRed0".equals(fa.getUserData()) && "woodblock".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"woodblock");

        } else if ("woodblock".equals(fa.getUserData()) && "thisRed0".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"woodblock");
        }
        else if ("thisRed1".equals(fa.getUserData()) && "woodblock".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"woodblock");

        } else if ("woodblock".equals(fa.getUserData()) && "thisRed1".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"woodblock");
        }
        else if ("thisRed2".equals(fa.getUserData()) && "woodblock".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"woodblock");

        }
        else if ("woodblock".equals(fa.getUserData()) && "thisRed2".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"woodblock");
        }

        //stone
        if ("thisRed0".equals(fa.getUserData()) && "stoneblock".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"stoneblock");

        } else if ("stoneblock".equals(fa.getUserData()) && "thisRed0".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"stoneblock");
        }
        else if ("thisRed1".equals(fa.getUserData()) && "stoneblock".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"stoneblock");

        } else if ("stoneblock".equals(fa.getUserData()) && "thisRed1".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"stoneblock");
        }
        else if ("thisRed2".equals(fa.getUserData()) && "stoneblock".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"stoneblock");

        }
        else if ("stoneblock".equals(fa.getUserData()) && "thisRed2".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"stoneblock");
        }

        //glass
        if ("thisRed0".equals(fa.getUserData()) && "glassblock".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"glassblock");

        } else if ("glassblock".equals(fa.getUserData()) && "thisRed0".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"glassblock");
        }
        else if ("thisRed1".equals(fa.getUserData()) && "glassblock".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"glassblock");

        } else if ("glassblock".equals(fa.getUserData()) && "thisRed1".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"glassblock");
        }
        else if ("thisRed2".equals(fa.getUserData()) && "glassblock".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"glassblock");

        }
        else if ("glassblock".equals(fa.getUserData()) && "thisRed2".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody() ,"glassblock" );
        }

        //pig0 (small pig)

        if ("thisRed0".equals(fa.getUserData()) && "pig0".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"pig0");

        } else if ("pig0".equals(fa.getUserData()) && "thisRed0".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"pig0");
        }
        else if ("thisRed1".equals(fa.getUserData()) && "pig0".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"pig0");

        } else if ("pig0".equals(fa.getUserData()) && "thisRed1".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"pig0");
        }
        else if ("thisRed2".equals(fa.getUserData()) && "pig0".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"pig0");

        }
        else if ("pig0".equals(fa.getUserData()) && "thisRed2".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody() ,"pig0" );
        }

        //pig1 (medium size)

        if ("thisRed0".equals(fa.getUserData()) && "pig1".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"pig1");

        } else if ("pig1".equals(fa.getUserData()) && "thisRed0".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"pig1");
        }
        else if ("thisRed1".equals(fa.getUserData()) && "pig1".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"pig1");

        } else if ("pig1".equals(fa.getUserData()) && "thisRed1".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"pig1");
        }
        else if ("thisRed2".equals(fa.getUserData()) && "pig1".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"pig1");

        }
        else if ("pig1".equals(fa.getUserData()) && "thisRed2".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody() ,"pig1" );
        }

        //pig2

        if ("thisRed0".equals(fa.getUserData()) && "pig2".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"pig2");

        } else if ("pig2".equals(fa.getUserData()) && "thisRed0".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"pig2");
        }
        else if ("thisRed1".equals(fa.getUserData()) && "pig2".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"pig2");

        } else if ("pig2".equals(fa.getUserData()) && "thisRed1".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody(),"pig2");
        }
        else if ("thisRed2".equals(fa.getUserData()) && "pig2".equals(fb.getUserData())) {
            handleCollision(fa.getBody(), fb.getBody(),"pig2");

        }
        else if ("pig2".equals(fa.getUserData()) && "thisRed2".equals(fb.getUserData())) {
            handleCollision(fb.getBody(), fa.getBody() ,"pig2" );
        }

        if (isStructure(fa.getUserData()) && isStructure(fb.getUserData())) {
            handleStructureCollision(fa, fb);
        }


    }
    private boolean isStructure(Object userData) {
        return "woodblock".equals(userData) || "stoneblock".equals(userData) || "glassblock".equals(userData);
    }

    private void handleStructureCollision(Fixture bodyA, Fixture bodyB) {
        float relativeVelocity = bodyA.getBody().getLinearVelocity().dst(bodyB.getBody().getLinearVelocity());
        float damageThreshold = 5.0f;

        System.out.println("Handling Structure-to-Structure Collision...");
        System.out.println("Relative Velocity: " + relativeVelocity);

        if (relativeVelocity > damageThreshold) {
            float damage = relativeVelocity * 0.5f; // Scale damage with velocity
            System.out.println("Applying Damage: " + damage);

            applyDamageToStructure(bodyA, damage);
            applyDamageToStructure(bodyB, damage);
        }
    }
    private void applyDamageToStructure(Fixture structureBody, float damage) {
        Prop_EX structureProp = getStructureProp(structureBody);
        if (structureProp != null) {
            float structHp = structureProp.getHp();
            System.out.println("Structure HP Before Damage: " + structHp);

            structHp -= damage;

            System.out.println("Damage Applied: " + damage);
            System.out.println("Structure HP After Damage: " + structHp);

            structureProp.setHp((int) structHp);

            if (structHp <= 0) {
                System.out.println("Structure is destroyed! Marking for destruction...");
                markForDestruction(structureBody.getBody());
            }
        }
    }
    private Prop_EX getStructureProp(Fixture structureBody) {
        Object userData = structureBody.getUserData();
        if ("woodblock".equals(userData)) {
            return TiledObjectUtil.getWoodProp().get(structureBody.getBody());
        } else if ("stoneblock".equals(userData)) {
            return TiledObjectUtil.getStoneProp().get(structureBody.getBody());
        } else if ("glassblock".equals(userData)) {
            return TiledObjectUtil.getGlassProp().get(structureBody.getBody());
        }
        return null;
    }


    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {}

    private void handleCollision(Body playerBody, Body otherBody, String ud) {
        System.out.println("Handling collision...");
        System.out.println("Player Body: " + playerBody);
        System.out.println("Other Body: " + otherBody);
        System.out.println("User Data (ud): " + ud);


        float structHp = 5;
        float playerHealth = birdProp.get(playerBody).getHp();
        System.out.println("Initial Player Health: " + playerHealth);


        float damage = (float) (1f * birdProp.get(playerBody).getDamage());
        System.out.println("Calculated Damage: " + damage);


        if ("woodblock".equals(ud)) {
            structHp = TiledObjectUtil.getWoodProp().get(otherBody).getHp();
        } else if ("stoneblock".equals(ud)) {
            structHp = TiledObjectUtil.getStoneProp().get(otherBody).getHp();
        } else if ("glassblock".equals(ud)) {
            structHp = TiledObjectUtil.getGlassProp().get(otherBody).getHp();
        } else if ("pig0".equals(ud)) {
            structHp = TiledObjectUtil.getEnemy0Prop().get(otherBody).getHp();
        }
        else if ("pig1".equals(ud)) {
            structHp = TiledObjectUtil.getEnemy1Prop().get(otherBody).getHp();

        }
        else if ("pig2".equals(ud)) {
            structHp = TiledObjectUtil.getEnemy2Prop().get(otherBody).getHp();

        }



        System.out.println("Structure Health Before Damage: " + structHp);

        // Subtract damage from player health and structure health
        playerHealth -= damage;
        structHp -= (float) birdProp.get(playerBody).getDamage();
        System.out.println("Player Health After Damage: " + playerHealth);
        System.out.println("Structure Health After Damage: " + structHp);

        // Update the health in the HashMap
        birdProp.get(playerBody).setHp((int) playerHealth);
        if ("woodblock".equals(ud)) {
            TiledObjectUtil.getWoodProp().get(otherBody).setHp((int) structHp);
            System.out.println("Updated WoodBlock Health: " + structHp);
        } else if ("stoneblock".equals(ud)) {
            TiledObjectUtil.getStoneProp().get(otherBody).setHp((int) structHp);
            System.out.println("Updated StoneBlock Health: " + structHp);
        } else if ("glassblock".equals(ud)) {
            TiledObjectUtil.getGlassProp().get(otherBody).setHp((int) structHp);
            System.out.println("Updated GlassBlock Health: " + structHp);
        } else if ("pig0".equals(ud)) {
            TiledObjectUtil.getEnemy0Prop().get(otherBody).setHp((int) structHp);
            System.out.println("Updated Pig0 Health: " + structHp);

        } else if ("pig1".equals(ud)) {
            TiledObjectUtil.getEnemy1Prop().get(otherBody).setHp((int) structHp);
            System.out.println("Updated Pig1 Health: " + structHp);

        }
        else if ("pig2".equals(ud)) {
            TiledObjectUtil.getEnemy2Prop().get(otherBody).setHp((int) structHp);
            System.out.println("Updated Pig2 Health: " + structHp);

        }


        if (playerHealth <= 0) {
            System.out.println("Player is too weak to destroy objects!");
            return;
        }


        if (structHp <= 0) {
            System.out.println("Structure is destroyed! Marking for destruction...");
            markForDestruction(otherBody);
        } else {
            System.out.println("Structure is still intact.");
        }
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
