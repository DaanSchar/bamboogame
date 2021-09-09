package com.university.maastricht;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.university.maastricht.components.GameObject;
import com.university.maastricht.components.Button;
import com.university.maastricht.tile.Tile;

public class Game extends ApplicationAdapter {

	public static final int WINDOW_HEIGHT = 720;
	public static final int WINDOW_WIDTH = 1280;

	public static Viewport viewport;
	public static TextureAtlas spriteSheet;
	private Camera camera;
	private SpriteBatch batch;

	private Tile tile;
	private Tile tile2;
	private Button actor;
	Stage stage;

	private GameObject lock;

	@Override
	public void create () {
		init();

		tile = new Tile(100, 100, 50, 0);
		tile2 = new Tile(100, 100, 50, 1);
		lock = new GameObject(spriteSheet, "block_locked_small", 300, 300);
		actor = new Button(spriteSheet.findRegion("ball_blue_large"), 50, 50, 100, 100);

		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);

		stage.addActor(actor);
		stage.addActor(tile.getActor());
		stage.setKeyboardFocus(actor);
	}

	// this method gets called at every new frame
	@Override
	public void render() {
		ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1); // sets the color of the background

		stage.act();
		stage.draw();
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
