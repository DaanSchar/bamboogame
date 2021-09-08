package com.university.maastricht;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.university.maastricht.tile.Tile;

public class Game extends ApplicationAdapter {

	public static Viewport viewport;
	private Camera camera;
	private ShapeRenderer shapeRenderer;	
	private SpriteBatch batch;

	private Tile[][] tiles;

	@Override
	public void create () {
		init();
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
				int x = j * tileRadius*2 + xOffset;
				int y = i * tileRadius*2 + yOffset;
				tiles[i][j] = new Tile(x, y, index);
				index++;
			}
		}
	}

	// this method gets called at every new frame
	@Override
	public void render() {
		ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1);

		// renders each tile inside the 5x5 tile field
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (Tile[] tiles : tiles)
			for (Tile tile : tiles)
				tile.render(shapeRenderer);

		shapeRenderer.end();

		// renders the text of each tile
		batch.begin();
		for (Tile[] tiles : tiles)
			for (Tile tile : tiles)
				tile.renderText(batch);
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
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
	}
}
