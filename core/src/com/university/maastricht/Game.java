package com.university.maastricht;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.university.maastricht.tile.Tile;

// Commit test
public class Game extends ApplicationAdapter {

	private Camera camera;
	private Viewport viewport;

	private ShapeRenderer shapeRenderer;

	private final int WORLD_WIDTH = 400;
	private final int WORLD_HEIGHT = 9 * WORLD_WIDTH / 16;

	private Tile tile;


	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		tile = new Tile(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);

	}

	@Override
	public void render () {
		ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		tile.render(shapeRenderer);
		shapeRenderer.end();

	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		shapeRenderer.setProjectionMatrix(camera.combined);
	}
}
