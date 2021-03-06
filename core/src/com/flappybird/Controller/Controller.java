package com.flappybird.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.flappybird.Model.GameMode;
import com.flappybird.View.MyGDXGame;

public class Controller implements InputProcessor {

    private MyGDXGame game;

    public Controller(MyGDXGame game) {
        this.game = game;
    }

    //keyboard events
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE)
            Gdx.app.exit();
        else if (MyGDXGame.getGameMode() == GameMode.GAME) {
            if (keycode == Input.Keys.SPACE)
                game.getBird().Fly();
        }
        else if (MyGDXGame.getGameMode() == GameMode.MENU) {
            if (keycode == Input.Keys.SPACE) {
                game.setGameMode(GameMode.GAME);
                game.getBird().Fly();
            }
        } else if (MyGDXGame.getGameMode() == GameMode.DEAD) {
            if (keycode == Input.Keys.ENTER) {
                resetBird();
            }
        }
        if (keycode == Input.Keys.NUM_1) {
            game.getBird().setNewSkin("orange");
        } else if (keycode == Input.Keys.NUM_2) {
            game.getBird().setNewSkin("blue");
        } else if (keycode == Input.Keys.NUM_3) {
            game.getBird().setNewSkin("red");
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    //mouse events
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        switch (MyGDXGame.getGameMode()) {
            case FIRSTVIEW:
                if (game.getPlayPic().isClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.getBird().setBirdPos();
                    game.setGameMode(GameMode.MENU);
                } else if (game.getRecordsPic().isClicked(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.setGameMode(GameMode.MENU_RECORDS);
                }
                break;
            case MENU:
                if (game.getPause().isClicked(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.setGameMode(GameMode.SKINS);
                } else {
                    game.setGameMode(GameMode.GAME);
                    game.getBird().Fly();
                }
                break;
            case MENU_RECORDS:
                if (game.getPlayPic().isClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.setGameMode(GameMode.FIRSTVIEW);
                }
                break;
            case DEAD:
                if (game.getPlayPic().isClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    resetBird();
                } else if (game.getRecordsPic().isClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.setGameMode(GameMode.RECORDS);
                }
                break;
            case RECORDS:
                if (game.getPlayPic().isClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.setGameMode(GameMode.DEAD);
                }
                break;
            case SKINS:
                if (game.getResume().isClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    resetBird();
                }
                if (game.getSkinPickerClass().isOrangeClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.getBird().setNewSkin("orange");
                } else if (game.getSkinPickerClass().isBlueClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.getBird().setNewSkin("blue");
                } else if (game.getSkinPickerClass().isRedClicked(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    game.getBird().setNewSkin("red");
                }
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (MyGDXGame.getGameMode() == GameMode.GAME) {
            game.getBird().Fly();
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private void resetBird() {
        game.getBird().default_();
        game.getObs().default_();
        game.setGameMode(GameMode.MENU);
        game.setScore(0);
    }
}
