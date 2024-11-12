package com.angrybirds;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import utils.TiledObjectUtil;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static utils.Constants.PPM;

public class Level_1 implements Screen{

    private final angryBirds app;
    private Stage stage;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Body player , platform;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Texture tex;



    public Level_1(final angryBirds app){

        this.app = app;
        this.stage = new Stage(new StretchViewport(angryBirds.V_WIDTH , angryBirds.V_HEIGHT , app.camera));
        Gdx.input.setInputProcessor(stage);
        b2dr = new Box2DDebugRenderer();
        tex = new Texture("cat.png");

    }
    @Override
    public void show() {



        world = new World(new Vector2(0, -9.6f), false);

        player = createBox(100 , 100 , 110 , 110 , false);
        platform = createBox(100,40,30000, 50 , true);

        map = new TmxMapLoader().load("map/level1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
//        TiledObjectUtil.parseTiledObjectLayer(world , map.getLayers().get("Object Layer 2").getObjects() , true);
      // TiledObjectUtil.parseTiledObjectLayer(world , map.getLayers().get("Object Layer 1").getObjects() , false);
        // Initialize the world in TiledObjectUtil
        TiledObjectUtil.initialize(world);

// Now call parseTiledObjectLayer with the updated parameters
        TiledObjectUtil.parseTiledObjectLayer(map.getLayers().get("Object Layer 1").getObjects(), false);

        stage.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);


      // tmr.render();
        b2dr.render(world, app.camera.combined.scl(PPM));
        app.batch.begin();
        app.batch.draw(tex , player.getPosition().x * PPM  -60 , player.getPosition().y * PPM -60, 110, 110);


        app.batch.end();



    }
    public void update(float delta){
        stage.act(delta);
        world.step(1/60f , 6 , 2);
        inputUpdate(delta);
        //camera moves with player

        cameraUpdate(delta);
        tmr.setView(app.camera);
        app.batch.setProjectionMatrix(app.camera.combined);
    }
    boolean launched = false;
    float launchSpeed = 24;       // Adjust as needed
    float launchAngle = 60;        // Launch angle in degrees
    float gravity = -6.8f;         // Gravity constant

    public void inputUpdate(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !launched) {
            launched = true;

            // Convert angle to radians for trigonometric calculations
            float angleInRadians = (float) Math.toRadians(launchAngle);

            // Calculate initial velocity components
            float initialVelocityX = launchSpeed * (float) Math.cos(angleInRadians);
            float initialVelocityY = launchSpeed * (float) Math.sin(angleInRadians);

            // Apply the initial launch velocity
            player.setLinearVelocity(initialVelocityX, initialVelocityY);
        }

        // Apply gravity to affect vertical velocity over time if launched
        if (launched) {
            // Get the current velocity
            float currentVelocityX = player.getLinearVelocity().x;
            float currentVelocityY = player.getLinearVelocity().y;

            // Update the vertical velocity to simulate gravity
            player.setLinearVelocity(currentVelocityX, currentVelocityY + gravity * delta);
        }
    }

    private void cameraUpdate(float delta) {
        Vector3 position = app.camera.position;
//        position.x =app.camera.position.x + ( player.getPosition().x * PPM - app.camera.position.x) * .1f;
//        position.y = app.camera.position.y + ( player.getPosition().y * PPM - app.camera.position.y) * .1f;

        position.x = 960;
        position.y = 40;
        app.camera.position.set(position.x, position.y, 0);

        app.camera.update();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        System.out.println("Disposing ");
        stage.dispose();
        b2dr.dispose();
        world.dispose();
        tmr.dispose();
        map.dispose();
        tex.dispose();
    }
    public Body createPlayer() {
        Body pBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(600, 600);
        def.fixedRotation = false;

        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((float) 128 / 2 / PPM, (float) 128 / 2 / PPM);
        pBody.createFixture(shape, 1.0f);//density
        shape.dispose();
        return pBody;
    }

    public Body createBox(int x , int y , int height, int width , boolean isStatic) {
        Body pBody;
        BodyDef def = new BodyDef();
        if(isStatic){
            def.type = BodyDef.BodyType.StaticBody;
        }
        else {
            def.type = BodyDef.BodyType.DynamicBody;
        }

        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((float) height / 2 / PPM, (float) width / 2 / PPM);
        pBody.createFixture(shape, 1.0f);//density
        shape.dispose();
        return pBody;
    }

    //getting the ppm value * it if setting the ppm value divide it

}
