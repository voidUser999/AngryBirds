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
import com.badlogic.gdx.utils.Timer;
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
    private Body player , platform , player1 , player2 ;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Texture tex;
    private int turn;
    Body PP;
    private Stage pauseStage;
    private Stage endStage;
    private Texture  resumeTex, exitTex , savexTex ;
    private ImageButton back;
    private Texture endTex , end2Tex;
    private Image endImage;
    private boolean isPaused = false;
    private boolean isEndScreen = false;
    private int bb ;
    private MyContactListener contactListener;
    private boolean isDestroyed = false;

    //bird properties
    private HashMap<Body ,Prop> birdProp;


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
            // Initialize the world
            world = new World(new Vector2(0, -9.6f), false);

            // Initialize birdProp HashMap
            birdProp = new HashMap<>();

            // Create players and assign properties
            player = createBox(100, 100, 70, 70, false, "thisRed0", 0.5f);
            player1 = createBox(100, 100, 70, 70, false, "thisRed1", 0.5f);
            player2 = createBox(100, 100, 70, 70, false, "thisRed2", 0.5f);

            // Populate birdProp
            birdProp.put(player, new Prop());
            birdProp.put(player1, new Prop());
            birdProp.put(player2, new Prop());

            // Create platform
            platform = createBox(100, 40, 50, 30000, true, "data", -1f);

            // Set up Contact Listener AFTER birdProp is populated
            contactListener = new MyContactListener(birdProp);
            world.setContactListener(contactListener);

            launched = false;

            // Load the map
            if (map != null) {
                map.dispose();
            }

            map = new TmxMapLoader().load("map/level1.tmx");
            tmr = new OrthogonalTiledMapRenderer(map);

            // Initialize the world in TiledObjectUtil
            TiledObjectUtil.initialize(world);

            // Parse the map layers
            TiledObjectUtil.parseTiledObjectLayer(map.getLayers().get("Object Layer 1").getObjects(), false);
        }

        // Clear and reinitialize stages
        stage.clear();
        pauseStage.clear();
        endStage.clear();
        initButtons();
        initPauseMenu();
        initEndStage();
        isEndScreen = false;
        isPaused = false;

        // Set the input processor
        Gdx.input.setInputProcessor(stage);

        // Set up back button
        back = new ImageButton(new TextureRegionDrawable(tex));
        back.setPosition(100, 200); // Position within bounds
        back.setSize(120, 120); // Ensure size is visible
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
                launched = false;
                turn = 0;
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

        app.batch.draw(tex, player.getPosition().x * PPM - 65, player.getPosition().y * PPM - 60, 110, 110);
        app.batch.draw(tex, player1.getPosition().x * PPM - 65, player1.getPosition().y * PPM - 60, 110, 110);
        app.batch.draw(tex, player2.getPosition().x * PPM - 65, player2.getPosition().y * PPM - 60, 110, 110);

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
    private boolean isLowMomentum = false; // To track if bird has low momentum
    private final float MOMENTUM_THRESHOLD = 0.5f; // Speed threshold
    private final float MOMENTUM_TIMEOUT = 1.5f; // 3-second timeout for low momentum
    public void update(float delta){
        stage.act(delta);
        world.step(1/60f , 6 , 2);
        // Process pending body destructions
        if (contactListener instanceof MyContactListener) {
            ((MyContactListener) contactListener).processPendingDestructions(world);
        }
        if(turn == 0){
            PP = player;
        }
        else if(turn == 2){
            PP = player2;
        }
        else if(turn == 1){
            PP = player1;
        }
        // Check if the bird has lost momentum
        if (launched && PP != null) { // Ensure the bird is launched
            Vector2 velocity = PP.getLinearVelocity();
            float speed = velocity.len(); // Total velocity magnitude

            if (speed < MOMENTUM_THRESHOLD) {
                // Bird has low momentum
                if (!isLowMomentum) {
                    isLowMomentum = true;
                    startMomentumTimer(); // Start the timer when speed falls below threshold
                }
            } else {
                // Reset the low momentum state if speed exceeds threshold
                isLowMomentum = false;
            }
        }

        inputUpdate(delta);
        //camera moves with player

        cameraUpdate(delta);
        tmr.setView(app.camera);
        app.batch.setProjectionMatrix(app.camera.combined);
    }
    boolean launched = false;
    float launchSpeed = 24;       // Adjust as needed
    float launchAngle = 55;        // Launch angle in degrees
    float gravity = -6.8f;         // Gravity constant

    public void inputUpdate(float delta) {
        if(turn == 0){
            PP = player;
        }
        else if(turn == 2){
            PP = player2;
        }
        else if(turn == 1){
            PP = player1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !launched) {
            launched = true;

            // Convert angle to radians for trigonometric calculations
            float angleInRadians = (float) Math.toRadians(launchAngle);

            // Calculate initial velocity components
            float initialVelocityX = launchSpeed * (float) Math.cos(angleInRadians);
            float initialVelocityY = launchSpeed * (float) Math.sin(angleInRadians);

            // Apply the initial launch velocity
            PP.setLinearVelocity(initialVelocityX, initialVelocityY);
        }

        // Apply gravity to affect vertical velocity over time if launched
        if (launched) {
            // Get the current velocity
            float currentVelocityX = PP.getLinearVelocity().x;
            float currentVelocityY = PP.getLinearVelocity().y;

            // Update the vertical velocity to simulate gravity
            PP.setLinearVelocity(currentVelocityX, currentVelocityY + gravity * delta);
        }
    }
    int x =30 , y =25;

    private void destroyBird() {

        System.out.println("Destroying " + turn + isDestroyed);
        if(turn == 0){
            PP = player;
        }
        else if(turn == 2 && launched){
            PP = player2;
            isDestroyed = false;
        }
        else if(turn == 1 && launched){
            PP = player1;
            isDestroyed = false;
        }
        if (!isDestroyed) {

            // Move the bird to a safe position (e.g., above the map)
            PP.setTransform(x, y , 0); // Example: move to (0, 100) above the map

            // Optionally set its velocity to zero
            PP.setLinearVelocity(0, 0);
            PP.setAngularVelocity(0);
            PP.setType(BodyDef.BodyType.StaticBody);
            isDestroyed = true;
            System.out.println("Bird moved above the map due to low momentum.");
            turn++;
            launched = false;
            x=x+3;

            System.out.println("DONE");
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

    public Body createBox(int x, int y, int height, int width, boolean isStatic, String temp , float restitute ) {
        Body pBody;
        BodyDef def = new BodyDef();

        // Set body type based on whether it is static or dynamic
        def.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true; // Prevents the body from rotating

        // Create the body in the world
        pBody = world.createBody(def);

        // Define shape and set it as a box
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((float) width / 2 / PPM, (float) height / 2 / PPM);

        // Create a fixture definition to apply properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Adjust density for mass
        fixtureDef.friction = 0.5f; // Adjust friction for sliding interactions
        fixtureDef.restitution = restitute; // Adjust restitution for bounciness


        // Attach the fixture to the body and set user data
        pBody.createFixture(fixtureDef).setUserData(temp);

        // Dispose of the shape to free memory
        shape.dispose();

        return pBody;
    }
    private void startMomentumTimer() {
        // Schedule the destruction of the bird after 3 seconds of low momentum
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {

                destroyBird(); // Destroy the bird after 3 seconds
            }
        }, MOMENTUM_TIMEOUT); // Timeout after 3 seconds
    }

    //getting the ppm value * it if setting the ppm value divide it

}
