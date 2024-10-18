package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class angryBirds extends Game {

    public static final String TITLE = "Slides";
    public SpriteBatch batch;
    public Texture image;
    public OrthographicCamera camera;

    public static final int V_WIDTH = 1920;
    public static final int V_HEIGHT = 1080;
    public BitmapFont font24;

    public AssetManager assets;

    public LoadingScreen loadingScreen;
    public SplashScreen splashScreen;
    public MainMenuScreen mainMenuScreen;
    public PlayScreen playScreen;


    @Override
    public void create() {
        assets = new AssetManager();
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        camera = new OrthographicCamera();


        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);


        initFonts();
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);

        loadingScreen = new LoadingScreen(this);
        splashScreen = new SplashScreen(this);
        mainMenuScreen = new MainMenuScreen(this);
        playScreen = new PlayScreen(this);
        this.setScreen(loadingScreen); // Sets SplashScreen
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
        font24.dispose();
        assets.dispose();
        splashScreen.dispose();
        loadingScreen.dispose();
        mainMenuScreen.dispose();
        playScreen.dispose();

    }

    private void initFonts() {
        FreeTypeFontGenerator generator =  new FreeTypeFontGenerator(Gdx.files.internal("Arcon-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 24;
        params.color = Color.BLACK;
        font24 = generator.generateFont(params);
    }
}
