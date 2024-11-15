package utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import java.util.HashMap;
import java.util.Map;

public class TiledObjectUtil {

    private static World world;
    private static final Map<Body, String> bodyTextures = new HashMap<>();
    private static final Map<String, Texture> textureCache = new HashMap<>();

    /**
     * Initializes the TiledObjectUtil with the Box2D world instance.
     */
    public static void initialize(World worldInstance) {
        world = worldInstance;
    }

    /**
     * Parses Tiled map objects, creates Box2D bodies, and associates textures.
     */
    public static void parseTiledObjectLayer(MapObjects objects, boolean isStatic) {
        for (MapObject object : objects) {
            if (!(object instanceof PolygonMapObject)) continue;

            PolygonMapObject polyObj = (PolygonMapObject) object;

            // Extract vertices directly from the polygon object
            float[] vertices = polyObj.getPolygon().getTransformedVertices();

            // Convert vertices to Box2D's coordinate system (scaled by PPM)
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = vertices[i] / 32;
            }

            // Create a polygon shape in Box2D using the vertices
            Body body = createPolygon(vertices, isStatic);

            // Retrieve the texture name from Tiled map properties
            String textureName = (String) polyObj.getProperties().get("texture");
            if (textureName != null) {
                bodyTextures.put(body, textureName);

                // Set texture as userData for the body
                Texture texture = getTexture(textureName);
                body.setUserData(texture);
            }

            // Log the body creation for debugging
            System.out.println("Created polygon at (" + polyObj.getPolygon().getX() + ", "
                + polyObj.getPolygon().getY() + ")");
        }
    }

    /**
     * Creates a Box2D polygon body and optionally enables rotation after 2 seconds.
     */
    public static Body createPolygon(float[] vertices, boolean isStatic) {
        BodyDef def = new BodyDef();
        def.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true; // Start with fixed rotation

        Body pBody = world.createBody(def);

        // Create and set the polygon shape
        PolygonShape shape = new PolygonShape();
        shape.set(vertices); // Set vertices to create a custom polygon shape

        pBody.createFixture(shape, 1.0f);
        shape.dispose();

        // Schedule enabling rotation after 2 seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                pBody.setFixedRotation(false); // Enable rotation
            }
        }, 2); // 2-second delay

        return pBody;
    }

    /**
     * Retrieves or loads a texture from the cache by its name.
     */
    public static Texture getTexture(String textureName) {
        if (!textureCache.containsKey(textureName)) {
            textureCache.put(textureName, new Texture(textureName));
        }
        return textureCache.get(textureName);
    }

    /**
     * Renders a Box2D body using its associated texture (from userData).
     */
    public static void renderBody(Body body, Batch batch) {
        Object userData = body.getUserData();
        if (userData instanceof Texture) {
            Texture texture = (Texture) userData;

            // Get body position for rendering
            float x = body.getPosition().x * 32; // Convert back to pixel coordinates
            float y = body.getPosition().y * 32;

            // Draw the texture
            batch.draw(texture, x+500, y+600);
        }
    }

    /**
     * Renders all bodies with textures in the world.
     */
    public static void renderAllBodies(Batch batch) {
        for (Body body : bodyTextures.keySet()) {
            renderBody(body, batch);
        }
    }
}
