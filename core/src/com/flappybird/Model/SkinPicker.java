package com.flappybird.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class SkinPicker {

    private Button orange;
    private Button blue;
    private Button red;

    class Button {

        private final int WIDTH = 51;
        private final int HEIGHT = 36;

        private Vector2 pos;
        private Vector2 wh;

        Button(int x, int y) {
            pos = new Vector2(x, y);
            wh = new Vector2(WIDTH, HEIGHT);
        }
    }

    public SkinPicker() {
        orange = new Button((Gdx.graphics.getWidth() - 51) / 2 - 98, (Gdx.graphics.getHeight() - 36) / 2);
        blue = new Button((Gdx.graphics.getWidth() - 51) / 2 + 4, (Gdx.graphics.getHeight() - 36) / 2);
        red = new Button((Gdx.graphics.getWidth() - 51) / 2 + 103, (Gdx.graphics.getHeight() - 36) / 2);
    }

    public boolean isOrangeClicked(float x, float y) {
        return (x >= orange.pos.x && x <= orange.pos.x + orange.wh.x
                && y >= orange.pos.y && y <= orange.pos.y + orange.wh.y);
    }

    public boolean isBlueClicked(float x, float y) {
        return (x >= blue.pos.x && x <= blue.pos.x + blue.wh.x
                && y >= blue.pos.y && y <= blue.pos.y + blue.wh.y);
    }

    public boolean isRedClicked(float x, float y) {
        return (x >= red.pos.x && x <= red.pos.x + red.wh.x
                && y >= red.pos.y && y <= red.pos.y + red.wh.y);
    }
}
