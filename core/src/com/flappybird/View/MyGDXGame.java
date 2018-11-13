package com.flappybird.View;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.Model.Background;
import com.flappybird.Model.Bird;
import com.flappybird.Model.Ground;
import com.flappybird.Model.Obstacles;

import java.util.Random;

public class MyGDXGame extends ApplicationAdapter {
	static public SpriteBatch batch;
	private Background background;
	private Ground ground;
	private Bird bird;
	private Obstacles obs;
	private boolean randomMode;

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

	private void update() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		background.update();
		obs.update();
		ground.update();
		bird.update();
	}
	
	@Override
	public void dispose () {
		bird.dispose();
		ground.dispose();
		obs.dispose();
		background.dispose();
		batch.dispose();
	}
}
