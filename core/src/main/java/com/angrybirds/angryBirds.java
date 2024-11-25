package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

//public class angryBirds extends Game {
//
//    public static final String TITLE = "Slides";
//    public SpriteBatch batch;
//    public Texture image;
//    public OrthographicCamera camera;
//
//    public static final int V_WIDTH = 1920;
//    public static final int V_HEIGHT = 1080;
//    public BitmapFont font24;
//
//    public AssetManager assets;
//
//    public LoadingScreen loadingScreen;
//    public SplashScreen splashScreen;
//    public MainMenuScreen mainMenuScreen;
//    public PlayScreen playScreen;
//    public LevelsScreen levelsScreen;
//    public Level_1 level_1;
//    public Lvl_1 lvl_1;
//    public Lvl_2 lvl_2;
//    public Lvl_3 lvl_3;
////    private Box2DDebugRenderer b2dr;
////    private World world;
////    private Body body;
//    public Pixmap defaultCursor;
//    public Pixmap clickedCursor;
//
//    public Cursor currentCursor;
//
//
//    @Override
//    public void create() {
//        assets = new AssetManager();
//        batch = new SpriteBatch();
//        image = new Texture("libgdx.png");
//        camera = new OrthographicCamera();
//
//        initCursors();
//
//        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
//
//
//        initFonts();
////        font = new BitmapFont();
////        font.setColor(Color.BLACK);
//
//        loadingScreen = new LoadingScreen(this);
//        splashScreen = new SplashScreen(this);
//        mainMenuScreen = new MainMenuScreen(this);
//        playScreen = new PlayScreen(this);
//        levelsScreen = new LevelsScreen(this);
//        level_1 = new Level_1(this);
//        lvl_1 = new Lvl_1(this);
//        lvl_2 = new Lvl_2(this);
//        lvl_3 = new Lvl_3(this);
//        this.setScreen(loadingScreen);
//    }
//
//    @Override
//    public void render() {
//        super.render();
//
//        Cursor newCursor;
//        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
//            newCursor = Gdx.graphics.newCursor(clickedCursor, 0, 0);
//        } else {
//            newCursor = Gdx.graphics.newCursor(defaultCursor, 0, 0);
//        }
//
//
//        if (currentCursor != newCursor) {
//            if (currentCursor != null) currentCursor.dispose();
//            Gdx.graphics.setCursor(newCursor);
//            currentCursor = newCursor;
//        }
//    }
//
//
//    @Override
//    public void dispose() {
//        batch.dispose();
//        image.dispose();
//        this.getScreen().dispose();
//        font24.dispose();
//        assets.dispose();
//        splashScreen.dispose();
//        loadingScreen.dispose();
//        mainMenuScreen.dispose();
//        playScreen.dispose();
//        level_1.dispose();
//        lvl_1.dispose();
//        defaultCursor.dispose();
//        clickedCursor.dispose();
//
//        lvl_2.dispose();
//        lvl_3.dispose();
//
//        levelsScreen.dispose();
//    }
//    private void initCursors() {
//        defaultCursor = new Pixmap(Gdx.files.internal("cursor1.png"));
//        clickedCursor = new Pixmap(Gdx.files.internal("cursor2.png"));
//
//        Gdx.graphics.setCursor(Gdx.graphics.newCursor(defaultCursor, 0, 0));
//    }
//
//    private void initFonts() {
//        FreeTypeFontGenerator generator =  new FreeTypeFontGenerator(Gdx.files.internal("Arcon-Regular.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
//
//        params.size = 24;
//        params.color = Color.BLACK;
//        font24 = generator.generateFont(params);
//    }
//
//
//}
public class angryBirds extends Game {

    public static final String TITLE = "Slides";
    public SpriteBatch batch;
    public Texture image;
    public OrthographicCamera camera;

    public static final int V_WIDTH = 1920;
    public static final int V_HEIGHT = 1080;
    public BitmapFont font24;

    public AssetManager assets;

    // Declare flags for music and sound
    public boolean isMusicMuted = true;
    public boolean isSoundMuted = true;

    // Declare music objects
    public Music gameMusic;
    public Sound buttonClickSound;

    public LoadingScreen loadingScreen;
    public SplashScreen splashScreen;
    public MainMenuScreen mainMenuScreen;
    public PlayScreen playScreen;
    public LevelsScreen levelsScreen;
    public Level_1 level_1;
    public Lvl_1 lvl_1;
    public Lvl_2 lvl_2;
    public Lvl_3 lvl_3;

    public Pixmap defaultCursor;
    public Pixmap clickedCursor;
    public Cursor currentCursor;

    public Story story;

    @Override
    public void create() {
        assets = new AssetManager();
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        camera = new OrthographicCamera();

        initCursors();
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        initFonts();

        // Initialize music and sound
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));
        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("buttonMusic.mp3"));

        // Start game music if not muted
        if (!isMusicMuted) {
            gameMusic.setLooping(true);
            gameMusic.play();
        }

        loadingScreen = new LoadingScreen(this);
        splashScreen = new SplashScreen(this);
        mainMenuScreen = new MainMenuScreen(this);
        playScreen = new PlayScreen(this);
        levelsScreen = new LevelsScreen(this);
        level_1 = new Level_1(this);
        lvl_1 = new Lvl_1(this);
        lvl_2 = new Lvl_2(this);
        lvl_3 = new Lvl_3(this);
        story = new Story(this);

        this.setScreen(loadingScreen);
    }

    @Override
    public void render() {
        super.render();

        Cursor newCursor;
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            newCursor = Gdx.graphics.newCursor(clickedCursor, 0, 0);
        } else {
            newCursor = Gdx.graphics.newCursor(defaultCursor, 0, 0);
        }

        if (currentCursor != newCursor) {
            if (currentCursor != null) currentCursor.dispose();
            Gdx.graphics.setCursor(newCursor);
            currentCursor = newCursor;
        }
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
        level_1.dispose();
        lvl_1.dispose();
        defaultCursor.dispose();
        clickedCursor.dispose();
        lvl_2.dispose();
        lvl_3.dispose();
        levelsScreen.dispose();

        // Dispose music and sound
        gameMusic.dispose();
        buttonClickSound.dispose();
    }

    private void initCursors() {
        defaultCursor = new Pixmap(Gdx.files.internal("cursor1.png"));
        clickedCursor = new Pixmap(Gdx.files.internal("cursor2.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(defaultCursor, 0, 0));
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Arcon-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 24;
        params.color = Color.BLACK;
        font24 = generator.generateFont(params);
    }

    // Call this to toggle background music
    public void toggleMusic() {
        isMusicMuted = !isMusicMuted;
        if (isMusicMuted) {
            gameMusic.pause();
        } else {
            gameMusic.play();
        }
    }

    // Call this to toggle sound effects
    public void toggleSound() {
        isSoundMuted = !isSoundMuted;
    }

    // Play button click sound if sound is not muted
    public void playButtonClickSound() {
        if (!isSoundMuted) {
            buttonClickSound.play();
        }
    }
    public Level_1 resetLevel1(Boolean reset) {
        if(reset) { level_1 = new Level_1(this);
            return level_1;
        }
       return null;
    }


}
