package com.angrybirds;

import com.badlogic.gdx.Game;
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

    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        camera = new OrthographicCamera();
        font = new BitmapFont();

        this.setScreen(new SplashScreen(this)); // Sets SplashScreen
    }

    @Override
    public void render() {
        super.render();  // Render the active screen (e.g., SplashScreen)
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
