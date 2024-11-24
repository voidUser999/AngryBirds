package utils;

import com.angrybirds.glass;
import com.angrybirds.stone;
import com.angrybirds.wood;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import java.util.HashMap;
import java.util.Map;

import static utils.Constants.PPM;

public class TiledObjectUtil2 {

    private static World world;
    private static final Map<Body, String> bodyTextures = new HashMap<>();
    private static final Map<String, Texture> textureCache = new HashMap<>();

    static HashMap<Body, wood> woodProp = new HashMap<>();
    static HashMap<Body, stone> stoneProp = new HashMap<>();
    static HashMap<Body, glass> glassProp = new HashMap<>();

    public static void initialize(World worldInstance) {
        world = worldInstance;
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

            // Offset vertices relative to the centroid
            for (int i = 0; i < vertices.length; i += 2) {
                vertices[i] -= centroid.x;
                vertices[i + 1] -= centroid.y;
            }

            // Create Box2D body at the centroid
            String type = (String) polyObj.getProperties().get("Type");
            Body body = createPolygon(vertices, centroid, isStatic , type);

            // Retrieve the texture name and associate it with the body
            String textureName = (String) polyObj.getProperties().get("texture");
            if (textureName != null) {
                bodyTextures.put(body, textureName);

                Texture texture = getTexture(textureName);
                body.setUserData(texture);
            }



        }
    }

    private static Body createPolygon(float[] vertices, Vector2 position, boolean isStatic , String type) {
        BodyDef def = new BodyDef();
        def.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.position.set(position);

        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f;


        if ("wood".equalsIgnoreCase(type)) {
            wood woodBlock = new wood(); // Create wood instance
            woodProp.put(body, woodBlock);
            body.createFixture(fixtureDef).setUserData("woodblock");
        } else if ("stone".equalsIgnoreCase(type)) {
            stone stoneBlock = new stone(); // Create stone instance
            stoneProp.put(body, stoneBlock);
            body.createFixture(fixtureDef).setUserData("stoneblock");
        } else if ("glass".equalsIgnoreCase(type)) {
            glass glassBlock = new glass(); // Create glass instance
            glassProp.put(body, glassBlock);
            body.createFixture(fixtureDef).setUserData("glassblock");
        }

        shape.dispose();

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                body.setFixedRotation(false);
            }
        }, 0.5f);
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
            float angle = (float) Math.toDegrees(body.getAngle());

            // Convert position to pixel coordinates
            float x = (position.x * PPM) - (texture.getWidth() / 2f);
            float y = (position.y * PPM) - (texture.getHeight() / 2f);

            // Draw the texture with rotation
            batch.draw(
                textureRegion,
                x, y,
                textureRegion.getRegionWidth() / 2f, texture.getHeight() / 2f,
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                1f, 1f,
                angle
            );
        }
    }

    public static void renderAllBodies(Batch batch) {
        for (Body body : bodyTextures.keySet()) {
            renderBody(body, batch);
        }
    }

    public static HashMap<Body, wood> getWoodProp() {
        return woodProp;
    }

    public static void setWoodProp(HashMap<Body, wood> woodProp) {
        TiledObjectUtil2.woodProp = woodProp;
    }

    public static HashMap<Body, stone> getStoneProp() {
        return stoneProp;
    }

    public static void setStoneProp(HashMap<Body, stone> stoneProp) {
        TiledObjectUtil2.stoneProp = stoneProp;
    }

    public static HashMap<Body, glass> getGlassProp() {
        return glassProp;
    }

    public static void setGlassProp(HashMap<Body, glass> glassProp) {
        TiledObjectUtil2.glassProp = glassProp;
    }
}
