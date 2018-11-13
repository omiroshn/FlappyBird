package com.flappybird.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.flappybird.View.MyGDXGame;

public class Bird implements Drawable {

    private final static int BIRD_WIDTH = 68;
    private final static int BIRD_HEIGTH = 48;
    private final static float START_X = Gdx.graphics.getWidth() / 2 - (BIRD_WIDTH / 2) - 100;
    private final static float START_Y = Gdx.graphics.getHeight() / 2 + 100;

    private Animation<TextureAtlas.AtlasRegion> birdAnimation;
    private TextureAtlas birdAnimAtlas;
    private float elapsedTime;
    private Vector2 pos;
    private Vector2 wh;

    private float vy;
    private float gravity;

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
//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//            vy = 10;
//            //todo delete this after dead sign
//            if (pos.y <= Ground.getHeight())
//                pos.y += vy;
//        }
        if (pos.y + vy > Ground.getHeight()) {
            vy += gravity;
            pos.y += vy;
        } else {
            pos.y = Ground.getHeight();
        }
    }

    public void checkIntersection(Obstacles.Wall[] walls) {
//        for (Obstacles.Wall wall: walls) {
//
//        }
    }

    public void Fly() {
        vy = 10;
    }

    public void dispose() {
        birdAnimAtlas.dispose();
    }

    public static int getWidth() {
        return BIRD_WIDTH;
    }

    public static int getHeight() {
        return BIRD_HEIGTH;
    }

    public static int getPosX() {
        return BIRD_WIDTH;
    }

    public static int getPosY() {
        return BIRD_HEIGTH;
    }

    public Vector2 getPos() {
        return pos;
    }
}
