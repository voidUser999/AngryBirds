package com.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class LoadingScreen implements Screen {
    private final angryBirds app;

    private ShapeRenderer shapeRenderer;
    private float progress;

    public LoadingScreen(angryBirds app) {
        this.app = app;
        this.shapeRenderer = new ShapeRenderer();

    }

    private void queueAssets() {
        app.assets.load("rovio.png", Texture.class);
        app.assets.load("splash.png", Texture.class);
        app.assets.load("skin/uiskin.atlas", TextureAtlas.class);
        app.assets.load("abc.png", Texture.class);
        app.assets.load("cat.png", Texture.class);
        app.assets.load("pause.png", Texture.class);
        app.assets.load("ab_name.png", Texture.class);
        app.assets.load("levels.png", Texture.class);
        app.assets.load("levels2.png", Texture.class);
        app.assets.load("1.png", Texture.class);
        app.assets.load("2.png", Texture.class);
        app.assets.load("3.png", Texture.class);
        app.assets.load("1d.png", Texture.class);
        app.assets.load("2d.png", Texture.class);
        app.assets.load("3d.png", Texture.class);
        app.assets.load("button_play.png", Texture.class);
        app.assets.load("button_quit.png", Texture.class);
        app.assets.load("cursor1.png", Texture.class);
        app.assets.load("cursor2.png", Texture.class);
        app.assets.load("levels.png", Texture.class);

    }

    @Override
    public void show() {
        System.out.println("LoadingScreen show");

        this.progress = 0f;

        queueAssets();

    }
    private void update(float delta) {
        progress = MathUtils.lerp(progress, app.assets.getProgress() ,.1f);
        if(app.assets.update() && progress>=(app.assets.getProgress()-.1f)){
            app.setScreen(app.mainMenuScreen);
        }



    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, app.camera.viewportHeight/2-8, app.camera.viewportWidth -64, 16);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(32, app.camera.viewportHeight/2-8,progress * (app.camera.viewportWidth -64), 16);
        System.out.println(app.camera.viewportWidth);
        shapeRenderer.end();

        app.batch.begin();
        app.font24.draw(app.batch, "Loading", 20, 20);
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
        shapeRenderer.dispose();

    }
}
