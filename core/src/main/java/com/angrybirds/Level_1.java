package com.angrybirds;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import utils.MyContactListener;
import utils.TiledObjectUtil;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static utils.Constants.PPM;

public class Level_1 implements Screen{
    private static World savedWorld = null;
    private static boolean worldSaved = false;
    private World world;
    private final angryBirds app;
    private Stage stage;
    private Box2DDebugRenderer b2dr;
    private Body player , platform;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Texture tex;


    private Stage pauseStage;
    private Stage endStage;
    private Texture  resumeTex, exitTex , savexTex ;
    private ImageButton back;
    private Texture endTex , end2Tex;
    private Image endImage;
    private boolean isPaused = false;
    private boolean isEndScreen = false;
    private int bb ;
    public Level_1(final angryBirds app){

        this.app = app;
        this.stage = new Stage(new StretchViewport(angryBirds.V_WIDTH , angryBirds.V_HEIGHT , app.camera));
        Gdx.input.setInputProcessor(stage);

        resumeTex = new Texture("resume.png");
        exitTex = new Texture("splash.png");
        savexTex = new Texture("splash.png");
        endTex = new Texture("endScreen.png");
        end2Tex = new Texture("lose.png");
        b2dr = new Box2DDebugRenderer();
        tex = new Texture("cat.png");

        initButtons();
        initPauseMenu();
        initEndStage();
    }
    @Override
    public void show() {
        if (worldSaved && savedWorld != null) {
            // Reuse the saved world
            this.world = savedWorld;
        } else {


            world = new World(new Vector2(0, -9.6f), false);
            world.setContactListener(new MyContactListener());
            launched = false;
            player = createBox(100, 100, 110, 110, false , "this");
            platform = createBox(100, 40, 30000, 50, true , "userdata");

            if (map != null) {
                map.dispose();
            }

            map = new TmxMapLoader().load("map/level1.tmx");
            tmr = new OrthogonalTiledMapRenderer(map);
//        TiledObjectUtil.parseTiledObjectLayer(world , map.getLayers().get("Object Layer 2").getObjects() , true);
            // TiledObjectUtil.parseTiledObjectLayer(world , map.getLayers().get("Object Layer 1").getObjects() , false);
            // Initialize the world in TiledObjectUtil
            TiledObjectUtil.initialize(world);

// Now call parseTiledObjectLayer with the updated parameters
            TiledObjectUtil.parseTiledObjectLayer(map.getLayers().get("Object Layer 1").getObjects(), false);
        }
        stage.clear();
        pauseStage.clear();
        endStage.clear();
        initButtons();
        initPauseMenu();
        initEndStage();
        isEndScreen = false;
        isPaused = false;


        Gdx.input.setInputProcessor(stage);
        back = new ImageButton(new TextureRegionDrawable(tex));
        back.setPosition(100, 200); // Position within bounds
        back.setSize(120, 120);  // Ensure size is visible
        back.setColor(1, 1, 1, 1); // Full opacity
        stage.addActor(back);

    }

