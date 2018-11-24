package com.flappybird.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.flappybird.View.MyGDXGame;

import java.util.Objects;


public class Bird implements Drawable {

    private MyGDXGame game;

    private final static int BIRD_WIDTH = 68;
    private final static int BIRD_HEIGHT = 48;
    private final float HALFX = Gdx.graphics.getWidth() / 2 - (BIRD_WIDTH / 2);
    private final float HALFY = Gdx.graphics.getHeight() / 2 - (BIRD_HEIGHT / 2) + 40;
    private final static float START_X = Gdx.graphics.getWidth() / 2 - (BIRD_WIDTH / 2) - 100;
    private final static float START_Y = Gdx.graphics.getHeight() / 2 + 50;

    private Animation<TextureAtlas.AtlasRegion> birdAnimation;
    private TextureAtlas birdAnimAtlas;
    private float elapsedTime;
    private Vector2 pos;

    private float vy;
    private float gravity;
    private boolean falling;
    private float rotation;
    private Circle boundingCircle;

    public Bird(MyGDXGame game) {
        this.game = game;
        birdAnimAtlas = new TextureAtlas(Gdx.files.internal("atl/bird.atlas"));
        birdAnimation = new Animation<TextureAtlas.AtlasRegion>(1/10f, birdAnimAtlas.getRegions());
        pos = new Vector2(HALFX, HALFY);
        vy = 0;
        gravity = -0.5f;
        elapsedTime = 0;
        falling = true;
        rotation = 0;
        boundingCircle = new Circle();
        updateCircle();
    }

    public void default_() {
        vy = 0;
        rotation = 0;
        pos = new Vector2(START_X, START_Y);
    }

    public void setNewSkin(String skinName) {
        if (Objects.equals(skinName, "orange")) {
            birdAnimAtlas = new TextureAtlas(Gdx.files.internal("atl/bird.atlas"));
        } else if (Objects.equals(skinName, "blue")) {
            birdAnimAtlas = new TextureAtlas(Gdx.files.internal("atl/birdblue.atlas"));
        } else if (Objects.equals(skinName, "red")) {
            birdAnimAtlas = new TextureAtlas(Gdx.files.internal("atl/birdred.atlas"));
        }
        birdAnimation = new Animation<TextureAtlas.AtlasRegion>(1/10f, birdAnimAtlas.getRegions());
    }

    public void draw() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        MyGDXGame.batch.draw(
                birdAnimation.getKeyFrame(elapsedTime, true),
                pos.x,
                pos.y,
                BIRD_WIDTH,
                BIRD_HEIGHT,
                BIRD_WIDTH,
                BIRD_HEIGHT,
                1,
                1,
                -rotation
        );
    }

    public void update() {
        if (MyGDXGame.getGameMode() == GameMode.FIRSTVIEW) {
            turnOnAnimation();
            if (falling) {
                pos.y -= 1.5;
            } else {
                pos.y += 1.5;
            }
            if (pos.y == HALFY - 19.5) {
                falling = false;
            } else if (pos.y == HALFY + 21) {
                falling = true;
            }
        } else if (MyGDXGame.getGameMode() == GameMode.MENU) {
            turnOnAnimation();
            if (falling) {
                pos.y -= 1.5;
            } else {
                pos.y += 1.5;
            }
            if (pos.y == START_Y - 19.5) {
                falling = false;
            } else if (pos.y == START_Y + 21) {
                falling = true;
            }
        } else if (MyGDXGame.getGameMode() == GameMode.GAME) {
            if (pos.y + vy > Ground.getHeight()) {
                vy += gravity;
                pos.y += vy;
            } else {
                game.setGameMode(GameMode.DEAD);
            }
        } else if (MyGDXGame.getGameMode() == GameMode.DEAD || MyGDXGame.getGameMode() == GameMode.RECORDS) {
            turnOffAnimation();
            if (pos.y + vy > Ground.getHeight()) {
                vy += gravity;
                pos.y += vy;
                if ((rotation += 2) > 90)
                    rotation = 90;
            } else {
                float percent = rotation * 100 / 90;
                float off = percent * BIRD_HEIGHT / 100;
                pos.y = Ground.getHeight() - off;
            }
        } else if (MyGDXGame.getGameMode() == GameMode.SKINS) {
            turnOffAnimation();
            pos.x = (Gdx.graphics.getWidth() - BIRD_WIDTH) / 2;
            pos.y = (Gdx.graphics.getHeight() - BIRD_HEIGHT) / 2 - 150;
        }
        updateCircle();
    }

    public void updateCircle() {
        boundingCircle.set(
                pos.x + (BIRD_WIDTH / 2),
                pos.y + (BIRD_HEIGHT / 2),
                25.0f
        );
    }

    private void turnOffAnimation() {
        birdAnimation.setFrameDuration(100000);
    }

    private void turnOnAnimation() {
        birdAnimation.setFrameDuration(1 / 10f);
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
        return BIRD_HEIGHT;
    }

    public void setBirdPos() {
        pos.x = START_X;
        pos.y = START_Y;
        updateCircle();
    }

    public boolean isFalling() {
        return vy > 110;
    }

    public boolean sholdntFlap() {
        return vy > 70;
    }

    public Circle getCircle() {
        return boundingCircle;
    }

    public Vector2 getPos() {
        return pos;
    }
}
