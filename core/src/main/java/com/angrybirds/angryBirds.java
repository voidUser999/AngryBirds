package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class angryBirds extends Game {

    public static final String TITLE = "Slides";
    public SpriteBatch batch;
    public Texture image;
    public OrthographicCamera camera;
    public static final int V_WIDTH = 480;
    public static final int V_HEIGHT = 420;
    public BitmapFont font;

    public AssetManager assets;

    @Override
    public void create() {
        assets = new AssetManager();
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        font = new BitmapFont();
        font.setColor(Color.BLACK);

        this.setScreen(new LoadingScreen(this)); // Sets SplashScreen
    }

    @Override
    public void render() {
        super.render();  // Render the active screen (e.g., SplashScreen)
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        this.getScreen().dispose();
        font.dispose();
        assets.dispose();
    }
}
