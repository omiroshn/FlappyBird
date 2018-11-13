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
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && MyGDXGame.getGameMode() == GameMode.GAME) {
            game.getBird().Fly();
        }
    }
}
