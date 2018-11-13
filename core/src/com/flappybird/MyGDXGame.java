package com.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class MyGDXGame extends ApplicationAdapter {
	static SpriteBatch batch;
	Background background;
	Ground ground;
	Bird bird;
	Obstacles obs;
	boolean randomMode;

	@Override
	public void create () {
		randomMode = new Random().nextBoolean();
		batch = new SpriteBatch();
		background = new Background(randomMode);
		ground = new Ground();
		bird = new Bird();
		obs = new Obstacles();
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		background.draw();
		obs.draw();
		ground.draw();
		bird.draw();
		batch.end();
	}

	public void update() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		background.update();
		obs.update();
		ground.update();
		bird.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
