package com.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Background {

    private final int BG_WIDTH = 480;
    private final int BG_HEIGHT = 800;

    private TextureRegion atlasRegion;
    private TextureAtlas atlas;
    private Vector2 pos1;
    private Vector2 pos2;
    private Vector2 wh;
    private int speed;

    Background(boolean randomMode) {
        atlas = new TextureAtlas(Gdx.files.internal("background.atlas"));
        String night = randomMode ? "0001" : "0002";
        atlasRegion = atlas.findRegion(night);
        pos1 = new Vector2(0,0);
        pos2 = new Vector2(BG_WIDTH,0);
        wh = new Vector2(BG_WIDTH, BG_HEIGHT);
        speed = 1;
    }

    public void draw() {
        MyGDXGame.batch.draw(
                atlasRegion,
                pos1.x,
                pos1.y,
                wh.x,
                wh.y
        );
        MyGDXGame.batch.draw(
                atlasRegion,
                pos2.x,
                pos2.y,
                wh.x,
                wh.y
        );
    }

    public void update() {
        pos1.x -= speed;
        if (pos1.x <= -BG_WIDTH) {
            pos1.x = 0;
        }
        pos2.x -= speed;
        if (pos2.x <= 0) {
            pos2.x = BG_WIDTH;
        }
    }

}
