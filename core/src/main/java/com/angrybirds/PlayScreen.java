package com.angrybirds;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class PlayScreen implements Screen {

    private final angryBirds app;

    private Stage stage;
    private Skin skin;
    public OrthoCachedTiledMapRenderer tmr;
    public TiledMap map;


    public PlayScreen(final angryBirds app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(angryBirds.V_WIDTH, angryBirds.V_HEIGHT , app.camera));
        map = new TmxMapLoader().load("map/map_1.tmx");

        tmr = new OrthoCachedTiledMapRenderer(map);
    }

    @Override
    public void show() {
        System.out.println("PLAY");
        Gdx.input.setInputProcessor(stage);
        stage.clear();
    }

    private void update(float delta) {
        tmr.setView(app.camera);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        update(delta);


        tmr.render();

        app.batch.begin();
        app.font24.draw(app.batch, "SCREEN:PLAY", 20, 20);
        app.batch.end();


        Gdx.gl.glDisable(GL20.GL_BLEND);
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
        tmr.dispose();
    }
}
