package com.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Story implements Screen {
    private final angryBirds app;
    private final Stage stage;
    private SpriteBatch batch;
    private Texture[] slides;
    private Texture blurBackgroundTexture;
    private float elapsedTime;
    private int currentSlide;
    private float alpha;

    public Story(final angryBirds app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(angryBirds.V_WIDTH, angryBirds.V_HEIGHT, app.camera));
        initialize();
    }

    private void initialize() {
        batch = new SpriteBatch();

        slides = new Texture[4];
        slides[0] = new Texture("assets/slide1.png");
        slides[1] = new Texture("assets/slide2.png");
        slides[2] = new Texture("assets/slide3.png");
        slides[3] = new Texture("assets/slide4.png");

        // Load the blurred background texture
        blurBackgroundTexture = new Texture("assets/blur_bG.png");

        elapsedTime = 0f;
        currentSlide = 0;
        alpha = 1f;


        Image blurBackground = new Image(new TextureRegionDrawable(new TextureRegion(blurBackgroundTexture)));
        blurBackground.setSize(angryBirds.V_WIDTH, angryBirds.V_HEIGHT);
        blurBackground.setPosition(0, 0);
        stage.addActor(blurBackground);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;


        if (elapsedTime > 1.5f) {
            elapsedTime = 0f;
            currentSlide = (currentSlide + 1) % slides.length;
            alpha = 0f;

            // After the last slide, switch to LEVELS_SCREEN
            if (currentSlide == 0) {
                app.setScreen(app.levelsScreen);
                return;
            }
        }


        if (alpha < 1f) {
            alpha += delta * 2;
        }

        // Start drawing the stage
        stage.act(delta);
        stage.draw();


        batch.begin();
        batch.setColor(1f, 1f, 1f, alpha);

        Texture currentTexture = slides[currentSlide];
        float textureWidth = currentTexture.getWidth();
        float textureHeight = currentTexture.getHeight();


        float scale = Math.min((float) angryBirds.V_WIDTH / textureWidth,
            (float) angryBirds.V_HEIGHT / textureHeight);


        scale *= 0.65f;

        float scaledWidth = textureWidth * scale;
        float scaledHeight = textureHeight * scale;


        float xPosition = (angryBirds.V_WIDTH - scaledWidth) / 2 - 250;
        float yPosition = (angryBirds.V_HEIGHT - scaledHeight) / 2 - 150;

        // Draw the image at the calculated position
        batch.draw(currentTexture, xPosition, yPosition, scaledWidth, scaledHeight);

        batch.setColor(1f, 1f, 1f, 1f); // Reset color
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        for (Texture slide : slides) {
            slide.dispose();
        }
        blurBackgroundTexture.dispose();
    }
}
