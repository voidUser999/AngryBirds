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

public class LevelsScreen implements Screen {

    private final angryBirds app;

    private Stage stage;

    private Skin skin;
    private Image splashImg;
    private ImageButton button1;
    private ImageButton button2;
    private ImageButton button3;
    private ImageButton button4;
    private Texture bptex;
    private Texture bqtex;
    private Texture bptex2;
    private Texture bqtex2;
    private Texture brtex;
    private Texture brtex2;

    private Texture b;
    private Texture b2;



    private ShapeRenderer shapeRenderer;

    public LevelsScreen(final angryBirds app){
        this.app = app;
        stage = new Stage(new StretchViewport(angryBirds.V_WIDTH,angryBirds.V_HEIGHT, app.camera));
        this.shapeRenderer = new ShapeRenderer();


        bqtex = new Texture("1.png");
        bqtex2  = new Texture("1d.png");

        bptex = new Texture("2.png");
        bptex2 = new Texture("2d.png");

        brtex = new Texture("3.png");
        brtex2 = new Texture("3d.png");

        b = new Texture("back_button.png");
        b2 = new Texture("back_dark.png");

    }

    @Override
    public void show() {
        System.out.println("MAIN MENU");
        Gdx.input.setInputProcessor(stage);

        stage.clear();
        Texture splashTex = app.assets.get("levels2.png", Texture.class);
        Texture splashTex2 = app.assets.get("ab_name.png", Texture.class);
        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("skin/uiskin.atlas" , TextureAtlas.class));
        this.skin.add("default-font", app.font24);
        this.skin.load(Gdx.files.internal("uiskin.json"));
        splashImg = new Image(splashTex);
        splashImg.setHeight(stage.getHeight());
        splashImg.setWidth(stage.getWidth());


//        splashImg2.setHeight(stage.getHeight());
//        splashImg2.setWidth(stage.getWidth());


        System.out.println("XWFC");
        stage.addActor(splashImg);
        //splashImg.setOrigin(splashImg.getWidth()/2, splashImg.getHeight()/2);
        splashImg.setPosition(0, 0);
        //  splashImg.addAction(sequence(alpha(0), scaleTo(.1f,.1f), parallel(fadeIn(2f, Interpolation.pow2),scaleTo(2f,2f,2.5f, Interpolation.pow5), moveTo(stage.getWidth()/2-32, stage.getHeight()/2-32 , 2f,Interpolation.swing)),delay(1.5f), fadeOut(1.25f)));
        initButtons();


    }

    private void initButtons() {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new TextureRegion(bptex));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new TextureRegion(bptex2));
        TextureRegionDrawable buttonOver = new TextureRegionDrawable(new TextureRegion(bptex2));

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonUp;
        buttonStyle.down = buttonDown;
        buttonStyle.over = buttonOver;

        button1 = new ImageButton(buttonStyle);
        button1.setPosition(760 , 451);
        button1.setSize(235 , 235);

        button1.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0,-20, 0.5f , Interpolation.pow5Out))));
        button1.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                if (pointer == -1) {
                    button1.addAction(Actions.moveBy(+5, -5, 0.1f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                if (pointer == -1) {
                    button1.addAction(Actions.moveBy(-5, 5, 0.1f));
                }
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.lvl_2);
            }
        });


        TextureRegionDrawable buttonUp1 = new TextureRegionDrawable(new TextureRegion(bqtex));
        TextureRegionDrawable buttonDown1 = new TextureRegionDrawable(new TextureRegion(bqtex2));
        TextureRegionDrawable buttonOver1 = new TextureRegionDrawable(new TextureRegion(bqtex2));

        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonUp1;
        buttonStyle.down = buttonDown1;
        buttonStyle.over = buttonOver1;


        button2 = new ImageButton(buttonStyle);
        button2.setPosition(1100 , 150);
        button2.setSize(235 , 235);
        button2.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0,-20, 0.5f , Interpolation.pow5Out))));
        button2.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                if (pointer == -1) {
                    button2.addAction(Actions.moveBy(+5, -5, 0.1f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                if (pointer == -1) {
                    button2.addAction(Actions.moveBy(-5, 5, 0.1f));
                }
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {

                app.setScreen(app.level_1);
            }
        });



        TextureRegionDrawable buttonUp2 = new TextureRegionDrawable(new TextureRegion(brtex));
        TextureRegionDrawable buttonDown2 = new TextureRegionDrawable(new TextureRegion(brtex2));
        TextureRegionDrawable buttonOver2 = new TextureRegionDrawable(new TextureRegion(brtex2));

        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonUp2;
        buttonStyle.down = buttonDown2;
        buttonStyle.over = buttonOver2;


        button3 = new ImageButton(buttonStyle);
        button3.setPosition(1340 , 451);
        button3.setSize(235 , 235);
        button3.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0,-20, 0.5f , Interpolation.pow5Out))));
        button3.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                if (pointer == -1) {
                    button3.addAction(Actions.moveBy(+5, -5, 0.1f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                if (pointer == -1) {
                    button3.addAction(Actions.moveBy(-5, 5, 0.1f));
                }
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {

                app.setScreen(app.lvl_3);
            }
        });

        TextureRegionDrawable buttonUp3 = new TextureRegionDrawable(new TextureRegion(b));
        TextureRegionDrawable buttonDown3 = new TextureRegionDrawable(new TextureRegion(b2));
        TextureRegionDrawable buttonOver3 = new TextureRegionDrawable(new TextureRegion(b2));

        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonUp3;
        buttonStyle.down = buttonDown3;
        buttonStyle.over = buttonOver3;


        button4 = new ImageButton(buttonStyle);
        button4.setPosition(1 , 47);
        button4.setSize(160 , 160);
        button4.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0,-20, 0.5f , Interpolation.pow5Out))));
        button4.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                if (pointer == -1) {
                    button4.addAction(Actions.moveBy(+5, -5, 0.1f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                if (pointer == -1) {
                    button4.addAction(Actions.moveBy(-5, 5, 0.1f));
                }
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {

                app.setScreen(app.mainMenuScreen);
            }
        });

        stage.addActor(button1);
        stage.addActor(button2);
        stage.addActor(button3);
        stage.addActor(button4);
    }

    private void update(float delta){
        stage.act(delta);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1f,1f,1f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.draw();
        app.batch.begin();

        app.font24.draw(app.batch, "SCREEN:MAIN MENU",20,20);

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
    }
}
