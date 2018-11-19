package com.flappybird.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.flappybird.Model.GameMode;
import com.flappybird.View.MyGDXGame;

public class Controller {

    private MyGDXGame game;

    public Controller(MyGDXGame game) {
        this.game = game;
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
        if (MyGDXGame.getGameMode() == GameMode.FIRSTVIEW) {
            if (Gdx.input.isTouched()) {
                if (game.getPlayPic().isClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.getBird().setBirdPos();
                    game.setGameMode(GameMode.MENU);
                } else if (game.getRecordsPic().isClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.setGameMode(GameMode.RECORDS);
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && MyGDXGame.getGameMode() == GameMode.GAME) {
            game.getBird().Fly();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && MyGDXGame.getGameMode() == GameMode.MENU) {
            game.setGameMode(GameMode.GAME);
            game.getBird().Fly();
        }
        if (MyGDXGame.getGameMode() == GameMode.DEAD) {
            if (Gdx.input.isTouched()) {
                if (game.getPlayPic().isClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.getBird().default_();
                    game.getObs().default_();
                    game.setGameMode(GameMode.MENU);
                    game.setScore(0);
                } else if (game.getRecordsPic().isClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.setGameMode(GameMode.RECORDS);
                }
            }
        }
        if (MyGDXGame.getGameMode() == GameMode.RECORDS) {
            if (Gdx.input.isTouched()) {
                System.out.println("RECORDS MODE");
                game.setGameMode(GameMode.DEAD);
            }
        }
    }
}
