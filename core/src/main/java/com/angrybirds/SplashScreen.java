package com.angrybirds;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements Screen{

    private final angryBirds app;
    private Stage stage;

    private Image splashImg;     //its an actor

    public  SplashScreen(final angryBirds app){

        this.app = app;
        this.stage = new Stage(new StretchViewport(angryBirds.V_WIDTH , angryBirds.V_HEIGHT , app.camera));
        Gdx.input.setInputProcessor(stage);


    }
    @Override
    public void show() {
        System.out.println("SplashScreen show");
        // Texture splashTex = new Texture("splash.png");
        Texture splashTex = app.assets.get("rovio.png" , Texture.class);

        Runnable transitionRunnable = new Runnable() {

            @Override
            public void run() {
                app.setScreen(new MainMenuScreen(app));
            }
        };
        splashImg = new Image(splashTex);
        splashImg.setHeight((float) splashTex.getHeight() /3);
        splashImg.setWidth((float) splashTex.getWidth() /3);
        splashImg.setOrigin(splashImg.getWidth()/2, splashImg.getHeight()/2);

        splashImg.setPosition(stage.getWidth()/2 -32, stage.getHeight()/2 + 32);
//        splashImg.addAction(sequence(alpha(0f), parallel(moveBy(30,20,2f),fadeIn(2f))));

        splashImg.addAction(sequence(alpha(0), scaleTo(.1f,.1f), parallel(fadeIn(2f, Interpolation.pow2),scaleTo(2f,2f,2.5f, Interpolation.pow5), moveTo(stage.getWidth()/2-32, stage.getHeight()/2-32 , 2f,Interpolation.swing)),delay(1.5f), fadeOut(1.25f), run(transitionRunnable)));


        stage.addActor(splashImg);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(11f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.draw();

        app.batch.begin();
        app.font24.draw(app.batch, "SplashScreen" ,20,20);
        app.font24.draw(app.batch, "2009-2024 Rovio Entertainment Corporation. Angry Birds , Rovio , Mighty Eagle, Bad Piggies and \n the Angry Birds figure are trademarks of Rovio Entertainmet Copropation . All rights reserved" , 460 , 100);
        app.batch.end();
    }
    public void update(float delta){
        stage.act(delta);
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
        System.out.println("Disposing ");
        stage.dispose();

    }
}
