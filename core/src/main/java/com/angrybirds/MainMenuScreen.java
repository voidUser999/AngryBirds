package com.angrybirds;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import javax.swing.event.ChangeListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MainMenuScreen implements Screen {

    private final angryBirds app;

    private Stage stage;

    private Skin skin;

    private TextButton buttonPlay , buttonExit;


    private ShapeRenderer shapeRenderer;

    public MainMenuScreen(final angryBirds app){
        this.app = app;
        stage = new Stage(new StretchViewport(angryBirds.V_WIDTH,angryBirds.V_HEIGHT, app.camera));
        this.shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void show() {
        System.out.println("MAIN MENU");
        Gdx.input.setInputProcessor(stage);

        stage.clear();
        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("skin/uiskin.atlas" , TextureAtlas.class));
        this.skin.add("default-font", app.font24);
        this.skin.load(Gdx.files.internal("uiskin.json"));
        System.out.println("XWFC");

        initButtons();


    }

    private void initButtons() {
            buttonPlay = new TextButton("Play", skin, "default");
            buttonPlay.setPosition(860 , 400);
            buttonPlay.setSize(280 , 60);

            buttonPlay.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0,-20, 0.5f , Interpolation.pow5Out))));
            buttonPlay.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    app.setScreen(app.playScreen);
                }
            });


            buttonExit = new TextButton("Exit", skin, "default");
            buttonExit.setPosition(860 , 320);
            buttonExit.setSize(280 , 60);
            buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0,-20, 0.5f , Interpolation.pow5Out))));
            buttonExit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.exit();
                }
            });
            stage.addActor(buttonPlay);
            stage.addActor(buttonExit);
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
    public void resize(int i, int i1) {

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