    private void clearWorld() {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            world.destroyBody(body);
        }
    }
    private void saveWorldState() {
        worldSaved = true;
        savedWorld = this.world;
    }

    private void initButtons() {
        back = new ImageButton(new TextureRegionDrawable(tex));
        back.setPosition(20, 970);
        back.setSize(120, 120);


        back.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow5Out))));
        stage.addActor(back);

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = true;  // Pause the game
                Gdx.input.setInputProcessor(pauseStage);  // Switch input to pause menu
            }
        });
    }
    private void initPauseMenu() {
        pauseStage = new Stage(new StretchViewport(angryBirds.V_WIDTH, angryBirds.V_HEIGHT, app.camera));
        Texture blackTexture = new Texture("blk.png");
        Texture whiteTexture = new Texture("file.png");



        Image background = new Image(new TextureRegionDrawable(blackTexture));
        Image mmb = new Image(new TextureRegionDrawable(whiteTexture));
        // A black texture
        mmb.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0,-20, 0.5f , Interpolation.pow5Out))));

        background.setSize(angryBirds.V_WIDTH, angryBirds.V_HEIGHT);
        background.setColor(1, 1, 1, 0.3f);

        mmb.setPosition(500 , 100);

        pauseStage.addActor(background);
        pauseStage.addActor(mmb);


        // Resume button
        ImageButton resumeButton = new ImageButton(new TextureRegionDrawable(resumeTex));
        resumeButton.setPosition((float) angryBirds.V_WIDTH / 2 - 275, (float) angryBirds.V_HEIGHT / 2 -20);
        resumeButton.setSize(200, 300);
        resumeButton.setColor(1, 1, 1, 0);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = false;

                Gdx.input.setInputProcessor(stage);
                mmb.clearActions();
                mmb.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow5Out))));
            }
        });


        ImageButton exitButton = new ImageButton(new TextureRegionDrawable(exitTex));
        exitButton.setPosition((float) angryBirds.V_WIDTH / 2 +80, (float) angryBirds.V_HEIGHT / 2 - 20);
        exitButton.setSize(200, 300);
        exitButton.setColor(1, 1, 1, 0);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = false;
                clearWorld();
                app.setScreen(app.level_1);

                mmb.clearActions();
                mmb.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow5Out))));
            }
        });

        ImageButton sav_exitButton = new ImageButton(new TextureRegionDrawable(savexTex));
        sav_exitButton.setPosition((float) angryBirds.V_WIDTH / 2 - 300, (float) angryBirds.V_HEIGHT / 2 - 250);
        sav_exitButton.setSize(600, 100);
        sav_exitButton.setColor(1, 1, 1, 0);
        sav_exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveWorldState();
                app.setScreen(app.levelsScreen);
            }
        });

        pauseStage.addActor(resumeButton);
        pauseStage.addActor(exitButton);
        pauseStage.addActor(sav_exitButton);
    }
    private void initEndStage() {
        endStage = new Stage(new StretchViewport(angryBirds.V_WIDTH, angryBirds.V_HEIGHT, app.camera));


        Texture blackTexture = new Texture("blk.png");
        Image background = new Image(new TextureRegionDrawable(blackTexture));

        background.setSize(angryBirds.V_WIDTH, angryBirds.V_HEIGHT);
        background.setColor(1, 1, 1, 0.3f);


        if (bb == 0){
            endImage = new Image(new TextureRegionDrawable(endTex));
        } else if (bb == 1) {
            endImage = new Image(new TextureRegionDrawable(end2Tex));

        }
        endImage.setPosition((float) angryBirds.V_WIDTH / 2 - endTex.getWidth() / 2, (float) angryBirds.V_HEIGHT / 2 - endTex.getHeight() / 2);
        endImage.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow5Out))));

        ImageButton sav_exitButton1 = new ImageButton(new TextureRegionDrawable(savexTex));
        sav_exitButton1.setPosition((float) angryBirds.V_WIDTH / 2 - 300, (float) angryBirds.V_HEIGHT / 2 - 250);
        sav_exitButton1.setSize(600, 100);
        sav_exitButton1.setColor(1, 1, 1, 0);
        sav_exitButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = false;
                app.setScreen(app.levelsScreen);
            }
        });

        endStage.addActor(background);
        endStage.addActor(endImage);
        endStage.addActor(sav_exitButton1);

    }
    @Override

    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Check if the game is paused
        if (!isPaused && !isEndScreen) {
            update(delta);  // Only update the world when not paused or in the end screen
        }

        // Render the tiled map
        tmr.render();

        // Render Box2D debug lines
        b2dr.render(world, app.camera.combined.scl(PPM));

        // Begin sprite batch rendering
        app.batch.begin();
        app.batch.draw(tex, player.getPosition().x * PPM - 60, player.getPosition().y * PPM - 60, 110, 110);

        // Draw objects from TiledObjectUtil
        TiledObjectUtil.renderAllBodies(app.batch);
        app.batch.end();

        // Handle UI rendering
        if (isPaused) {
            Gdx.input.setInputProcessor(pauseStage);
            pauseStage.act(delta);
            pauseStage.draw();
        } else if (isEndScreen) {
            Gdx.input.setInputProcessor(endStage);
            endStage.act(delta);
            endStage.draw();
        } else {
            stage.draw();
        }
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
    float launchSpeed = 28;       // Adjust as needed
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
        position.y = 540;
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
        if (!isPaused) { // Only dispose if not paused
            clearWorld();
            world.dispose();
            worldSaved = false;
            savedWorld = null;
        }
        stage.dispose();
        b2dr.dispose();

        tmr.dispose();
        map.dispose();
        tex.dispose();
        resumeTex.dispose();
        exitTex.dispose();
        savexTex.dispose();
        endTex.dispose();

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

    public Body createBox(int x , int y , int height, int width , boolean isStatic , String temp) {

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
        pBody.createFixture(shape, 1.0f).setUserData(temp);//density
        shape.dispose();
        return pBody;
    }

    //getting the ppm value * it if setting the ppm value divide it

}
