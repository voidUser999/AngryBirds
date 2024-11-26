package utils;

import com.angrybirds.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

import static utils.Constants.PPM;

public class TiledObjectUtil implements Serializable {


    private static World world;
    private static  Map<Body, String> bodyTextures = new HashMap<>();
    private static  Map<String, Texture> textureCache = new HashMap<>();


    static HashMap<Body, wood> woodProp = new HashMap<>();
    static HashMap<Body, stone> stoneProp = new HashMap<>();
    static HashMap<Body, glass> glassProp = new HashMap<>();
    static HashMap<Body, enemy0> enemy0Prop = new HashMap<>();
    static HashMap<Body, enemy1> enemy1Prop = new HashMap<>();
    static HashMap<Body, enemy2> enemy2Prop = new HashMap<>();


    public static void initialize(World worldInstance) {
        world = worldInstance;
    }

    public static void parseTiledObjectLayer(MapObjects objects, boolean isStatic) {
        for (MapObject object : objects) {
            if (!(object instanceof PolygonMapObject)) continue;

            PolygonMapObject polyObj = (PolygonMapObject) object;
            float[] vertices = polyObj.getPolygon().getTransformedVertices();


            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = vertices[i] / PPM;
            }


            Vector2 centroid = calculateCentroid(vertices);


            for (int i = 0; i < vertices.length; i += 2) {
                vertices[i] -= centroid.x;
                vertices[i + 1] -= centroid.y;
            }




            String type = (String) polyObj.getProperties().get("Type");
            System.out.println("body type " + type);
            Body body = createPolygon(vertices, centroid, isStatic , type);


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
        //fixtureDef.restitution = 0.2f;



        if ("woodblock".equalsIgnoreCase(type)) {
            wood woodBlock = new wood(); // Create wood instance
            woodProp.put(body, woodBlock);
            body.createFixture(fixtureDef).setUserData("woodblock");
        } else if ("stoneblock".equalsIgnoreCase(type)) {
            stone stoneBlock = new stone(); // Create stone instance
            stoneProp.put(body, stoneBlock);
            body.createFixture(fixtureDef).setUserData("stoneblock");
        } else if ("glassblock".equalsIgnoreCase(type)) {
            glass glassBlock = new glass(); // Create glass instance
            glassProp.put(body, glassBlock);
            body.createFixture(fixtureDef).setUserData("glassblock");
        }
        else if("pig0".equalsIgnoreCase(type)) {
            enemy0 pig0 = new enemy0();
            enemy0Prop.put(body, pig0);
            body.createFixture(fixtureDef).setUserData("pig0");

        }
        else if("pig1".equalsIgnoreCase(type)) {
            enemy1 pig1 = new enemy1();
            enemy1Prop.put(body, pig1);
            body.createFixture(fixtureDef).setUserData("pig1");
        }
        else if("pig2".equalsIgnoreCase(type)) {
            enemy2 pig2 = new enemy2();
            enemy2Prop.put(body, pig2);
            body.createFixture(fixtureDef).setUserData("pig2");
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

    public static Texture getTexture(String textureName) {
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


            Vector2 position = body.getPosition();
            float angle = (float) Math.toDegrees(body.getAngle());


            float x = (position.x * PPM) - (texture.getWidth() / 2f);
            float y = (position.y * PPM) - (texture.getHeight() / 2f);


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
        TiledObjectUtil.woodProp = woodProp;
    }

    public static HashMap<Body, stone> getStoneProp() {
        return stoneProp;
    }

    public static void setStoneProp(HashMap<Body, stone> stoneProp) {
        TiledObjectUtil.stoneProp = stoneProp;
    }

    public static HashMap<Body, glass> getGlassProp() {
        return glassProp;
    }

    public static void setGlassProp(HashMap<Body, glass> glassProp) {
        TiledObjectUtil.glassProp = glassProp;
    }

    public static HashMap<Body, enemy0> getEnemy0Prop() {
        return enemy0Prop;
    }

    public static void setEnemy0Prop(HashMap<Body, enemy0> enemy0Prop) {
        TiledObjectUtil.enemy0Prop = enemy0Prop;
    }

    public static HashMap<Body, enemy1> getEnemy1Prop() {
        return enemy1Prop;
    }

    public static void setEnemy1Prop(HashMap<Body, enemy1> enemy1Prop) {
        TiledObjectUtil.enemy1Prop = enemy1Prop;
    }

    public static Map<Body, String> getBodyTextures() {
        return bodyTextures;
    }

    public static void setBodyTextures(Map<Body, String> bodyTextures) {
        TiledObjectUtil.bodyTextures = bodyTextures;
    }

    public static Map<String, Texture> getTextureCache() {
        return textureCache;
    }

    public static void setTextureCache(Map<String, Texture> textureCache) {
        TiledObjectUtil.textureCache = textureCache;
    }

    public static HashMap<Body, enemy2> getEnemy2Prop() {
        return enemy2Prop;
    }

    public static void setEnemy2Prop(HashMap<Body, enemy2> enemy2Prop) {
        TiledObjectUtil.enemy2Prop = enemy2Prop;
    }
}
