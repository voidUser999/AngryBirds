package com.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import utils.MyContactListener;
import utils.TiledObjectUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static utils.Constants.PPM;
import static utils.TiledObjectUtil.getTexture;

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
    private Stage endStage1;
    private Stage endStage2;
    private Texture  resumeTex, exitTex , savexTex ;
    private ImageButton back;
    private Texture endTex , end2Tex;
    private Image endImage;
    private boolean isPaused = false;
    private boolean isEndScreen = false;
    private boolean isEndScreenW = false;
    private boolean isEndScreenL = false;

    private int bb = 0 ;
    private MyContactListener contactListener;
    private boolean isDestroyed = false;

    //bird properties
    private HashMap<Body ,Prop> birdProp;
    private HashMap<Body ,wood> woodProp;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();


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
        initEndStageW();
        initEndStageL();
    }
    @Override
    public void show() {
        if (worldSaved && savedWorld != null) {
            // Reuse the saved world
            this.world = savedWorld;
            loadWorldState();  // Load the saved game state
        } else {
            // Initialize the world
            world = new World(new Vector2(0, -9.8f), false);

            // Initialize birdProp HashMap
            birdProp = new HashMap<>();

            // Create players and assign properties
            player = createBox(100, 100, 70, 70, false, "thisRed0", 0.2f);
            player1 = createBox(100, 100, 70, 70, false, "thisRed1", 0.2f);
            player2 = createBox(100, 100, 70, 70, false, "thisRed2", 0.2f);

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
        endStage1.clear();
        endStage2.clear();
        initButtons();
        initPauseMenu();
        initEndStageW();
        initEndStageL();
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
//    private void saveWorldState() {
//        worldSaved = true;
//        savedWorld = this.world;
//    }

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
                isDestroyed = false;
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
    private void initEndStageW() {
        endStage1 = new Stage(new StretchViewport(angryBirds.V_WIDTH, angryBirds.V_HEIGHT, app.camera));


        Texture blackTexture = new Texture("blk.png");
        Image background = new Image(new TextureRegionDrawable(blackTexture));

        background.setSize(angryBirds.V_WIDTH, angryBirds.V_HEIGHT);
        background.setColor(1, 1, 1, 0.3f);



        endImage = new Image(new TextureRegionDrawable(endTex));

        //endImage = new Image(new TextureRegionDrawable(end2Tex));


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

        endStage1.addActor(background);
        endStage1.addActor(endImage);
        endStage1.addActor(sav_exitButton1);

    }
    private void initEndStageL() {
        endStage2 = new Stage(new StretchViewport(angryBirds.V_WIDTH, angryBirds.V_HEIGHT, app.camera));


        Texture blackTexture = new Texture("blk.png");
        Image background = new Image(new TextureRegionDrawable(blackTexture));

        background.setSize(angryBirds.V_WIDTH, angryBirds.V_HEIGHT);
        background.setColor(1, 1, 1, 0.3f);





        endImage = new Image(new TextureRegionDrawable(end2Tex));


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

        endStage2.addActor(background);
        endStage2.addActor(endImage);
        endStage2.addActor(sav_exitButton1);

    }
    @Override

    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Check if the game is paused
        if (!isPaused && !isEndScreen) {
            update(delta);
            handleInput(); // Only update the world when not paused or in the end screen
        }

        // Render the tiled map
        tmr.render();

        // Render Box2D debug lines (optional, for debugging purposes)
        b2dr.render(world, app.camera.combined.scl(PPM));

        // Begin sprite batch rendering for textures
        app.batch.begin();

        // Draw the player textures
        app.batch.draw(tex, player.getPosition().x * PPM - 65, player.getPosition().y * PPM - 60, 110, 110);
        app.batch.draw(tex, player1.getPosition().x * PPM - 65, player1.getPosition().y * PPM - 60, 110, 110);
        app.batch.draw(tex, player2.getPosition().x * PPM - 65, player2.getPosition().y * PPM - 60, 110, 110);

        // Draw textures for polygon bodies
        renderPolygonTextures();

        // Draw objects from TiledObjectUtil
        TiledObjectUtil.renderAllBodies(app.batch);

        app.batch.end();

        // Draw the slingshot line if dragging
        if (isDragging) {
            app.batch.begin();
            drawSlingLine(slingStart, slingEnd); // Draw a line for the slingshot
            app.batch.end();
        }

        // Handle UI rendering
        if (isPaused) {
            Gdx.input.setInputProcessor(pauseStage);
            pauseStage.act(delta);
            pauseStage.draw();
        } else if (isEndScreen) {
            if (isEndScreenW) {
                Gdx.input.setInputProcessor(endStage1);
                endStage1.act(delta);
                endStage1.draw();
            } else if (isEndScreenL) {
                Gdx.input.setInputProcessor(endStage2);
                endStage2.act(delta);
                endStage2.draw();
            }
        } else {
            stage.draw();
        }

        // Check if all birds are destroyed
        if (turn == 3) {
            checkWinOrLose();
        }
    }

    private void renderPolygonTextures() {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            // Retrieve the texture path from TiledObjectUtil
            String texturePath = TiledObjectUtil.getBodyTextures().get(body);

            if (texturePath != null) {
                Texture texture = getTexture(texturePath); // Load texture from the cache
                TextureRegion textureRegion = new TextureRegion(texture);
                // Get body's position and rotation
                Vector2 position = body.getPosition();
                float angle = (float) Math.toDegrees(body.getAngle());

//                // Calculate width and height based on your polygon body size
//                float width = 50;  // Adjust to fit your body size
//                float height = 50; // Adjust to fit your body size
                float x = (position.x * PPM) - (texture.getWidth() / 2f);
                float y = (position.y * PPM) - (texture.getHeight() / 2f);

                // Draw the texture
                app.batch.draw(
                    textureRegion,
                    x, y,
                    textureRegion.getRegionWidth() / 2f, texture.getHeight() / 2f,
                    textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                    1f, 1f,
                    angle
                );
            }
        }
    }

    private boolean isLowMomentum = false;
    private final float MOMENTUM_THRESHOLD = 0.5f;
    private final float MOMENTUM_TIMEOUT = 1.5f;
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


        //camera moves with player

        cameraUpdate(delta);
        tmr.setView(app.camera);
        app.batch.setProjectionMatrix(app.camera.combined);
    }
     boolean launched = false;

    // Add variables for slingshot mechanics
    private boolean isDragging = false;
    private Vector2 slingStart = new Vector2(); // Starting position of the bird
    private Vector2 slingEnd = new Vector2();   // End position after drag
    private float maxDragDistance = 100f;// To check if the bird is being dragged
    private Vector2 visualBirdPosition = new Vector2(); // For smooth dragging visuals
    private int trajectoryPoints = 20; // Number of trajectory points to draw
    private float trajectoryTimeStep = 0.1f; // Time step between trajectory points

    private float timer = 0; // Timer to count down after launch
    private boolean checkWinScheduled = false; // Flag to ensure checkWin is called after the delay

    private void handleInput() {
        if (turn == 0) {
            PP = player;
        } else if (turn == 2) {
            PP = player2;
        } else if (turn == 1) {
            PP = player1;
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) { // If left mouse button is pressed
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            app.camera.unproject(touchPos); // Convert to world coordinates

            Vector2 touchPoint = new Vector2(touchPos.x, touchPos.y);

            if (!isDragging && PP.getPosition().dst(touchPoint.scl(1 / PPM)) <= 1) {
                isDragging = true;
                slingStart.set(PP.getPosition().scl(PPM));
            }

            if (isDragging) {
                slingEnd.set(touchPoint);

                // Limit drag distance
                if (slingEnd.dst(slingStart) > maxDragDistance) {
                    slingEnd.set(slingStart.cpy().add(slingEnd.cpy().sub(slingStart).nor().scl(maxDragDistance)));
                }

                // Update visual bird position for dragging effect
                visualBirdPosition.set(slingEnd.cpy());
            }
        } else if (isDragging) { // Mouse released
            isDragging = false;
            launched = true;

            // Calculate launch force
            Vector2 launchForce = slingStart.cpy().sub(slingEnd).scl(10f); // Scale for desired launch power
            PP.setLinearVelocity(launchForce.scl(1 / PPM));

            // Start the 3-second timer
            timer = 3f;
            checkWinScheduled = true; // Flag to call checkWin after the timer expires
        }

        // Update the timer (this should be called in the render or update method)
        if (timer > 0) {
            timer -= Gdx.graphics.getDeltaTime();  // Decrease timer by frame delta time
        }

        // If timer reaches zero, check for win
        if (timer <= 0 && checkWinScheduled) {
            checkWin(); // Call the checkWin method
            checkWinScheduled = false; // Reset flag to prevent repeated calls
        }
    }


    // Render slingshot line and trajectory
    private void drawSlingLine(Vector2 start, Vector2 end) {
        // Draw slingshot line
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(start.x, start.y, end.x, end.y);
        shapeRenderer.end();

        // Draw trajectory dots
        drawTrajectoryDots(start, end);
    }

    private void drawTrajectoryDots(Vector2 start, Vector2 end) {
        Vector2 launchForce = start.cpy().sub(end).scl(10f); // Scale for trajectory simulation
        Vector2 position = PP.getPosition().cpy();
        Vector2 velocity = launchForce.scl(1 / PPM); // Convert to world scale
        float gravity = world.getGravity().y; // Use Box2D gravity

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.PINK);

        for (int i = 0; i < trajectoryPoints; i++) {
            float time = i * trajectoryTimeStep;

            // Calculate trajectory point
            float x = position.x + velocity.x * time;
            float y = position.y + velocity.y * time + 0.5f * gravity * time * time;

            // Draw point
            shapeRenderer.circle(x * PPM, y * PPM, 3); // Scale to PPM
        }

        shapeRenderer.end();
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
    private void checkWin() {
        boolean allEnemiesDestroyed = true;

        // Check enemy0Prop for any remaining HP
        for (enemy0 enemy : TiledObjectUtil.getEnemy0Prop().values()) {
            if (enemy.getHp() > 0) {
                allEnemiesDestroyed = false;
                break;
            }
        }

        // Check enemy1Prop for any remaining HP (if enemy0Prop passed)
        if (allEnemiesDestroyed) {
            for (enemy1 enemy : TiledObjectUtil.getEnemy1Prop().values()) {
                if (enemy.getHp() > 0) {
                    allEnemiesDestroyed = false;
                    break;
                }
            }
        }

        // Trigger win or lose screen
        if (allEnemiesDestroyed) {
            triggerWinScreen();
        }
    }
    private void checkWinOrLose() {
        boolean allEnemiesDestroyed = true;

        // Check enemy0Prop for any remaining HP
        for (enemy0 enemy : TiledObjectUtil.getEnemy0Prop().values()) {
            if (enemy.getHp() > 0) {
                allEnemiesDestroyed = false;
                break;
            }
        }

        // Check enemy1Prop for any remaining HP (if enemy0Prop passed)
        if (allEnemiesDestroyed) {
            for (enemy1 enemy : TiledObjectUtil.getEnemy1Prop().values()) {
                if (enemy.getHp() > 0) {
                    allEnemiesDestroyed = false;
                    break;
                }
            }
        }

        // Trigger win or lose screen
        if (allEnemiesDestroyed) {

            triggerWinScreen();
        } else {

            triggerLoseScreen();
        }
    }

    private void triggerWinScreen() {
        isEndScreen = true;
        isEndScreenW = true;

        // Set up the win screen elements or switch to a win screen
        System.out.println("You Win!");

    }

    private void triggerLoseScreen() {
        isEndScreen = true;
        isEndScreenL = true;

        // Set up the lose screen elements or switch to a lose screen
        System.out.println("You Lose!");

    }

    //getting the ppm value * it if setting the ppm value divide it
    private void saveWorldState() {
        worldSaved = true;
        savedWorld = this.world;

        List<BodyData> bodyDataList = new ArrayList<>();
        List<PolygonBodyData> polygonBodyDataList = new ArrayList<>();

        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            String type = getBodyType(body);
            System.out.println("Body type = " + type);

            float x = body.getPosition().x;
            float y = body.getPosition().y;
            float angle = body.getAngle();
            float velocityX = body.getLinearVelocity().x;
            float velocityY = body.getLinearVelocity().y;

            if ("img_map/mats/tri1_wood1_1_r.png".equals(type) || " img_map/mats/sqr_wood1_1.png".equals(type) || "img_map/mats/sqr_stone1_1.png".equals(type) ||"img_map/mats/sqr_wood1_1.png".equals(type) ||"img_map/mats/s_stone1_1.png".equals(type) ||"img_map/mats/long_wood1_1_v.png".equals(type) ||"img_map/mats/wood1_1.png".equals(type) ||"img_map/mats/tri1_wood1_1.png".equals(type) ||"img_map/mats/long_wood1_1.png".equals(type) ||"img_map/pigs/pig4_1.png".equals(type) ||"img_map/mats/s_glass1_1.png".equals(type)||"img_map/pigs/pig3_1.png".equals(type) ) {
                // Handle polygon-specific data
                Fixture fixture = body.getFixtureList().first(); // Assuming one fixture per body
                if (fixture.getShape() instanceof PolygonShape) {
                    PolygonShape shape = (PolygonShape) fixture.getShape();
                    int vertexCount = shape.getVertexCount();
                    float[] vertices = new float[vertexCount * 2];
                    Vector2 vertex = new Vector2();

                    for (int i = 0; i < vertexCount; i++) {
                        shape.getVertex(i, vertex);
                        vertices[i * 2] = vertex.x;
                        vertices[i * 2 + 1] = vertex.y;
                    }

                    String textureName = TiledObjectUtil.getBodyTextures().get(body);
                    polygonBodyDataList.add(new PolygonBodyData(vertices, textureName, x, y, angle, velocityX, velocityY));
                }
            } else {
                // Handle rectangular or other bodies
                bodyDataList.add(new BodyData(x, y, angle, velocityX, velocityY, type));
            }
        }

        // Create a GameState object and serialize it
        GameState gameState = new GameState(bodyDataList, polygonBodyDataList);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game_save.dat"))) {
            out.writeObject(gameState);
            System.out.println("Game state saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getBodyType(Body body) {
        // Check if user data is assigned to the body
        Object userData = body.getUserData();

        if (userData != null) {
            // If userData is available, return it as a string
            return userData.toString();
        } else if (body == platform) {
            return "platform";
        }
        else if (body == player){
            return "thisRed0";
        }
        else if (body == player1){
            return "thisRed1";
        }
        else if (body == player2){
            return "thisRed2";
        }

        return "unknown";
    }

    private void loadWorldState() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("game_save.dat"))) {
            GameState gameState = (GameState) in.readObject();
            List<BodyData> bodyDataList = gameState.getBodyDataList();
            List<PolygonBodyData> polygonBodyDataList = gameState.getPolygonBodyDataList();

            clearWorld();
            world = new World(new Vector2(0, -9.8f), false);

            for (BodyData bodyData : bodyDataList) {
                createBodyFromData(bodyData);
            }

            for (PolygonBodyData polygonBodyData : polygonBodyDataList) {
                createPolygonBodyFromData(polygonBodyData);
            }

            System.out.println("Game state loaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPolygonBodyFromData(PolygonBodyData polygonBodyData) {
        BodyDef def = new BodyDef();
        def.position.set(polygonBodyData.getX(), polygonBodyData.getY());
        def.angle = polygonBodyData.getAngle();
        def.type = BodyDef.BodyType.DynamicBody;

        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.set(polygonBodyData.getVertices());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;

        body.createFixture(fixtureDef);
        TiledObjectUtil.getBodyTextures().put(body, polygonBodyData.getTextureName());

        shape.dispose();
    }


    private void createBodyFromData(BodyData bodyData) {
        // Define the body (e.g., a dynamic body, static body, etc.)
        BodyDef def = new BodyDef();
        def.position.set(bodyData.getX(), bodyData.getY());
        def.angle = bodyData.getAngle();
        if(bodyData.getType().equals("platform")){
            def.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(def);

            // Set the body type and velocity based on the saved data
            body.setLinearVelocity(bodyData.getVelocityX(), bodyData.getVelocityY());

            // Recreate the body's fixtures (you need to know the shape and type of each body)
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(3000, 1);  // Example, you might need to adjust this based on your object type

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1.0f;
            fixtureDef.friction = 0.5f;

            body.createFixture(fixtureDef);
            shape.dispose();
            return;
        }
        else if (bodyData.getType().equals("thisRed1") || bodyData.getType().equals("thisRed2") || bodyData.getType().equals("thisRed3")){
            def.type = BodyDef.BodyType.DynamicBody;
            Body body = world.createBody(def);

            // Set the body type and velocity based on the saved data
            body.setLinearVelocity(bodyData.getVelocityX(), bodyData.getVelocityY());

            // Recreate the body's fixtures (you need to know the shape and type of each body)
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(1, 1);  // Example, you might need to adjust this based on your object type

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1.0f;
            fixtureDef.friction = 0.5f;

            body.createFixture(fixtureDef);
            shape.dispose();
        }
        else if (bodyData.getType().equals("img_map/mats/s_glass1_1.png")){

        }

        // Create a body
        Body body = world.createBody(def);

        // Set the body type and velocity based on the saved data
        body.setLinearVelocity(bodyData.getVelocityX(), bodyData.getVelocityY());

        // Recreate the body's fixtures (you need to know the shape and type of each body)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);  // Example, you might need to adjust this based on your object type

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

}
