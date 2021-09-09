package com.university.maastricht;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.university.maastricht.components.Button;
import com.university.maastricht.components.GameObject;
import com.university.maastricht.tile.Tile;
import com.university.maastricht.tile.TileMap;

public class Game extends ApplicationAdapter {

	public static final int WINDOW_HEIGHT = 720;
	public static final int WINDOW_WIDTH = 1280;

	public static Viewport viewport;
	public static TextureAtlas spriteSheet;
	private Camera camera;
	private SpriteBatch batch;

	private Tile tile;
	private Button button;
	private TileMap tileMap;

	private GameObject gameObject;

	Stage stage;


	@Override
	public void create () {
		init();

		tile = new Tile(200, 50, 100, 0);
		button = new Button(spriteSheet.findRegion("ball_blue_large"), 50, 50, 200, 200);
		tileMap = new TileMap(0,0,60);

		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);

		gameObject = new GameObject(spriteSheet,"block_locked_large", 500, 300);

		stage.addActor(button);
		stage.addActor(tile.getActor());
		stage.setKeyboardFocus(button);
		tileMap.addToStage(stage);
	}

	// this method gets called at every new frame
	@Override
	public void render() {
		ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1); // sets the color of the background
		stage.act();
		stage.draw();

		batch.begin();
		// here you call your render methods
		gameObject.render(batch);

		batch.end();
	}


	// ------ stuff you dont really need to worry about for now -----

	public void init() {
		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);
		batch = new SpriteBatch();
		spriteSheet = new TextureAtlas(Gdx.files.internal("tile/sprites.atlas"));
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		batch.setProjectionMatrix(camera.combined);
	}
}
