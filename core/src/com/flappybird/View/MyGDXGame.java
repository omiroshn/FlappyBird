package com.flappybird.View;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.flappybird.Controller.Controller;
import com.flappybird.Model.*;

import java.util.ArrayList;
import java.util.Random;

public class MyGDXGame extends ApplicationAdapter {
	static public SpriteBatch batch;
	static public ShapeRenderer shape;
	private Background background;
	private Ground ground;
	private Bird bird;
	private Obstacles obs;
	private Font scoreFont;
	private boolean randomMode;
	private static GameMode gameMode;
	private int score;
	private Controller controller;
	private DatabaseFB databaseFB;

	private Atlas flappyBirdLogo;
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
		bird = new Bird(this);
		obs = new Obstacles();
		scoreFont = new Font();
		controller = new Controller(this);
		Gdx.input.setInputProcessor(controller);
		databaseFB = new DatabaseFB("score.db");
		gameMode = GameMode.FIRSTVIEW;
		score = 0;
		flappyBirdLogo = new Atlas(
				"atl/flappybirdlogo.atlas",
				267,
				72,
				(Gdx.graphics.getWidth() - 267) / 2,
				(Gdx.graphics.getHeight() - 72) / 2 + 200
		);
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
				(Gdx.graphics.getHeight() - 147) / 2 - 80
		);
		getReady = new Atlas(
				"atl/getready.atlas",
				276,
				75,
				(Gdx.graphics.getWidth() - 276) / 2,
				(Gdx.graphics.getHeight() - 75) / 2 + 200
		);

		shape = new ShapeRenderer();
	}

	@Override
	public void render () {
	//todo bird falling +
	//todo score count +
	//todo score font +
	//todo clickable buttons +
	//todo databaseFB +
	//todo viewController +

		//todo bird rotation
		//todo Птица повернута в соответствующую сторону движения,
		// т.е. падая — птица смотрит внизу, взлетая — вверх.
		// Анимация (взмах крыльями) присутствует только когда птица летит вверх.


		//todo score displaying in table
		//todo new label
		//todo medals
		//add drawing shit to other class in main
		//todo меняющиеся скины рандомно!
		//todo music

		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		switch (gameMode) {
			case FIRSTVIEW:
				renderViewController();
				break;
			case MENU_RECORDS:
				renderMenuRecords();
				break;
			case MENU:
				renderMenu();
				break;
			case GAME:
				renderGame();
				break;
			case RECORDS:
				renderGameRecords();
				break;
			case DEAD:
				renderDead();
				break;
		}
		batch.end();
//		shape.begin(ShapeRenderer.ShapeType.Filled);
//		shape.setColor(Color.RED);
//		shape.circle(
//				bird.getCircle().x,
//				bird.getCircle().y,
//				bird.getCircle().radius
//		);
//		for (Obstacles.Wall wall: obs.getWallPair()) {
//			shape.rect(wall.getTopRect().getX(),
//					wall.getTopRect().getY(),
//					wall.getTopRect().getWidth(),
//					wall.getTopRect().getHeight()
//			);
//			shape.rect(wall.getBotRect().getX(),
//					wall.getBotRect().getY(),
//					wall.getBotRect().getWidth(),
//					wall.getBotRect().getHeight()
//			);
//		}
//		shape.end();
	}

	private void renderViewController() {
		update();
		background.draw();
		ground.draw();
		bird.draw();
		flappyBirdLogo.draw();
		playPic.draw();
		recordsPic.draw();
	}

	private void renderMenu() {
		update();
		background.draw();
		ground.draw();
		bird.draw();
		tap_tap.draw();
		getReady.draw();
	}

	private void renderGame() {
		update();
		background.draw();
		obs.draw();
		ground.draw();
		bird.draw();
		scoreFont.draw(
			Integer.toString(score),
			Gdx.graphics.getWidth() / 2 - 15,
			Gdx.graphics.getHeight() - 60,
			2f
		);
	}

	private void renderDead() {
		bird.update();
		background.draw();
		obs.draw();
		ground.draw();
		bird.draw();
		gameOverPic.draw();
		scorePic.draw();
		playPic.draw();
		recordsPic.draw();
		scoreFont.draw(
				Integer.toString(score),
				Gdx.graphics.getWidth() / 2 + 120,
				Gdx.graphics.getHeight() / 2 + 52,
				1f
		);
		scoreFont.draw(
				Integer.toString(databaseFB.getMaxScoreFromTable()),
				Gdx.graphics.getWidth() / 2 + 120,
				Gdx.graphics.getHeight() / 2 - 12,
				1f
		);
	}

	private void renderMenuRecords() {
		update();
		background.draw();
		obs.draw();
		ground.draw();
		playPic.draw();
		recordsPic.draw();
		drawTopFiveRecords();
	}

	private void renderGameRecords() {
		bird.update();
		background.draw();
		obs.draw();
		ground.draw();
		bird.draw();
		gameOverPic.draw();
		playPic.draw();
		recordsPic.draw();
		drawTopFiveRecords();
	}

	private void drawTopFiveRecords() {
		ArrayList<Integer> r = databaseFB.getTopFiveFromTable();

		int offset = Gdx.graphics.getHeight() / 2 + 300;
		for (Integer i : r) {
			scoreFont.draw(
					Integer.toString(i),
					Gdx.graphics.getWidth() / 2 + 120,
					offset,
					1f
			);
			offset -= 30;
		}
	}

	private void update() {
		background.update();
		obs.update();
		ground.update();
		bird.update();
		if (obs.checkIntersection(this, bird)) {
			databaseFB.insertValueToTable(score);
			gameMode = GameMode.DEAD;
		}
	}
	
	@Override
	public void dispose () {
		databaseFB.closeConnection();
		batch.dispose();
		scoreFont.dispose();
		bird.dispose();
		ground.dispose();
		obs.dispose();
		background.dispose();
		flappyBirdLogo.dispose();
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

	public Atlas getScorePic() {
		return scorePic;
	}

	public Atlas getGameOverPic() {
		return gameOverPic;
	}

	public Atlas getPlayPic() {
		return playPic;
	}

	public Atlas getRecordsPic() {
		return recordsPic;
	}

	public Atlas getTap_tap() {
		return tap_tap;
	}

	public Atlas getGetReady() {
		return getReady;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}
}
