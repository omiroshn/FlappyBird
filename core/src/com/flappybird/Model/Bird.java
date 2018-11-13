package com.flappybird.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.flappybird.View.MyGDXGame;

public class Bird implements Drawable {

    private final int BIRD_WIDTH = 68;
    private final int BIRD_HEIGTH = 48;
    private final float START_X = Gdx.graphics.getWidth() / 2 - (BIRD_WIDTH / 2) - 100;
    private final float START_Y = Gdx.graphics.getHeight() / 2 + 100;

    private Animation<TextureAtlas.AtlasRegion> birdAnimation;
    private TextureAtlas birdAnimAtlas;
    private float elapsedTime;
    private Vector2 pos;
    private Vector2 wh;

    private float vy;
    private float gravity;
    private boolean tap;

    public Bird() {
        birdAnimAtlas = new TextureAtlas(Gdx.files.internal("bird.atlas"));
        birdAnimation = new Animation<TextureAtlas.AtlasRegion>(1/10f, birdAnimAtlas.getRegions());
        pos = new Vector2(START_X, START_Y);
        wh = new Vector2(BIRD_WIDTH, BIRD_HEIGTH);
        vy = 0;
        gravity = -0.5f;
        elapsedTime = 0;

    }

    public void default_() {
        vy = 0;
        pos = new Vector2(START_X, START_Y);
    }

    public void draw() {
        //todo dead bird = duration maxfloat
        birdAnimation.setFrameDuration(1/10f);
        elapsedTime += Gdx.graphics.getDeltaTime();
        MyGDXGame.batch.draw(
                birdAnimation.getKeyFrame(elapsedTime, true),
                pos.x,
                pos.y,
                wh.x,
                wh.y
        );
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            vy = 10;
            //todo delete this after dead sign
            if (pos.y <= Ground.getHeight())
                pos.y += vy;
        }
        if (pos.y + vy > Ground.getHeight()) {
            vy += gravity;
            pos.y += vy;
        } else {
            pos.y = Ground.getHeight();
        }
    }

    public void dispose() {
        birdAnimAtlas.dispose();
    }

    public int getWidth() {
        return BIRD_WIDTH;
    }

    public int getHeight() {
        return BIRD_HEIGTH;
    }
}
