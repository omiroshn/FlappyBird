package com.flappybird.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.flappybird.View.MyGDXGame;

public class Font {

    private BitmapFont font;

    public Font() {
        font = new BitmapFont(Gdx.files.internal("flappyBird.fnt"));
        font.setColor(Color.WHITE);
    }

    public void draw(String msg, int x, int y, float scale) {
        font.getData().setScale(scale);
        font.draw(MyGDXGame.batch, msg, x, y);
    }

    public void dispose() {
        font.dispose();
    }

}
