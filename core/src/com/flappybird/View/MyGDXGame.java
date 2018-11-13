package com.flappybird.View;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.Controller.Controller;
import com.flappybird.Model.*;

import java.util.Random;

public class MyGDXGame extends ApplicationAdapter {
	static public SpriteBatch batch;
	private Background background;
	private Ground ground;
	private Bird bird;
	private Obstacles obs;
	private boolean randomMode;
	private static GameMode gameMode;
	public Controller controller;

	@Override
	public void create () {
		randomMode = new Random().nextBoolean();
		batch = new SpriteBatch();
		background = new Background(randomMode);
		ground = new Ground();
		bird = new Bird();
		obs = new Obstacles();
		controller = new Controller(this);
		gameMode = GameMode.GAME;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		controller.update();
		switch (gameMode) {
			case MENU:
				renderMenu();
				break;
			case GAME:
				renderGame();
				break;
			case DEAD:
				renderDead();
				break;
		}
		batch.end();
	}

	private void renderMenu() {

	}

	private void renderGame() {
		update();
		background.draw();
		obs.draw();
		ground.draw();
		bird.draw();
	}

	private void renderDead() {
		background.draw();
		obs.draw();
		ground.draw();
		bird.draw();
	}

	private void update() {

		background.update();
		obs.update();
		ground.update();
		bird.update();
		if (obs.checkIntersection(bird)) {
			gameMode = GameMode.DEAD;
		}
	}
	
	@Override
	public void dispose () {
		bird.dispose();
		ground.dispose();
		obs.dispose();
		background.dispose();
		batch.dispose();
	}

	public Background getBackground() {
		return background;
	}

	public Ground getGround() {
		return ground;
	}

	public Bird getBird() {
		return bird;
	}

	public Obstacles getObs() {
		return obs;
	}

	public boolean isRandomMode() {
		return randomMode;
	}

	public static GameMode getGameMode() {
		return gameMode;
	}
}
