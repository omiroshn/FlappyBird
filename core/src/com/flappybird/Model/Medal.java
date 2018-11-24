package com.flappybird.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.flappybird.View.MyGDXGame;

public class Medal {
    private final int WIDTH = 66;
    private final int HEIGHT = 66;

    private TextureRegion atlasRegion1;
    private TextureRegion atlasRegion2;
    private TextureRegion atlasRegion3;
    private TextureRegion atlasRegion4;
    private TextureAtlas atlas;
    private Vector2 pos;
    private Vector2 wh;

    public Medal(int x, int y) {
        atlas = new TextureAtlas(Gdx.files.internal("atl/medals.atlas"));
        atlasRegion1 = atlas.findRegion("0001");
        atlasRegion2 = atlas.findRegion("0002");
        atlasRegion3 = atlas.findRegion("0003");
        atlasRegion4 = atlas.findRegion("0004");
        wh = new Vector2(WIDTH, HEIGHT);
        pos = new Vector2(x, y);
    }

    public void draw(int score) {
        TextureRegion tempRegion;

        if (score >= 10 && score < 20) {
            tempRegion = atlasRegion1;
        } else if (score >= 20 && score < 30) {
            tempRegion = atlasRegion2;
        } else if (score >= 30 && score < 40) {
            tempRegion = atlasRegion3;
        } else  {
            tempRegion = atlasRegion4;
        }
        MyGDXGame.batch.draw(
                tempRegion,
                pos.x,
                pos.y,
                wh.x,
                wh.y
        );
    }

    public void drawUnlockedMedals(int max) {
        if (max >= 10) {
            MyGDXGame.batch.draw(
                atlasRegion1,
                Gdx.graphics.getWidth() / 2 - 37,
                Gdx.graphics.getHeight() / 2 + 35,
                wh.x,
                wh.y
            );
        }
        if (max >= 20) {
            MyGDXGame.batch.draw(
                atlasRegion2,
                Gdx.graphics.getWidth() / 2 - 37,
                Gdx.graphics.getHeight() / 2 - 35,
                wh.x,
                wh.y
            );
        }
        if (max >= 30) {
            MyGDXGame.batch.draw(
                atlasRegion3,
                Gdx.graphics.getWidth() / 2 + 32,
                Gdx.graphics.getHeight() / 2 + 35,
                wh.x,
                wh.y
            );
        }
        if (max >= 40) {
            MyGDXGame.batch.draw(
                atlasRegion4,
               Gdx.graphics.getWidth() / 2 + 32,
               Gdx.graphics.getHeight() / 2 - 35,
                wh.x,
                wh.y
            );
        }
    }

    public void dispose() {
        atlas.dispose();
    }
}
