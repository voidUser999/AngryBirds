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

        // Initialize the slideshow textures
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

        // Add the blurred background image
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

        // Transition to the next slide every 1.5 seconds
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

        // Gradual fade-in effect for the current slide
        if (alpha < 1f) {
            alpha += delta * 2; // Speed of the fade-in effect
        }

        // Start drawing the stage
        stage.act(delta);
        stage.draw();

        // Draw the current slide in the center of the screen
        batch.begin();
        batch.setColor(1f, 1f, 1f, alpha);

        Texture currentTexture = slides[currentSlide];
        float textureWidth = currentTexture.getWidth();
        float textureHeight = currentTexture.getHeight();

        // Calculate scale factor to fit the image proportionally within the screen
        float scale = Math.min((float) angryBirds.V_WIDTH / textureWidth,
            (float) angryBirds.V_HEIGHT / textureHeight);

        // Apply a scale factor to ensure images are not too large
        scale *= 0.65f;

        float scaledWidth = textureWidth * scale;
        float scaledHeight = textureHeight * scale;

        // Center the scaled texture
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
        // Handle pause if needed
    }

    @Override
    public void resume() {
        // Handle resume if needed
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
