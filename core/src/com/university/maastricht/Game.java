package com.university.maastricht;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.university.maastricht.tile.Tile;

public class Game extends ApplicationAdapter {

	public static final int WINDOW_HEIGHT = 720;
	public static final int WINDOW_WIDTH = 1280;

	public static Viewport viewport;
	public static TextureAtlas spriteSheet;
	private Camera camera;
	private ShapeRenderer shapeRenderer;	
	private SpriteBatch batch;

	private Tile[][] tiles;

	@Override
	public void create () {
		init();
		spriteSheet = new TextureAtlas(Gdx.files.internal("tile/sprites.atlas"));

		createPlayingField();
	}

	private void createPlayingField() {
		tiles = new Tile[5][5];
		int tileRadius = 50;
		int xOffset = tileRadius + 200;
		int yOffset = tileRadius + 100;

		int index = 0;

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				int x = j *tileRadius*2 + xOffset;
				int y = i * tileRadius*2 + yOffset;
				tiles[i][j] = new Tile(x, y, index);
				index++;
			}
		}
	}

	public void renderMap(SpriteBatch batch) {
		for (Tile[] tileRow : tiles)
			for (Tile tile : tileRow)
				tile.render(batch);
	}

	// this method gets called at every new frame
	@Override
	public void render() {
		ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1);

		// renders the text of each tile
		batch.begin();
		renderMap(batch);
		batch.end();
	}


	// ------ stuff you dont really need to worry about for now -----

	public void init() {
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);
		batch = new SpriteBatch();
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		createPlayingField();
	}
}
