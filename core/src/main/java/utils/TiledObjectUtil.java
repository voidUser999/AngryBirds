package utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

public class TiledObjectUtil {

    private static World world;

    public static void initialize(World worldInstance) {
        world = worldInstance;
    }

    public static void parseTiledObjectLayer(MapObjects objects, boolean isStatic) {
        for (MapObject object : objects) {
            if (!(object instanceof PolygonMapObject)) continue;

            PolygonMapObject polyObj = (PolygonMapObject) object;

            // Extract vertices directly from the polygon object
            float[] vertices = polyObj.getPolygon().getTransformedVertices();

            // Convert vertices to Box2D's coordinate system (scaled by PPM)
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] /= Constants.PPM;
            }

            // Create a polygon shape in Box2D using the vertices
            Body body = createPolygon(vertices, isStatic);

            // Log the body creation for debugging
            System.out.println("Created polygon at (" + polyObj.getPolygon().getX() + ", "
                + polyObj.getPolygon().getY() + ")");
        }
    }

    public static Body createPolygon(float[] vertices, boolean isStatic) {
        BodyDef def = new BodyDef();
        def.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true; // Start with fixed rotation

        Body pBody = world.createBody(def);

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
}
