package com.university.maastricht;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.university.maastricht.tile.Tile;

public class Game extends ApplicationAdapter {

	private Camera camera;
	private Viewport viewport;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;


	private final int WORLD_WIDTH = 1000;
	private final int WORLD_HEIGHT = 1000;

	private Tile[][] tiles;


	@Override
	public void create () {
		init();

		// creating a 5x5 field of tiles.
		// I know its a hexagon, this is just a place holder
		tiles = new Tile[5][5];
		int tileRadius = 90;
		int xOffset = tileRadius + 50;
		int yOffset = tileRadius + 50;

		int k = 0;

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				int x = j * 2 * tileRadius;
				int y = i * 2 * tileRadius;
				tiles[i][j] = new Tile(x + xOffset, y + yOffset, k);
				k++;
			}
		}
	}

	// this method gets called at every new frame
	@Override
	public void render() {
		ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


		// renders each tile inside the 5x5 tile field
		for (Tile[] tiles : tiles)
			for (Tile tile : tiles)
				tile.render(shapeRenderer);

		shapeRenderer.end();
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
		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
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
