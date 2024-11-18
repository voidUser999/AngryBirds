package utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static utils.Constants.PPM;

public class TiledObjectUtil {

    private static World world;
    private static final Map<Body, String> bodyTextures = new HashMap<>();
    private static final Map<String, Texture> textureCache = new HashMap<>();

    public static void initialize(World worldInstance) {
        world = worldInstance;
        //System.out.println("TiledObjectUtil initialized with world: " + worldInstance);
    }

    public static void parseTiledObjectLayer(MapObjects objects, boolean isStatic) {
        for (MapObject object : objects) {
            if (!(object instanceof PolygonMapObject)) continue;

            PolygonMapObject polyObj = (PolygonMapObject) object;
            float[] vertices = polyObj.getPolygon().getTransformedVertices();

            // Scale vertices for Box2D (convert from pixels to meters)
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = vertices[i] / PPM;
            }

            // Calculate centroid of the polygon for accurate body placement
            Vector2 centroid = calculateCentroid(vertices);
           // System.out.println("Centroid calculated at: " + centroid);

            // Offset vertices relative to the centroid
            for (int i = 0; i < vertices.length; i += 2) {
                vertices[i] -= centroid.x;
                vertices[i + 1] -= centroid.y;
            }

            // Create Box2D body at the centroid
            Body body = createPolygon(vertices, centroid, isStatic);

            // Retrieve the texture name and associate it with the body
            String textureName = (String) polyObj.getProperties().get("texture");
            if (textureName != null) {
                bodyTextures.put(body, textureName);

                Texture texture = getTexture(textureName);
                body.setUserData(texture);

               // System.out.println("Associated texture: " + textureName);
            }
        }
    }

    private static Body createPolygon(float[] vertices, Vector2 position, boolean isStatic) {
        BodyDef def = new BodyDef();
        def.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody; // Walls are static
        def.fixedRotation = true; // Walls don't rotate
        def.position.set(position); // Set the wall's position

        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Standard density for a wall
        fixtureDef.friction = 0.5f; // Slight friction for wall interactions
        fixtureDef.restitution = 0.2f; // Slight bounciness for realism

        body.createFixture(fixtureDef).setUserData("userdata"); // Mark as wall for collision
        shape.dispose();

        //System.out.println("Body created at: " + body.getPosition());
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                body.setFixedRotation(false); // Enable rotation
                //System.out.println("Rotation enabled for body at: " + body.getPosition().x + ", " + body.getPosition().y);
            }
        },2);
        return body;
    }

    private static Vector2 calculateCentroid(float[] vertices) {
        Vector2 centroid = new Vector2(0, 0);
        int vertexCount = vertices.length / 2;

        for (int i = 0; i < vertices.length; i += 2) {
            centroid.x += vertices[i];
            centroid.y += vertices[i + 1];
        }

        centroid.x /= vertexCount;
        centroid.y /= vertexCount;

        return centroid;
    }

    private static Texture getTexture(String textureName) {
        if (!textureCache.containsKey(textureName)) {
            textureCache.put(textureName, new Texture(textureName));
            //System.out.println("Loaded new texture: " + textureName);
        }
        return textureCache.get(textureName);
    }

    public static void renderBody(Body body, Batch batch) {
        Object userData = body.getUserData();
        if (userData instanceof Texture) {
            Texture texture = (Texture) userData;
            TextureRegion textureRegion = new TextureRegion(texture);
            // Get body position and angle
            Vector2 position = body.getPosition();
            float angle = (float) Math.toDegrees(body.getAngle()); // Convert radians to degrees for Batch

            // Convert position to pixel coordinates
            float x = (position.x * PPM) - (texture.getWidth() / 2f);
            float y = (position.y * PPM) - (texture.getHeight() / 2f);

            //System.out.println("Drawing texture at: " + x + ", " + y + " with rotation: " + angle);

            // Draw the texture with rotation
            batch.draw(
                textureRegion,
                x, y,                                 // Bottom-left corner of the texture
                textureRegion.getRegionWidth() / 2f, texture.getHeight() / 2f, // Origin for rotation
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), // Width and height of the texture
                1f, 1f,                               // Scale
                angle                                 // Rotation angle
            );
        }
    }


    public static void renderAllBodies(Batch batch) {
        for (Body body : bodyTextures.keySet()) {
            renderBody(body, batch);
        }
    }
}
