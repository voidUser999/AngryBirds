package com.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MainMenuScreen implements Screen {

    private final angryBirds app;

    private Stage stage;

    private Skin skin;
    private Image splashImg;
    private ImageButton buttonPlay;
    private ImageButton buttonExit;
    private ImageButton buttonSettings;
    private Image splashImg2;
    private Texture bptex;
    private Texture bqtex;
    private Texture bptex2;
    private Texture bqtex2;

    private Texture b;
    private Texture b2;

    private Texture resumeTex, exitTex , savexTex;

    private Stage settingsStage;
    private boolean settingsClicked = false;

    private Texture blurTex;

    private ImageButton button2;
    private ImageButton button3;
    private ImageButton button4;

    private Texture c;
    private Texture c2;

    private Texture s;
    private Texture s2;

    private Texture m;
    private Texture m2;



    private ShapeRenderer shapeRenderer;

    public MainMenuScreen(final angryBirds app){
        this.app = app;
        stage = new Stage(new StretchViewport(angryBirds.V_WIDTH,angryBirds.V_HEIGHT, app.camera));
        this.shapeRenderer = new ShapeRenderer();

        bptex = new Texture("button_play.png");
        bqtex = new Texture("button_quit.png");
        bqtex2  = new Texture("button_quit2.png");
        bptex2 = new Texture("button_play2.png");

        b = new Texture("settings.png");
        b2 = new Texture("sett.png");

        s = new Texture("sound.png");
        s2 = new Texture("sound2.png");

        m = new Texture("music.png");
        m2 = new Texture("music2.png");


        c = new Texture("back_button.png");
        c2 = new Texture("back_dark.png");


        blurTex = new Texture("blur_bg.png");

    }

    @Override
    public void show() {
        System.out.println("MAIN MENU");
        Gdx.input.setInputProcessor(stage);

        stage.clear();
        Texture splashTex = app.assets.get("abc.png", Texture.class);
        Texture splashTex2 = app.assets.get("ab_name.png", Texture.class);
        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("skin/uiskin.atlas" , TextureAtlas.class));
        this.skin.add("default-font", app.font24);
        this.skin.load(Gdx.files.internal("uiskin.json"));
        splashImg = new Image(splashTex);
        splashImg.setHeight(stage.getHeight());
        splashImg.setWidth(stage.getWidth());

        splashImg2 = new Image(splashTex2);
//        splashImg2.setHeight(stage.getHeight());
//        splashImg2.setWidth(stage.getWidth());


        System.out.println("XWFC");
        stage.addActor(splashImg);
        stage.addActor(splashImg2);
        //splashImg.setOrigin(splashImg.getWidth()/2, splashImg.getHeight()/2);
        splashImg2.setPosition(600  , 800);
        splashImg.setPosition(0, 0);
      //  splashImg.addAction(sequence(alpha(0), scaleTo(.1f,.1f), parallel(fadeIn(2f, Interpolation.pow2),scaleTo(2f,2f,2.5f, Interpolation.pow5), moveTo(stage.getWidth()/2-32, stage.getHeight()/2-32 , 2f,Interpolation.swing)),delay(1.5f), fadeOut(1.25f)));
        initButtons();
        initSettingsMenu();


    }

    private void initButtons() {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new TextureRegion(bptex));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new TextureRegion(bptex2));
        TextureRegionDrawable buttonOver = new TextureRegionDrawable(new TextureRegion(bptex2));

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonUp;
        buttonStyle.down = buttonDown;
        buttonStyle.over = buttonOver;

        buttonPlay = new ImageButton(buttonStyle);
        buttonPlay.setPosition(850, 350);
        buttonPlay.setSize(200, 75);

        buttonPlay.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow5Out))));
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                if (pointer == -1) {
                    buttonPlay.addAction(Actions.moveBy(+5, -5, 0.1f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                if (pointer == -1) {
                    buttonPlay.addAction(Actions.moveBy(-5, 5, 0.1f));
                }
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.levelsScreen);
            }
        });


        TextureRegionDrawable buttonUp1 = new TextureRegionDrawable(new TextureRegion(bqtex));
        TextureRegionDrawable buttonDown1 = new TextureRegionDrawable(new TextureRegion(bqtex2));
        TextureRegionDrawable buttonOver1 = new TextureRegionDrawable(new TextureRegion(bqtex2));

        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonUp1;
        buttonStyle.down = buttonDown1;
        buttonStyle.over = buttonOver1;


        buttonExit = new ImageButton(buttonStyle);
        buttonExit.setPosition(850, 220);
        buttonExit.setSize(200, 75);
        buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow5Out))));
        buttonExit.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                if (pointer == -1) {
                    buttonExit.addAction(Actions.moveBy(+5, -5, 0.1f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                // Move back to the original position
                if (pointer == -1) {
                    buttonExit.addAction(Actions.moveBy(-5, 5, 0.1f));
                }
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {

                Gdx.app.exit();
            }
        });

        TextureRegionDrawable buttonUp3 = new TextureRegionDrawable(new TextureRegion(b));
        TextureRegionDrawable buttonDown3 = new TextureRegionDrawable(new TextureRegion(b2));
        TextureRegionDrawable buttonOver3 = new TextureRegionDrawable(new TextureRegion(b2));

        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonUp3;
        buttonStyle.down = buttonDown3;
        buttonStyle.over = buttonOver3;

        buttonSettings = new ImageButton(buttonStyle);
        buttonSettings.setPosition(1780, 30);
        buttonSettings.setSize(120, 120);

        buttonSettings.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow5Out))));
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                // Move slightly down and left when hovered
                if (pointer == -1) {  // Ensure it's only triggered by hover, not by touch
                    buttonSettings.addAction(Actions.moveBy(+5, -5, 0.1f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                // Move back to the original position when the mouse leaves
                if (pointer == -1) {  // Ensure it's only triggered by hover exit
                    buttonSettings.addAction(Actions.moveBy(-5, 5, 0.1f));
                }
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsClicked = true;
                Gdx.input.setInputProcessor(settingsStage);
//
            }
        });

        stage.addActor(buttonPlay);
        stage.addActor(buttonExit);
        stage.addActor(buttonSettings);


    }


    private void initSettingsMenu() {
        settingsStage = new Stage(new StretchViewport(angryBirds.V_WIDTH, angryBirds.V_HEIGHT, app.camera));

        Image blurBackground = new Image(new TextureRegionDrawable(new TextureRegion(blurTex)));

        blurBackground.setSize(angryBirds.V_WIDTH, angryBirds.V_HEIGHT);


        blurBackground.setPosition(0, 0);


        settingsStage.addActor(blurBackground);


        TextureRegionDrawable buttonUp1 = new TextureRegionDrawable(new TextureRegion(s));
        TextureRegionDrawable buttonDown1 = new TextureRegionDrawable(new TextureRegion(s2));
        TextureRegionDrawable buttonOver1 = new TextureRegionDrawable(new TextureRegion(s2));

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonUp1;
        buttonStyle.down = buttonDown1;
        buttonStyle.over = buttonOver1;


        button2 = new ImageButton(buttonStyle);
        button2.setPosition((float) angryBirds.V_WIDTH / 2 - 275, (float) angryBirds.V_HEIGHT / 2 - 20);
        button2.setSize(235 , 235);
        button2.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0,-20, 0.5f , Interpolation.pow5Out))));
        button2.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                // Move slightly down and left when hovered
                if (pointer == -1) {  // Ensure it's only triggered by hover, not by touch
                    button2.addAction(Actions.moveBy(+5, -5, 0.1f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                // Move back to the original position when the mouse leaves
                if (pointer == -1) {  // Ensure it's only triggered by hover exit
                    button2.addAction(Actions.moveBy(-5, 5, 0.1f));
                }
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {

//                sound mute krne ka logic
            }
        });




        TextureRegionDrawable buttonUp2 = new TextureRegionDrawable(new TextureRegion(m));
        TextureRegionDrawable buttonDown2 = new TextureRegionDrawable(new TextureRegion(m2));
        TextureRegionDrawable buttonOver2 = new TextureRegionDrawable(new TextureRegion(m2));

        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonUp2;      // Default button texture
        buttonStyle.down = buttonDown2;  // Texture when button is pressed
        buttonStyle.over = buttonOver2;


        button3 = new ImageButton(buttonStyle);
        button3.setPosition((float) angryBirds.V_WIDTH / 2 + 80, (float) angryBirds.V_HEIGHT / 2 - 20);
        button3.setSize(235 , 235);
        button3.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0,-20, 0.5f , Interpolation.pow5Out))));
        button3.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                // Move slightly down and left when hovered
                if (pointer == -1) {  // Ensure it's only triggered by hover, not by touch
                    button3.addAction(Actions.moveBy(+5, -5, 0.1f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                // Move back to the original position when the mouse leaves
                if (pointer == -1) {  // Ensure it's only triggered by hover exit
                    button3.addAction(Actions.moveBy(-5, 5, 0.1f));
                }
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {

//                music mute krne ka logic
            }
        });





        TextureRegionDrawable buttonUp3 = new TextureRegionDrawable(new TextureRegion(c));
        TextureRegionDrawable buttonDown3 = new TextureRegionDrawable(new TextureRegion(c2));
        TextureRegionDrawable buttonOver3 = new TextureRegionDrawable(new TextureRegion(c2));

        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonUp3;      // Default button texture
        buttonStyle.down = buttonDown3;  // Texture when button is pressed
        buttonStyle.over = buttonOver3;


        button4 = new ImageButton(buttonStyle);
        button4.setPosition(1 , 47);
        button4.setSize(160 , 160);
        button4.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0,-20, 0.5f , Interpolation.pow5Out))));
        button4.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                // Move slightly down and left when hovered
                if (pointer == -1) {  // Ensure it's only triggered by hover, not by touch
                    button4.addAction(Actions.moveBy(+5, -5, 0.1f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                // Move back to the original position when the mouse leaves
                if (pointer == -1) {  // Ensure it's only triggered by hover exit
                    button4.addAction(Actions.moveBy(-5, 5, 0.1f));
                }
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsClicked = false;
                app.setScreen(app.mainMenuScreen);
            }
        });


        settingsStage.addActor(button2);
        settingsStage.addActor(button3);
        settingsStage.addActor(button4);

    }

    private void update(float delta){
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        if (settingsClicked) {
            settingsStage.act(delta);  // Update the settings stage
            settingsStage.draw();      // Draw the settings stage when it's clicked
        } else {
            stage.draw();  // Draw the main stage if the settings menu is not active
        }

        app.batch.begin();
        app.font24.draw(app.batch, "SCREEN:MAIN MENU", 20, 20);
        app.batch.end();
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
    stage.dispose();
    shapeRenderer.dispose();
    settingsStage.dispose();
    }
}
