package com.angrybirds;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SplashScreen implements Screen{

    private final angryBirds app;
    private Stage stage;

    public  SplashScreen(final angryBirds app){

        this.app = app;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.85f, .25f, .25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.draw();

        app.batch.begin();
        app.font.draw(app.batch, "SplashScreen" ,50,50);
        app.batch.end();
    }
    public void update(float delta){
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
