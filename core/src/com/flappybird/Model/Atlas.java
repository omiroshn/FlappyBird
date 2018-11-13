package com.flappybird.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.flappybird.View.MyGDXGame;

public class Atlas {
    private TextureRegion atlasRegion;
    private TextureAtlas atlas;
    private Vector2 pos;
    private Vector2 wh;

    public Atlas(String path, int width, int height, int x, int y) {
        atlas = new TextureAtlas(Gdx.files.internal(path));
        atlasRegion = atlas.findRegion("0001");
        pos = new Vector2(x, y);
        wh = new Vector2(width, height);
    }

    public void draw() {
        MyGDXGame.batch.draw(
                atlasRegion,
                pos.x,
                pos.y,
                wh.x,
                wh.y
        );
    }

    public boolean isClicked(float x, float y) {
//        if (
//                x >= pos.x && x <= pos.y &&
//                y <= Gdx
//                )

        return true;
    }

    public void dispose() {
        atlas.dispose();
    }
}
