package com.flappybird.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.flappybird.View.MyGDXGame;

public class Ground implements Drawable {
    private static final int GROUND_WIDTH = 480;
    private static final int GROUND_HEIGHT = 167;

    private TextureRegion atlasRegion;
    private TextureAtlas atlas;
    private Vector2 pos1;
    private Vector2 pos2;
    private Vector2 wh;
    private int speed;

    public Ground() {
        atlas = new TextureAtlas(Gdx.files.internal("atl/ground.atlas"));
        atlasRegion = atlas.findRegion("0001");
        pos1 = new Vector2(0,0);
        pos2 = new Vector2(GROUND_WIDTH,0);
        wh = new Vector2(GROUND_WIDTH, GROUND_HEIGHT);
        speed = 4;
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
        if (pos1.x <= -GROUND_WIDTH) {
            pos1.x = 0;
        }
        pos2.x -= speed;
        if (pos2.x <= 0) {
            pos2.x = GROUND_WIDTH;
        }
    }

    public void dispose() {
        atlas.dispose();
    }

    public static int getHeight() {
        return GROUND_HEIGHT;
    }
}
