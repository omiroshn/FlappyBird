package com.flappybird.View;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
	private Font scoreFont;
	private boolean randomMode;
	private static GameMode gameMode;
	private int score;
	private Controller controller;

	private Atlas scorePic;
	private Atlas gameOverPic;
	private Atlas playPic;
	private Atlas recordsPic;
	private Atlas tap_tap;
	private Atlas getReady;

	@Override
	public void create () {
		randomMode = new Random().nextBoolean();
		batch = new SpriteBatch();
		background = new Background(randomMode);
		ground = new Ground();
		bird = new Bird();
		obs = new Obstacles();
		scoreFont = new Font();
		controller = new Controller(this);
		gameMode = GameMode.MENU;
		score = 0;
		scorePic = new Atlas(
			"atl/score.atlas",
				339,
				171,
				(Gdx.graphics.getWidth() - 339) / 2,
				(Gdx.graphics.getHeight() - 171) / 2 + 20
		);
		gameOverPic = new Atlas(
				"atl/gameover.atlas",
				288,
				63,
				(Gdx.graphics.getWidth() - 288) / 2,
				(Gdx.graphics.getHeight() - 171) / 2 + 300
		);
		playPic = new Atlas(
				"atl/play.atlas",
				156,
				87,
				(Gdx.graphics.getWidth() - 156) / 2 - 100,
				(Gdx.graphics.getHeight() - 87) / 2 - 180
		);
		recordsPic = new Atlas(
				"atl/records.atlas",
				156,
				87,
				(Gdx.graphics.getWidth() - 156) / 2 + 100,
				(Gdx.graphics.getHeight() - 87) / 2 - 180
		);
		tap_tap = new Atlas(
				"atl/taptap.atlas",
				171,
				147,
				(Gdx.graphics.getWidth() - 171) / 2,
				(Gdx.graphics.getHeight() - 147) / 2
		);
		getReady = new Atlas(
				"atl/getready.atlas",
				276,
				75,
				(Gdx.graphics.getWidth() - 276) / 2,
				(Gdx.graphics.getHeight() - 75) / 2 + 200
		);
	}

	@Override
	public void render () {
		//todo bird rotation
		//todo bird falling
		//todo score font
		//todo score count
		//todo score displaying in table
		//todo database
		//todo clickable buttons
		//todo medals
		//todo firstfiev
		//todo change skins
		//todo music
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
		update();
		background.draw();
		ground.draw();
		bird.draw();
		tap_tap.draw();
		getReady.draw();
		scoreFont.draw(
				Integer.toString(score),
				Gdx.graphics.getWidth() / 2 - 25,
				Gdx.graphics.getHeight() - 60,
				1.4f
		);
	}

	private void renderGame() {
		update();
		background.draw();
		obs.draw();
		ground.draw();
		bird.draw();
		scoreFont.draw(
			Integer.toString(score),
			Gdx.graphics.getWidth() / 2 - 25,
			Gdx.graphics.getHeight() - 60,
			1.4f
		);
	}

	private void renderDead() {
		background.draw();
		obs.draw();
		ground.draw();
		bird.draw();
		gameOverPic.draw();
		scorePic.draw();
		playPic.draw();
		recordsPic.draw();
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
		batch.dispose();
		scoreFont.dispose();
		bird.dispose();
		ground.dispose();
		obs.dispose();
		background.dispose();
		gameOverPic.dispose();
		scorePic.dispose();
		playPic.dispose();
		recordsPic.dispose();
		tap_tap.dispose();
		getReady.dispose();
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
	public void setGameMode(GameMode mode) {
		gameMode = mode;
	}
}
